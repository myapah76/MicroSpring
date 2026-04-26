package com.microservice.IdentityService.Application.Services;


import com.microservice.IdentityService.Application.Dtos.User.Request.CreateUserRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.UserCommonRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Application.Mapper.UserProfile;
import com.microservice.IdentityService.Application.Persistences.Repositories.IRoleRepository;
import com.microservice.IdentityService.Application.Persistences.Repositories.IUserRepository;
import com.microservice.IdentityService.Domain.Entities.Role;
import com.microservice.IdentityService.Domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfile userMapper;


    public UserResponse createUser(CreateUserRequest request) {

        UserCommonRequest userCommonRequest = request.getUserCommonRequest();
        // 1. Check email exist
        if (userRepository.findByEmail(userCommonRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFirstName(userCommonRequest.getFirstName());
        user.setLastName(userCommonRequest.getLastName());
        user.setEmail(userCommonRequest.getEmail());
        user.setUsername(userCommonRequest.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(userCommonRequest.getPhone());
        user.setAddress(userCommonRequest.getAddress());
        user.setGender(userCommonRequest.getGender());
        user.setDateOfBirth(userCommonRequest.getDateOfBirth());
        user.setAvatarUrl(userCommonRequest.getAvatarUrl());
        user.setAvatarPublicId(userCommonRequest.getAvatarPublicId());

        if (userCommonRequest.getRoleId() != null) {
            Role role = new Role();
            role.setId(UUID.fromString(userCommonRequest.getRoleId()));
            user.setRole(role);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toResponse(user);
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

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

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}