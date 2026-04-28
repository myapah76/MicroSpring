package com.microservice.IdentityService.API.Controllers;

import com.microservice.IdentityService.Application.Abstrations.IAuthService;
import com.microservice.IdentityService.Application.Dtos.User.Request.LoginRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.RefreshRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.AuthResponse;
import com.microservice.IdentityService.Application.Services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }
        String token = header.substring(7);
        authService.logout(token);
    }
}