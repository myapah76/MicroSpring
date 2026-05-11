package com.microservice.NotificationService.Application.Abstractions.Cache;

public interface IRedisOtpService {

    boolean saveOtp(String email, String otp);

    String getOtp(String email);

    void deleteOtp(String email);

    boolean validateOtp(String email, String inputOtp);
}