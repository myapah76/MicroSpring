package com.microservice.IdentityService.Application.Abstrations;

import com.microservice.IdentityService.Application.Dtos.User.Request.LoginRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.RefreshRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.AuthResponse;

public interface IAuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshRequest request);
    void logout(String accessToken);
}
