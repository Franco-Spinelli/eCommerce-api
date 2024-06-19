package com.mateocuevas.ecommerceapi.controller;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.service.admin.AdminService;
import com.mateocuevas.ecommerceapi.service.admin.AdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

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

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProduct(@RequestBody Long id) {
        return ResponseEntity.ok(adminService.deleteProduct(id));
    }
}
