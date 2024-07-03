package com.mateocuevas.ecommerceapi.service.order;

import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.entity.Order;
import jakarta.mail.MessagingException;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    Order makeOrder(HasDeliveryRequest hasDelivery) throws MessagingException;
    List<Order>getAllOrders();
}
