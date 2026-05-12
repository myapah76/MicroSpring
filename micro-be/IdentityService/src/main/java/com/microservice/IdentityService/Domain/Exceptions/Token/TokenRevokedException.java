package com.microservice.IdentityService.Domain.Exceptions.Token;

import com.microservice.IdentityService.Domain.Common.CommonCode;

public class TokenRevokedException extends TokenException {
    public TokenRevokedException(String message) {
        super(CommonCode.TOKEN_REVOKED, message);
    }
}
