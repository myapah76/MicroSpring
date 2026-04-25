package com.microservice.IdentityService.Application.Services;

import com.microservice.IdentityService.Application.Persistences.Repositories.IRefreshTokenRepository;
import com.microservice.IdentityService.Domain.Entities.RefreshToken;
import com.microservice.IdentityService.Domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final IRefreshTokenRepository IRefreshTokenRepository;
    private final JwtService jwtService;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenDays;

    public String createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();

        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiresAt(OffsetDateTime.now().plusDays(refreshTokenDays));
        token.setIsRevoked(false);

        IRefreshTokenRepository.save(token);

        return token.getToken();
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = IRefreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getIsRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    public String refreshAccessToken(String refreshTokenStr) {

        RefreshToken refreshToken = validateRefreshToken(refreshTokenStr);

        User user = refreshToken.getUser();

        return jwtService.generateToken((UserDetails) user);
    }

    public void revokeToken(String token) {
        RefreshToken refreshToken = IRefreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        refreshToken.setIsRevoked(true);
        IRefreshTokenRepository.save(refreshToken);
    }
}