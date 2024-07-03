package com.mateocuevas.ecommerceapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private boolean hasDelivery;
    private Date date;
    private String code;
    private AddressDTO deliveryAddress;
    private Integer totalItems;
    private double totalPrice;

    private Set<OrderItemDTO> orderItems;

    private String customer;

}
