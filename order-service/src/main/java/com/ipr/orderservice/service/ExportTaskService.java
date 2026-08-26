package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.ExportTaskDto;

import java.util.List;

public interface ExportTaskService {

    ExportTaskDto createExportTask(Long userId);

    void generateExportTask(Long taskId);

    ExportTaskDto getExportTaskById(Long taskId);

    List<ExportTaskDto> getExportTasksByUserId(Long userId);
}
