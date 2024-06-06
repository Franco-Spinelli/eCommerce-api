package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;

public interface CategoryService {
    Category saveCategory(Category category);
    Category findByName(String categoryName);
}
