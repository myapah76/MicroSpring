package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class EmailNotFoundException extends AuthException {
    public EmailNotFoundException(String message) {
        super(ErrorCode.Email_Not_Found, message);
    }
}
