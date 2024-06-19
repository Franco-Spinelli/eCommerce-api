package com.mateocuevas.ecommerceapi.exception;

import lombok.Getter;

@Getter
public class NoDeliveryAddressFoundException extends RuntimeException {
    public NoDeliveryAddressFoundException(String message) {
        super(message);
    }
}
