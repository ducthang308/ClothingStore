package com.example.demo.Repository;

import com.example.demo.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String name);
    Page<Product> findAll(Pageable pageable);

    @Query("Select p From Product p Where " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.categories.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.productName LIKE %:keyword%)")
    List<Product> searchProducts
            (@Param("categoryId") Long categoryId,
             @Param("keyword") String keyword);


    @Query("Select p FROM Product p LEFT JOIN FETCH p.productImages Where p.id = :productId")
    Optional<Product> getDetailProduct(@Param("productId") Long productId);


    @Query("Select p FROM Product p Where p.id IN :productIds")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);
}
