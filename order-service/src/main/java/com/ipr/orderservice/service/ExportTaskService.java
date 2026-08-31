package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.ExportFile;
import com.ipr.orderservice.dto.ExportTaskDto;

import java.util.List;

public interface ExportTaskService {

    ExportTaskDto createExportTask(Long userId);

    ExportTaskDto getExportTaskById(Long taskId);

    List<ExportTaskDto> getExportTasksByUserId(Long userId);

    ExportFile getExportFile(Long id);
}
