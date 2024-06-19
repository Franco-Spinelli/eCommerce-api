package com.mateocuevas.ecommerceapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Rating;
import com.mateocuevas.ecommerceapi.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private Float price;
    private String description;
    private Rating rating;
    private String image;
    private String category;
    private Integer Stock;
}
