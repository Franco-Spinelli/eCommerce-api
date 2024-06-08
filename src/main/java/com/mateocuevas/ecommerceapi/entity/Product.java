package com.mateocuevas.ecommerceapi.entity;

import com.mateocuevas.ecommerceapi.enums.UserRole;
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
@Table(name="products",uniqueConstraints =@UniqueConstraint(columnNames = ("name")))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Float price;
    @Lob
    @Column(length = 1000)
    private String description;
    @Embedded
    private Rating rating;
    @Lob
    private String image;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;
    private Integer Stock;

}
