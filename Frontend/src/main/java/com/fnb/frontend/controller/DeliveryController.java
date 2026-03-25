package com.fnb.frontend.controller;

import com.fnb.frontend.service.DeliveryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/delivery/{orderId}")
    public String delivery(@PathVariable Long orderId,
                           HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            model.addAttribute("deliveries", deliveryService.getDeliveries(orderId));
            model.addAttribute("orderId", orderId);
            model.addAttribute("order", session.getAttribute("lastOrder"));
        } catch (Exception e) {
            model.addAttribute("error", "Could not fetch delivery details.");
        }

        return "delivery";
    }
}