package com.microservice.Constants;

import org.springframework.stereotype.Component;

@Component("kafkaTopics")
public class KafkaTopicsBean {

    public static String[] all() {
        return new String[]{
                KafkaTopics.OTP_NOTIFICATIONS,
                KafkaTopics.OTP_FORGET_PASSWORD,
        };
    }
}
