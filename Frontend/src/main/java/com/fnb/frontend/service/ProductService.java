package com.fnb.frontend.service;

import com.fnb.frontend.dto.InventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public List<InventoryDto> getProducts() {
        return restTemplate.exchange(
                gatewayUrl + "/api/inventory",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryDto>>() {}
        ).getBody();
    }
}