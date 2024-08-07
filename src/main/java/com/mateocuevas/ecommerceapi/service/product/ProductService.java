package com.mateocuevas.ecommerceapi.service.product;

import com.mateocuevas.ecommerceapi.api.ProductApi;
import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface ProductService {
    void deleteById(Long id);
    boolean existByTitle(String title);
    Set<ProductDTO> getAllProducts();
    ProductDTO findByTitle(String title);
    Optional<Product> findById(Long id);
    Set<ProductDTO> findByPriceBetween(double minPrice, double maxPrice);
    Set<ProductDTO> findProductByCategory(String categoryRequest);
    void processProductStock(Long productId, Integer quantity);
    Product checkStock(Long productId, Integer quantity);
    ProductDTO productToProductDto(Product product);
    Product productDtoToProduct(ProductDTO productDTO);
    void saveProductInUserAdmin(Product product);
    void fetchAndSaveProducts();
    void fetchAndSaveProductsFromExtendedSource();
    boolean existsByTitle(String title);
    Product save(Product product);
    void updateRating(Long idProduct, Double rate);

}
