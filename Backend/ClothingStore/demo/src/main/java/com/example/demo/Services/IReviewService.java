package com.example.demo.Services;

import com.example.demo.DTO.ReviewDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Review;

import java.util.List;

public interface IReviewService {
    Review createReview(ReviewDTO reviewDTO) throws DataNotFoundException;
    List<Review> getAllReviewByProductId(Long productId);
}
