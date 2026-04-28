package com.microservice.IdentityService.Application.Exceptions.Token;

import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;

public class InvalidTokenException extends TokenException {
    public InvalidTokenException(String message) {
        super(CommonCode.TOKEN_INVALID, message);
    }
}