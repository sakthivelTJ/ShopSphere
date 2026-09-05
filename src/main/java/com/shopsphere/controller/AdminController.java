package com.shopsphere.controller;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.entity.Category;
import com.shopsphere.entity.Product;
import com.shopsphere.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductSizeService productSizeService;
    private final OrderService orderService;
    private final UserService userService;
    private final ReportService reportService;

    public AdminController(ProductService productService, CategoryService categoryService,
                           ProductSizeService productSizeService, OrderService orderService,
                           UserService userService, ReportService reportService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productSizeService = productSizeService;
        this.orderService = orderService;
        this.userService = userService;
        this.reportService = reportService;
    }

    private boolean checkAdmin(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        return "ADMIN".equalsIgnoreCase(role);
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("dashboard", reportService.getAdminDashboardData());
        return "admin/dashboard";
    }

    // --- PRODUCT MANAGEMENT ---
    @GetMapping("/products")
    public String listAdminProducts(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("products", productService.getAllProducts());
        return "admin/product/products";
    }

    @GetMapping("/products/add")
    public String addProductForm(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product/add-product";
    }

    @PostMapping("/products/add")
    public String saveProduct(@Valid @ModelAttribute("productRequest") ProductRequest productRequest,
                              BindingResult bindingResult,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product/add-product";
        }

        productService.addProduct(productRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable("id") Integer productId, HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        Product product = productService.getProductById(productId);
        ProductRequest req = new ProductRequest();
        req.setCategoryId(product.getCategory().getCategoryId());
        req.setProductName(product.getProductName());
        req.setDescription(product.getDescription());
        req.setPrice(product.getPrice());
        req.setDiscountPercent(product.getDiscountPercent());
        req.setImageUrl(product.getImageUrl());
        req.setIsActive(product.getIsActive());

        model.addAttribute("product", product);
        model.addAttribute("productRequest", req);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product/edit-product";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable("id") Integer productId,
                                @Valid @ModelAttribute("productRequest") ProductRequest productRequest,
                                BindingResult bindingResult,
                                HttpSession session,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product/edit-product";
        }

        productService.updateProduct(productId, productRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/toggle/{id}")
    public String toggleProduct(@PathVariable("id") Integer productId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        productService.toggleProductStatus(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Product status updated.");
        return "redirect:/admin/products";
    }

    // --- CATEGORY MANAGEMENT ---
    @GetMapping("/categories")
    public String listAdminCategories(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category/categories";
    }

    @GetMapping("/categories/add")
    public String addCategoryForm(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("category", new Category());
        return "admin/category/add-category";
    }

    @PostMapping("/categories/add")
    public String saveCategory(@ModelAttribute("category") Category category, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        categoryService.addCategory(category);
        redirectAttributes.addFlashAttribute("successMessage", "Category added successfully!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Integer categoryId, HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("category", categoryService.getCategoryById(categoryId));
        return "admin/category/edit-category";
    }

    @PostMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable("id") Integer categoryId, @ModelAttribute("category") Category category, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        category.setCategoryId(categoryId);
        categoryService.updateCategory(category);
        redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/toggle/{id}")
    public String toggleCategory(@PathVariable("id") Integer categoryId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        categoryService.toggleCategoryStatus(categoryId);
        redirectAttributes.addFlashAttribute("successMessage", "Category status toggled.");
        return "redirect:/admin/categories";
    }

    // --- INVENTORY MANAGEMENT ---
    @GetMapping("/inventory")
    public String viewInventory(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("products", productService.getAllProducts());
        return "admin/inventory/inventory";
    }

    @PostMapping("/inventory/update-stock")
    public String updateStock(@RequestParam("productSizeId") Integer productSizeId,
                              @RequestParam("stockQuantity") Integer stockQuantity,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        productSizeService.updateStock(productSizeId, stockQuantity);
        redirectAttributes.addFlashAttribute("successMessage", "Stock updated successfully.");
        return "redirect:/admin/inventory";
    }

    // --- ORDER MANAGEMENT ---
    @GetMapping("/orders")
    public String listAdminOrders(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/order/orders";
    }

    @GetMapping("/orders/{id}")
    public String viewAdminOrderDetails(@PathVariable("id") Integer orderId, HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("order", orderService.getOrderDetails(orderId));
        return "admin/order/order-details";
    }

    @PostMapping("/orders/status")
    public String updateOrderStatus(@RequestParam("orderId") Integer orderId,
                                    @RequestParam("orderStatus") String orderStatus,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        if (!checkAdmin(session)) return "redirect:/login";

        orderService.updateOrderStatus(orderId, orderStatus);
        redirectAttributes.addFlashAttribute("successMessage", "Order status updated!");
        return "redirect:/admin/orders/" + orderId;
    }

    // --- USER MANAGEMENT ---
    @GetMapping("/users")
    public String listUsers(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";

        model.addAttribute("users", userService.getAllUsers());
        return "admin/user/users";
    }
}
