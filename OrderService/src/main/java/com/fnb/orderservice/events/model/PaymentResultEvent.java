package com.fnb.orderservice.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultEvent {
    private Long orderId;
    private Long customerId;
    private String productName;
    private Integer quantity;
    private Double totalPrice;
    private String status;
}