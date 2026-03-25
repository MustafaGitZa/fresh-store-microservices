package com.fnb.frontend.controller;

import com.fnb.frontend.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/{orderId}")
    public String payment(@PathVariable Long orderId,
                          HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            model.addAttribute("payments", paymentService.getPayments(orderId));
            model.addAttribute("orderId", orderId);
            model.addAttribute("order", session.getAttribute("lastOrder"));
        } catch (Exception e) {
            model.addAttribute("error", "Could not fetch payment details.");
        }

        return "payment";
    }
}