package com.microservice.IdentityService.Application.Persistences.Repositories;

import com.microservice.IdentityService.Domain.Entities.OutboxMessage;

import java.util.List;

public interface OutboxRepository {
    List<OutboxMessage> findByIsProcessedFalse();
    void saveAll(List<OutboxMessage> messages);
    void save(OutboxMessage message);
}
