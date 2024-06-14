package com.mateocuevas.ecommerceapi.service.address;

import com.mateocuevas.ecommerceapi.dto.AddressDTO;
import com.mateocuevas.ecommerceapi.entity.Address;

import java.util.Optional;
import java.util.Set;

public interface AddressService {

    Address addAddress(Address address);
    AddressDTO updateAddress(AddressDTO addressDTO);
    String deleteAddress(AddressDTO addressDTO);
    Set<AddressDTO> getAddresses();
    Address addressDtoToAddress(AddressDTO addressDTO);
    AddressDTO addressToAddressDto(Address address);
    Address findAddress(String street, String number, String city);
}
