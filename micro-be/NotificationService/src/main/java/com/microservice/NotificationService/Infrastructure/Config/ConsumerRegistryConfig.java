package com.microservice.NotificationService.Infrastructure.Config;

import com.microservice.Constants.KafkaTopics;
import com.microservice.Events.ForgetPasswordOtpEvent;
import com.microservice.Events.OtpNotificationEvent;
import com.microservice.Infrastructure.Kafka.KafkaConsumerRegistry;
import com.microservice.NotificationService.Application.EventHandler.OtpNotificationHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ConsumerRegistryConfig {

    private final KafkaConsumerRegistry registry;

    @PostConstruct
    public void registerConsumers() {
        registry.register(
                KafkaTopics.OTP_NOTIFICATIONS,
                OtpNotificationEvent.class,
                OtpNotificationHandler.class
        );

        registry.register(
                KafkaTopics.OTP_FORGET_PASSWORD,
                ForgetPasswordOtpEvent.class,
                OtpNotificationHandler.class
        );
    }
}
