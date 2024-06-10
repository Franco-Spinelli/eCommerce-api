package com.mateocuevas.ecommerceapi.exception;

import lombok.Getter;

@Getter
public class ProductStockException extends RuntimeException{
    private final String productTitle;

    public ProductStockException(String message, String productTitle) {
        super(message);
        this.productTitle = productTitle;
    }

}
