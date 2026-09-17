package com.ipr.notificationservice.event;

public record OrderEvent(
        Long orderId,
        Long userId,
        String message
){
}
