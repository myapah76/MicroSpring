package com.microservice.IdentityService.Infrastructure.Persistences.Repositories;

import com.microservice.IdentityService.Application.Abstrations.Repositories.PermissionRepository;
import com.microservice.IdentityService.Infrastructure.Persistences.JpaRepositories.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImp implements PermissionRepository {
    private final PermissionJpaRepository permissionJpaRepository;
}
