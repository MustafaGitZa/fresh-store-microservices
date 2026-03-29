package com.fnb.orderservice.controller;

import com.fnb.orderservice.dto.OrderRequest;
import com.fnb.orderservice.dto.OrderResponse;
import com.fnb.orderservice.model.Order;
import com.fnb.orderservice.model.OrderStatus;
import com.fnb.orderservice.repository.OrderRepository;
import com.fnb.orderservice.repository.OrderStatusRepository;
import com.fnb.orderservice.security.JwtUtil;
import com.fnb.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long customerId = jwtUtil.extractUserId(token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request, customerId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                orderRepository.findByCustomerId(customerId)
                        .stream()
                        .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{orderId}/tracking")
    public ResponseEntity<List<OrderStatus>> getTracking(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderStatusRepository
                .findByOrderIdOrderByCreatedAtAsc(orderId));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        long totalOrders = orderRepository.count();
        double totalRevenue = orderRepository.findAll()
                .stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", totalOrders);
        summary.put("totalRevenue", totalRevenue);

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Order>> getRecentOrders() {
        return ResponseEntity.ok(
                orderRepository.findAll()
                        .stream()
                        .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                        .limit(10)
                        .collect(java.util.stream.Collectors.toList())
        );
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OrderService is UP");
    }

}