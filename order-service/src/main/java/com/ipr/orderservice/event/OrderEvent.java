package com.ipr.orderservice.event;

public record OrderEvent (
        Long userId,
        String message
){
}
