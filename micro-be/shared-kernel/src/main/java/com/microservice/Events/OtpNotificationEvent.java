package com.microservice.Events;


import com.fasterxml.jackson.annotation.JsonProperty;

public record OtpNotificationEvent(
        @JsonProperty("id") String id,
        @JsonProperty("email") String email,
        @JsonProperty("otp") String otp,
        @JsonProperty("type") OtpType type
) {}

