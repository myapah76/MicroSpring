package com.microservice.Infrastructure.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.Abstractions.IKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements IKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topic, String data) {
        kafkaTemplate.send(topic, data);
    }
}