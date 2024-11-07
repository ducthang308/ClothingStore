package com.example.demo.Repository;

import com.example.demo.Models.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, Long> {

}
