package com.shopsphere.service;

import com.shopsphere.entity.ProductSize;
import java.util.List;

public interface ProductSizeService {
    ProductSize addSizeToProduct(Integer productId, String sizeLabel, Integer stockQuantity, String skuCode);
    ProductSize updateStock(Integer productSizeId, Integer newQuantity);
    List<ProductSize> getSizesByProduct(Integer productId);
    ProductSize getProductSizeById(Integer productSizeId);
}
