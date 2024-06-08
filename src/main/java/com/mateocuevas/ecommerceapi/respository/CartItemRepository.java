package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

}
