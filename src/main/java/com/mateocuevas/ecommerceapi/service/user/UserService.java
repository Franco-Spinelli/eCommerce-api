package com.mateocuevas.ecommerceapi.service.user;

import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserAuthenticated();
    void save(User user);
    Optional<User>findById(Long id);
    Optional<User>findByRole(UserRole role);
    Optional<User> findByUsername(String username);
}
