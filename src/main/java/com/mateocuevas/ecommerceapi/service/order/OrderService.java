package com.mateocuevas.ecommerceapi.service.order;

import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.dto.OrderDTO;
import com.mateocuevas.ecommerceapi.dto.OrderItemDTO;
import com.mateocuevas.ecommerceapi.entity.Order;
import com.mateocuevas.ecommerceapi.entity.OrderItem;
import com.mateocuevas.ecommerceapi.enums.OrderStatus;
import jakarta.mail.MessagingException;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    Order makeOrder(HasDeliveryRequest hasDelivery) throws MessagingException;
    List<OrderDTO>getAllOrdersByUser();
    List<OrderDTO>getAllOrders();
    Order changeOrderStatus(Long orderId, OrderStatus newStatus);
    OrderDTO ordertoOrderDTO(Order order);
}
