package com.mateocuevas.ecommerceapi.service.category;

import com.mateocuevas.ecommerceapi.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getCategories();
    Category createCategory(String name);
    Category saveCategory(Category category);
    Category findByName(String categoryName);
    Optional<Category>  findByNameIgnoreCase(String name);
}
