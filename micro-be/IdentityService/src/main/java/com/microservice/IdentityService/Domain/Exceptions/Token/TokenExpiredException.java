package com.microservice.IdentityService.Domain.Exceptions.Token;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class TokenExpiredException extends TokenException {
    public TokenExpiredException(String message) {
        super(ErrorCode.TOKEN_EXPIRED, message);
    }
}