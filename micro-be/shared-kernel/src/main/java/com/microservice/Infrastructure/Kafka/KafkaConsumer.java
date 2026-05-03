package com.microservice.Infrastructure.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.Abstractions.IIntegrationEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@DependsOn("kafkaTopicInitializer")
@Slf4j
public class KafkaConsumer {

    private final KafkaConsumerRegistry registry;
    private final ObjectMapper objectMapper;
    private final ApplicationContext context;

    @KafkaListener(
            topics = "#{kafkaTopics.all()}",
            groupId = "default-group-v1"
    )
    public void consume(org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record) {
        // Get the raw values from the record
        String message = record.value();
        String topic = record.topic();

        try {
            log.info("Incoming topic: '{}'", topic);
            log.info("Registered topics: {}", registry.getAllTopics());
            Class<?> eventType = registry.getEventType(topic);
            Class<?> handlerType = registry.getHandler(topic);

            if (eventType == null || handlerType == null) {
                log.error("No handler/event type found for topic {}", topic);
                return;
            }
            Object event = objectMapper.readValue(message, eventType);

            IIntegrationEventHandler handler =
                    (IIntegrationEventHandler) context.getBean(handlerType);

            handler.handle(event);

            log.info("Successfully processed message from topic {}", topic);

        } catch (Exception ex) {
            log.error("Failed processing Kafka message from topic {}: {}", topic, message, ex);
            throw new RuntimeException(ex);
        }
    }
}