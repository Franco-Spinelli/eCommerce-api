package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.Product;

public interface ProductService {
    void saveProductInUserAdmin(Product product);
    public void fetchAndSaveProducts();
}
