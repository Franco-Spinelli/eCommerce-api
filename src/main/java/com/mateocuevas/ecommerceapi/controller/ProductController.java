package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.service.category.CategoryService;
import com.mateocuevas.ecommerceapi.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/get-categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories=categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/get-products")
    public ResponseEntity<Set<ProductDTO>> getAllProducts(){
        Set<ProductDTO> productDTOS=productService.getAllProducts();
        return ResponseEntity.ok(productDTOS);
    }

    /**
     * This endpoint fetches products from an external API and saves them into the local database.
     *
     * It is intended to be used only once to initially populate the database with products.
     * Before using this endpoint, make sure that an admin user has been created in the system.
     *
     * @return A confirmation message indicating that the products have been fetched and saved successfully.
     */
    @GetMapping("/fetch-products")
    public String fetchAndSaveProducts() {
        productService.fetchAndSaveProducts();
        return "Products fetched and saved successfully!";
    }
    /**
     * This endpoint fetches additional products from an extended external API source and saves them into the local database.
     * It retrieves a larger set of products compared to the default source.
     *
     * It is intended for scenarios where more products are needed beyond the initial database population.
     * Before using this endpoint, ensure that an admin user exists in the system.
     *
     * @return A confirmation message indicating that the additional products have been fetched and saved successfully.
     */
    @GetMapping("/fetch-more-products")
    public String fetchAndSaveProducts2() {
        productService.fetchAndSaveProductsFromExtendedSource();
        return "Additional products fetched and saved successfully!";
    }
}
