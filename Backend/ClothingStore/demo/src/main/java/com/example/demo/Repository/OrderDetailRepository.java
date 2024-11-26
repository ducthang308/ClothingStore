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

    @Query("SELECT new com.example.demo.DTO.OrderDetailReturnDTO(od.numberOfProduct, od.totalMoney, p.productName, pi.imageUrl, o.users.address, o.orderDate, o.status) " +
            "FROM Orders o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "JOIN p.productImages pi " +
            "WHERE o.id = :orderId")
    List<OrderDetailReturnDTO> findOrderDetailsByOrderId(@Param("orderId") Long orderId);

}
