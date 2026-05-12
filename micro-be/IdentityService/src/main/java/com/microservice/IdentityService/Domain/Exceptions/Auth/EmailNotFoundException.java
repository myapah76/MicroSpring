package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.CommonCode;

public class EmailNotFoundException extends AuthException {
    public EmailNotFoundException(String message) {
        super(CommonCode.Email_Not_Found, message);
    }
}
