package com.shopsphere.controller;

import com.shopsphere.dto.CheckoutRequest;
import com.shopsphere.dto.OrderResponse;
import com.shopsphere.entity.Cart;
import com.shopsphere.entity.Order;
import com.shopsphere.entity.User;
import com.shopsphere.exception.InsufficientStockException;
import com.shopsphere.service.CartService;
import com.shopsphere.service.OrderService;
import com.shopsphere.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;

    public OrderController(OrderService orderService, CartService cartService, UserService userService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your cart is empty. Add items before checkout!");
            return "redirect:/cart";
        }

        User user = userService.getUserById(userId);
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setDeliveryAddress(user.getAddress());

        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        model.addAttribute("checkoutRequest", checkoutRequest);
        return "checkout/checkout";
    }

    @PostMapping("/checkout/place-order")
    public String processOrderPlacement(@Valid @ModelAttribute("checkoutRequest") CheckoutRequest checkoutRequest,
                                        BindingResult bindingResult,
                                        HttpSession session,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            Cart cart = cartService.getCartByUserId(userId);
            User user = userService.getUserById(userId);
            model.addAttribute("cart", cart);
            model.addAttribute("user", user);
            return "checkout/checkout";
        }

        try {
            Order order = orderService.placeOrder(userId, checkoutRequest);
            redirectAttributes.addFlashAttribute("orderId", order.getOrderId());
            return "redirect:/checkout/order-success";
        } catch (InsufficientStockException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/cart";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Order failed: " + ex.getMessage());
            Cart cart = cartService.getCartByUserId(userId);
            User user = userService.getUserById(userId);
            model.addAttribute("cart", cart);
            model.addAttribute("user", user);
            return "checkout/checkout";
        }
    }

    @GetMapping("/checkout/order-success")
    public String orderSuccess(@ModelAttribute("orderId") Object orderIdAttr, Model model) {
        if (orderIdAttr != null) {
            Integer orderId = (Integer) orderIdAttr;
            OrderResponse order = orderService.getOrderDetails(orderId);
            model.addAttribute("order", order);
        }
        return "checkout/order-success";
    }

    @GetMapping("/orders")
    public String listUserOrders(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getUserOrders(userId);
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetails(@PathVariable("id") Integer orderId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        OrderResponse order = orderService.getOrderDetails(orderId);
        model.addAttribute("order", order);
        return "order/order-details";
    }
}
