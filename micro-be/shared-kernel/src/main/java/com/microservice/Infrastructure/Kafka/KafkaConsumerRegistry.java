package com.microservice.Infrastructure.Kafka;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumerRegistry {

    private final Map<String, Class<?>> eventTypes = new HashMap<>();
    private final Map<String, Class<?>> handlers = new HashMap<>();

    public void register(String topic, Class<?> eventType, Class<?> handler) {
        eventTypes.put(topic, eventType);
        handlers.put(topic, handler);
    }

    public Class<?> getEventType(String topic) {
        return eventTypes.get(topic);
    }

    public Class<?> getHandler(String topic) {
        return handlers.get(topic);
    }
    public String[] getAllTopics() {
        return eventTypes.keySet().toArray(new String[0]);
    }
}