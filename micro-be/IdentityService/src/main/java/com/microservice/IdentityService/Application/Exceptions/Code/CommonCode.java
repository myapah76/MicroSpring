package com.microservice.IdentityService.Application.Exceptions.Code;

import org.springframework.stereotype.Component;

@Component
public class CommonCode {
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String TOKEN_INVALID = "TOKEN_INVALID";
    public static final String TOKEN_REVOKED = "TOKEN_REVOKED";

    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
}
