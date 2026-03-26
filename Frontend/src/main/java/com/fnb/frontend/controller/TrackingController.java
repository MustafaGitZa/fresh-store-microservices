package com.fnb.frontend.controller;

import com.fnb.frontend.service.TrackingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping("/tracking/{orderId}")
    public String tracking(@PathVariable Long orderId,
                           HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            model.addAttribute("timeline", trackingService.getTracking(orderId));
            model.addAttribute("orderId", orderId);
            model.addAttribute("order", session.getAttribute("lastOrder"));
        } catch (Exception e) {
            model.addAttribute("error", "Could not load tracking info.");
        }

        return "tracking";
    }
}