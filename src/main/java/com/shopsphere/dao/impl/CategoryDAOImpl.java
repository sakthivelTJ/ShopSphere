package com.shopsphere.dao.impl;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Override
    public Category update(Category category) {
        return entityManager.merge(category);
    }

    @Override
    public Category findById(Integer categoryId) {
        return entityManager.find(Category.class, categoryId);
    }

    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c ORDER BY c.categoryId ASC", Category.class)
                .getResultList();
    }

    @Override
    public List<Category> findActiveCategories() {
        return entityManager.createQuery("SELECT c FROM Category c WHERE c.isActive = true ORDER BY c.categoryName ASC", Category.class)
                .getResultList();
    }

    @Override
    public void delete(Integer categoryId) {
        Category category = findById(categoryId);
        if (category != null) {
            entityManager.remove(category);
        }
    }
}
