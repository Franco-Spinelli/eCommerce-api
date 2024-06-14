package com.mateocuevas.ecommerceapi.exception;

import lombok.Getter;

@Getter
public class AddressAlreadyExistsException extends RuntimeException{
    String address;

    public AddressAlreadyExistsException(String message, String address) {
        super(message);
        this.address = address;
    }
}
