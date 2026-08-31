package com.ipr.orderservice.mapper;

import com.ipr.orderservice.dto.StatusHistoryDto;
import com.ipr.orderservice.entity.StatusHistoryEntity;
import org.mapstruct.Mapper;

import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StatusHistoryDtoEntityMapper {

    StatusHistoryEntity statusHistoryDtoToStatusHistoryEntity(StatusHistoryDto statusHistoryDto);

    StatusHistoryDto statusHistoryEntityToStatusHistoryDto(StatusHistoryEntity statusHistoryEntity);

    List<StatusHistoryDto> statusHistoryEntityToStatusHistoryDtoAsList(List<StatusHistoryEntity> statusHistoryEntities);

}
