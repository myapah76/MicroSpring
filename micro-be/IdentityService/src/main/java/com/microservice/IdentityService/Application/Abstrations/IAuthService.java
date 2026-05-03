package com.microservice.IdentityService.Application.Abstrations;

import com.microservice.IdentityService.Application.Dtos.Auth.Request.ConfirmOtpRequest;
import com.microservice.IdentityService.Application.Dtos.Auth.Request.RegisterRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.CreateUserRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.LoginRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.RefreshRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.AuthResponse;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;

public interface IAuthService {
    void register(RegisterRequest request);
    UserResponse confirmOtp(ConfirmOtpRequest request);

    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshRequest request);
    void logout(String accessToken);
}
