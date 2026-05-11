package com.microservice.NotificationService.Application.Abstractions.Cache;

public interface IRedisIdempotencyService {

    boolean isProcessed(String eventId);

    void markProcessed(String eventId);
}
