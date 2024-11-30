package com.example.demo.Controllers;

import com.example.demo.DTO.OrdersDTO;
import com.example.demo.Models.Orders;
import com.example.demo.Services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> createOrders(@Valid @RequestBody OrdersDTO ordersDTO, BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Orders orders = orderService.createOrder(ordersDTO);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> getAllOrderByUserId(@Valid @PathVariable("user_id") Long userId){
        List<Orders> orders = orderService.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<List<Orders>> getAllOrders(){
        List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> getOrderById(@Valid @PathVariable("id") Long id){
        Orders orders = orderService.getOrder(id);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
                                           @Valid @RequestBody OrdersDTO ordersDTO)
    {
        try {
            orderService.updateOrders(id, ordersDTO);
            return ResponseEntity.ok("Update successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> deleteOrders(@PathVariable("id") Long id)
    {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id,
                                          @Valid @RequestBody OrdersDTO orderDTO)
    {
        try {
            orderService.updateStatus(id, orderDTO);
            return ResponseEntity.ok("Update successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
