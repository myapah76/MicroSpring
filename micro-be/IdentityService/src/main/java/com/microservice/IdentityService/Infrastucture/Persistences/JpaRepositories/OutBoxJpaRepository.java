package com.microservice.IdentityService.Infrastucture.Persistences.JpaRepositories;

import com.microservice.IdentityService.Domain.Entities.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutBoxJpaRepository extends JpaRepository<OutboxMessage, UUID> {
    List<OutboxMessage> findByIsProcessedFalse();
}
