package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.AddressDTO;
import com.mateocuevas.ecommerceapi.entity.Address;
import com.mateocuevas.ecommerceapi.service.address.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final AddressService addressService;

    /**
     * This endpoint adds addresses to the set of addresses of the authenticated user.
     * @param addressDTO The request containing the info of the address to add.
     * @return ResponseEntity with the address added.
     */
    @PostMapping("/add-address")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO){
        Address newAddress = addressService.addressDtoToAddress(addressDTO);
        newAddress = addressService.addAddress(newAddress);
        addressDTO.setId(newAddress.getId());
        return ResponseEntity.ok(addressDTO);
    }

    /**
     * This endpoint update a specific address of the set of addresses of the authenticated user.
     * @param addressDTO The request containing the info of the address to update.
     * @return ResponseEntity with the address updated.
     */
    @PutMapping("/update-address")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO){
        AddressDTO addressResponse = addressService.updateAddress(addressDTO);
        return ResponseEntity.ok(addressResponse);
    }

    /**
     * This endpoint delete a specific address of the set of addresses of the authenticated user.
     * @param addressDTO The request containing the info of the address to delete.
     * @return ResponseEntity with a message.
     */
    @DeleteMapping("/delete-address")
    public ResponseEntity<String> deleteAddress(@RequestBody AddressDTO addressDTO){
        String msj= addressService.deleteAddress(addressDTO);
        return ResponseEntity.ok(msj);
    }

    /**
     * This endpoint get all the address of the authenticated user.
     * @return ResponseEntity with a set of addresses.
     */
    @GetMapping("/get-addresses")
    public ResponseEntity<Set<AddressDTO>> getAddresses(){
        Set<AddressDTO> addressesResponse= addressService.getAddresses();
        return ResponseEntity.ok(addressesResponse);
    }
}
