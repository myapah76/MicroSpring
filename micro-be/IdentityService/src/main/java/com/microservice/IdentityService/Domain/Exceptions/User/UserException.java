package com.microservice.IdentityService.Domain.Exceptions.User;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final String code;

    public UserException(String code,String message) {
        super(message);
        this.code = code;
    }
}
