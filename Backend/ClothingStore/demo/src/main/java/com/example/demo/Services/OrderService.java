package com.example.demo.Services;

import com.example.demo.DTO.CartItemDTO;
import com.example.demo.DTO.OrdersDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.OrderDetail;
import com.example.demo.Models.OrderStatus;
import com.example.demo.Models.Orders;
import com.example.demo.Models.Users;
import com.example.demo.Repository.OrdersRepository;
import com.example.demo.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public Orders createOrder(OrdersDTO orderDTO) throws Exception {
        Users existingUser = usersRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Not found userId: " + orderDTO.getUserId()));
        Orders orders = Orders.builder()
                .users(existingUser)
                .note(orderDTO.getNote())
                .orderDate(orderDTO.getOrderDate())
                .status(OrderStatus.WAITING)
                .totalMoney(orderDTO.getTotalMoney())
                .paymentMethod(orderDTO.getPaymentMethod())
                .build();
        ordersRepository.save(orders);
//        List<OrderDetail> orderDetails = new ArrayList<>();
//
//        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrders(orders);
//            orderDetail.setProduct(cartItemDTO.getProduct());
//            orderDetail.setQuantity(cartItemDTO.getQuantity());
//            orderDetail.setPrice(cartItemDTO.getPrice());
//            orderDetails.add(orderDetail);
//        }
//        orderDetailRepository.saveAll(orderDetails)
//        orders.setOrderDetails(orderDetails);

        return orders;
    }
    @Override
    public Orders getOrder(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public List<Orders> findByUserId(Long userId) {
        return ordersRepository.findByUsersId(userId);
    }

    @Override
    public Orders updateOrders(long id, OrdersDTO orderDTO) throws Exception {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(()->
                        new DataNotFoundException("Not found orderId: "+id));
//        Users existUser = usersRepository.findById(orderDTO.getUserId())
//                .orElseThrow(()->
//                        new DataNotFoundException("Not found userId: "+orderDTO.getUserId()));
        if(orders != null){
            orders.setNote(orderDTO.getNote());
            orders.setOrderDate(orderDTO.getOrderDate());
            orders.setStatus(orderDTO.getStatus());
            orders.setTotalMoney(orderDTO.getTotalMoney());
            orders.setPaymentMethod(orderDTO.getPaymentMethod());
            return ordersRepository.save(orders);
        }
        return null;
    }

    @Override
    public void deleteOrder(long id) {
        ordersRepository.deleteById(id);
    }
}
