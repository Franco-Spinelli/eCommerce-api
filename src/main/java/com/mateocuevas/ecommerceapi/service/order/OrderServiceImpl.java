package com.mateocuevas.ecommerceapi.service.order;

import com.mateocuevas.ecommerceapi.dto.AddressDTO;
import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.entity.*;
import com.mateocuevas.ecommerceapi.exception.NoDeliveryAddressFoundException;
import com.mateocuevas.ecommerceapi.respository.OrderRepository;
import com.mateocuevas.ecommerceapi.service.address.AddressService;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.orderItem.OrderItemService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final AddressService addressService;
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
                .hasDelivery(hasDelivery.isHasDelivery())
                .totalItems(cart.getTotalItems())
                .totalPrice(cart.getTotalPrice())
                .build();
        if(hasDelivery.isHasDelivery()){
         addressVerificationsInOrder(order,hasDelivery.getAddress(),user);
        }
        orderRepository.save(order);
        return order;

    }
    private void addressVerificationsInOrder(Order order,AddressDTO addressDTO, User user){
        if (addressDTO.getStreet() != null) {
            // Find existing address based on provided details (street, number, city)
            Address existingAddress = addressService.findAddress(addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getCity());

            // Use existing address or create a new one if not found
            Address deliveryAddress = existingAddress != null ? existingAddress : addressService.addAddress(addressService.addressDtoToAddress(addressDTO));

            // Set the delivery address for the order
            order.setDeliveryAddress(deliveryAddress);
        } else {
            // Use the last address from user's list if no street provided
            List<Address> addressList = new ArrayList<>(user.getAddresses());
            if (addressList.isEmpty()) {
                throw new NoDeliveryAddressFoundException("No delivery address found. Please add a valid delivery address.");
            }
            order.setDeliveryAddress(addressList.get(addressList.size() - 1));
        }
    }
    private void convertCartItemsToOrderItems(Set<CartItem> cartItems, Order order) {
        HashSet<OrderItem> orderItems = new HashSet<>();  // Initialize a HashSet to hold OrderItems
        Iterator<CartItem> iterator = cartItems.iterator();  // Create an iterator to iterate over cartItems

        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();  // Get the next CartItem from the iterator

            // Create an OrderItem using builder pattern
            OrderItem orderItem = OrderItem.builder()
                    .order(order)                     // Set the Order for the OrderItem
                    .totalPrice(cartItem.getTotalPrice())  // Set the total price of the OrderItem
                    .quantity(cartItem.getQuantity())      // Set the quantity of the OrderItem
                    .product(cartItem.getProduct())        // Set the product of the OrderItem
                    .build();

            orderItemService.saveOrderItem(orderItem);   // Save the OrderItem using orderItemService
            orderItems.add(orderItem);                   // Add the OrderItem to the HashSet

            iterator.remove();  // Remove the current CartItem from the iterator (and implicitly from cartItems)
            cartItemService.deleteCartItem(cartItem);   // Delete the CartItem using cartItemService
        }

        order.setOrderItems(orderItems);  // Set the HashSet of OrderItems to the Order
    }


}
