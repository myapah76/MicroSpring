package com.microservice.NotificationService.Application.Abstractions.Cache;

public interface RedisIdempotencyService {

    boolean isProcessed(String eventId);

    void markProcessed(String eventId);
}
