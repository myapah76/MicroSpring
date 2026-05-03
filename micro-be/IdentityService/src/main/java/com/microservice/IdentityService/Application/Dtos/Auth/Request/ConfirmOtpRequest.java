package com.microservice.IdentityService.Application.Dtos.Auth.Request;

public record ConfirmOtpRequest(
        String email,
        String otp
) {}