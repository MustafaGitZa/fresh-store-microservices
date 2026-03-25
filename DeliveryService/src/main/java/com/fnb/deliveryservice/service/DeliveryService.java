package com.fnb.deliveryservice.service;

import com.fnb.deliveryservice.events.model.DeliveryConfirmedEvent;
import com.fnb.deliveryservice.events.model.InventoryUpdatedEvent;
import com.fnb.deliveryservice.events.publisher.DeliveryEventPublisher;
import com.fnb.deliveryservice.model.Delivery;
import com.fnb.deliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryEventPublisher deliveryEventPublisher;

    public void processDelivery(InventoryUpdatedEvent event) {

        Delivery.DeliveryStatus deliveryStatus;

        if ("DEDUCTED".equals(event.getInventoryStatus())) {
            deliveryStatus = Delivery.DeliveryStatus.CONFIRMED;
            log.info("Delivery confirmed for orderId: {}", event.getOrderId());
        } else {
            deliveryStatus = Delivery.DeliveryStatus.SKIPPED;
            log.info("Delivery skipped for orderId: {} — inventory status: {}",
                    event.getOrderId(), event.getInventoryStatus());
        }

        Delivery delivery = Delivery.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .productName(event.getProductName())
                .quantity(event.getQuantity())
                .status(deliveryStatus)
                .createdAt(LocalDateTime.now())
                .build();

        deliveryRepository.save(delivery);

        DeliveryConfirmedEvent confirmedEvent = DeliveryConfirmedEvent.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .productName(event.getProductName())
                .quantity(event.getQuantity())
                .totalPrice(event.getTotalPrice())
                .deliveryStatus(deliveryStatus.name())
                .build();

        deliveryEventPublisher.publishDeliveryConfirmed(confirmedEvent);
    }
}