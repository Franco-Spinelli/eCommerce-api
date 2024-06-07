package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
}
