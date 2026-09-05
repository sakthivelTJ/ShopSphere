package com.shopsphere.service.impl;

import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.ProductSizeDAO;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.ProductSize;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.ProductSizeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeDAO productSizeDAO;
    private final ProductDAO productDAO;

    public ProductSizeServiceImpl(ProductSizeDAO productSizeDAO, ProductDAO productDAO) {
        this.productSizeDAO = productSizeDAO;
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public ProductSize addSizeToProduct(Integer productId, String sizeLabel, Integer stockQuantity, String skuCode) {
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with ID: " + productId);
        }

        ProductSize ps = new ProductSize(product, sizeLabel, stockQuantity, skuCode, true);
        return productSizeDAO.save(ps);
    }

    @Override
    @Transactional
    public ProductSize updateStock(Integer productSizeId, Integer newQuantity) {
        ProductSize ps = getProductSizeById(productSizeId);
        ps.setStockQuantity(newQuantity);
        ps.setIsAvailable(newQuantity > 0);
        return productSizeDAO.update(ps);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductSize> getSizesByProduct(Integer productId) {
        return productSizeDAO.findByProductId(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSize getProductSizeById(Integer productSizeId) {
        ProductSize ps = productSizeDAO.findById(productSizeId);
        if (ps == null) {
            throw new ResourceNotFoundException("Product size option not found with ID: " + productSizeId);
        }
        return ps;
    }
}
