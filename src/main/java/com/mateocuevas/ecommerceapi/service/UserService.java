package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User>findById(Long id);
}
