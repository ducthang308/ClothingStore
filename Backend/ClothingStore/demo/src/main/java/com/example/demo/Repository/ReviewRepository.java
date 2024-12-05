package com.example.demo.Repository;

import com.example.demo.DTO.ReviewWithUserFullNameDTO;
import com.example.demo.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("Select new com.example.demo.DTO.ReviewWithUserFullNameDTO(v, u.fullName) From Review v INNER JOIN Users u ON v.users.id = u.id Where v.product.id = :productId")
    List<ReviewWithUserFullNameDTO> getAllReviewByProductId(Long productId);

    boolean existsByOrders_IdAndProduct_Id(Long orderId, Long productId);
}
