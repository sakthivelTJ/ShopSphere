package com.shopsphere.dao;

import com.shopsphere.entity.Category;
import java.util.List;

public interface CategoryDAO {
    Category save(Category category);
    Category update(Category category);
    Category findById(Integer categoryId);
    List<Category> findAll();
    List<Category> findActiveCategories();
    void delete(Integer categoryId);
}
