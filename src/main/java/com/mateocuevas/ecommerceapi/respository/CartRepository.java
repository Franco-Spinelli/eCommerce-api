package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    /**
     * Retrieves the cart of a specific user using their id.
     *
     * @param userId The ID of the user.
     * @return The cart of a specific user.
     */
    @Query("SELECT c FROM Cart c " +
            "WHERE c.customer.id = :userId")
    Cart getCartByUserId(@Param("userId") Long userId);
}
