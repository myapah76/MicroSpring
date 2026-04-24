package com.microservice.IdentityService.Application.Persistences.Repositories;

import com.microservice.IdentityService.Domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    List<User> findByEmailContains(String email);
    Optional<User>  findByEmail(String email);
}
