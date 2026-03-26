package com.fnb.frontend.controller;

import com.fnb.frontend.dto.CardRequest;
import com.fnb.frontend.dto.OrderRequest;
import com.fnb.frontend.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public String placeOrder(@ModelAttribute OrderRequest request,
                             HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            String token = (String) session.getAttribute("token");
            Map body = orderService.placeOrder(request, token);
            session.setAttribute("lastOrder", body);

            // Redirect to card payment page instead of order status
            model.addAttribute("order", body);
            model.addAttribute("cardRequest", new CardRequest());
            return "cardPayment";

        } catch (Exception e) {
            model.addAttribute("error", "Order failed: " + e.getMessage());
            return "products";
        }
    }
}