package com.shopsphere.dao;

import com.shopsphere.entity.ProductSize;
import java.util.List;

public interface ProductSizeDAO {
    ProductSize save(ProductSize productSize);
    ProductSize update(ProductSize productSize);
    ProductSize findById(Integer productSizeId);
    List<ProductSize> findByProductId(Integer productId);
    void delete(Integer productSizeId);
}
