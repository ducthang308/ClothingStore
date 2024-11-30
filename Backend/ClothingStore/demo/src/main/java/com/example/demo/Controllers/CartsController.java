package com.example.demo.Controllers;

import com.example.demo.DTO.CartsDTO;
import com.example.demo.DTO.CategoriesDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Carts;
import com.example.demo.Services.CartsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartsController {
    private final CartsService cartsService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> createCarts(@Valid @RequestBody CartsDTO cartsDTO, BindingResult result) throws DataNotFoundException {
        if(result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        cartsService.createCart(cartsDTO);
        return ResponseEntity.ok("Create successfully");
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<CartsDTO>> getAllCarts(){
        List<Carts> carts = cartsService.getAllCarts();
        List<CartsDTO> cartsDTOList = carts.stream().map(cart -> {
            CartsDTO dto = new CartsDTO();
            dto.setId(cart.getId().intValue());
            dto.setUserId(cart.getUsers().getId());
            dto.setCreatedAt(cart.getCreatedAt());
            dto.setUpdatedAt(cart.getUpdatedAt());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(cartsDTOList);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> getCartByUserId(@PathVariable("userId") Long userId){
        Long carts = cartsService.getCartByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> updateCart(@PathVariable Long id) throws DataNotFoundException {
        cartsService.updateCart(id);
        return ResponseEntity.ok("Update successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> deleteCart(@PathVariable Long id){
        cartsService.deleteCart(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
