package com.fnb.frontend.controller;

import com.fnb.frontend.service.OrderHistoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @GetMapping("/orders/history")
    public String orderHistory(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            Long customerId = Long.valueOf(
                    session.getAttribute("userId").toString());
            List<Map> orders = orderHistoryService.getOrdersByCustomer(customerId);
            model.addAttribute("orders", orders);
            model.addAttribute("username", session.getAttribute("username"));
        } catch (Exception e) {
            model.addAttribute("error", "Could not load order history.");
        }

        return "orderHistory";
    }
}