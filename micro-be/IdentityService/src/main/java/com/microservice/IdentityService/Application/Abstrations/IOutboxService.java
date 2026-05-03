package com.microservice.IdentityService.Application.Abstrations;

import com.microservice.Constants.KafkaTopics;

public interface IOutboxService {
    void add(Object event, String type);
}
