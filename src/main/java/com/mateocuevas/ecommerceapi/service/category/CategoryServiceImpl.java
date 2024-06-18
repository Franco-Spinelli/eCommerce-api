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
        if(categoryRepository.existByName(name)){
            throw new EntityExistsException("The category "+name+" already exists ");
        }
        return Category.builder()
                .name(name)
                .build();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        return Optional.ofNullable(categoryRepository.findByName(categoryName));
    }
}
