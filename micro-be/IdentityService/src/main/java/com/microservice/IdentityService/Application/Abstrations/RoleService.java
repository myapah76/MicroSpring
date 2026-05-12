package com.microservice.IdentityService.Application.Abstrations;

import com.microservice.IdentityService.Application.Dtos.Role.Request.CommonRoleRequest;
import com.microservice.IdentityService.Application.Dtos.Role.Request.CreateRoleRequest;
import com.microservice.IdentityService.Application.Dtos.Role.Response.RoleResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleResponse createRole(CreateRoleRequest request);
    List<RoleResponse> getAll();
    RoleResponse getById(UUID id);
    RoleResponse getByName(String name);
    RoleResponse update(UUID id, CommonRoleRequest request);
    void deleteById(UUID id);
}
