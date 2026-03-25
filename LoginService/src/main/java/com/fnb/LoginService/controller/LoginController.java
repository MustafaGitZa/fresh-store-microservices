package com.fnb.LoginService.controller;

import com.fnb.LoginService.dto.LoginRequest;
import com.fnb.LoginService.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
class LoginController {

    @Autowired
    private final LoginService loginService;

    @PostMapping("/login")  // lowercase 'l'
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.login(loginRequest));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("LoginService is UP");

    }

}
