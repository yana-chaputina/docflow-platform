package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.OrderDto;

public interface OrderValidityChecker {
    boolean validateOrder (OrderDto orderDto);
}
