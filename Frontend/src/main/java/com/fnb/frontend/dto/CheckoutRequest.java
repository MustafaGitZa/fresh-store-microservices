package com.fnb.frontend.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String fullName;
    private String phone;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}