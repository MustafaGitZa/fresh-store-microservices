package com.fnb.inventoryservice.service;

import com.fnb.inventoryservice.events.model.InventoryUpdatedEvent;
import com.fnb.inventoryservice.events.model.PaymentResultEvent;
import com.fnb.inventoryservice.events.publisher.InventoryEventPublisher;
import com.fnb.inventoryservice.model.Inventory;
import com.fnb.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryEventPublisher inventoryEventPublisher;

    public void processInventory(PaymentResultEvent event) {

        String inventoryStatus;

        if ("APPROVED".equals(event.getStatus())) {
            Optional<Inventory> inventoryOpt = inventoryRepository
                    .findByProductName(event.getProductName());

            if (inventoryOpt.isPresent()) {
                Inventory inventory = inventoryOpt.get();
                inventory.setQuantity(inventory.getQuantity() - event.getQuantity());
                inventoryRepository.save(inventory);
                inventoryStatus = "DEDUCTED";
                log.info("Inventory deducted for product: {} new quantity: {}",
                        event.getProductName(), inventory.getQuantity());
            } else {
                inventoryStatus = "PRODUCT_NOT_FOUND";
                log.warn("Product not found in inventory: {}", event.getProductName());
            }
        } else {
            inventoryStatus = "SKIPPED";
            log.info("Payment declined for orderId: {} — inventory unchanged",
                    event.getOrderId());
        }

        InventoryUpdatedEvent updatedEvent = InventoryUpdatedEvent.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .productName(event.getProductName())
                .quantity(event.getQuantity())
                .totalPrice(event.getTotalPrice())
                .paymentStatus(event.getStatus())
                .inventoryStatus(inventoryStatus)
                .build();

        inventoryEventPublisher.publishInventoryUpdated(updatedEvent);
    }
}