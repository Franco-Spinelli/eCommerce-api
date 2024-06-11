package com.mateocuevas.ecommerceapi.service.cartItem;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.Product;

public interface CartItemService {
    CartItem productToCartItem(Product product, Integer quantity, Cart cart);
    CartItem saveCartItem(CartItem cartItem);
}
