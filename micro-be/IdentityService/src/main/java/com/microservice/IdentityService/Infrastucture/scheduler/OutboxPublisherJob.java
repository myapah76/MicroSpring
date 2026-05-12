package com.microservice.IdentityService.Infrastucture.scheduler;

import com.microservice.Abstractions.IKafkaProducer;
import com.microservice.Constants.KafkaTopics;
import com.microservice.IdentityService.Application.Persistences.Repositories.OutboxRepository;
import com.microservice.IdentityService.Domain.Entities.OutboxMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisherJob {

    private final OutboxRepository outboxRepository;
    private final IKafkaProducer kafkaProducer;

    @Scheduled(fixedDelay = 2000) // 2s
    public void publishOutbox() {

        List<OutboxMessage> messages = outboxRepository.findByIsProcessedFalse();

        for (OutboxMessage msg : messages) {
            try {
                // 1. lấy topic từ type
                String topic = mapTopic(msg.getType());
                String payload = msg.getContent();
                // 2. gui kafka
                kafkaProducer.publish(topic, payload);

                // 3. mark processed
                msg.setProcessed(true);
                msg.setLastError(null);
                msg.setProcessedOn(OffsetDateTime.now());

                log.info("Published outbox {}", msg.getId());

            } catch (Exception e) {

                // fail -> tăng retry
                msg.setRetryCount(msg.getRetryCount() + 1);
                msg.setLastError(e.getMessage());

                log.error("Failed outbox {} retry {}", msg.getId(), msg.getRetryCount(), e);

                // retryCount == 5 -> sì tóp
                if (msg.getRetryCount() >= 5) {
                    msg.setProcessed(true); // dead letter style
                    log.error("Outbox moved to DLQ {}", msg.getId());
                }
            }
        }

        outboxRepository.saveAll(messages);
    }

    private final Map<String, String> topicMap = Map.of(
            "otp-notifications", KafkaTopics.OTP_NOTIFICATIONS,
            "otp-forget-password", KafkaTopics.OTP_FORGET_PASSWORD
    );

    private String mapTopic(String type) {
        String topic = topicMap.get(type);
        if (topic == null) {
            throw new RuntimeException("Unknown topic type: " + type);
        }
        return topic;
    }
}
