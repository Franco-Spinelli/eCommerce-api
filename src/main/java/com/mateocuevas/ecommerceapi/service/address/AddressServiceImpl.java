package com.mateocuevas.ecommerceapi.service.address;

import com.mateocuevas.ecommerceapi.dto.AddressDTO;
import com.mateocuevas.ecommerceapi.entity.Address;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.exception.AddressAlreadyExistsException;
import com.mateocuevas.ecommerceapi.respository.AddressRepository;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserService userService;
    private final AddressRepository addressRepository;



    @Override
    public Address addAddress(Address address) {
        User user =userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        Address existAddress = findAddress(address.getStreet(),address.getNumber(),address.getCity());
        if(existAddress==null){
            address.setCustomer(user);
            addressRepository.save(address);
        }else {
            throw new AddressAlreadyExistsException("The address already exist!", address.getStreet() + " " +address.getNumber());
        }
        address.setCustomer(user);
        return addressRepository.save(address);
    }

    /**
     * Updates an existing address for the authenticated user.
     *
     * @param addressDTO The AddressDTO containing updated address information.
     * @return The updated AddressDTO.
     * @throws IllegalStateException if the user is not authenticated.
     * @throws EntityNotFoundException if the address is not found.
     */
    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        User user =userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        Address address=addressRepository.findById(addressDTO.getId()).orElseThrow(EntityNotFoundException::new);
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setFloor(addressDTO.getFloor());
        addressRepository.save(address);
        return addressDTO;
    }

    @Override
    public String deleteAddress(AddressDTO addressDTO) {
        User user =userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        addressRepository.deleteById(addressDTO.getId());
        return "Successful delete";
    }

    @Override
    public Set<AddressDTO> getAddresses(){
        User user =userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        return user.getAddresses().stream()
                .map(this::addressToAddressDto)
                .collect(Collectors.toSet());
    }

    public Address addressDtoToAddress(AddressDTO addressDTO){
        return Address.builder()
                .country(addressDTO.getCountry())
                .city(addressDTO.getCity())
                .postalCode(addressDTO.getPostalCode())
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .floor(addressDTO.getFloor())
                .build();

    }
    public AddressDTO addressToAddressDto(Address address){
        return AddressDTO.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .number(address.getNumber())
                .floor(address.getFloor())
                .build();

    }

    @Override
    public Address findAddress(String street, String number, String city) {
        Optional<Address>optionalAddress = addressRepository.findByStreetAndNumberAndCity(street, number, city);
        return optionalAddress.orElse(null);
    }
}
