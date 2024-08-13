package com.mateocuevas.ecommerceapi.service.order;

import com.mateocuevas.ecommerceapi.dto.AddressDTO;
import com.mateocuevas.ecommerceapi.dto.EmailDTO;
import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.dto.OrderDTO;
import com.mateocuevas.ecommerceapi.entity.*;
import com.mateocuevas.ecommerceapi.enums.OrderStatus;
import com.mateocuevas.ecommerceapi.enums.ShippingConstants;
import com.mateocuevas.ecommerceapi.exception.NoDeliveryAddressFoundException;
import com.mateocuevas.ecommerceapi.respository.OrderRepository;
import com.mateocuevas.ecommerceapi.service.address.AddressService;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.orderItem.OrderItemService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final AddressService addressService;
    private final JavaMailSender javaMailSender;
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
    public Order makeOrder(HasDeliveryRequest hasDelivery) throws MessagingException{
        User user = userService.getUserAuthenticated().orElseThrow();
        Cart cart = user.getCart();
        Set<CartItem> cartItems = cart.getCartItems();

        Order order = createAndSaveOrder(user,cart, hasDelivery);

        convertCartItemsToOrderItems(cartItems,order);

        cartService.resetCart(cart);

        return order;
    }

    public List<OrderDTO> getAllOrdersByUser() {
        User user = userService.getUserAuthenticated().orElseThrow();
        return user.getOrders().stream()
                .map(this::ordertoOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order>orderList = orderRepository.findAll();
        return orderList.stream()
                .map(this::ordertoOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO ordertoOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        OrderDTO orderDTO = OrderDTO.builder()
                .id(order.getId())
                .hasDelivery(order.isHasDelivery())
                .code(order.getCode())
                .date(order.getDate())
                .totalItems(order.getTotalItems())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .orderItems(order.getOrderItems().stream()
                        .map(orderItemService::orderItemtoOrderItemDTO)
                        .collect(Collectors.toSet()))
                .customer(order.getCustomer().getFirstName() + " " +order.getCustomer().getLastName())
                .build();
        if(order.isHasDelivery()){
            orderDTO.setDeliveryAddress(addressService.addressToAddressDto(order.getDeliveryAddress()));
        }
        return orderDTO;
    }


    private Order createAndSaveOrder(User user, Cart cart, HasDeliveryRequest hasDelivery) throws MessagingException {
        if(cart.getTotalPrice() != 0){
        cartService.processCart(cart);
        Order order = Order.builder()
                .customer(user)
                .hasDelivery(hasDelivery.isHasDelivery())
                .date(new Date())
                .code(generateOrderCode())
                .status(OrderStatus.PENDING)
                .totalItems(cart.getTotalItems())
                .totalPrice(cart.getTotalPrice())
                .build();
        if(hasDelivery.isHasDelivery()){
         addressVerificationsInOrder(order,hasDelivery.getAddress(),user);
         order.setTotalPrice(cart.getTotalPrice() + ShippingConstants.SHIPPING_COST);
        }
        order = orderRepository.save(order);

        EmailDTO email = EmailDTO.builder()
                .receiver(user.getUsername())
                .affair("Order Confirmation")
                .message("<html>"
                        + "<body style=\"font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0;\">"
                        + "<div style=\"max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0;\">"
                        + "<div style=\"background-color: #4567b7; height: 60px; width: 100%; display: flex; align-items: center; justify-content: center; color: white;\">"
                        + "<span style=\"font-size: 24px; font-weight: bold;\">Martinez eCommerce</span>"
                        + "</div>"
                        + "<h2 style=\"color: #333;\">Order Confirmation</h2>"
                        + "<p>Dear " + user.getFirstName() + ",</p>"
                        + "<p>Thank you for your order! Your order has been confirmed, and we are processing it. Below are the details:</p>"
                        + "<p><strong>Order ID:</strong> " + order.getId() + "</p>"
                        + "<p><strong>Total Amount:</strong> $" + order.getTotalPrice() + "</p>"
                        + "<p>We appreciate your business and hope to serve you again soon.</p>"
                        + "<p style=\"margin-top: 30px;\">Best regards,<br/>"
                        + "<strong>The Martinez Ecommerce Team</strong></p>"
                        + "</div>"
                        + "</body>"
                        + "</html>")


                .build();
        sendMail(email);

        return order;
        }else {
            throw new RuntimeException("Please add items to the cart");
        }
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
    public void sendMail(EmailDTO email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setTo(email.getReceiver());
        helper.setSubject(email.getAffair());
        helper.setText(email.getMessage(),true);
        javaMailSender.send(message);
    }

    public Order changeOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }
        throw new EntityNotFoundException();
    }
    private String generateOrderCode() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");
        return uuidStr.substring(0, 6).toUpperCase();
    }
}
