package com.fnb.notificationservice.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnb.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created", groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void onOrderCreated(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            String email = getEmail(node);
            Long orderId = node.get("orderId").asLong();
            String productName = node.get("productName").asText();
            Integer quantity = node.get("quantity").asInt();
            Double totalPrice = node.get("totalPrice").asDouble();

            log.info("Sending order confirmation for orderId: {}", orderId);
            emailService.sendOrderConfirmation(email, orderId,
                    productName, quantity, totalPrice);

        } catch (Exception e) {
            log.error("Error processing order-created notification: {}",
                    e.getMessage());
        }
    }

    @KafkaListener(topics = "payment-result", groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void onPaymentResult(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            String email = getEmail(node);
            Long orderId = node.get("orderId").asLong();
            String status = node.get("status").asText();
            Double totalPrice = node.get("totalPrice").asDouble();

            log.info("Sending payment {} notification for orderId: {}",
                    status, orderId);

            if ("APPROVED".equals(status)) {
                emailService.sendPaymentApproved(email, orderId, totalPrice);
            } else {
                emailService.sendPaymentDeclined(email, orderId);
            }

        } catch (Exception e) {
            log.error("Error processing payment-result notification: {}",
                    e.getMessage());
        }
    }

    @KafkaListener(topics = "delivery-confirmed", groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void onDeliveryConfirmed(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            String email = getEmail(node);
            Long orderId = node.get("orderId").asLong();
            String deliveryStatus = node.get("deliveryStatus").asText();
            String productName = node.has("productName") ?
                    node.get("productName").asText() : "your order";
            Integer quantity = node.has("quantity") ?
                    node.get("quantity").asInt() : 0;

            if ("CONFIRMED".equals(deliveryStatus)) {
                log.info("Sending delivery confirmation for orderId: {}", orderId);
                emailService.sendDeliveryConfirmed(email, orderId,
                        productName, quantity);
            }

        } catch (Exception e) {
            log.error("Error processing delivery-confirmed notification: {}",
                    e.getMessage());
        }
    }

    private String getEmail(JsonNode node) {
        if (node.has("customerEmail") && !node.get("customerEmail").isNull()) {
            return node.get("customerEmail").asText();
        }
        return null;
    }
}