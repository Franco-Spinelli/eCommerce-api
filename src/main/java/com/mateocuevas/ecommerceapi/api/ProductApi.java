package com.mateocuevas.ecommerceapi.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductApi {

    private Long id;
    private String title;
    private String description;
    private String category;
    private Double price;
    private Double discountPercentage;
    private Integer stock;
    private List<String> tags;
    private String brand;
    private String sku;
    private Double weight;
    private DimensionsDTO dimensions;
    private String warrantyInformation;
    private String shippingInformation;
    private String availabilityStatus;
    private List<ReviewDTO> reviews;
    private String returnPolicy;
    private Integer minimumOrderQuantity;
    private MetaDTO meta;
    private List<String> images;
    private String thumbnail;



    public static class DimensionsDTO {
        private Double width;
        private Double height;
        private Double depth;
    }

    public static class ReviewDTO {
        private Integer rating;
        private String comment;
        private String date;
        private String reviewerName;
        private String reviewerEmail;
    }

    public static class MetaDTO {
        private String createdAt;
        private String updatedAt;
        private String barcode;
        private String qrCode;
    }
}
