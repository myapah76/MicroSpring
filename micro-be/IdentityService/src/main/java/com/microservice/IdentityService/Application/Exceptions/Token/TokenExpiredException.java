package com.microservice.IdentityService.Application.Exceptions.Token;

import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;

public class TokenExpiredException extends TokenException {
    public TokenExpiredException(String message) {
        super(CommonCode.TOKEN_EXPIRED, message);
    }
}