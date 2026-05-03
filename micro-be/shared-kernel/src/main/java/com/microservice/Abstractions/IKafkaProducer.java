package com.microservice.Abstractions;

public interface IKafkaProducer {
    <T> void publish(String topic, T message);
}
