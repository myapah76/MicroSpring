package com.microservice.IdentityService.Application.Abstrations;

public interface OutboxService {
    void add(Object event, String type);
}
