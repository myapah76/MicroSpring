package com.microservice.IdentityService.Application.Exceptions.Auth;

import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;

public class OtpExpiredException extends AuthException {
    public OtpExpiredException(String message) {
        super(CommonCode.Otp_Expired, message);
    }
}
