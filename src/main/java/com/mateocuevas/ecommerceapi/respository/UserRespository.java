package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.dto.UserDto;
import com.mateocuevas.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
