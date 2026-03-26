package com.fnb.orderservice.events.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnb.orderservice.model.OrderStatus;
import com.fnb.orderservice.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatusConsumer {

    private final OrderStatusRepository orderStatusRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-result", groupId = "order-status-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void onPaymentResult(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            Long orderId = node.get("orderId").asLong();
            String status = node.get("status").asText();
            Double totalPrice = node.get("totalPrice").asDouble();

            log.info("Payment result for orderId: {} status: {}", orderId, status);

            OrderStatus.StatusType statusType;
            String msg;

            if ("APPROVED".equals(status)) {
                statusType = OrderStatus.StatusType.PAYMENT_APPROVED;
                msg = "Payment of R" + totalPrice + " was approved successfully";
            } else {
                statusType = OrderStatus.StatusType.PAYMENT_DECLINED;
                msg = "Payment was declined";
            }

            orderStatusRepository.save(OrderStatus.builder()
                    .orderId(orderId)
                    .status(statusType)
                    .message(msg)
                    .createdAt(LocalDateTime.now())
                    .build());

        } catch (Exception e) {
            log.error("Error processing payment result: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "inventory-updated", groupId = "order-status-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void onInventoryUpdated(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            Long orderId = node.get("orderId").asLong();
            String inventoryStatus = node.get("inventoryStatus").asText();
            String productName = node.has("productName") && !node.get("productName").isNull()
                    ? node.get("productName").asText() : "Product";
            Integer quantity = node.has("quantity") && !node.get("quantity").isNull()
                    ? node.get("quantity").asInt() : 0;

            log.info("Inventory update for orderId: {} status: {}", orderId, inventoryStatus);

            OrderStatus.StatusType statusType;
            String msg;

            if ("DEDUCTED".equals(inventoryStatus)) {
                statusType = OrderStatus.StatusType.INVENTORY_UPDATED;
                msg = productName + " x" + quantity + " reserved from stock";
            } else {
                statusType = OrderStatus.StatusType.INVENTORY_FAILED;
                msg = "Product not available in stock";
            }

            orderStatusRepository.save(OrderStatus.builder()
                    .orderId(orderId)
                    .status(statusType)
                    .message(msg)
                    .createdAt(LocalDateTime.now())
                    .build());

        } catch (Exception e) {
            log.error("Error processing inventory update: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "delivery-confirmed", groupId = "order-status-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void onDeliveryConfirmed(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            Long orderId = node.get("orderId").asLong();
            String deliveryStatus = node.get("deliveryStatus").asText();

            log.info("Delivery confirmed for orderId: {} status: {}", orderId, deliveryStatus);

            if ("CONFIRMED".equals(deliveryStatus)) {
                orderStatusRepository.save(OrderStatus.builder()
                        .orderId(orderId)
                        .status(OrderStatus.StatusType.OUT_FOR_DELIVERY)
                        .message("Your order is out for delivery!")
                        .createdAt(LocalDateTime.now())
                        .build());

                orderStatusRepository.save(OrderStatus.builder()
                        .orderId(orderId)
                        .status(OrderStatus.StatusType.DELIVERED)
                        .message("Order delivered successfully!")
                        .createdAt(LocalDateTime.now().plusSeconds(5))
                        .build());
            }

        } catch (Exception e) {
            log.error("Error processing delivery confirmation: {}", e.getMessage());
        }
    }
}