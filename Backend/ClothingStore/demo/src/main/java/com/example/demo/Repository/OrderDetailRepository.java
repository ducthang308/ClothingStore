package com.example.demo.Repository;

import com.example.demo.DTO.OrderDetailReturnDTO;
import com.example.demo.Models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrdersId(Long orderId);

    @Query("SELECT new com.example.demo.DTO.OrderDetailReturnDTO(od.orders, od.product, od.numberOfProduct, od.totalMoney, pi.imageUrl) " +
            "FROM OrderDetail od " +
            "JOIN ProductImages pi ON pi.product.id = od.product.id " +
            "WHERE od.orders.id = :orderId")
    List<OrderDetailReturnDTO> findOrderDetailsByOrderId(@Param("orderId") Long orderId);
}
