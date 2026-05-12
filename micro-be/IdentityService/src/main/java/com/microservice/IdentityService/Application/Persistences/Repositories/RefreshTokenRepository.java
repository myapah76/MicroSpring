package com.microservice.IdentityService.Application.Persistences.Repositories;

import com.microservice.IdentityService.Domain.Entities.RefreshToken;
import com.microservice.IdentityService.Domain.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserAndIsRevokedFalse(User user);
    RefreshToken save(RefreshToken refreshToken);
}
