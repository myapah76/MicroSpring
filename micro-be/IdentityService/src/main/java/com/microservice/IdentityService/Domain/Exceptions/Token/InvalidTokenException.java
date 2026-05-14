package com.microservice.IdentityService.Domain.Exceptions.Token;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class InvalidTokenException extends TokenException {
    public InvalidTokenException(String message) {
        super(ErrorCode.TOKEN_INVALID, message);
    }
}