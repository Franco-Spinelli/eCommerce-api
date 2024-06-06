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

    @GetMapping("/fetch-products")
    public String fetchAndSaveProducts() {
        productService.fetchAndSaveProducts();
        return "Products fetched and saved successfully!";
    }
}
