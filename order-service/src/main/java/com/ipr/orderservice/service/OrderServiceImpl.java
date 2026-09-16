package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.OrderDto;
import com.ipr.orderservice.entity.Order;
import com.ipr.orderservice.event.OrderEvent;
import com.ipr.orderservice.kafka.KafkaProducer;
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
    private final KafkaProducer kafkaProducer;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDtoEntityMapper orderDTOEntityMapper, OrderValidityChecker orderValidityChecker, KafkaProducer kafkaProducer) {
        this.orderRepository = orderRepository;
        this.orderDTOEntityMapper = orderDTOEntityMapper;
        this.orderValidityChecker = orderValidityChecker;
        this.kafkaProducer = kafkaProducer;
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
            OrderEvent orderEvent = new OrderEvent(order.getUserId(),
                    "Order with id " + order.getId() + " has been created");
            kafkaProducer.sendMessage(String.valueOf(order.getUserId()),orderEvent);
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
