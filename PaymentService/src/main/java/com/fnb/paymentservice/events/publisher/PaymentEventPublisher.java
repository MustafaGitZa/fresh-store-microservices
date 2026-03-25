package com.fnb.paymentservice.events.publisher;

import com.fnb.paymentservice.events.model.PaymentResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;

    private static final String TOPIC = "payment-result";

    public void publishPaymentResult(PaymentResultEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.getOrderId()), event);
        log.info("Published PaymentResultEvent for orderId: {} with status: {}",
                event.getOrderId(), event.getStatus());
    }
}