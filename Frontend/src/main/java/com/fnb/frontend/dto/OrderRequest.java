package com.fnb.frontend.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String productName;
    private Integer quantity;
    private Double totalPrice;
}