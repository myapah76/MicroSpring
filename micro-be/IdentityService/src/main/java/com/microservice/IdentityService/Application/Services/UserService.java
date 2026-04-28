package com.microservice.IdentityService.Application.Services;


import com.microservice.IdentityService.Application.Abstrations.IUserService;
import com.microservice.IdentityService.Application.Dtos.User.Request.CreateUserRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.UserCommonRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Application.Mapper.UserProfile;
import com.microservice.IdentityService.Application.Persistences.Repositories.IRoleRepository;
import com.microservice.IdentityService.Application.Persistences.Repositories.IUserRepository;
import com.microservice.IdentityService.Domain.Entities.Role;
import com.microservice.IdentityService.Domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final UserProfile userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        UserCommonRequest userCommonRequest = request.getUserCommonRequest();
        // 1. Check email exist
        if (userRepository.findByEmail(userCommonRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.fromCreateRequest(request);
        if (userCommonRequest.getRoleId() != null) {
            Role role = roleRepository.findById(UUID.fromString(userCommonRequest.getRoleId()))
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
    @Override
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toResponse(user);
    }
    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }
    @Override
    public UserResponse update(UserCommonRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = null;
        if (request.getRoleId() != null) {
            role = roleRepository.findById(UUID.fromString(request.getRoleId()))
                    .orElseThrow(() -> new RuntimeException("Role not found"));
        }
        userMapper.update(user, request, role);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }
    @Override
    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}