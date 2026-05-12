package com.microservice.IdentityService.Domain.Exceptions.Token;

import com.microservice.IdentityService.Domain.Common.CommonCode;

public class TokenExpiredException extends TokenException {
    public TokenExpiredException(String message) {
        super(CommonCode.TOKEN_EXPIRED, message);
    }
}