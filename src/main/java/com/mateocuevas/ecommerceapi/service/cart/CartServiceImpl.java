package com.mateocuevas.ecommerceapi.service.cart;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.exception.PorductStockException;
import com.mateocuevas.ecommerceapi.respository.CartRepository;

import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartItemService cartItemService;

    /**
     * This method verifies which user is authenticated. Searches the database for your shopping cart to be able to add
     * the product returned by the function productToCartItem.
     * In addition, it saves the new cartItem in the database with the id of the cart to which it was added.
     * @param productId The id of the product that wants to be added to the cart.
     * @param quantity The quantity that wants to be added to the cart.
     * @return The Cart of the user.
     * @throws IllegalStateException If there is no authenticated user .
     */
    public Cart addProductToCart(Long productId, Integer quantity) {
        User user=userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Usuario no autenticado"));
        Cart cart=cartRepository.getCartByUserId(user.getId());
        CartItem cartItem=cartItemService.productToCartItem(productId,quantity);
        cartItem.setCart(cart);
        cartItemService.saveCartItem(cartItem);
        return cart;
    }

    /**
     * This method get all the carItems and the price of all them.
     * @return The Cart of the user who is authenticated.
     * @throws IllegalStateException If there is no authenticated user .
     */
    @Override
    public Cart getUserCart() {
        User user=userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Usuario no autenticado"));
        return cartRepository.getCartByUserId(user.getId());
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
