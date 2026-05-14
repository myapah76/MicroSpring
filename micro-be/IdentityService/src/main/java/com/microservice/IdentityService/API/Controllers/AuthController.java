package com.microservice.IdentityService.API.Controllers;

import com.microservice.IdentityService.Application.Abstrations.Service.AuthService;
import com.microservice.IdentityService.Application.Dtos.Auth.Request.ConfirmOtpRequest;
import com.microservice.IdentityService.Application.Dtos.Auth.Request.RegisterRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.LoginRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.RefreshRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.AuthResponse;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Domain.Exceptions.Token.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/confirm-otp")
    public UserResponse confirmOtp(@RequestBody ConfirmOtpRequest request){
        return authService.confirmOtp(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new TokenExpiredException("Missing token");
        }

        String token = header.substring(7);
        authService.logout(token);

        return ResponseEntity.ok().build();
    }
}