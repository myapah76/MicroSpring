package com.microservice.IdentityService.Application.Services;

import com.microservice.IdentityService.Application.Dtos.Role.Request.CommonRoleRequest;
import com.microservice.IdentityService.Application.Dtos.Role.Request.CreateRoleRequest;
import com.microservice.IdentityService.Application.Dtos.Role.Response.RoleResponse;
import com.microservice.IdentityService.Application.Mapper.RoleProfile;
import com.microservice.IdentityService.Application.Persistences.Repositories.IRoleRepository;
import com.microservice.IdentityService.Domain.Entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final IRoleRepository roleRepository;
    private final RoleProfile roleProfile;

    public RoleResponse createRole(CreateRoleRequest request) {
        if(roleRepository.findBySlug(request.getSlug()).isPresent()){
            throw new RuntimeException("Role with Slug already exists");
        }
        Role role = new Role();
        role.setSlug(request.getSlug());
        role.setDescription(request.getCommonRoleRequest().getDescription());
        role.setName(request.getCommonRoleRequest().getName());
        roleRepository.save(role);

        return roleProfile.mapToResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleProfile::mapToResponse)
                .toList();
    }

    public RoleResponse getById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return roleProfile.mapToResponse(role);
    }

    public RoleResponse getByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return roleProfile.mapToResponse(role);
    }

    public RoleResponse update(UUID id, CommonRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roleProfile.update(role, request);
        roleRepository.save(role);
        return roleProfile.mapToResponse(role);
    }

    public void deleteById(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }
}