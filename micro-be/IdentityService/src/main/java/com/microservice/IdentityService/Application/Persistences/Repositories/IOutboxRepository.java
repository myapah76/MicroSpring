package com.microservice.IdentityService.Application.Persistences.Repositories;

import com.microservice.IdentityService.Domain.Entities.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IOutboxRepository extends JpaRepository<OutboxMessage, UUID> {
    List<OutboxMessage> findByIsProcessedFalse();
}
