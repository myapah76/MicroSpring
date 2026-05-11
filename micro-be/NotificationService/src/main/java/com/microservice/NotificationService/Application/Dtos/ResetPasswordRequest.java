package com.microservice.NotificationService.Application.Dtos;

public record ResetPasswordRequest(
        String email,
        String link
) {}