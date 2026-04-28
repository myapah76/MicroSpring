package com.microservice.IdentityService.Application.Abstrations;

import com.microservice.IdentityService.Application.Dtos.User.Request.CreateUserRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.UserCommonRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getById(UUID id);
    List<UserResponse> getAll();
    UserResponse update(UserCommonRequest request);
    void deleteById(UUID id);
}
