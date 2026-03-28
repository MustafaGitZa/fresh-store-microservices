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
public class AdminService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public Map getOrderSummary() {
        return restTemplate.getForObject(
                gatewayUrl + "/api/orders/summary", Map.class);
    }

    public List<Map> getRecentOrders() {
        return restTemplate.exchange(
                gatewayUrl + "/api/orders/recent",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map>>() {}
        ).getBody();
    }

    public List<Map> getLowStockProducts() {
        return restTemplate.exchange(
                        gatewayUrl + "/api/inventory",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Map>>() {}
                ).getBody()
                .stream()
                .filter(p -> ((Number) p.get("quantity")).intValue() < 10)
                .collect(java.util.stream.Collectors.toList());
    }

    public long getTotalUsers() {
        return 0L; // placeholder
    }
}