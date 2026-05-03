package com.microservice.Abstractions;

public interface IKafkaProducer {
    void publish(String topic, String message);
}
