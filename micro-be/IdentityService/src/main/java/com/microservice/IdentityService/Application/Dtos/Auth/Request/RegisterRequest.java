package com.microservice.IdentityService.Application.Dtos.Auth.Request;

public record RegisterRequest(
        String email,
        String username,
        String password,
        String firstName,
        String lastName,
        Integer gender
) {}