package com.microservice.IdentityService.Infrastructure.Persistences.JpaRepositories;

import com.microservice.IdentityService.Domain.Entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<Permission, UUID> {
}
