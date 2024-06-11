package com.mateocuevas.ecommerceapi.service.cartItem;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.exception.ProductStockException;
import com.mateocuevas.ecommerceapi.respository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService{
    private final CartItemRepository cartItemRepository;



    /**
     * This method searches for the product that arrives by parameter in the database, and verifies if it is in stock.
     * If there is the requested quantity, the stock is updated and the cartitem object is returned
     * @param product The product that wants to be added to the cart.
     * @param quantity The quantity that wants to be added to the cart.
     * @return The CartItem with the data of the product, quantity and the total price.
     * @throws EntityNotFoundException If the product id that was sent is not in the database .
     * @throws ProductStockException If there is not enough stock of the product.
     */

    @Override
    public CartItem productToCartItem(Product product, Integer quantity, Cart cart) {
        CartItem existingCartItem = findCartItemByProduct(cart, product);

        if (existingCartItem != null) {
            updateCartItem(existingCartItem, quantity, product.getPrice());
            updateCartTotalPrice(cart, quantity * product.getPrice());
            return existingCartItem;
        }

        return createNewCartItem(cart, product, quantity);
    }

    @Override
    public CartItem  saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }


    private CartItem findCartItemByProduct(Cart cart, Product product) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }

    private void updateCartItem(CartItem cartItem, int quantity, float productPrice) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * productPrice));
    }

    private void updateCartTotalPrice(Cart cart, double additionalPrice) {
        cart.setTotalPrice(cart.getTotalPrice() + additionalPrice);
    }

    private CartItem createNewCartItem(Cart cart, Product product, int quantity) {
        cart.setTotalItems(cart.getTotalItems() + 1);
        double totalPrice = quantity * product.getPrice();
        cart.setTotalPrice(cart.getTotalPrice() + totalPrice);

        return CartItem.builder()
                .id(null)
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }

}
