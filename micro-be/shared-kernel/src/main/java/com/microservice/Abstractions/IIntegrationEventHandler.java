package com.microservice.Abstractions;

public interface IIntegrationEventHandler<T> {
    void handle(T event);
}
