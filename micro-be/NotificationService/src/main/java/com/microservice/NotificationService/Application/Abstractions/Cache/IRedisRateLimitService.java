package com.microservice.NotificationService.Application.Abstractions.Cache;

public interface IRedisRateLimitService {

    boolean isAllowed(String key);
}