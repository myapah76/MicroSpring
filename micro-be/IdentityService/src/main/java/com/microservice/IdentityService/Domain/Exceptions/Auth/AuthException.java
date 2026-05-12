package com.microservice.IdentityService.Domain.Exceptions.Auth;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    private final String code;
    public AuthException(String code, String message) {
        super(message);
        this.code = code;
    }
}
