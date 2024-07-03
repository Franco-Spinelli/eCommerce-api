package com.mateocuevas.ecommerceapi.service.orderItem;

import com.mateocuevas.ecommerceapi.dto.OrderItemDTO;
import com.mateocuevas.ecommerceapi.entity.OrderItem;

public interface OrderItemService {
    void saveOrderItem(OrderItem OrderItem);
    OrderItemDTO orderItemtoOrderItemDTO(OrderItem orderItem);
}
