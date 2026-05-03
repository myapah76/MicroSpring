package com.microservice.NotificationService.Infrastructure.Configurations;

import com.microservice.Constants.KafkaTopics;
import com.microservice.Events.ForgetPasswordOtpEvent;
import com.microservice.Events.OtpNotificationEvent;
import com.microservice.Infrastructure.Kafka.KafkaConsumerRegistry;
import com.microservice.NotificationService.Application.EvenHanlder.OtpNotificationHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

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
