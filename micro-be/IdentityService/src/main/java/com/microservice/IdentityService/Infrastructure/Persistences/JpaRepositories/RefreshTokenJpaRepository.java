package com.microservice.IdentityService.Infrastructure.Persistences.JpaRepositories;

import com.microservice.IdentityService.Domain.Entities.RefreshToken;
import com.microservice.IdentityService.Domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserAndIsRevokedFalse(User user);
}
