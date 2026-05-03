package com.microservice.IdentityService.Application.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.Constants.KafkaTopics;
import com.microservice.IdentityService.Application.Abstrations.IOutboxService;
import com.microservice.IdentityService.Domain.Entities.OutboxMessage;
import com.microservice.IdentityService.Application.Persistences.Repositories.IOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxService implements IOutboxService {

    private final IOutboxRepository outboxRepository;
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