package com.microservice.NotificationService.Domain.Entities;

import com.microservice.NotificationService.Domain.Enums.NotificationStatus;
import com.microservice.NotificationService.Domain.Enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "NotificationLogs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false)
    private NotificationType type;

    @Column(name = "Recipient", nullable = false)
    private String recipient;

    @Column(name = "Subject", nullable = false)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private NotificationStatus status;

    @Column(name = "RetryCount", nullable = false)
    private int retryCount;

    @Column(name = "ErrorMessage")
    private String errorMessage;

    @Column(name = "Metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "SentAt")
    private LocalDateTime sentAt;

    @Column(name = "ReferenceId", nullable = false, unique = true)
    private String referenceId;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;
}