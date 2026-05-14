package com.microservice.NotificationService.Infrastructure.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.Abstractions.IntegrationEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final KafkaConsumerRegistry registry;
    private final ObjectMapper objectMapper;
    private final ApplicationContext context;

    @KafkaListener(
            topics = {"#{@kafkaConsumerRegistry.getAllTopics()}"},
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record) {
        String message = record.value();
        String topic = record.topic();

        try {
            log.info("Incoming topic: '{}'", topic);
            Class<?> eventType = registry.getEventType(topic);
            Class<?> handlerType = registry.getHandler(topic);

            if (eventType == null || handlerType == null) {
                log.error("No handler/event type found for topic {}", topic);
                return;
            }

            Object event = objectMapper.readValue(message, eventType);

            @SuppressWarnings("unchecked")
            IntegrationEventHandler<Object> handler =
                    (IntegrationEventHandler<Object>) context.getBean(handlerType);

            handler.handle(event);

            log.info("Successfully processed message from topic {}", topic);

        } catch (Exception ex) {
            log.error("Failed processing Kafka message from topic {}: {}", topic, message, ex);
            throw new RuntimeException(ex);
        }
    }
}
