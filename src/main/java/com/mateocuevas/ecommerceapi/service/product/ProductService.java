package com.mateocuevas.ecommerceapi.service.product;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;
import jakarta.persistence.EntityNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

public interface ProductService {

    void deleteByTitle(String title);
    void save(Product product);
    Boolean existByTitle(String title);
    Set<ProductDTO> getAllProducts();
    ProductDTO findByTitle(String title);
    Set<ProductDTO> findByPriceBetween(double minPrice, double maxPrice);
    Set<ProductDTO> findProductByCategory(String categoryRequest);
    Product checkStock(Long productId, Integer quantity);
    ProductDTO productToProductDto(Product product);
    Product productDtoToProduct(ProductDTO productDTO);
    void saveProductInUserAdmin(Product product);
    void fetchAndSaveProducts();

}
