package com.microservice.Infrastructure.Kafka;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducerRegistry {

    private final Map<String, Mapping> mappings = new HashMap<>();

    public static class Mapping {
        public final Class<?> eventType;
        public final String topic;

        public Mapping(Class<?> eventType, String topic) {
            this.eventType = eventType;
            this.topic = topic;
        }
    }

    public <T> void register(Class<T> eventType, String topic) {
        mappings.put(eventType.getSimpleName(),
                new Mapping(eventType, topic));
    }

    public boolean tryGet(String typeName, Holder holder) {
        Mapping mapping = mappings.get(typeName);
        if (mapping == null) return false;

        holder.eventType = mapping.eventType;
        holder.topic = mapping.topic;
        return true;
    }

    public static class Holder {
        public Class<?> eventType;
        public String topic;
    }
}