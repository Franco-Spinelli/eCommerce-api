package com.mateocuevas.ecommerceapi.service.product;

import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.respository.ProductRepository;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import com.mateocuevas.ecommerceapi.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private  final UserService userService;
    private static final String API_URL = "https://fakestoreapi.com/products";
    private final RestTemplate restTemplate;

    /**
     * This method fetches a list of products from an external API and saves them into the local database.
     *
     * It uses `RestTemplate` to make an HTTP GET request to the URL specified by `API_URL`
     * and retrieves an array of `Product` objects.
     *
     * It then iterates over the array of products and calls the `saveProductInUserAdmin` method
     * to save each product into the local database.
     */
    public void fetchAndSaveProducts() {
        Product[] products = restTemplate.getForObject(API_URL, Product[].class);
        if (products != null) {
            for (Product product : products) {
                saveProductInUserAdmin(product);
            }
        }
    }
    /**
     * This method assigns an admin user to a product and saves it into the local database.
     * It also fetches or creates the appropriate category for the product.
     *
     * It first fetches the admin user by their ID, then ensures the product's category exists in the database,
     * and finally, it sets the admin and category to the product before saving it to the repository.
     *
     * @param product The product to be saved.
     */
    @Override
    public void saveProductInUserAdmin(Product product) {
        User userAdmin = new User();
        userAdmin = userService.findByRole(UserRole.ADMIN).orElseThrow();
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
        product.setStock(20);
        // Save the product
        productRepository.save(product);
    }

}


