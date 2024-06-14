package com.mateocuevas.ecommerceapi.respository;

import com.mateocuevas.ecommerceapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByStreetAndNumberAndCity(String street, String number, String city);
}
