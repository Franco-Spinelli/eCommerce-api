package com.mateocuevas.ecommerceapi.dto;

import com.mateocuevas.ecommerceapi.entity.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private String title;
    private String img;
    private Float price;
    private double totalPriceItem;
}
