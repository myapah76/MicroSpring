package com.microservice.IdentityService.Application.Abstrations.Service;

import com.microservice.IdentityService.Domain.Entities.RefreshToken;
import com.microservice.IdentityService.Domain.Entities.User;

public interface RefreshTokenService {
    String createRefreshToken(User user);
    RefreshToken validateRefreshToken(String token);
    String refreshAccessToken(String refreshToken);
    void revokeToken(String token);
}
