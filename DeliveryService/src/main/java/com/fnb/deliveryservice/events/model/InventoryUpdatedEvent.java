package com.fnb.deliveryservice.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdatedEvent {
    private Long orderId;
    private Long customerId;
    private String productName;
    private Integer quantity;
    private Double totalPrice;
    private String paymentStatus;
    private String inventoryStatus;
    private String customerEmail;
}