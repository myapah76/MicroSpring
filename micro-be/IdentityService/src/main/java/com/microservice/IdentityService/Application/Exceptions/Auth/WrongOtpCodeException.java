package com.microservice.IdentityService.Application.Exceptions.Auth;

import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;

public class WrongOtpCodeException extends AuthException {
    public WrongOtpCodeException(String message) {
        super(CommonCode.Wrong_Otp_Code, message);
    }
}
