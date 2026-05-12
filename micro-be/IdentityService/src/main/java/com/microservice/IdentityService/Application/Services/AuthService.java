package com.microservice.IdentityService.Application.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.Constants.KafkaTopics;
import com.microservice.Events.OtpNotificationEvent;
import com.microservice.Events.OtpType;
import com.microservice.IdentityService.Application.Abstrations.Cache.IRedisTokenService;
import com.microservice.IdentityService.Application.Abstrations.IAuthService;
import com.microservice.IdentityService.Application.Abstrations.IOutboxService;
import com.microservice.IdentityService.Application.Dtos.Auth.PendingUser;
import com.microservice.IdentityService.Application.Dtos.Auth.Request.ConfirmOtpRequest;
import com.microservice.IdentityService.Application.Dtos.Auth.Request.RegisterRequest;
import com.microservice.IdentityService.Application.Dtos.User.CustomUserDetails;
import com.microservice.IdentityService.Application.Dtos.User.Request.LoginRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.RefreshRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.AuthResponse;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Domain.Exceptions.Auth.EmailNotFoundException;
import com.microservice.IdentityService.Domain.Exceptions.Auth.WrongOtpCodeException;
import com.microservice.IdentityService.Domain.Exceptions.Auth.WrongPasswordException;
import com.microservice.IdentityService.Domain.Common.CommonCode;
import com.microservice.IdentityService.Application.Mapper.UserProfile;
import com.microservice.IdentityService.Application.Persistences.Repositories.IRoleRepository;
import com.microservice.IdentityService.Domain.Entities.RefreshToken;
import com.microservice.IdentityService.Domain.Entities.Role;
import com.microservice.IdentityService.Domain.Entities.User;
import com.microservice.IdentityService.Application.Persistences.Repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RefreshTokenService refreshTokenService;
    private final IRedisTokenService redisTokenService;
    private final IOutboxService outboxService;
    private final UserProfile userMapper;
    private final ObjectMapper objectMapper;
    private final IRoleRepository roleRepository;


    @Override
    public void register(RegisterRequest request) {

        String key = "PENDING_USER:" + request.email();
        // 1. generate OTP
        String otp = generateOtp();
        String hashedOtp = hashOtp(otp);

        if (userRepository.findByEmail(request.email()).isPresent()
                || redisTemplate.hasKey(key)) {
            throw new RuntimeException(CommonCode.Email_Already_Registered);
        }
        // 2. create pending user object (staging in Redis)
        PendingUser pendingUser = new PendingUser(
                request.email(),
                request.username(),
                request.password(),
                request.firstName(),
                request.lastName(),
                request.gender(),
                hashedOtp,
                false
        );
        // 3. save to Redis
        try {
            redisTemplate.opsForValue()
                    .set(key, objectMapper.writeValueAsString(pendingUser), Duration.ofMinutes(5));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 4. create OTP event (Outbox → Kafka)
        OtpNotificationEvent event = new OtpNotificationEvent(
                request.email()+ otp, //Id
                request.email(),
                otp,
                OtpType.REGISTER
        );

        outboxService.add(event, KafkaTopics.OTP_NOTIFICATIONS);
    }

    @Override
    @Transactional
    public UserResponse confirmOtp(ConfirmOtpRequest request) {


        String key = "PENDING_USER:" + request.email();

        String json = (String) redisTemplate.opsForValue().get(key);

        PendingUser pendingUser =
                null;
        try {
            pendingUser = objectMapper.readValue(json, PendingUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (pendingUser == null) {
            throw new RuntimeException(CommonCode.Pending_User_Not_Found);
        }
        if (request.otp() == null ||
                !hashOtp(request.otp()).equals(pendingUser.otp())) {
            throw new WrongOtpCodeException("Invalid OTP");
        }

        User user = new User();
        user.setEmail(pendingUser.email());
        user.setUsername(pendingUser.username());
        user.setPassword(passwordEncoder.encode(pendingUser.password()));
        user.setGender(pendingUser.gender());
        user.setFirstName(pendingUser.firstName() != null ? pendingUser.firstName() : "Unknown");
        user.setLastName(pendingUser.lastName() != null ? pendingUser.lastName() : "Unknown");


        Role role = roleRepository.findByName("Customer")
                .orElseThrow(() -> new RuntimeException(CommonCode.Role_Not_Found));
        user.setRole(role);
        user.setCreatedAt(OffsetDateTime.now());

        userRepository.save(user);
        redisTemplate.delete(key); // simpler
        return userMapper.toResponse(user);
    }

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
    private String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
    public String hashOtp(String otp) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(otp.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new RuntimeException("Failed to hash OTP", e);
        }
    }
}