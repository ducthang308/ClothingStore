package com.example.demo.Services;

import com.example.demo.DTO.OrdersDTO;
import com.example.demo.Models.Orders;

import java.util.List;

public interface IOrdersService {
    Orders createOrder(OrdersDTO orderDTO) throws Exception;
    Orders getOrder(Long id);
    List<Orders> findByUserId(Long userId);
    Orders updateOrders(long id, OrdersDTO orderDTO) throws Exception;
    void deleteOrder(long id);
}
