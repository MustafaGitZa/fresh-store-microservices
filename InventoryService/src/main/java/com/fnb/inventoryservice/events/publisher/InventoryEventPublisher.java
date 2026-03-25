package com.fnb.inventoryservice.events.publisher;

import com.fnb.inventoryservice.events.model.InventoryUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryEventPublisher {

    private final KafkaTemplate<String, InventoryUpdatedEvent> kafkaTemplate;

    private static final String TOPIC = "inventory-updated";

    public void publishInventoryUpdated(InventoryUpdatedEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.getOrderId()), event);
        log.info("Published InventoryUpdatedEvent for orderId: {} with status: {}",
                event.getOrderId(), event.getInventoryStatus());
    }
}