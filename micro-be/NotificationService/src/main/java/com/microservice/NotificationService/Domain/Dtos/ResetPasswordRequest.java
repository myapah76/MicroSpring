package com.microservice.NotificationService.Domain.Dtos;

public record ResetPasswordRequest(
        String email,
        String link
) {}