package com.fnb.frontend.service;

import com.fnb.frontend.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public List<DeliveryDto> getDeliveries(Long orderId) {
        return restTemplate.exchange(
                gatewayUrl + "/api/deliveries/order/" + orderId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DeliveryDto>>() {}
        ).getBody();
    }
}