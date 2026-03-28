package com.fnb.frontend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public Map forgotPassword(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(Map.of("email", email), headers);

        return restTemplate.exchange(
                gatewayUrl + "/api/users/forgot-password",
                HttpMethod.POST,
                entity,
                Map.class).getBody();
    }

    public void resetPassword(String token, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(Map.of("token", token, "password", password), headers);

        restTemplate.exchange(
                gatewayUrl + "/api/users/reset-password",
                HttpMethod.POST,
                entity,
                String.class);
    }
}