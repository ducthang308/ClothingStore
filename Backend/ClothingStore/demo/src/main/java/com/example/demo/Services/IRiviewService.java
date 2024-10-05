package com.example.demo.Services;

import com.example.demo.DTO.ReviewDTO;
import com.example.demo.Models.Review;

import java.util.List;

public interface IRiviewService {
    Review createReview(ReviewDTO reviewDTO);
    List<Review> getReviewByProductId(long productId);
}
