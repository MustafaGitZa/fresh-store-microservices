package com.fnb.frontend.controller;

import com.fnb.frontend.dto.CardRequest;
import com.fnb.frontend.dto.CartItem;
import com.fnb.frontend.dto.CheckoutRequest;
import com.fnb.frontend.dto.OrderRequest;
import com.fnb.frontend.service.CardPaymentService;
import com.fnb.frontend.service.CartService;
import com.fnb.frontend.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final CardPaymentService cardPaymentService;

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        if (cartService.getCart(session).isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("total", cartService.getTotal(session));
        model.addAttribute("checkoutRequest", new CheckoutRequest());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute CheckoutRequest request,
                                  HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        session.setAttribute("deliveryDetails", request);
        return "redirect:/checkout/payment";
    }

    @GetMapping("/checkout/payment")
    public String paymentPage(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        if (cartService.getCart(session).isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("total", cartService.getTotal(session));
        model.addAttribute("delivery", session.getAttribute("deliveryDetails"));
        return "checkoutPayment";
    }

    @PostMapping("/checkout/pay")
    public String pay(@RequestParam String cardHolder,
                      @RequestParam String cardNumber,
                      @RequestParam Integer expiryMonth,
                      @RequestParam Integer expiryYear,
                      @RequestParam String cvv,
                      HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        try {
            String token = (String) session.getAttribute("token");
            List<CartItem> cart = cartService.getCart(session);
            Double total = cartService.getTotal(session);
            CheckoutRequest delivery = (CheckoutRequest) session.getAttribute("deliveryDetails");

            List<Map> orders = new ArrayList<>();

            for (CartItem item : cart) {
                // Place order
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setProductName(item.getProductName());
                orderRequest.setQuantity(item.getQuantity());
                orderRequest.setTotalPrice(item.getSubtotal());

                Map order = orderService.placeOrder(orderRequest, token);
                orders.add(order);

                // Process payment
                CardRequest cardRequest = new CardRequest();
                cardRequest.setCardHolder(cardHolder);
                cardRequest.setCardNumber(cardNumber);
                cardRequest.setExpiryMonth(expiryMonth);
                cardRequest.setExpiryYear(expiryYear);
                cardRequest.setCvv(cvv);
                cardRequest.setOrderId(Long.valueOf(order.get("orderId").toString()));
                cardRequest.setCustomerId(Long.valueOf(session.getAttribute("userId").toString()));
                cardRequest.setAmount(item.getSubtotal());
                cardRequest.setProductName(item.getProductName());
                cardRequest.setQuantity(item.getQuantity());
                cardRequest.setCustomerEmail(session.getAttribute("email").toString());

                cardPaymentService.processPayment(cardRequest);
            }

            session.setAttribute("completedOrders", orders);
            cartService.clearCart(session);

            model.addAttribute("orders", orders);
            model.addAttribute("delivery", delivery);
            model.addAttribute("total", total);
            return "orderConfirmation";

        } catch (Exception e) {
            model.addAttribute("error", "Payment failed: " + e.getMessage());
            model.addAttribute("cart", cartService.getCart(session));
            model.addAttribute("total", cartService.getTotal(session));
            model.addAttribute("delivery", session.getAttribute("deliveryDetails"));
            return "checkoutPayment";
        }
    }
}