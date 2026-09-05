package com.shopsphere.dao.impl;

import com.shopsphere.dao.ProductDAO;
import com.shopsphere.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product save(Product product) {
        entityManager.persist(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        return entityManager.merge(product);
    }

    @Override
    public Product findById(Integer productId) {
        return entityManager.find(Product.class, productId);
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.productSizes ORDER BY p.productId DESC", Product.class)
                .getResultList();
    }

    @Override
    public List<Product> findActiveProducts() {
        return entityManager.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.productSizes WHERE p.isActive = true AND p.category.isActive = true ORDER BY p.productId DESC", Product.class)
                .getResultList();
    }

    @Override
    public List<Product> findByCategory(Integer categoryId) {
        return entityManager.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.productSizes WHERE p.category.categoryId = :catId AND p.isActive = true", Product.class)
                .setParameter("catId", categoryId)
                .getResultList();
    }

    @Override
    public List<Product> searchProducts(String query) {
        String searchPattern = "%" + query.toLowerCase() + "%";
        return entityManager.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.productSizes WHERE p.isActive = true AND (LOWER(p.productName) LIKE :q OR LOWER(p.description) LIKE :q)", Product.class)
                .setParameter("q", searchPattern)
                .getResultList();
    }

    @Override
    public void delete(Integer productId) {
        Product product = findById(productId);
        if (product != null) {
            entityManager.remove(product);
        }
    }
}
