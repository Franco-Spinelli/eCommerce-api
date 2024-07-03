package com.mateocuevas.ecommerceapi.service.orderItem;

import com.mateocuevas.ecommerceapi.dto.OrderItemDTO;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.OrderItem;
import com.mateocuevas.ecommerceapi.respository.OrderItemRepository;
import com.mateocuevas.ecommerceapi.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemImpl implements OrderItemService{
    private OrderItemRepository orderItemRepository;
    private ProductService productService;
    @Override
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItemDTO orderItemtoOrderItemDTO(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }

        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .product(productService.productToProductDto(orderItem.getProduct()))
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
