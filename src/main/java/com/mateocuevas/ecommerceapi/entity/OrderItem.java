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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="product_id",nullable = false)
    private Product product;
    private double totalPrice;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
