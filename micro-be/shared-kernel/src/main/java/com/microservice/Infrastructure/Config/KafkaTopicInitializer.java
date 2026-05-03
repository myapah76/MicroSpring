package com.microservice.Infrastructure.Config;

import com.microservice.Constants.KafkaTopics;
import com.microservice.Events.ForgetPasswordOtpEvent;
import com.microservice.Events.OtpNotificationEvent;
import com.microservice.Infrastructure.Kafka.KafkaProducerRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicInitializer {

    private final KafkaProducerRegistry registry;

    public KafkaTopicInitializer(KafkaProducerRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        registry.register(OtpNotificationEvent.class, KafkaTopics.OTP_NOTIFICATIONS);
        registry.register(ForgetPasswordOtpEvent.class, KafkaTopics.OTP_FORGET_PASSWORD);
    }
}
