package com.example.demo.Controllers;

import com.example.demo.DTO.CartItemQuantityDTO;
import com.example.demo.DTO.CartItemsDTO;
import com.example.demo.DTO.CartsDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.CartItems;
import com.example.demo.Models.Carts;
import com.example.demo.Services.CartItemsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/cartitems")
@RequiredArgsConstructor
public class CartItemsController {
    private final CartItemsService cartItemsService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> createCartItems(@Valid @RequestBody CartItemsDTO cartItemsDTO, BindingResult result) throws DataNotFoundException {
        if(result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        cartItemsService.createCartItem(cartItemsDTO);
        return ResponseEntity.ok("Create successfully");
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<CartItemsDTO>> getAllCartItems(){
        List<CartItemsDTO> cartItems = cartItemsService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/cart/{cartId}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<CartItemsDTO>> getAllCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItemsDTO> cartItems = cartItemsService.getAllCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> updateCartItems(@PathVariable Long id, @RequestBody CartItemQuantityDTO cartItemQuantityDTO) throws DataNotFoundException {
        cartItemsService.updateCartItem(id, cartItemQuantityDTO);
        return ResponseEntity.ok("Cart item updated successfully");
    }



    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> deleteCartItemsByUserIdAndProductId(
            @RequestParam Long cartId,
            @RequestParam Long productId) {
        cartItemsService.deleteCartItem(cartId, productId);
        return ResponseEntity.ok("Delete successfully");
    }
}
