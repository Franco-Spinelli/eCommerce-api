package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
}
