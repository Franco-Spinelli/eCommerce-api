package com.mateocuevas.ecommerceapi.service.cartItem;

import com.mateocuevas.ecommerceapi.entity.CartItem;

public interface CartItemService {
    CartItem productToCartItem(Long productId,Integer quantity);
    void saveCartItem(CartItem cartItem);
}
