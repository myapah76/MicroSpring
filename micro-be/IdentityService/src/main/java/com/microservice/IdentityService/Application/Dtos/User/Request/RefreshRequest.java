package com.microservice.IdentityService.Application.Dtos.User.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    private String refreshToken;
}