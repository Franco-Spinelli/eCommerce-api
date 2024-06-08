package com.mateocuevas.ecommerceapi.service.cart;

import com.mateocuevas.ecommerceapi.entity.Cart;
import org.springframework.http.ResponseEntity;

public interface CartService {
    Cart addProductToCart(Long productId, Integer quantity);
    Cart getUserCart();
    void saveCart(Cart cart);
}
