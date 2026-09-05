package com.shopsphere.dao;

import com.shopsphere.entity.Product;
import java.util.List;

public interface ProductDAO {
    Product save(Product product);
    Product update(Product product);
    Product findById(Integer productId);
    List<Product> findAll();
    List<Product> findActiveProducts();
    List<Product> findByCategory(Integer categoryId);
    List<Product> searchProducts(String query);
    void delete(Integer productId);
}
