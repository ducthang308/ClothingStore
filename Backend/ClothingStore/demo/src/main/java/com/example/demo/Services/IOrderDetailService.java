package com.example.demo.Services;

import com.example.demo.DTO.OrderDetailDTO;
import com.example.demo.Models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(Long id);
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
