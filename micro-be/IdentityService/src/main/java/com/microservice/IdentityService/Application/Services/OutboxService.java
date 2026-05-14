package com.microservice.IdentityService.Application.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.IdentityService.Domain.Entities.OutboxMessage;
import com.microservice.IdentityService.Application.Abstrations.Repositories.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class OutboxService implements com.microservice.IdentityService.Application.Abstrations.Service.OutboxService {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public void add(Object event, String type) {
        try {
            OutboxMessage message = new OutboxMessage();

            message.setType(type);
            message.setContent(objectMapper.writeValueAsString(event));
            message.setOccurredOn(OffsetDateTime.now());

            message.setProcessed(false);
            message.setRetryCount(0);
            message.setLastError(null);

            outboxRepository.save(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create outbox message", e);
        }
    }
}