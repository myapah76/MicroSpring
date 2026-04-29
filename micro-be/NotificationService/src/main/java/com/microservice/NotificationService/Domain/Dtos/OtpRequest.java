package com.microservice.NotificationService.Domain.Dtos;

public record OtpRequest(
        String email,
        String otp
) {}