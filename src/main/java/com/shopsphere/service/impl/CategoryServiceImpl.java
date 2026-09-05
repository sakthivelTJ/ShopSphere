package com.shopsphere.service.impl;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.entity.Category;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    @Transactional
    public Category addCategory(Category category) {
        return categoryDAO.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {
        Category existing = getCategoryById(category.getCategoryId());
        existing.setCategoryName(category.getCategoryName());
        existing.setDescription(category.getDescription());
        if (category.getIsActive() != null) {
            existing.setIsActive(category.getIsActive());
        }
        return categoryDAO.update(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Integer categoryId) {
        Category category = categoryDAO.findById(categoryId);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }
        return category;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getActiveCategories() {
        return categoryDAO.findActiveCategories();
    }

    @Override
    @Transactional
    public void toggleCategoryStatus(Integer categoryId) {
        Category category = getCategoryById(categoryId);
        category.setIsActive(!category.getIsActive());
        categoryDAO.update(category);
    }
}
