package com.mateocuevas.ecommerceapi.service.order;

import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.entity.*;
import com.mateocuevas.ecommerceapi.respository.OrderRepository;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.orderItem.OrderItemService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;
    private UserService userService;
    private CartItemService cartItemService;
    private CartService cartService;
    private OrderItemService orderItemService;
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * This method is responsible for creating an order from the authenticated user's cart.
     * It performs the following steps:
     * 1. Retrieves the authenticated user.
     * 2. Gets the user's cart and its items.
     * 3. Creates and saves a new order based on the cart's content.
     * 4. Converts the cart items into order items and removes them from the cart.
     * 5. Resets the cart to an empty state.
     * 6. Returns the created order.
     */
    @Override
    @Transactional
    public Order makeOrder(HasDeliveryRequest hasDelivery) {
        User user = userService.getUserAuthenticated().orElseThrow();
        Cart cart = user.getCart();
        Set<CartItem> cartItems = cart.getCartItems();

        Order order = createAndSaveOrder(user,cart, hasDelivery);

        convertCartItemsToOrderItems(cartItems,order);

        cartService.resetCart(cart);

        return order;
    }

    private Order createAndSaveOrder(User user, Cart cart, HasDeliveryRequest hasDelivery){
        Order order = Order.builder()
                .customer(user)
                .deliveryAddress(hasDelivery.getAddress())
                .hasDelivery(hasDelivery.isHasDelivery())
                .totalItems(cart.getTotalItems())
                .totalPrice(cart.getTotalPrice())
                .build();
        if(hasDelivery.getAddress().isBlank()){
            order.setDeliveryAddress(user.getAddress());
        }
        orderRepository.save(order);
        return order;
    }
    private void convertCartItemsToOrderItems(Set<CartItem> cartItems, Order order){
        HashSet<OrderItem> orderItems = new HashSet<>();
        Iterator<CartItem> iterator = cartItems.iterator();

        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .totalPrice(cartItem.getTotalPrice())
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .build();

            orderItemService.saveOrderItem(orderItem);
            orderItems.add(orderItem);

            iterator.remove();
            cartItemService.deleteCartItem(cartItem);
        }
        order.setOrderItems(orderItems);
    }


}
