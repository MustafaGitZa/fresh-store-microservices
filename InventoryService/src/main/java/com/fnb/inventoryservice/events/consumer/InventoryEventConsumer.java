package com.fnb.inventoryservice.events.consumer;

import com.fnb.inventoryservice.events.model.PaymentResultEvent;
import com.fnb.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "payment-result", groupId = "inventory-group")
    public void consumePaymentResult(PaymentResultEvent event) {
        log.info("Received PaymentResultEvent for orderId: {} with status: {}",
                event.getOrderId(), event.getStatus());
        inventoryService.processInventory(event);
    }
}