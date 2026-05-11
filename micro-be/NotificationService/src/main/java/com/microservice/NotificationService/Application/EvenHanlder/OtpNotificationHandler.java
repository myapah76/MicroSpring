package com.microservice.NotificationService.Application.EvenHanlder;

import com.microservice.Abstractions.IIntegrationEventHandler;
import com.microservice.NotificationService.Application.Abstractions.Cache.IRedisIdempotencyService;
import com.microservice.NotificationService.Application.Abstractions.Cache.IRedisOtpService;
import com.microservice.NotificationService.Application.Abstractions.Cache.IRedisRateLimitService;
import com.microservice.Events.OtpNotificationEvent;
import com.microservice.NotificationService.Application.Abstractions.IEmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpNotificationHandler implements IIntegrationEventHandler<OtpNotificationEvent> { // Implement your interface{

    private final IRedisOtpService otpService;
    private final IRedisRateLimitService rateLimitService;
    private final IRedisIdempotencyService idempotencyService;
    private final IEmailSender emailSender;

        @Override
        public void handle(OtpNotificationEvent event) {

        //IDENTITY CHECK
        if (idempotencyService.isProcessed(event.id())) {
            return;
        }

        //RATE LIMIT CHECK
        if (!rateLimitService.isAllowed(event.email())) {
            log.warn("Rate limit hit for email {}", event.email());
            return; // NOT throw
        }

        if(!otpService.saveOtp(event.email(), event.otp())){
            throw new RuntimeException("Fail to save otp code with email: " + event.email());
        }

        //SEND EMAIL
        emailSender.sendOtpEmailAsync(event.email(), event.otp());

        //MARK AS PROCESSED
        idempotencyService.markProcessed(event.id());
    }

    public void handleForgetPassword(OtpNotificationEvent event) {
        log.info("Received forget password OTP for email: {}", event.email());
        emailSender.sendPasswordResetEmailAsync(
                event.email(),
                "TEMP_RESET_LINK"
        );
    }


}