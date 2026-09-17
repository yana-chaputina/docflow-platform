package com.ipr.orderservice.event;

public record OrderEvent (

        Long orderId,
        Long userId,
        String message
){
}
