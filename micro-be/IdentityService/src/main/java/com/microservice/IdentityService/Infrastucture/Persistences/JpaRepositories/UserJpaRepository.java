package com.microservice.IdentityService.Infrastucture.Persistences.JpaRepositories;

import com.microservice.IdentityService.Domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {

    List<User> findByEmailContains(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
