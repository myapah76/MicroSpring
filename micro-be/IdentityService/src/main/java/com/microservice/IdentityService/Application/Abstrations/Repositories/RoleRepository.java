package com.microservice.IdentityService.Application.Abstrations.Repositories;

import com.microservice.IdentityService.Domain.Entities.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository {
    Optional<Role> findByName(String name);

    Optional<Role> findBySlug(String slug);

    Optional<Role> findById(UUID id);

    List<Role> findAll();

    Role save(Role role);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
