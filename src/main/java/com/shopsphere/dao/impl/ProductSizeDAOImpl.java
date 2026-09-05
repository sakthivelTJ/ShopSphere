package com.shopsphere.dao.impl;

import com.shopsphere.dao.ProductSizeDAO;
import com.shopsphere.entity.ProductSize;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductSizeDAOImpl implements ProductSizeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductSize save(ProductSize productSize) {
        entityManager.persist(productSize);
        return productSize;
    }

    @Override
    public ProductSize update(ProductSize productSize) {
        return entityManager.merge(productSize);
    }

    @Override
    public ProductSize findById(Integer productSizeId) {
        return entityManager.find(ProductSize.class, productSizeId);
    }

    @Override
    public List<ProductSize> findByProductId(Integer productId) {
        return entityManager.createQuery("SELECT ps FROM ProductSize ps WHERE ps.product.productId = :productId", ProductSize.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public void delete(Integer productSizeId) {
        ProductSize ps = findById(productSizeId);
        if (ps != null) {
            entityManager.remove(ps);
        }
    }
}
