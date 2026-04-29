package com.microservice.IdentityService.Application.Services;

import com.microservice.IdentityService.Application.Abstrations.IAuthService;
import com.microservice.IdentityService.Application.Dtos.User.CustomUserDetails;
import com.microservice.IdentityService.Application.Dtos.User.Request.LoginRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.RefreshRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.AuthResponse;
import com.microservice.IdentityService.Application.Exceptions.Auth.EmailNotFoundException;
import com.microservice.IdentityService.Application.Exceptions.Auth.WrongPasswordException;
import com.microservice.IdentityService.Application.Exceptions.Code.CommonCode;
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
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RedisTokenService redisTokenService;
    private final UserProfile userMapper;

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(CommonCode.Email_Not_Found));
        if (user.getIsBlocked()) {
            throw new RuntimeException("User is blocked");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongPasswordException(CommonCode.Wrong_Password);
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
    @Override
    public AuthResponse refresh(RefreshRequest request) {
        RefreshToken token = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        UserDetails userDetails = new CustomUserDetails(token.getUser());
        String newAccessToken = jwtService.generateToken(userDetails);
        refreshTokenService.revokeToken(token.getToken());
        User user = token.getUser();
        return new AuthResponse(
                userMapper.toResponse(user),
                newAccessToken,
                request.getRefreshToken()
        );
    }
    @Override
    public void logout(String accessToken) {

        String jti = jwtService.extractJwtId(accessToken);
        long ttl = getRemainingTime(accessToken);
        redisTokenService.blacklistToken(jti, ttl);

    }

// Function Helper
    public long getRemainingTime(String token) {
        long ttl = jwtService.extractExpiration(token).getTime() - System.currentTimeMillis();
        return Math.max(ttl, 0);
    }
}