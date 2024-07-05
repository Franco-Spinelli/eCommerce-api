package com.mateocuevas.ecommerceapi.service.cart;

import com.mateocuevas.ecommerceapi.dto.CartDTO;
import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.respository.CartRepository;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.product.ProductService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserService userService;

    private final ProductService productService;

    private final CartItemService cartItemService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserService userService, ProductService productService, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }


    /**
     * This method verifies which user is authenticated. Searches the database for your shopping cart to be able to add
     * the product returned by the function productToCartItem.
     * In addition, it saves the new cartItem in the database with the id of the cart to which it was added.
     * @param productId The id of the product that wants to be added to the cart.
     * @param quantity The quantity that wants to be added to the cart.
     * @return The Cart of the user.
     * @throws IllegalStateException If there is no authenticated user .
     */
    @Override
    @Transactional
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        User user=userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        Cart cart=cartRepository.getCartByUserId(user.getId());
        Product product = productService.checkStock(productId,quantity);
        CartItem cartItem=cartItemService.productToCartItem(product,quantity,cart);
        cartItemService.saveCartItem(cartItem);
        saveCart(cart);
        cart.getCartItems().add(cartItem);
        return createCartDTO(cartItem.getCart());
    }
    public CartDTO removeCartItem(Long cartItemId) {
        User user = userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        Cart cart = cartRepository.getCartByUserId(user.getId());
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        if (cartItem != null && cartItem.getCart().getId().equals(cart.getId())) {
            cart.setTotalPrice(cart.getTotalPrice() - cartItem.getTotalPrice());
            cart.setTotalItems(cart.getTotalItems() - 1);
            cartItemService.deleteCartItem(cartItem);
            saveCart(cart);
            return createCartDTO(cart);
        } else {
            throw new IllegalArgumentException("CartItem not found or does not belong to the current user's cart");
        }
    }
    /**
     * This method get all the carItems and the price of all them.
     * @return The Cart of the user who is authenticated.
     * @throws IllegalStateException If there is no authenticated user .
     */
    @Override
    public CartDTO getUserCart() {
        User user=userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        Cart cart = cartRepository.getCartByUserId(user.getId());
        if (cart != null) {
            updateCartPrices(cart);
        }
        return createCartDTO(cart);
    }
    private void updateCartPrices(Cart cart) {
        cart.setTotalPrice(0);
        for (CartItem item : cart.getCartItems()) {
            Product product = productService.findById(item.getProduct().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Product not found"));
            if(product.getDiscount().compareTo(4.9) > 0){
                item.setProduct(product);
                item.setTotalPrice(product.getDiscountPrice() * item.getQuantity());
            }else {
                item.setProduct(product);
                item.setTotalPrice(product.getPrice() * item.getQuantity());
            }
        cart.setTotalPrice(cart.getTotalPrice() + item.getTotalPrice());
        }
        cartRepository.save(cart);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    private CartDTO createCartDTO(Cart cart) {
        CartDTO cartDTO = CartDTO.builder()
                .customer(cart.getCustomer().getFirstName()+ " " + cart.getCustomer().getLastName())
                .totalItems(cart.getTotalItems())
                .totalPrice(cart.getTotalPrice())
                .build();
        for (CartItem c :cart.getCartItems()) {
            cartDTO.addCartItem(cartItemService.createCartItemDTO(c));
        }
        return cartDTO;
    }
    public void processCart(Cart cart) {
        for (CartItem cartProduct : cart.getCartItems()) {

            productService.processProductStock(cartProduct.getProduct().getId(),cartProduct.getQuantity());
        }
    }

    public void resetCart(Cart cart){
        cart.setCartItems(new HashSet<>());
        cart.setTotalPrice(0.0);
        cart.setTotalItems(0);

        saveCart(cart);
    }


}
