package com.fnb.deliveryservice.events.consumer;

import com.fnb.deliveryservice.events.model.InventoryUpdatedEvent;
import com.fnb.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryEventConsumer {

    private final DeliveryService deliveryService;

    @KafkaListener(topics = "inventory-updated", groupId = "delivery-group")
    public void consumeInventoryUpdated(InventoryUpdatedEvent event) {
        log.info("Received InventoryUpdatedEvent for orderId: {} with status: {}",
                event.getOrderId(), event.getInventoryStatus());
        deliveryService.processDelivery(event);
    }
}