package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class WrongPasswordException extends AuthException{
    public WrongPasswordException(String message) {
        super(ErrorCode.Email_Not_Found, message);
    }
}
