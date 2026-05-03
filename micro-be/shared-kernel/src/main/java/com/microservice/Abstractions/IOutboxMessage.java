package com.microservice.Abstractions;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface IOutboxMessage {
    UUID getId();
    String getType();
    String getContent();
    OffsetDateTime getOccurredOn();
}
