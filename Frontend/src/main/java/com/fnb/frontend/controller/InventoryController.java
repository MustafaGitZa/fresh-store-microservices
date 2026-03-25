package com.fnb.frontend.controller;

import com.fnb.frontend.dto.InventoryDto;
import com.fnb.frontend.service.InventoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public String inventory(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/products";
        }

        try {
            model.addAttribute("inventory", inventoryService.getInventory());
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("newProduct", new InventoryDto());
        } catch (Exception e) {
            model.addAttribute("error", "Could not load inventory.");
        }

        return "inventory";
    }

    @PostMapping("/inventory/add")
    public String addProduct(@ModelAttribute InventoryDto product,
                             HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/products";
        }

        try {
            inventoryService.addProduct(product);
            return "redirect:/inventory";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add product.");
            return "inventory";
        }
    }
}