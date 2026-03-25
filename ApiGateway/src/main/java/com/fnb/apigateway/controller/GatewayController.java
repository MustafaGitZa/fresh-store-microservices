package com.fnb.apigateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GatewayController {

    private final RestTemplate restTemplate;

    private static final Map<String, String> SERVICE_MAP = Map.of(
            "/api/users", "http://localhost:8081",
            "/api/auth", "http://localhost:8082",
            "/api/orders", "http://localhost:8083",
            "/api/payments", "http://localhost:8084",
            "/api/inventory", "http://localhost:8085",
            "/api/deliveries", "http://localhost:8086"
    );

    @RequestMapping("/**")
    public ResponseEntity<String> route(
            @RequestBody(required = false) String body,
            HttpMethod method,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        String targetBase = resolveService(path);

        if (targetBase == null) {
            return ResponseEntity.notFound().build();
        }

        String targetUrl = targetBase + path +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(targetUrl, method, entity, String.class);
    }

    private String resolveService(String path) {
        return SERVICE_MAP.entrySet().stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}