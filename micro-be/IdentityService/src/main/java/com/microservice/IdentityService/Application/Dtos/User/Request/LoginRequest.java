package com.microservice.IdentityService.Application.Dtos.User.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}