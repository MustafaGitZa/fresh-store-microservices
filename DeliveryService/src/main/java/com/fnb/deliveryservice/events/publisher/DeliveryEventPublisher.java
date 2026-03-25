package com.fnb.deliveryservice.events.publisher;

import com.fnb.deliveryservice.events.model.DeliveryConfirmedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryEventPublisher {

    private final KafkaTemplate<String, DeliveryConfirmedEvent> kafkaTemplate;

    private static final String TOPIC = "delivery-confirmed";

    public void publishDeliveryConfirmed(DeliveryConfirmedEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.getOrderId()), event);
        log.info("Published DeliveryConfirmedEvent for orderId: {} with status: {}",
                event.getOrderId(), event.getDeliveryStatus());
    }
}