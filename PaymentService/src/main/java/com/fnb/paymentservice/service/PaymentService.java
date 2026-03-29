package com.fnb.paymentservice.service;

import com.fnb.paymentservice.dto.CardRequest;
import com.fnb.paymentservice.dto.CardResponse;
import com.fnb.paymentservice.events.model.OrderCreatedEvent;
import com.fnb.paymentservice.events.model.PaymentResultEvent;
import com.fnb.paymentservice.events.publisher.PaymentEventPublisher;
import com.fnb.paymentservice.model.Payment;
import com.fnb.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    public CardResponse processCardPayment(CardRequest request) {

        // Validate card number — must be 16 digits
        String cleanCardNumber = request.getCardNumber().replaceAll("\\s", "");
        if (cleanCardNumber.length() != 16 || !cleanCardNumber.matches("\\d+")) {
            return CardResponse.builder()
                    .status("DECLINED")
                    .message("Invalid card number — must be 16 digits")
                    .orderId(request.getOrderId())
                    .amount(request.getAmount())
                    .build();
        }

        // Validate CVV — must be 3 digits
        if (request.getCvv().length() != 3 || !request.getCvv().matches("\\d+")) {
            return CardResponse.builder()
                    .status("DECLINED")
                    .message("Invalid CVV — must be 3 digits")
                    .orderId(request.getOrderId())
                    .amount(request.getAmount())
                    .build();
        }

        // Validate expiry — must not be in the past
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1;

        if (request.getExpiryYear() < currentYear ||
                (request.getExpiryYear() == currentYear &&
                        request.getExpiryMonth() < currentMonth)) {
            return CardResponse.builder()
                    .status("DECLINED")
                    .message("Card has expired")
                    .orderId(request.getOrderId())
                    .amount(request.getAmount())
                    .build();
        }

        // Simulate balance check
        // Last 4 digits determine balance (just for simulation)
        String last4 = cleanCardNumber.substring(12);
        double simulatedBalance = (Double.parseDouble(last4) % 500) + 100;

        if (simulatedBalance < request.getAmount()) {
            return CardResponse.builder()
                    .status("DECLINED")
                    .message("Insufficient funds")
                    .orderId(request.getOrderId())
                    .amount(request.getAmount())
                    .build();
        }

        // Get customerEmail from request
        String customerEmail = request.getCustomerEmail();

// Save payment record
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .status(Payment.PaymentStatus.APPROVED)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

// Publish payment result event — pass customerEmail through
        PaymentResultEvent resultEvent = PaymentResultEvent.builder()
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .totalPrice(request.getAmount())
                .status("APPROVED")
                .customerEmail(customerEmail)
                .build();

        paymentEventPublisher.publishPaymentResult(resultEvent);
        paymentEventPublisher.publishPaymentResult(resultEvent);

        log.info("Payment APPROVED for orderId: {} amount: R{}",
                request.getOrderId(), request.getAmount());

        return CardResponse.builder()
                .status("APPROVED")
                .message("Payment successful!")
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .cardHolder(request.getCardHolder())
                .last4Digits(last4)
                .build();
    }

    public void processPayment(OrderCreatedEvent event) {
        // Keep for Kafka flow
        log.info("Kafka payment event received for orderId: {}", event.getOrderId());
    }
}