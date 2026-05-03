package com.microservice.Infrastructure.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.Abstractions.IKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements IKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public <T> void publish(String topic, T message) {
        kafkaTemplate.send(topic, message);
    }
}