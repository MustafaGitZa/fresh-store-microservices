package com.fnb.frontend.dto;

import lombok.Data;

@Data
public class PaymentDto {
    private Long paymentId;
    private Long orderId;
    private Long customerId;
    private Double amount;
    private String status;
    private String createdAt;
}