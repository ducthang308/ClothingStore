package com.example.demo.Controllers;

import com.example.demo.DTO.ReviewDTO;
import com.example.demo.DTO.ReviewWithUserFullNameDTO;
import com.example.demo.Models.Review;
import com.example.demo.Services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDTO reviewDTO,
                                          BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            reviewService.createReview(reviewDTO);
            return ResponseEntity.ok("Create successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getAllReviewByProductId(@Valid @PathVariable("productId") Long productId){
        List<ReviewWithUserFullNameDTO> reviews = reviewService.getAllReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/check/{orderId}/{productId}")
//    @PreAuthorize("hasRole('ROLE_User')")
    public ResponseEntity<?> checkIfReviewed(
            @PathVariable("orderId") Long orderId,
            @PathVariable("productId") Long productId) {
        try {
            if (orderId == null || productId == null) {
                throw new IllegalArgumentException("Order ID and Product ID must not be null");
            }

            // Call the service to check the review status
            boolean hasReviewed = reviewService.hasReviewed(orderId, productId);

            // Return success response
            return ResponseEntity.ok(Collections.singletonMap("hasReviewed", hasReviewed));

        } catch (IllegalArgumentException e) {
            // Return bad request for invalid inputs
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (AccessDeniedException e) {
            // Return forbidden response when access is denied
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("error", "Access Denied: " + e.getMessage()));
        } catch (Exception e) {
            // Return internal server error for unexpected issues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }
}
