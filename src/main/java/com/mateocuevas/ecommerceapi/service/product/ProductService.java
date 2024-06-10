package com.mateocuevas.ecommerceapi.service.product;

import com.mateocuevas.ecommerceapi.entity.Product;

public interface ProductService {
    void saveProductInUserAdmin(Product product);
    void fetchAndSaveProducts();
}
