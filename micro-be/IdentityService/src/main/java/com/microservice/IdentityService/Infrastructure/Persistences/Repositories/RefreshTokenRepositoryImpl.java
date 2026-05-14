package com.microservice.IdentityService.Infrastructure.Persistences.Repositories;

import com.microservice.IdentityService.Application.Abstrations.Repositories.RefreshTokenRepository;
import com.microservice.IdentityService.Domain.Entities.RefreshToken;
import com.microservice.IdentityService.Domain.Entities.User;
import com.microservice.IdentityService.Infrastructure.Persistences.JpaRepositories.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token);
    }

    @Override
    public List<RefreshToken> findByUserAndIsRevokedFalse(User user) {
        return refreshTokenJpaRepository.findByUserAndIsRevokedFalse(user);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(refreshToken);
    }
}
