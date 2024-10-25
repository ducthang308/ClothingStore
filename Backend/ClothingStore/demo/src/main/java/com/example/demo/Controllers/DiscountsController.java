package com.example.demo.Controllers;

import com.example.demo.DTO.DiscountsDTO;
import com.example.demo.Models.Discounts;
import com.example.demo.Services.DiscountsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/discounts")
@RequiredArgsConstructor
public class DiscountsController {

    private final DiscountsService discountsService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> createDiscount(@RequestBody @Valid DiscountsDTO discountsDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            discountsService.createDiscount(discountsDTO);
            return ResponseEntity.ok("Create successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Discounts>> getAllDiscountByUserId(){
        List<Discounts> discounts = discountsService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> updateDiscount(@PathVariable("id") Long id,
                                           @Valid @RequestBody DiscountsDTO discountsDTO)
    {
        try {
            discountsService.updateDiscount(id, discountsDTO);
            return ResponseEntity.ok("Update successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> deleteDiscount(@PathVariable("id") Long id)
    {
        try {
            discountsService.deleteDiscount(id);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
