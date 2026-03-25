package com.fnb.frontend.dto;

import lombok.Data;

@Data
public class DeliveryDto {
    private Long deliveryId;
    private Long orderId;
    private Long customerId;
    private String productName;
    private Integer quantity;
    private String status;
    private String createdAt;
}