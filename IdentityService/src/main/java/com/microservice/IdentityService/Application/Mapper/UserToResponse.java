package com.microservice.IdentityService.Application.Mapper;

import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Domain.Entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserToResponse {
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
            res.setRoleName(user.getRole().getName());
        }
        return res;
    }
}
