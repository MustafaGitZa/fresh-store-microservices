package com.fnb.paymentservice.controller;

import com.fnb.paymentservice.dto.CardRequest;
import com.fnb.paymentservice.dto.CardResponse;
import com.fnb.paymentservice.model.Payment;
import com.fnb.paymentservice.repository.PaymentRepository;
import com.fnb.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<CardResponse> processPayment(
            @RequestBody CardRequest request) {
        return ResponseEntity.ok(paymentService.processCardPayment(request));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getPaymentsByOrder(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(paymentRepository.findByOrderId(orderId));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("PaymentService is UP");
    }
}