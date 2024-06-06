package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private  final UserService userService;
    private static final String API_URL = "https://fakestoreapi.com/products";
    private final RestTemplate restTemplate;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, UserService userService, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }


    public void fetchAndSaveProducts() {
        Product[] products = restTemplate.getForObject(API_URL, Product[].class);
        if (products != null) {
            for (Product product : products) {
                saveProduct(product);
            }
        }
    }
    @Override
    public void saveProduct(Product product) {
        User userAdmin = new User();
        userAdmin = userService.findById(1L).orElseThrow();
        // Fetch or create the category
        String categoryName = product.getCategory().getName();
        Category category = categoryService.findByName(categoryName);
        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            category = categoryService.saveCategory(category);
        }
        product.setCategory(category);
        product.setAdmin(userAdmin);
        // Save the product
        productRepository.save(product);
    }

}


