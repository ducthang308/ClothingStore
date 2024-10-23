package com.example.demo.Repository;

import com.example.demo.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("Select v From Review v Where v.product.id = :productId")
    List<Review> getAllReviewByProductId(Long productId);
}
