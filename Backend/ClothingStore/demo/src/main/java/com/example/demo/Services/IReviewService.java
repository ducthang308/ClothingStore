package com.example.demo.Services;

import com.example.demo.DTO.ReviewDTO;
import com.example.demo.DTO.ReviewWithUserFullNameDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Review;

import java.util.List;

public interface IReviewService {
    Review createReview(ReviewDTO reviewDTO) throws DataNotFoundException;
    List<ReviewWithUserFullNameDTO> getAllReviewByProductId(Long productId);
    boolean hasReviewed(Long orderId, Long productId);
}
