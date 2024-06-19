package com.mateocuevas.ecommerceapi.exception;

import lombok.Getter;

@Getter
public class ProductAlreadyExistsException extends RuntimeException{
    String productTitle;

    public ProductAlreadyExistsException(String message, String productTitle) {
        super(message);
        this.productTitle = productTitle;
    }
}
