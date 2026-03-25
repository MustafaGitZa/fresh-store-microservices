package com.fnb.frontend.service;

import com.fnb.frontend.dto.InventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public List<InventoryDto> getInventory() {
        return restTemplate.exchange(
                gatewayUrl + "/api/inventory",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryDto>>() {}
        ).getBody();
    }

    public void addProduct(InventoryDto product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InventoryDto> entity = new HttpEntity<>(product, headers);
        restTemplate.exchange(
                gatewayUrl + "/api/inventory",
                HttpMethod.POST,
                entity,
                InventoryDto.class);
    }
}