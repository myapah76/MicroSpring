package com.microservice.IdentityService.Application.Mapper;

import com.microservice.IdentityService.Application.Dtos.Role.Request.CommonRoleRequest;
import com.microservice.IdentityService.Application.Dtos.Role.Response.RoleResponse;
import com.microservice.IdentityService.Domain.Entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleProfile {
    public RoleResponse mapToResponse(Role role) {
        RoleResponse res = new RoleResponse();
        res.setId(role.getId());
        res.setName(role.getName());
        res.setSlug(role.getSlug());
        res.setDescription(role.getDescription());
        return res;
    }
    public void update(Role role, CommonRoleRequest request) {
        if (role == null || request == null) return;

        if (request.getName() != null) {
            role.setName(request.getName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

    }
}
