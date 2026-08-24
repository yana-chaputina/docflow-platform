package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.StatusHistoryDto;

import java.util.List;

public interface StatusHistoryService {

    public List<StatusHistoryDto> getStatusHistoryByOrderId(Long orderId);

    public StatusHistoryDto createStatusHistoryRecord(StatusHistoryDto statusHistoryDto);
}
