package com.ipr.orderservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;


public record OrderDto(
        Long id,

        @NotBlank(message = "Title is mandatory")
        String title,

        String description,

        String status,

        String priority,

        Long userId,

        Long assigneeId,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        LocalDateTime resolvedAt,

        String rejectionReason
) {
}
