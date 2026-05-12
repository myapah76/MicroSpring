package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.CommonCode;

public class WrongPasswordException extends AuthException{
    public WrongPasswordException(String message) {
        super(CommonCode.Email_Not_Found, message);
    }
}
