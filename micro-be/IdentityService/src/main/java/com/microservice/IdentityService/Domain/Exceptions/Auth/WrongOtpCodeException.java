package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.CommonCode;

public class WrongOtpCodeException extends AuthException {
    public WrongOtpCodeException(String message) {
        super(CommonCode.Wrong_Otp_Code, message);
    }
}
