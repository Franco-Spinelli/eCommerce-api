package com.mateocuevas.ecommerceapi.service.cart;

import com.mateocuevas.ecommerceapi.dto.CartDTO;
import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import org.springframework.http.ResponseEntity;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
    CartDTO getUserCart();
    void saveCart(Cart cart);
    void resetCart(Cart cart);
    void processCart(Cart cart);
    CartDTO removeCartItem(Long cartItemId);
}
