package com.microservice.IdentityService.Application.Abstrations.Cache;

public interface IRedisTokenService {

    void blacklistToken(String jti, long ttlMs);

    boolean isBlacklisted(String jti);
}