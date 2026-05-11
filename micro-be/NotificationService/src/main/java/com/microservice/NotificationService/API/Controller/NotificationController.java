package com.microservice.NotificationService.API.Controller;

import com.microservice.NotificationService.Application.Abstractions.IEmailSender;
import com.microservice.NotificationService.Application.Dtos.OtpRequest;
import com.microservice.NotificationService.Application.Dtos.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final IEmailSender emailSender;

    @PostMapping("/otp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequest request) {
        emailSender.sendOtpEmailAsync(
                request.email(),
                request.otp()
        );
        return ResponseEntity.ok("OTP email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> sendResetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        emailSender.sendPasswordResetEmailAsync(
                request.email(),
                request.link()
        );
        return ResponseEntity.ok("Reset password email sent");
    }
}