package com.microservice.IdentityService.Infrastucture.Kafka;

import com.microservice.Abstractions.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(String topic, String data) {
        kafkaTemplate.send(topic, data);
    }
}
