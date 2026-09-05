package com.shopsphere.controller;

import com.shopsphere.entity.Product;
import com.shopsphere.service.CategoryService;
import com.shopsphere.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
        model.addAttribute("categories", categoryService.getActiveCategories());
        
        List<Product> products;
        if (categoryId != null) {
            products = productService.getProductsByCategory(categoryId);
            model.addAttribute("selectedCategoryId", categoryId);
        } else {
            products = productService.getActiveProducts();
        }
        
        model.addAttribute("products", products);
        return "product/products";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable("id") Integer productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "product/product-details";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchProducts(query);
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", query);
        model.addAttribute("categories", categoryService.getActiveCategories());
        return "product/search-results";
    }
}
