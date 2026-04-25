package com.microservice.IdentityService.Application.Dtos.User.Respone;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {

    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String username;

    private String phone;
    private String address;
    private Integer gender;

    private OffsetDateTime dateOfBirth;

    private Boolean isBlocked;

    private String avatarUrl;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private String roleName; // hoặc roleSlug tùy bạn
}