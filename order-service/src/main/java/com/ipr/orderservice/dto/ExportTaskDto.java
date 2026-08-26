package com.ipr.orderservice.dto;

import com.ipr.orderservice.entity.ExportTaskStatus;

import java.time.LocalDateTime;

public record ExportTaskDto(
        Long id,

        Long userId,

        String fileName,

        ExportTaskStatus status,

        String fileUrl,

        String errorMessage,

        LocalDateTime requestedAt,

        LocalDateTime completedAt
) {
}
