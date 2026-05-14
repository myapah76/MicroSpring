package com.microservice.IdentityService.Application.Services;

import com.microservice.IdentityService.Application.Dtos.Role.Request.CommonRoleRequest;
import com.microservice.IdentityService.Application.Dtos.Role.Request.CreateRoleRequest;
import com.microservice.IdentityService.Application.Dtos.Role.Response.RoleResponse;
import com.microservice.IdentityService.Application.Mapper.RoleProfile;
import com.microservice.IdentityService.Application.Abstrations.Repositories.RoleRepository;
import com.microservice.IdentityService.Domain.Entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService  implements com.microservice.IdentityService.Application.Abstrations.Service.RoleService {

    private final RoleRepository roleRepository;
    private final RoleProfile roleProfile;

    @Override
    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.findByName(request.getCommonRoleRequest().getName()).isPresent()) {
            throw new RuntimeException("Role name already exists");
        }
        Role role = roleProfile.fromCreateRequest(request);
        roleRepository.save(role);

        return roleProfile.mapToResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleProfile::mapToResponse)
                .toList();
    }

    @Override
    public RoleResponse getById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return roleProfile.mapToResponse(role);
    }

    @Override
    public RoleResponse getByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return roleProfile.mapToResponse(role);
    }

    @Override
    public RoleResponse update(UUID id, CommonRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roleProfile.update(role, request);
        roleRepository.save(role);
        return roleProfile.mapToResponse(role);
    }

    @Override
    public void deleteById(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }
}