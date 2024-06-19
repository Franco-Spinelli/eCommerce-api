package com.mateocuevas.ecommerceapi.service.admin;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.exception.ProductAlreadyExistsException;
import com.mateocuevas.ecommerceapi.service.product.ProductService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {


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

    public String deleteProduct(String title) {
        if (productService.existByTitle(title)) {
            throw new EntityNotFoundException("The product doesn't exist");
        }
        productService.deleteByTitle(title);
        return "Product has been successfully deleted.";
    }
}
