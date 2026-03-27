package com.fnb.frontend.controller;

import com.fnb.frontend.dto.CartItem;
import com.fnb.frontend.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String productName,
                            @RequestParam Integer quantity,
                            @RequestParam Double price,
                            @RequestParam Long productId,
                            HttpSession session) {
        CartItem item = CartItem.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .price(price)
                .subtotal(quantity * price)
                .build();

        cartService.addToCart(session, item);
        return "redirect:/products?added=" + productName;
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        List<CartItem> cart = cartService.getCart(session);
        Double total = cartService.getTotal(session);

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        model.addAttribute("itemCount", cartService.getItemCount(session));
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam String productName,
                                 HttpSession session) {
        cartService.removeFromCart(session, productName);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam String productName,
                             @RequestParam Integer quantity,
                             HttpSession session) {
        if (quantity <= 0) {
            cartService.removeFromCart(session, productName);
        } else {
            cartService.updateQuantity(session, productName, quantity);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }
}