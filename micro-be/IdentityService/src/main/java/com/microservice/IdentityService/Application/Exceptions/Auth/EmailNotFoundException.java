package com.microservice.IdentityService.Application.Exceptions.Auth;

import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;

public class EmailNotFoundException extends AuthException {
    public EmailNotFoundException(String message) {
        super(CommonCode.Email_Not_Found, message);
    }
}
