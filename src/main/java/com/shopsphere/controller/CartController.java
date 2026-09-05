package com.shopsphere.controller;

import com.shopsphere.entity.Cart;
import com.shopsphere.exception.InsufficientStockException;
import com.shopsphere.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Integer productId,
                            @RequestParam("productSizeId") Integer productSizeId,
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please log in to add items to your cart.");
            return "redirect:/login";
        }

        try {
            cartService.addToCart(userId, productId, productSizeId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Product added to cart!");
        } catch (InsufficientStockException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not add item to cart: " + ex.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("cartItemId") Integer cartItemId,
                                 @RequestParam("quantity") Integer quantity,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            cartService.updateCartItemQuantity(userId, cartItemId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Cart updated!");
        } catch (InsufficientStockException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Integer cartItemId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        cartService.removeFromCart(userId, cartItemId);
        redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart.");
        return "redirect:/cart";
    }
}
