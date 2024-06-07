package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.respository.CartRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
