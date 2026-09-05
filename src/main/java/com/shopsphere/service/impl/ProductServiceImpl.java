package com.shopsphere.service.impl;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.ProductSizeDAO;
import com.shopsphere.dto.ProductRequest;
import com.shopsphere.entity.Category;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.ProductSize;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final ProductSizeDAO productSizeDAO;

    public ProductServiceImpl(ProductDAO productDAO, CategoryDAO categoryDAO, ProductSizeDAO productSizeDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.productSizeDAO = productSizeDAO;
    }

    @Override
    @Transactional
    public Product addProduct(ProductRequest productRequest) {
        Category category = categoryDAO.findById(productRequest.getCategoryId());
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with ID: " + productRequest.getCategoryId());
        }

        Product product = new Product(
                category,
                productRequest.getProductName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getDiscountPercent(),
                productRequest.getImageUrl(),
                productRequest.getIsActive() != null ? productRequest.getIsActive() : true
        );

        Product savedProduct = productDAO.save(product);

        // If initial size/stock provided, create default ProductSize
        String sizeLabel = productRequest.getDefaultSizeLabel();
        if (sizeLabel == null || sizeLabel.trim().isEmpty()) {
            sizeLabel = "Standard";
        }
        Integer stock = productRequest.getInitialStock() != null ? productRequest.getInitialStock() : 10;
        ProductSize defaultSize = new ProductSize(savedProduct, sizeLabel, stock, "SKU-" + savedProduct.getProductId() + "-" + sizeLabel, true);
        productSizeDAO.save(defaultSize);

        return savedProduct;
    }

    @Override
    @Transactional
    public Product updateProduct(Integer productId, ProductRequest productRequest) {
        Product existingProduct = getProductById(productId);
        Category category = categoryDAO.findById(productRequest.getCategoryId());
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with ID: " + productRequest.getCategoryId());
        }

        existingProduct.setCategory(category);
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setDiscountPercent(productRequest.getDiscountPercent());
        if (productRequest.getImageUrl() != null && !productRequest.getImageUrl().trim().isEmpty()) {
            existingProduct.setImageUrl(productRequest.getImageUrl());
        }
        if (productRequest.getIsActive() != null) {
            existingProduct.setIsActive(productRequest.getIsActive());
        }

        return productDAO.update(existingProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Integer productId) {
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with ID: " + productId);
        }
        return product;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getActiveProducts() {
        return productDAO.findActiveProducts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productDAO.findByCategory(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getActiveProducts();
        }
        return productDAO.searchProducts(query);
    }

    @Override
    @Transactional
    public void toggleProductStatus(Integer productId) {
        Product product = getProductById(productId);
        product.setIsActive(!product.getIsActive());
        productDAO.update(product);
    }
}
