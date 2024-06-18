package com.mateocuevas.ecommerceapi.service.admin;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;

public interface AdminService {
    ProductDTO createProduct(ProductDTO productDTO);
    String deleteProduct(String title);
}
