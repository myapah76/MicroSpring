package com.microservice.IdentityService.Domain.Exceptions.User;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND,message);
    }
}
