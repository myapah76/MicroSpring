package com.microservice.IdentityService.Application.Mapper;

import com.microservice.IdentityService.Application.Dtos.User.Request.UserCommonRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Domain.Entities.Role;
import com.microservice.IdentityService.Domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProfile {
    private final RoleProfile roleProfile;

    public UserResponse toResponse(User user) {
        if (user == null) return null;

        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setEmail(user.getEmail());
        res.setUsername(user.getUsername());
        res.setPhone(user.getPhone());
        res.setAddress(user.getAddress());
        res.setGender(user.getGender());
        res.setDateOfBirth(user.getDateOfBirth());
        res.setIsBlocked(user.getIsBlocked());
        res.setAvatarUrl(user.getAvatarUrl());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());
        if (user.getRole() != null) {
            res.setRoleResponse(roleProfile.mapRole(user.getRole()));
        }
        return res;
    }
    public void update(User user, UserCommonRequest request, Role role) {
        if (user == null || request == null) return;

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }

        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        if (request.getAvatarPublicId() != null) {
            user.setAvatarPublicId(request.getAvatarPublicId());
        }

        if (role != null) {
            user.setRole(role);
        }
    }

    // Optional helper if you want to convert roleId inside mapper
    public UUID mapRoleId(String roleId) {
        return roleId != null ? UUID.fromString(roleId) : null;
    }
}
