package com.mateocuevas.ecommerceapi.service.cartItem;

import com.mateocuevas.ecommerceapi.dto.CartItemDTO;
import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.Product;

public interface CartItemService {
    CartItem productToCartItem(Product product, Integer quantity, Cart cart);
    void saveCartItem(CartItem cartItem);
    void deleteCartItem(CartItem cartItem);
    CartItemDTO createCartItemDTO(CartItem cartItem);
    CartItem getCartItemById(Long cartItemId);
}
