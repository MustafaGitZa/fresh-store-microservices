package com.fnb.frontend.service;

import com.fnb.frontend.dto.LoginRequest;
import com.fnb.frontend.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public Map login(LoginRequest request) {
        log.info("Attempting login for user: {}", request.getUsername());
        log.info("Calling gateway at: {}", gatewayUrl + "/api/auth/login");
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    gatewayUrl + "/api/auth/login", request, Map.class);
            log.info("Login response: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            throw e;
        }
    }

    public void register(RegisterRequest request) {
        log.info("Registering user: {}", request.getUsername());
        restTemplate.postForEntity(
                gatewayUrl + "/api/users/register", request, Map.class);
    }
}