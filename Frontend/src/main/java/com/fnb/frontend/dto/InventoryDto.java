package com.fnb.frontend.dto;

import lombok.Data;

@Data
public class InventoryDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String category;
}