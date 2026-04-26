package com.microservice.IdentityService.Application.Mapper;

import com.microservice.IdentityService.Application.Dtos.Role.Response.RoleResponse;
import com.microservice.IdentityService.Domain.Entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleProfile {
    public RoleResponse mapRole(Role role) {
        RoleResponse roleRes = new RoleResponse();
        roleRes.setId(role.getId());
        roleRes.setName(role.getName());
        roleRes.setSlug(role.getSlug());
        roleRes.setDescription(role.getDescription());
        return roleRes;
    }
}
