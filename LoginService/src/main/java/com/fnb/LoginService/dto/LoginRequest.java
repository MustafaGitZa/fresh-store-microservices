package com.fnb.LoginService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username is reuqired")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
