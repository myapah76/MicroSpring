package com.microservice.IdentityService.Application.Abstrations.Repositories;

import com.microservice.IdentityService.Domain.Entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    List<User> findByEmailContains(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(UUID id);

    List<User> findAll();

    User save(User user);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
