package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.OrderDto;


import java.util.List;

public interface OrderService {

    public List<OrderDto> getOrders();

    public OrderDto getOrderById(Long id);

    public OrderDto createOrder(OrderDto orderDto);

    public void deleteOrder(Long id);
}
