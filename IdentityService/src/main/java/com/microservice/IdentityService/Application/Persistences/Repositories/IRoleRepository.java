package com.microservice.IdentityService.Application.Persistences.Repositories;

import com.microservice.IdentityService.Domain.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID>{
    Optional<Role> findByName(String name);
    Optional<Role> findBySlug(String slug);
}
