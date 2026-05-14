package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class WrongOtpCodeException extends AuthException {
    public WrongOtpCodeException(String message) {
        super(ErrorCode.Wrong_Otp_Code, message);
    }
}
