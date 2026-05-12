package com.microservice.NotificationService.Application.Abstractions.Cache;

public interface RedisRateLimitService {

    boolean isAllowed(String key);
}