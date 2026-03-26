package com.fnb.frontend.controller;

import com.fnb.frontend.dto.CardRequest;
import com.fnb.frontend.service.CardPaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CardPaymentController {

    private final CardPaymentService cardPaymentService;

    @PostMapping("/payment/process")
    public String processPayment(@ModelAttribute CardRequest request,
                                 HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            Map result = cardPaymentService.processPayment(request);
            session.setAttribute("paymentResult", result);
            model.addAttribute("result", result);
            model.addAttribute("order", session.getAttribute("lastOrder"));
            return "paymentResult";
        } catch (Exception e) {
            model.addAttribute("error", "Payment failed: " + e.getMessage());
            model.addAttribute("order", session.getAttribute("lastOrder"));
            return "cardPayment";
        }
    }
}