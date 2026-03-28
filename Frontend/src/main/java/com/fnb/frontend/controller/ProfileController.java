package com.fnb.frontend.controller;

import com.fnb.frontend.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            Long userId = Long.valueOf(session.getAttribute("userId").toString());
            Map profile = profileService.getProfile(userId);
            model.addAttribute("profile", profile);
        } catch (Exception e) {
            model.addAttribute("error", "Could not load profile.");
        }

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam(required = false) String username,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String password,
                                HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            Long userId = Long.valueOf(session.getAttribute("userId").toString());
            Map result = profileService.updateProfile(userId, username, email, password);

            if (username != null && !username.isEmpty()) {
                session.setAttribute("username", username);
            }

            model.addAttribute("profile", result);
            model.addAttribute("success", "Profile updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Update failed: " + e.getMessage());
        }

        return "profile";
    }
}