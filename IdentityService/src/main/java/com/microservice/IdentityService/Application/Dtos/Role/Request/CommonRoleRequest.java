package com.microservice.IdentityService.Application.Dtos.Role.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRoleRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
}
