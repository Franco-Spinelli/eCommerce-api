package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
