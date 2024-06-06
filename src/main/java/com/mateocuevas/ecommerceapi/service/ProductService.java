package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.Product;

public interface ProductService {
    void saveProduct(Product product);
    public void fetchAndSaveProducts();
}
