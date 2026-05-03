package com.microservice.NotificationService.Application.Persistences.Cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.script.RedisScript;
import java.util.Collections;
@Service
@RequiredArgsConstructor
public class RedisOtpService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisScript<Long> saveOtpScript;
    private final RedisScript<Long> validateOtpScript;


    private static final String PREFIX = "otp:";
    private static final long OTP_TTL_MINUTES = 5;

    public boolean saveOtp(String email, String otp) {
        String key = PREFIX + email;

        Long result = redisTemplate.execute(
                saveOtpScript,
                Collections.singletonList(key),
                otp,
                String.valueOf(OTP_TTL_MINUTES * 60)
        );

        return result == 1;
    }

    public String getOtp(String email) {
        return (String) redisTemplate.opsForValue().get(PREFIX + email);
    }

    public void deleteOtp(String email) {
        redisTemplate.delete(PREFIX + email);
    }

    public boolean validateOtp(String email, String inputOtp) {
        String key = PREFIX + email;

        Long result = redisTemplate.execute(
                validateOtpScript,
                Collections.singletonList(key),
                inputOtp
        );

        return result == 1;
    }
}
