package com.fnb.paymentservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardResponse {
    private String status;
    private String message;
    private Long orderId;
    private Double amount;
    private String cardHolder;
    private String last4Digits;
}