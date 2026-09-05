package com.shopsphere.service;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.entity.Product;
import java.util.List;

public interface ProductService {
    Product addProduct(ProductRequest productRequest);
    Product updateProduct(Integer productId, ProductRequest productRequest);
    Product getProductById(Integer productId);
    List<Product> getAllProducts();
    List<Product> getActiveProducts();
    List<Product> getProductsByCategory(Integer categoryId);
    List<Product> searchProducts(String query);
    void toggleProductStatus(Integer productId);
}
