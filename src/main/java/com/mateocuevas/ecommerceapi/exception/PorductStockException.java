package com.mateocuevas.ecommerceapi.exception;

public class PorductStockException extends RuntimeException{
    public PorductStockException(String message,String productTitle) {
        super(message);
    }
}
