package com.microservice.IdentityService.Infrastructure.Persistences.JpaRepositories;

import com.microservice.IdentityService.Domain.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    Optional<Role> findBySlug(String slug);
}
