package com.mateocuevas.ecommerceapi.service.order;

import com.mateocuevas.ecommerceapi.dto.AddressDTO;
import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.entity.*;
import com.mateocuevas.ecommerceapi.respository.OrderRepository;
import com.mateocuevas.ecommerceapi.service.address.AddressService;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.orderItem.OrderItemService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
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
        // Check if the street in AddressDTO is not null
        if (addressDTO.getStreet() != null) {
            // Find if an existing address matches the given details (street, number, city)
            Address existAddress = addressService.findAddress(addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getCity());

            // If no existing address found, create and add a new Address
            if (existAddress == null) {
                // Convert AddressDTO to Address object
                Address newAddress = addressService.addressDtoToAddress(addressDTO);
                // Save the new address to the database
                Address address = addressService.addAddress(newAddress);
                // Set the newly added address as the deliveryAddress for the order
                order.setDeliveryAddress(address);
            } else {
                // Set the existing address as the deliveryAddress for the order
                order.setDeliveryAddress(existAddress);
            }
        } else {
            // If street in AddressDTO is null, use the last address from user's list of addresses
            List<Address> addressList = new ArrayList<>(user.getAddresses());
            order.setDeliveryAddress(addressList.getLast());
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
