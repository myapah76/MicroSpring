package com.microservice.NotificationService.Application.Dtos;

public record OtpRequest(
        String email,
        String otp
) {}