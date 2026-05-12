package com.microservice.NotificationService.Application.EvenHanlder;

import com.microservice.Abstractions.IntegrationEventHandler;
import com.microservice.NotificationService.Application.Abstractions.Cache.RedisIdempotencyService;
import com.microservice.NotificationService.Application.Abstractions.Cache.RedisOtpService;
import com.microservice.NotificationService.Application.Abstractions.Cache.RedisRateLimitService;
import com.microservice.Events.OtpNotificationEvent;
import com.microservice.NotificationService.Application.Abstractions.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpNotificationHandler implements IntegrationEventHandler<OtpNotificationEvent> { // Implement your interface{

    private final RedisOtpService otpService;
    private final RedisRateLimitService rateLimitService;
    private final RedisIdempotencyService idempotencyService;
    private final EmailSender emailSender;

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