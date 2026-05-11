package com.microservice.NotificationService.Application.Abstractions;

import com.microservice.NotificationService.Domain.Entities.NotificationLog;
import com.microservice.NotificationService.Domain.Enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface INotificationRepository extends JpaRepository<NotificationLog, UUID> {

    // Equivalent to GetByRecipientAsync
    List<NotificationLog> findByRecipient(String recipient);

    // Equivalent to HasProcessedAsync
    boolean existsByReferenceIdAndType(String referenceId, NotificationType type);

    // Optional (you already have findById from JpaRepository)
    Optional<NotificationLog> findByReferenceId(String referenceId);
}