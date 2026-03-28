package com.fnb.UserManagementService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.from-email}")
    private String fromEmail;

    public void sendPasswordResetEmail(String toEmail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("FNB Fresh Store - Password Reset");
            message.setText(
                    "Hi,\n\n" +
                            "You requested a password reset for your FNB Fresh Store account.\n\n" +
                            "Click the link below to reset your password:\n" +
                            baseUrl + "/reset-password?token=" + token + "\n\n" +
                            "This link expires in 1 hour.\n\n" +
                            "If you did not request this, please ignore this email.\n\n" +
                            "FNB Fresh Store Team"
            );

            mailSender.send(message);
            log.info("Password reset email sent to: {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }
}