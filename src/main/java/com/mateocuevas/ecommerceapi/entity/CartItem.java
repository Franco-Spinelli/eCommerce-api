package com.mateocuevas.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="product_id",nullable = false)
    private Product product;
    private double totalPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
}
