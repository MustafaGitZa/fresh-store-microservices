package com.fnb.frontend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public List<Map> getOrdersByCustomer(Long customerId) {
        return restTemplate.exchange(
                gatewayUrl + "/api/orders/customer/" + customerId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map>>() {}
        ).getBody();
    }
}