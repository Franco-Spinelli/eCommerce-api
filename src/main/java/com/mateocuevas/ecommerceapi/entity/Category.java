package com.mateocuevas.ecommerceapi.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mateocuevas.ecommerceapi.config.CategoryDeserializer;
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
@JsonDeserialize(using = CategoryDeserializer.class)
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
