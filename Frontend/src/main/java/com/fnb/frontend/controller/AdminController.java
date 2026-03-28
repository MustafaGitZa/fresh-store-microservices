package com.fnb.frontend.controller;

import com.fnb.frontend.service.AdminService;
import com.fnb.frontend.service.InventoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/products";
        }

        try {
            Map summary = adminService.getOrderSummary();
            List<Map> recentOrders = adminService.getRecentOrders();
            List<Map> lowStock = adminService.getLowStockProducts();

            model.addAttribute("totalOrders", summary.get("totalOrders"));
            model.addAttribute("totalRevenue", summary.get("totalRevenue"));
            model.addAttribute("recentOrders", recentOrders);
            model.addAttribute("lowStock", lowStock);
            model.addAttribute("username", session.getAttribute("username"));
        } catch (Exception e) {
            model.addAttribute("error", "Could not load dashboard: " + e.getMessage());
        }

        return "admin/dashboard";
    }
}