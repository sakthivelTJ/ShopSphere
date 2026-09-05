package com.shopsphere.service;

import com.shopsphere.entity.Category;
import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category);
    Category getCategoryById(Integer categoryId);
    List<Category> getAllCategories();
    List<Category> getActiveCategories();
    void toggleCategoryStatus(Integer categoryId);
}
