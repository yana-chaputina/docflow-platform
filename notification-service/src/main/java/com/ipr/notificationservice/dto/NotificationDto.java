package com.ipr.notificationservice.dto;

import com.ipr.notificationservice.entity.NotificationStatus;
import com.ipr.notificationservice.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationDto (
        Long id,
        Long userId,
        Long orderId,
        NotificationType type,
        String content,
        NotificationStatus status,
        Long retryCount,
        String errorMessage,
        LocalDateTime createdAt,
        LocalDateTime sentAt
) {
}
