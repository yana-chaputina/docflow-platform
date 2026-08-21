package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.OrderDto;
import com.ipr.orderservice.entity.Order;
import com.ipr.orderservice.mapper.OrderDTOEntityMapper;
import com.ipr.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDTOEntityMapper orderDTOEntityMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDTOEntityMapper orderDTOEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderDTOEntityMapper = orderDTOEntityMapper;
    }


    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderDTOEntityMapper.orderToOrderDtoAsList(orders);
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderDTOEntityMapper.orderDtoToOrder(orderDto);
        return orderDTOEntityMapper.orderToOrderDto(orderRepository.save(order));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
