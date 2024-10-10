package com.example.demo.Services;

import com.example.demo.DTO.OrderDetailDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(Long id) throws Exception;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrdersId(Long orderId);
}
