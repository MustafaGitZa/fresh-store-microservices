package com.fnb.notificationservice.service;

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

    @Value("${app.from-email}")
    private String fromEmail;

    public void sendOrderConfirmation(String toEmail, Long orderId,
                                      String productName, Integer quantity,
                                      Double totalPrice) {
        send(toEmail,
                "FNB Fresh Store - Order #" + orderId + " Confirmed",
                "Hi,\n\n" +
                        "Your order has been placed successfully!\n\n" +
                        "Order Details:\n" +
                        "Order ID: #" + orderId + "\n" +
                        "Product: " + productName + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Total: R" + totalPrice + "\n\n" +
                        "We will notify you once your payment is processed.\n\n" +
                        "Thank you for shopping with FNB Fresh Store!");
    }

    public void sendPaymentApproved(String toEmail, Long orderId,
                                    Double amount) {
        send(toEmail,
                "FNB Fresh Store - Payment Approved for Order #" + orderId,
                "Hi,\n\n" +
                        "Great news! Your payment has been approved.\n\n" +
                        "Order ID: #" + orderId + "\n" +
                        "Amount: R" + amount + "\n\n" +
                        "Your order is now being prepared for delivery.\n\n" +
                        "FNB Fresh Store Team");
    }

    public void sendPaymentDeclined(String toEmail, Long orderId) {
        send(toEmail,
                "FNB Fresh Store - Payment Declined for Order #" + orderId,
                "Hi,\n\n" +
                        "Unfortunately your payment was declined for Order #" + orderId + ".\n\n" +
                        "Please try again with a different card.\n\n" +
                        "FNB Fresh Store Team");
    }

    public void sendDeliveryConfirmed(String toEmail, Long orderId,
                                      String productName, Integer quantity) {
        send(toEmail,
                "FNB Fresh Store - Order #" + orderId + " Delivered!",
                "Hi,\n\n" +
                        "Your order has been delivered!\n\n" +
                        "Order ID: #" + orderId + "\n" +
                        "Product: " + productName + "\n" +
                        "Quantity: " + quantity + "\n\n" +
                        "Thank you for shopping with FNB Fresh Store!\n" +
                        "We hope you enjoy your fresh produce.\n\n" +
                        "FNB Fresh Store Team");
    }

    private void send(String toEmail, String subject, String body) {
        try {
            if (toEmail == null || toEmail.isEmpty()) {
                log.warn("No email address provided — skipping notification");
                return;
            }
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Email sent to: {} | Subject: {}", toEmail, subject);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }
}