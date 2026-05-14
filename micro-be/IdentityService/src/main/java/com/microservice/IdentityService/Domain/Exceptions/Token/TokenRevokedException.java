package com.microservice.IdentityService.Domain.Exceptions.Token;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class TokenRevokedException extends TokenException {
    public TokenRevokedException(String message) {
        super(ErrorCode.TOKEN_REVOKED, message);
    }
}
