package com.mateocuevas.ecommerceapi.service.admin;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.exception.ProductAlreadyExistsException;
import com.mateocuevas.ecommerceapi.respository.ProductRepository;
import com.mateocuevas.ecommerceapi.service.product.ProductService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{


    private final ProductService productService;
    private final UserService userService;

    public ProductDTO createProduct(ProductDTO productDTO)
    {
        if(productService.existsByTitle(productDTO.getTitle())){
           throw new ProductAlreadyExistsException("The product already exist!", productDTO.getTitle());
        }
        Product product=productService.productDtoToProduct(productDTO);
        product.setAdmin(userService.findByRole(UserRole.ROLE_ADMIN).orElseThrow());
        productService.save(product);
        return productDTO;
    }

    @Override
    public String deleteProduct(String title) {
        return null;
    }
}
