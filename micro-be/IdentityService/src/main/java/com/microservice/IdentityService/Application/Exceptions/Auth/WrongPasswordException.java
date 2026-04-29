package com.microservice.IdentityService.Application.Exceptions.Auth;

import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;

public class WrongPasswordException extends AuthException{
    public WrongPasswordException(String message) {
        super(CommonCode.Email_Not_Found, message);
    }
}
