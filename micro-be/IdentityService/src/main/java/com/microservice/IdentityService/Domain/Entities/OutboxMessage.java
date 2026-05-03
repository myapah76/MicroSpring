package com.microservice.IdentityService.Domain.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_messages")
@Getter
@Setter
public class OutboxMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "occurred_on", nullable = false)
    private OffsetDateTime occurredOn;

    @Column(nullable = false)
    private boolean isProcessed = false;

    @Column(name = "retry_count")
    private int retryCount = 0;

    @Column(name = "last_error")
    private String lastError;

    @Column(name = "processed_on")
    private OffsetDateTime processedOn;


    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (occurredOn == null) {
            occurredOn = OffsetDateTime.now();
        }
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}