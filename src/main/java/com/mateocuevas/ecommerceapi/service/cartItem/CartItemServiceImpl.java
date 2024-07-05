package com.mateocuevas.ecommerceapi.service.cartItem;

import com.mateocuevas.ecommerceapi.dto.CartItemDTO;
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
            if(product.getDiscount()>5){
                updateCartItem(existingCartItem, quantity, product.getDiscountPrice());
                updateCartTotalPrice(cart, quantity * product.getDiscountPrice());
                return existingCartItem;
            }else {
                updateCartItem(existingCartItem, quantity, product.getPrice());
                updateCartTotalPrice(cart, quantity * product.getPrice());
                return existingCartItem;
            }

        }

        return createNewCartItem(cart, product, quantity);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
         cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found with ID: " + cartItemId));
    }

    private CartItem findCartItemByProduct(Cart cart, Product product) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }

    private void updateCartItem(CartItem cartItem, int quantity, double productPrice) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * productPrice));
    }

    private void updateCartTotalPrice(Cart cart, double additionalPrice) {
        cart.setTotalPrice(cart.getTotalPrice() + additionalPrice);
    }

    private CartItem createNewCartItem(Cart cart, Product product, int quantity) {
        double totalPrice;
        cart.setTotalItems(cart.getTotalItems() + 1);
        if(product.getDiscount()>5){
            totalPrice = quantity * product.getDiscountPrice();

        }else{
            totalPrice = quantity * product.getPrice();
        }

        cart.setTotalPrice(cart.getTotalPrice() + totalPrice);

        return CartItem.builder()
                .id(null)
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }

    public CartItemDTO createCartItemDTO(CartItem cartItem) {

        CartItemDTO cartItemDTO = CartItemDTO.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .title(cartItem.getProduct().getTitle())
                .img(cartItem.getProduct().getImage())
                .totalPriceItem(cartItem.getTotalPrice())
                .build();
        if(cartItem.getProduct().getDiscount()>5){
            cartItemDTO.setPrice(cartItem.getProduct().getDiscountPrice());
        }
        return cartItemDTO;
    }

}
