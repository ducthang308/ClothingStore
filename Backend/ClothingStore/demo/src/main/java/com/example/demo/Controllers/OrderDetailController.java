package com.example.demo.Controllers;

import com.example.demo.DTO.OrderDetailDTO;
import com.example.demo.DTO.OrderDetailReturnDTO;
import com.example.demo.Models.OrderDetail;
import com.example.demo.Services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderdetail")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO,
                                               BindingResult result ){
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable("id") Long id){
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
            return ResponseEntity.ok(orderDetail);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long orderId){
        List<OrderDetail> orderDetails =orderDetailService.findByOrdersId(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long id,
                                               @Valid @RequestBody OrderDetailDTO orderDetailDTO){
        try {
            orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok("Update successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        try {
            orderDetailService.deleteById(id);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orders/{orderId}/details")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<OrderDetailReturnDTO>> getOrderDetails(@PathVariable Long orderId) {
        List<OrderDetailReturnDTO> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/orders/details/{status}/by-ids")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<OrderDetailReturnDTO>> getOrderDetailsByStatus(@PathVariable String status, @RequestParam("ids") List<Long> ids) {
        List<OrderDetailReturnDTO> orderDetails = orderDetailService.getOrderDetailsByStatus(status, ids);
        return ResponseEntity.ok(orderDetails);
    }
}
