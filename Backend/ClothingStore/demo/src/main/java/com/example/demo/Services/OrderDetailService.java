package com.example.demo.Services;

import com.example.demo.DTO.OrderDetailDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.OrderDetail;
import com.example.demo.Models.Orders;
import com.example.demo.Models.Product;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrdersRepository;
import com.example.demo.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{

    private final OrderDetailRepository detailRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found product id: "+orderDetailDTO.getProductId()));
        Orders existingOrders = ordersRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found product id: "+orderDetailDTO.getOrderId()));
        if(existingProduct != null && existingOrders != null){
            OrderDetail orderDetail = OrderDetail.builder()
                    .orders(existingOrders)
                    .product(existingProduct)
                    .price(orderDetailDTO.getPrice())
                    .numberOfProduct(orderDetailDTO.getNumberOfProduct())
                    .totalMoney(orderDetailDTO.getTotalMoney())
                    .build();
            return detailRepository.save(orderDetail);
        }
        return null;
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return detailRepository.findById(id)
                .orElseThrow(()->
                        new DataNotFoundException("Not found order detail id: "+id));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail existingOD = detailRepository.findById(id)
                .orElseThrow(()->
                        new DataNotFoundException("Not found product id: "+ id));
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found product id: "+orderDetailDTO.getProductId()));
        Orders existingOrders = ordersRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found product id: "+orderDetailDTO.getOrderId()));
        existingOD.setOrders(existingOrders);
        existingOD.setProduct(existingProduct);
        existingOD.setPrice(orderDetailDTO.getPrice());
        existingOD.setNumberOfProduct(orderDetailDTO.getNumberOfProduct());
        existingOD.setTotalMoney(orderDetailDTO.getTotalMoney());
        return detailRepository.save(existingOD);
    }

    @Override
    public void deleteById(Long id) {
        detailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrdersId(Long orderId) {
        return detailRepository.findByOrdersId(orderId);
    }
}
