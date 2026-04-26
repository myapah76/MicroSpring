package com.microservice.IdentityService.Application.Services;

import com.microservice.IdentityService.Application.Dtos.User.CustomUserDetails;
import com.microservice.IdentityService.Application.Dtos.User.Request.LoginRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.RefreshRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.AuthResponse;
import com.microservice.IdentityService.Application.Mapper.UserProfile;
import com.microservice.IdentityService.Application.Persistences.Cache.RedisTokenService;
import com.microservice.IdentityService.Domain.Entities.RefreshToken;
import com.microservice.IdentityService.Domain.Entities.User;
import com.microservice.IdentityService.Application.Persistences.Repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RedisTokenService redisTokenService;
    private final UserProfile userMapper;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        UserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                userMapper.toResponse(user),
                accessToken,
                refreshToken
        );
    }

    public AuthResponse refresh(RefreshRequest request) {
        RefreshToken token = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        UserDetails userDetails = new CustomUserDetails(token.getUser());
        String newAccessToken = jwtService.generateToken(userDetails);
        User user = token.getUser();
        return new AuthResponse(
                userMapper.toResponse(user),
                newAccessToken,
                request.getRefreshToken()
        );
    }

    public void logout(String accessToken) {

        String jti = jwtService.extractJwtId(accessToken);
        long ttl = getRemainingTime(accessToken);
        redisTokenService.blacklistToken(jti, ttl);

    }

    public long getRemainingTime(String token) {
        return jwtService.extractExpiration(token).getTime() - System.currentTimeMillis();
    }
}