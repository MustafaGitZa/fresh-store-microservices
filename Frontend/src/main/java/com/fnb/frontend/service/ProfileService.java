package com.fnb.frontend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public Map getProfile(Long userId) {
        return restTemplate.getForObject(
                gatewayUrl + "/api/users/" + userId, Map.class);
    }

    public Map updateProfile(Long userId, String username,
                             String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        if (username != null && !username.isEmpty()) body.put("username", username);
        if (email != null && !email.isEmpty()) body.put("email", email);
        if (password != null && !password.isEmpty()) body.put("password", password);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                gatewayUrl + "/api/users/" + userId,
                HttpMethod.PUT,
                entity,
                Map.class).getBody();
    }
}