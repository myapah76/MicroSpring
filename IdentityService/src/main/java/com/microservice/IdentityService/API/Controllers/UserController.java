package com.microservice.IdentityService.API.Controllers;

import com.microservice.IdentityService.Application.Dtos.User.Request.CreateUserRequest;
import com.microservice.IdentityService.Application.Dtos.User.Request.UserCommonRequest;
import com.microservice.IdentityService.Application.Dtos.User.Respone.UserResponse;
import com.microservice.IdentityService.Application.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @GetMapping()
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PatchMapping
    public UserResponse updateUser(@RequestBody UserCommonRequest request) {
        return userService.update(request);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable UUID id
    ) {
        userService.deleteById(id);
        return "Delete successfully";
    }
}
