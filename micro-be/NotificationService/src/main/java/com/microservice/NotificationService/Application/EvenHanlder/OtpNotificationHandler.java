package com.microservice.NotificationService.Application.EvenHanlder;

import com.microservice.NotificationService.Application.Persistences.Cache.RedisIdempotencyService;
import com.microservice.NotificationService.Application.Persistences.Cache.RedisOtpService;
import com.microservice.NotificationService.Application.Persistences.Cache.RedisRateLimitService;
import com.microservice.constants.KafkaTopics;
import com.microservice.Events.OtpNotificationEvent;
import com.microservice.NotificationService.Application.Abstractions.IEmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpNotificationHandler {

    private final RedisOtpService otpService;
    private final RedisRateLimitService rateLimitService;
    private final RedisIdempotencyService idempotencyService;
    private final IEmailSender emailSender;

    @KafkaListener(
            topics = KafkaTopics.OTP_NOTIFICATIONS,
            groupId = "notification-service-group"
    )
    public void handle(OtpNotificationEvent event) {

        String eventId = event.email() + ":" + event.type();

        //IDENTITY CHECK (Kafka deduplication)
        if (idempotencyService.isProcessed(eventId)) {
            return;
        }

        //RATE LIMIT CHECK
        if (!rateLimitService.isAllowed(event.email())) {
            throw new RuntimeException("Too many OTP requests");
        }

        //GENERATE OTP
        String otp = generateOtp();
        otpService.saveOtp(event.email(), otp);

        //SEND EMAIL
        emailSender.sendOtpEmailAsync(event.email(), otp);

        //MARK AS PROCESSED
        idempotencyService.markProcessed(eventId);
    }


    @KafkaListener(
            topics = KafkaTopics.OTP_FORGET_PASSWORD,
            groupId = "notification-service-group"
    )
    public void handleForgetPassword(OtpNotificationEvent event) {
        log.info("Received forget password OTP for email: {}", event.email());
        emailSender.sendPasswordResetEmailAsync(
                event.email(),
                "TEMP_RESET_LINK"
        );
    }

    //Helper functions
    private String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}