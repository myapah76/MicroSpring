package com.microservice.IdentityService.Application.Dtos.Auth;

public record PendingUser(
        String email,
        String username,
        String password,
        String firstName,
        String lastName,
        Integer gender,
        String otp,
        boolean isExpired
) {}