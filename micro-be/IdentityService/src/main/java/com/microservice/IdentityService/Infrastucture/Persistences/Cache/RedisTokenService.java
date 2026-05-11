package com.microservice.IdentityService.Infrastucture.Persistences.Cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "blacklist:";

    public void blacklistToken(String jti, long ttlMs) {
        redisTemplate.opsForValue().set(PREFIX + jti, "revoked", ttlMs, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + jti));
    }
}