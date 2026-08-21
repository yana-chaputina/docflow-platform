package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.StatusHistoryDto;
import com.ipr.orderservice.entity.StatusHistoryEntity;
import com.ipr.orderservice.mapper.StatusHistoryDTOEntityMapper;
import com.ipr.orderservice.repository.StatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusHistoryService {
    private final StatusHistoryRepository statusHistoryRepository;
    private final StatusHistoryDTOEntityMapper statusHistoryDTOEntityMapper;

    @Autowired
    public StatusHistoryService(StatusHistoryRepository statusHistoryRepository, StatusHistoryDTOEntityMapper statusHistoryDTOEntityMapper) {
        this.statusHistoryRepository = statusHistoryRepository;
        this.statusHistoryDTOEntityMapper = statusHistoryDTOEntityMapper;
    }


    public List<StatusHistoryDto> getStatusHistoryByOrderId(Long orderId) {
        List<StatusHistoryEntity> statusHistoryEntities = statusHistoryRepository.getStatusHistoryByOrderId(orderId);
        return statusHistoryDTOEntityMapper.statusHistoryEntityToStatusHistoryDtoAsList(statusHistoryEntities);
    }

    public StatusHistoryDto createStatusHistoryRecord(StatusHistoryDto statusHistoryDto) {
        StatusHistoryEntity statusHistoryEntity = statusHistoryDTOEntityMapper.statusHistoryDtoToStatusHistoryEntity(statusHistoryDto);
        return statusHistoryDTOEntityMapper.statusHistoryEntityToStatusHistoryDto(statusHistoryRepository.save(statusHistoryEntity));
    }

}
