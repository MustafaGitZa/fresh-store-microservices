package com.fnb.frontend.controller;

import com.fnb.frontend.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        try {
            Map result = passwordResetService.forgotPassword(email);

            // token may or may not be present depending on email config
            Object token = result.get("token");
            if (token != null) {
                model.addAttribute("token", token.toString());
            }

            model.addAttribute("success", result.get("message").toString());
            return "forgotPassword";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forgotPassword";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam(required = false) String token,
                                    Model model) {
        model.addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("token", token);
            return "resetPassword";
        }

        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters");
            model.addAttribute("token", token);
            return "resetPassword";
        }

        try {
            passwordResetService.resetPassword(token, password);
            return "redirect:/login?reset=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("token", token);
            return "resetPassword";
        }
    }
}