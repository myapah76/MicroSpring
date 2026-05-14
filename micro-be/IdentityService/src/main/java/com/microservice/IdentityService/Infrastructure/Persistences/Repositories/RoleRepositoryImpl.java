package com.microservice.IdentityService.Infrastructure.Persistences.Repositories;


import com.microservice.IdentityService.Application.Abstrations.Repositories.RoleRepository;
import com.microservice.IdentityService.Domain.Entities.Role;
import com.microservice.IdentityService.Infrastructure.Persistences.JpaRepositories.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleJpaRepository.findByName(name);
    }

    @Override
    public Optional<Role> findBySlug(String slug) {
        return roleJpaRepository.findBySlug(slug);
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return roleJpaRepository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleJpaRepository.save(role);
    }

    @Override
    public boolean existsById(UUID id) {
        return roleJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        roleJpaRepository.deleteById(id);
    }
}
