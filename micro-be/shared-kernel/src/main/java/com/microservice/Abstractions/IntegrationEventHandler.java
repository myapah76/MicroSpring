package com.microservice.Abstractions;

public interface IntegrationEventHandler<T> {
    void handle(T event);
}
