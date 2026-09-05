package com.shopsphere.controller;

import com.shopsphere.service.CategoryService;
import com.shopsphere.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("categories", categoryService.getActiveCategories());
        model.addAttribute("featuredProducts", productService.getActiveProducts());
        return "common/home";
    }
}
