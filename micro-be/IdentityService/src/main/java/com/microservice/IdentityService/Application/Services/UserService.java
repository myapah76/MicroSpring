package com.microservice.IdentityService.Application.Services;


import com.microservice.IdentityService.Application.Dtos.User.Request.ChangePasswordRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.CreateUserRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.UserCommonRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Domain.Exceptions.Auth.WrongPasswordException;
import com.microservice.IdentityService.Domain.Common.ErrorCode;
import com.microservice.IdentityService.Application.Mapper.UserProfile;
import com.microservice.IdentityService.Application.Abstrations.Repositories.RoleRepository;
import com.microservice.IdentityService.Application.Abstrations.Repositories.UserRepository;
import com.microservice.IdentityService.Domain.Entities.Role;
import com.microservice.IdentityService.Domain.Entities.User;
import com.microservice.IdentityService.Domain.Exceptions.User.UserExistException;
import com.microservice.IdentityService.Domain.Exceptions.User.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements com.microservice.IdentityService.Application.Abstrations.Service.UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserProfile userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        UserCommonRequest userCommonRequest = request.getUserCommonRequest();
        // 1. Check email exist
        if (userRepository.findByEmail(userCommonRequest.getEmail()).isPresent()){
            throw new UserExistException(ErrorCode.Email_Already_Registered);
        }

        User user = userMapper.fromCreateRequest(request);
        if (userCommonRequest.getRoleId() != null) {
            Role role = roleRepository.findById(UUID.fromString(userCommonRequest.getRoleId()))
                    .orElseThrow(() -> new RuntimeException(ErrorCode.Role_Not_Found));
            user.setRole(role);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
    @Override
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.User_Not_Found));

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
    @Transactional
    public UserResponse update(UserCommonRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.User_Not_Found));
        Role role = null;
        if (request.getRoleId() != null) {
            role = roleRepository.findById(UUID.fromString(request.getRoleId()))
                    .orElseThrow(() -> new RuntimeException(ErrorCode.Role_Not_Found));
        }
        userMapper.update(user, request, role);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse changePassword(ChangePasswordRequest request){
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.User_Not_Found));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new WrongPasswordException(ErrorCode.Wrong_Password);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(ErrorCode.User_Not_Found);
        }
        userRepository.deleteById(id);
    }
}