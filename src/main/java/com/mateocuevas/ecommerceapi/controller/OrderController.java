package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.CartDTO;
import com.mateocuevas.ecommerceapi.dto.CartItemRequest;
import com.mateocuevas.ecommerceapi.dto.HasDeliveryRequest;
import com.mateocuevas.ecommerceapi.entity.Order;
import com.mateocuevas.ecommerceapi.service.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<?> addProductToCart(@RequestBody HasDeliveryRequest hasDelivery){
        Order order = orderService.makeOrder(hasDelivery);
        return ResponseEntity.ok("Order success");
    }
}
