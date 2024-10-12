package com.example.demo.Services;

import com.example.demo.DTO.ReviewDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Orders;
import com.example.demo.Models.Product;
import com.example.demo.Models.Review;
import com.example.demo.Models.Users;
import com.example.demo.Repository.OrdersRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;

    @Override
    public Review createReview(ReviewDTO reviewDTO) throws DataNotFoundException {
        Product existingProduct = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found product by: " + reviewDTO.getProductId()));
        Orders existingOrders = ordersRepository.findById(reviewDTO.getOrderId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found orders by: " + reviewDTO.getOrderId()));
        Users existingUsers = usersRepository.findById(reviewDTO.getUserId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found users by: " + reviewDTO.getUserId()));
        Review review = Review.builder()
                .product(existingProduct)
                .orders(existingOrders)
                .users(existingUsers)
                .note(reviewDTO.getNote())
                .numberOfStars(reviewDTO.getNumberOfStars())
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviewByProductId(Long productId) {
        return reviewRepository.getAllReviewByProductId(productId);
    }
}
