package com.mateocuevas.ecommerceapi.service.orderItem;

import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.OrderItem;
import com.mateocuevas.ecommerceapi.respository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemImpl implements OrderItemService{
    private OrderItemRepository orderItemRepository;
    @Override
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
