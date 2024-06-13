package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Optional<Product> findByTitle(String title);
    Set<Product> findByCategory(Category category);
    Set<Product> findByPriceBetween(double minPrice, double maxPrice);
}
