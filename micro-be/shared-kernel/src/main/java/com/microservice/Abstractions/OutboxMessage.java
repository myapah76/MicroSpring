package com.microservice.Abstractions;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface OutboxMessage {
    UUID getId();
    String getType();
    String getContent();
    OffsetDateTime getOccurredOn();
}
