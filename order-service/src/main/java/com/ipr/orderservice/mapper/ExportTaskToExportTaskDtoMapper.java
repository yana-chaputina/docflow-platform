package com.ipr.orderservice.mapper;

import com.ipr.orderservice.dto.ExportTaskDto;
import com.ipr.orderservice.entity.ExportTask;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExportTaskToExportTaskDtoMapper {

    ExportTask exportTaskDtoToExportTask(ExportTaskDto exportTaskDto);

    ExportTaskDto exportTaskToExportTaskDto(ExportTask exportTask);

    List<ExportTaskDto> exportTasksToExportTaskDtoAsList(List<ExportTask> exportTasks);
}
