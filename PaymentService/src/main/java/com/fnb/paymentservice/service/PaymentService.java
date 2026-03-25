package com.fnb.paymentservice.service;

import com.fnb.paymentservice.events.model.OrderCreatedEvent;
import com.fnb.paymentservice.events.model.PaymentResultEvent;
import com.fnb.paymentservice.events.publisher.PaymentEventPublisher;
import com.fnb.paymentservice.model.Payment;
import com.fnb.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    public void processPayment(OrderCreatedEvent event) {

        // Randomly approve or decline
        boolean approved = new Random().nextBoolean();
        Payment.PaymentStatus status = approved
                ? Payment.PaymentStatus.APPROVED
                : Payment.PaymentStatus.DECLINED;

        log.info("Processing payment for orderId: {} — {}",
                event.getOrderId(), status);

        // Save payment record
        Payment payment = Payment.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .amount(event.getTotalPrice())
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        // Publish result event
        PaymentResultEvent resultEvent = PaymentResultEvent.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .productName(event.getProductName())
                .quantity(event.getQuantity())
                .totalPrice(event.getTotalPrice())
                .status(status.name())
                .build();

        paymentEventPublisher.publishPaymentResult(resultEvent);
    }
}
