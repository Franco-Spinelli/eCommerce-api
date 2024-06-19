package com.mateocuevas.ecommerceapi.dto;

import com.mateocuevas.ecommerceapi.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HasDeliveryRequest {
    private boolean hasDelivery;
    private AddressDTO address;
}
