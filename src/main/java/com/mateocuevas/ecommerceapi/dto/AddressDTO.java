package com.mateocuevas.ecommerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String street;
    private String number;
    private String floor;
}
