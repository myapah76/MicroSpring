package com.microservice.IdentityService.Application.Dtos.User.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phone;

    private String address;

    @NotNull(message = "Gender is required")
    private Integer gender; // 0: Male, 1: Female

    @NotNull(message = "Date of birth is required")
    private OffsetDateTime dateOfBirth;

    private String avatarUrl;

    private String avatarPublicId;

    private String roleId; // nhận từ client (UUID dạng string)
}