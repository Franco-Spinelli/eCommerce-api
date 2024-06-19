package com.mateocuevas.ecommerceapi.service.category;

import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.respository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(String name){
        Category category = categoryRepository.findByNameIgnoreCase(name);
        if(category!=null){
            return category;
        }
        Category newCategory = Category.builder()
                .name(name)
                .build();
        categoryRepository.save(newCategory);
        return newCategory;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    @Override
    public Optional<Category> findByNameIgnoreCase(String name) {
        return Optional.ofNullable(categoryRepository.findByNameIgnoreCase(name));
    }
}
