package com.ipr.notificationservice.event;

public record OrderEvent(
        Long userId,
        String message
){
}
