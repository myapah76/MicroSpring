package com.microservice.NotificationService.Application.Persistences.Cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisIdempotencyService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "otp:idempotency:";
    private static final long TTL_MINUTES = 10;

    public boolean isProcessed(String eventId) {
        return redisTemplate.hasKey(PREFIX + eventId);
    }

    public void markProcessed(String eventId) {
        redisTemplate.opsForValue()
                .set(PREFIX + eventId, "1", TTL_MINUTES, TimeUnit.MINUTES);
    }
}
