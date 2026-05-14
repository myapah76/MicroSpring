package com.microservice.IdentityService.Domain.Exceptions.Auth;

import com.microservice.IdentityService.Domain.Common.ErrorCode;

public class OtpExpiredException extends AuthException {
    public OtpExpiredException(String message) {
        super(ErrorCode.Otp_Expired, message);
    }
}
