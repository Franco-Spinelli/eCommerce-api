package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
public class CartController {

    private CartService cartService;

    /**
     * This endpoint gets the cart of the user who is authenticated.
     * @return ResponseEntity with the cart of the user.
     */
    @GetMapping
    public ResponseEntity<Cart> getUserCart(){
        return ResponseEntity.ok(cartService.getUserCart());
    }

    /**
     * This endpoint adds products to the cart of the user who is authenticated.
     * @param productId The id of the product that wants to be added to the cart.
     * @param quantity The quantity that wants to be added to the cart.
     * @return ResponseEntity with the cart of the user.
     */
    @PostMapping("/addProducts")
    public ResponseEntity<?> addProductToCart(@RequestBody Long productId,Integer quantity){
        return ResponseEntity.ok(cartService.addProductToCart(productId,quantity));
    }
}
