package com.mateocuevas.ecommerceapi.service.category;

import com.mateocuevas.ecommerceapi.entity.Category;

public interface CategoryService {
    Category saveCategory(Category category);
    Category findByName(String categoryName);
}
