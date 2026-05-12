package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.CommonCode;

public class OtpExpiredException extends AuthException {
    public OtpExpiredException(String message) {
        super(CommonCode.Otp_Expired, message);
    }
}
