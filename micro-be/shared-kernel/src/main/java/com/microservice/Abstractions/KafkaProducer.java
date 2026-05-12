package com.microservice.Abstractions;

public interface KafkaProducer {
    void publish(String topic, String message);
}
