package com.example.demo.Controllers;

import com.example.demo.DTO.ReviewDTO;
import com.example.demo.Models.Review;
import com.example.demo.Services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("")
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
        List<Review> reviews = reviewService.getAllReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
}
