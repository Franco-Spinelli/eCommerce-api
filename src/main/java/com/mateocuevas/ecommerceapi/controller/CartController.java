package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.CartDTO;
import com.mateocuevas.ecommerceapi.dto.CartItemDTO;
import com.mateocuevas.ecommerceapi.dto.CartItemRequest;
import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * This endpoint gets the cart of the user who is authenticated.
     * @return ResponseEntity with the cart of the user.
     */
    @GetMapping("/get")
    public ResponseEntity<CartDTO> getUserCart(){
        CartDTO cartDTO = cartService.getUserCart();
        return ResponseEntity.ok(cartDTO);
    }

    /**
     * This endpoint adds products to the cart of the authenticated user.
     * @param cartItemRequest The request containing the product ID and quantity to be added to the cart.
     * @return ResponseEntity with the updated cart of the user.
     */
    @PostMapping("/add-products")
    public ResponseEntity<?> addProductToCart(@RequestBody CartItemRequest cartItemRequest){
        CartDTO cartDTO = cartService.addProductToCart(cartItemRequest.getProductId(),cartItemRequest.getQuantity());
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/remove-product/{cartItemId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long cartItemId) {
        CartDTO cartDTO = cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok(cartDTO);
    }
}
