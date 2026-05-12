package com.microservice.IdentityService.Domain.Exceptions.Token;

import com.microservice.IdentityService.Domain.Common.CommonCode;

public class InvalidTokenException extends TokenException {
    public InvalidTokenException(String message) {
        super(CommonCode.TOKEN_INVALID, message);
    }
}