package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.OrderDto;
import com.ipr.orderservice.entity.Order;
import com.ipr.orderservice.mapper.OrderDTOEntityMapper;
import com.ipr.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDTOEntityMapper orderDTOEntityMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDTOEntityMapper orderDTOEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderDTOEntityMapper = orderDTOEntityMapper;
    }

    @Override
    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderDTOEntityMapper.orderToOrderDtoAsList(orders);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order= orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderDTOEntityMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderDTOEntityMapper.orderDtoToOrder(orderDto);
        return orderDTOEntityMapper.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
