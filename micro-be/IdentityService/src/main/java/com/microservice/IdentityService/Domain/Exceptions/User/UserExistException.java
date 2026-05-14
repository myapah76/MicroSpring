package com.microservice.IdentityService.Domain.Exceptions.User;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class UserExistException extends UserException {
    public UserExistException(String message) {
        super(ErrorCode.Email_Already_Registered,message);
    }
}
