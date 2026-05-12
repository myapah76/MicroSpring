package com.microservice.IdentityService.Infrastucture.Persistences.Repositories;

import com.microservice.IdentityService.Application.Persistences.Repositories.PermissionRepository;
import com.microservice.IdentityService.Infrastucture.Persistences.JpaRepositories.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImp implements PermissionRepository {
    private final PermissionJpaRepository permissionJpaRepository;
}
