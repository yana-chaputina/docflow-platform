package com.ipr.orderservice.mapper;

import com.ipr.orderservice.dto.OrderDto;
import com.ipr.orderservice.entity.Order;
import org.mapstruct.Mapper;

import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderDtoEntityMapper {

    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);

    List<OrderDto> orderToOrderDtoAsList(List<Order> orders);

}
