package com.microservice.IdentityService.Application.Exceptions.Token;

import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;

public class TokenRevokedException extends TokenException {
    public TokenRevokedException(String message) {
        super(CommonCode.TOKEN_REVOKED, message);
    }
}
