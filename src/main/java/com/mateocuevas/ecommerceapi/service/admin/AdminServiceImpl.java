package com.mateocuevas.ecommerceapi.service.admin;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.exception.ProductAlreadyExistsException;
import com.mateocuevas.ecommerceapi.service.category.CategoryService;
import com.mateocuevas.ecommerceapi.service.product.ProductService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productService.existByTitle(productDTO.getTitle())) {
            throw new ProductAlreadyExistsException("The product already exist!", productDTO.getTitle());
        }
        Product product = productService.productDtoToProduct(productDTO);
        product.setAdmin(userService.findByRole(UserRole.ROLE_ADMIN).orElseThrow());
        productService.save(product);
        return productDTO;
    }

    public String deleteProduct(Long id) {
        Optional<Product>optionalProduct = productService.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("The product doesn't exist");
        }
        productService.deleteById(id);
        return "Product has been successfully deleted.";
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Optional<Product> optionalProduct = productService.findById(productDTO.getId());
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            // Update only non-null fields
            if (productDTO.getStock() != null) {
                existingProduct.setStock(productDTO.getStock());
            }
            if (productDTO.getPrice() != null) {
                existingProduct.setPrice(productDTO.getPrice());
            }
            if (productDTO.getImage() != null) {
                existingProduct.setImage(productDTO.getImage());
            }
            if (productDTO.getDescription() != null) {
                existingProduct.setDescription(productDTO.getDescription());
            }
            if (productDTO.getTitle() != null) {
                if(!productService.existByTitle(productDTO.getTitle())) {
                    existingProduct.setTitle(productDTO.getTitle());
                }else {
                    throw new ProductAlreadyExistsException("One product is already exist with this title",productDTO.getTitle());
                }
            }
            if (productDTO.getRating() != null) {
                existingProduct.setRating(productDTO.getRating());
            }

            // Handle category
            if (productDTO.getCategory() != null) {
                Category category = categoryService.findByName(productDTO.getCategory());
                existingProduct.setCategory(category);
            }

            // Handle admin user
            User user = userService.findByRole(UserRole.ROLE_ADMIN).orElseThrow(() -> new EntityNotFoundException("Admin user not found"));
            existingProduct.setAdmin(user);

            // Save the updated product
            productService.save(existingProduct);

            // Convert and return the updated product
            return productService.productToProductDto(existingProduct);
        } else {
            throw new EntityNotFoundException("Product not found");
        }
    }
}
