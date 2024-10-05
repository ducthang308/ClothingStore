package com.example.demo.Services;

import com.example.demo.DTO.OrdersDTO;
import com.example.demo.Models.Orders;

import java.util.List;

public interface IOrdersService {
    Orders createOrder(OrdersDTO orderDTO) throws Exception;
    Orders getOrder(Long id);
    List<Orders> findByUserId(Long userId);
    Orders updateOrder(long id, OrdersDTO orderDTO);
    void deleteOrder(long id);
}
