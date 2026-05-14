package com.microservice.IdentityService.Application.Abstrations.Service;

public interface OutboxService {
    void add(Object event, String type);
}
