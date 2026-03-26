package com.fnb.frontend.dto;

import lombok.Data;

@Data
public class CardRequest {
    private String cardNumber;
    private String cardHolder;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String cvv;
    private Long orderId;
    private Long customerId;
    private Double amount;
    private String productName;
    private Integer quantity;
}