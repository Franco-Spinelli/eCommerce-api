package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.dto.OrderDTO;
import com.mateocuevas.ecommerceapi.entity.Order;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.service.order.OrderService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private OrderService orderService;
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> addProductToCart(@RequestBody HasDeliveryRequest hasDelivery) throws MessagingException {
        Order order = orderService.makeOrder(hasDelivery);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @GetMapping("/get-all-user")
    public ResponseEntity<List<OrderDTO>> getOrders()  {
        return ResponseEntity.ok(orderService.getAllOrdersByUser());
    }

    @PutMapping("/customer/{orderId}/toggle-archive")
    public ResponseEntity<?> toggleArchiveMyOrder(@PathVariable Long orderId) {
        User user = userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        orderService.toggleArchiveOrderForCustomer(orderId, user.getId());
        return ResponseEntity.ok().build();
    }
}
