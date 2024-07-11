package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.OrderDTO;
import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Order;
import com.mateocuevas.ecommerceapi.enums.OrderStatus;
import com.mateocuevas.ecommerceapi.service.admin.AdminService;
import com.mateocuevas.ecommerceapi.service.admin.AdminServiceImpl;
import com.mateocuevas.ecommerceapi.service.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final OrderService orderService;
    @PostMapping("/create-product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO newProductDTO = adminService.createProduct(productDTO);
        return ResponseEntity.ok(newProductDTO);
    }

    @PutMapping("/update-product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO response = adminService.updateProduct(productDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        String message = adminService.deleteProduct(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-all-orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders()  {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    @PutMapping("/{orderId}/change-status")
    public ResponseEntity<Order> changeOrderStatus(@PathVariable("orderId") Long orderId, @RequestBody Map<String, String> statusMap) {
        String newStatusStr = statusMap.get("status");
        if (newStatusStr == null || newStatusStr.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        OrderStatus newStatus = OrderStatus.valueOf(newStatusStr);
        if (newStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        Order updatedOrder = orderService.changeOrderStatus(orderId, newStatus);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
