package com.example.demo.Repository;

import com.example.demo.Models.Discounts;
import com.example.demo.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, Long> {
    List<Discounts> findByUsersId(Long userId);
}
