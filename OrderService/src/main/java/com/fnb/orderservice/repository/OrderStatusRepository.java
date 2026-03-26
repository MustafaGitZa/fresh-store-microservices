package com.fnb.orderservice.repository;

import com.fnb.orderservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    List<OrderStatus> findByOrderIdOrderByCreatedAtAsc(Long orderId);
}