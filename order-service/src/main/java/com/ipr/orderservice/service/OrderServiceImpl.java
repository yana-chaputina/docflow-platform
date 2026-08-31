package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.OrderDto;
import com.ipr.orderservice.entity.Order;
import com.ipr.orderservice.mapper.OrderDtoEntityMapper;
import com.ipr.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDtoEntityMapper orderDTOEntityMapper;
    private final OrderValidityChecker orderValidityChecker;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDtoEntityMapper orderDTOEntityMapper, OrderValidityChecker orderValidityChecker) {
        this.orderRepository = orderRepository;
        this.orderDTOEntityMapper = orderDTOEntityMapper;
        this.orderValidityChecker = orderValidityChecker;
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
        if(orderValidityChecker.validateOrder(orderDto)) {
            Order order = orderDTOEntityMapper.orderDtoToOrder(orderDto);
            orderRepository.save(order);
            return orderDTOEntityMapper.orderToOrderDto(order);
        } else {
            throw new RuntimeException("Order validation failed");
        }
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
