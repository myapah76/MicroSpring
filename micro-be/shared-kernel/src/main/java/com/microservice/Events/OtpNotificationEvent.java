package com.microservice.Events;

public record OtpNotificationEvent(
        String email,
        String otp,
        String type
) {}