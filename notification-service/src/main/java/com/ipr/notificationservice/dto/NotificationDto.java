package com.ipr.notificationservice.dto;

import com.ipr.notificationservice.entity.NotificationStatus;
import com.ipr.notificationservice.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationDto (
        Long id,
        Long userId,
        NotificationType notificationType,
        String description,
        NotificationStatus notificationStatus,
        Long retryCount,
        String errorMessage,
        LocalDateTime createdAt,
        LocalDateTime sentAt
) {
}
