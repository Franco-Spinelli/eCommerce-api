package com.mateocuevas.ecommerceapi.service.cart;

import com.mateocuevas.ecommerceapi.dto.CartDTO;
import com.mateocuevas.ecommerceapi.dto.CartItemDTO;
import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.exception.ProductStockException;
import com.mateocuevas.ecommerceapi.respository.CartRepository;

import com.mateocuevas.ecommerceapi.respository.ProductRepository;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserService userService;

    private final ProductRepository productRepository;

    private final CartItemService cartItemService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserService userService, ProductRepository productRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productRepository = productRepository;
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
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        User user=userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        Cart cart=cartRepository.getCartByUserId(user.getId());
        Product product=productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        if(product.getStock()<quantity){
            throw new ProductStockException("there is not enough stock of the product:", product.getTitle());
        }
        product.setStock(product.getStock()-quantity);
        productRepository.save(product);

        CartItem cartItem=cartItemService.productToCartItem(product,quantity,cart);
        cartItemService.saveCartItem(cartItem);
        saveCart(cartItem.getCart());
        return createCartDTO(cartItem.getCart());
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
        return createCartDTO(cart);
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
            cartDTO.addCartItem(createCartItemDTO(c));
        }
        return cartDTO;
    }

    private CartItemDTO createCartItemDTO(CartItem cartItem) {
        return CartItemDTO.builder()
                .quantity(cartItem.getQuantity())
                .title(cartItem.getProduct().getTitle())
                .price(cartItem.getProduct().getPrice())
                .totalPriceItem(cartItem.getTotalPrice())
                .build();
    }
}
