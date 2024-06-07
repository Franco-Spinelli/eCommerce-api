package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Optional<User> findByRole(UserRole role);
}
