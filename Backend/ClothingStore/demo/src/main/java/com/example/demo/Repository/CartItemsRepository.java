package com.example.demo.Repository;

import com.example.demo.Models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findByCarts_Id(Long cartId);
    void deleteByCarts_IdAndProduct_Id(Long cartId, Long productId);
}
