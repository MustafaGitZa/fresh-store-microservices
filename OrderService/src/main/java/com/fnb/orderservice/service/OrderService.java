package com.fnb.orderservice.service;

import com.fnb.orderservice.dto.OrderRequest;
import com.fnb.orderservice.dto.OrderResponse;
import com.fnb.orderservice.events.model.OrderCreatedEvent;
import com.fnb.orderservice.events.publisher.OrderEventPublisher;
import com.fnb.orderservice.model.Order;
import com.fnb.orderservice.model.OrderStatus;
import com.fnb.orderservice.repository.OrderRepository;
import com.fnb.orderservice.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderResponse createOrder(OrderRequest request, Long customerId) {

        Order order = Order.builder()
                .customerId(customerId)
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .totalPrice(request.getTotalPrice())
                .status(Order.OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);

        // Add this after saving the order
        orderStatusRepository.save(OrderStatus.builder()
                .orderId(saved.getOrderId())
                .status(OrderStatus.StatusType.PLACED)
                .message("Order placed for " + request.getQuantity() + "x " + request.getProductName())
                .createdAt(LocalDateTime.now())
                .build());

        // Publish event to Kafka
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(saved.getOrderId())
                .customerId(saved.getCustomerId())
                .productName(saved.getProductName())
                .quantity(saved.getQuantity())
                .totalPrice(saved.getTotalPrice())
                .build();

        orderEventPublisher.publishOrderCreated(event);

        return OrderResponse.builder()
                .orderId(saved.getOrderId())
                .customerId(saved.getCustomerId())
                .productName(saved.getProductName())
                .quantity(saved.getQuantity())
                .totalPrice(saved.getTotalPrice())
                .status(saved.getStatus().name())
                .createdAt(saved.getCreatedAt())
                .message("Order created successfully")
                .build();
    }
}