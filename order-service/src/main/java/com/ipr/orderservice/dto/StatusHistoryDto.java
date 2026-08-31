package com.ipr.orderservice.dto;

import com.ipr.orderservice.entity.Order;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;

public record StatusHistoryDto (
    Long id,

    @NotBlank(message = "Order is mandatory")
    Order order,

    String oldStatus,

    @NotBlank(message = "New status is mandatory")
    String newStatus,

    Long changedBy,

    LocalDateTime changedAt
) {}
