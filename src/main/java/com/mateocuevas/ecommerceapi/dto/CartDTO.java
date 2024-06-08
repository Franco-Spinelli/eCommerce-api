package com.mateocuevas.ecommerceapi.dto;

import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private String customer;
    private Set<CartItemDTO> cartItems = new HashSet<>();
    private Double totalItems;
    private Double totalPrice;

    public void addCartItem(CartItemDTO cartItemDTO) {
        if (this.cartItems == null) {
            this.cartItems = new HashSet<>();
        }
        this.cartItems.add(cartItemDTO);
    }
}
