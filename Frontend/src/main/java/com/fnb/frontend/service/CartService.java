package com.fnb.frontend.service;

import com.fnb.frontend.dto.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private static final String CART_KEY = "cart";

    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_KEY, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, CartItem item) {
        List<CartItem> cart = getCart(session);

        Optional<CartItem> existing = cart.stream()
                .filter(c -> c.getProductName().equals(item.getProductName()))
                .findFirst();

        if (existing.isPresent()) {
            CartItem existingItem = existing.get();
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            existingItem.setSubtotal(existingItem.getQuantity() * existingItem.getPrice());
        } else {
            item.setSubtotal(item.getQuantity() * item.getPrice());
            cart.add(item);
        }

        session.setAttribute(CART_KEY, cart);
    }

    public void removeFromCart(HttpSession session, String productName) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(c -> c.getProductName().equals(productName));
        session.setAttribute(CART_KEY, cart);
    }

    public void updateQuantity(HttpSession session, String productName, Integer quantity) {
        List<CartItem> cart = getCart(session);
        cart.stream()
                .filter(c -> c.getProductName().equals(productName))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setSubtotal(quantity * item.getPrice());
                });
        session.setAttribute(CART_KEY, cart);
    }

    public void clearCart(HttpSession session) {
        session.setAttribute(CART_KEY, new ArrayList<>());
    }

    public Double getTotal(HttpSession session) {
        return getCart(session).stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public int getItemCount(HttpSession session) {
        return getCart(session).stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}