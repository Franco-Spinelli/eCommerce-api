package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;

import javax.management.relation.Role;
import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User>findById(Long id);
    Optional<User>findByRole(UserRole role);
}
