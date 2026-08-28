package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.ExportFile;
import com.ipr.orderservice.dto.ExportTaskDto;
import com.ipr.orderservice.entity.ExportTask;
import com.ipr.orderservice.entity.ExportTaskStatus;
import com.ipr.orderservice.mapper.ExportTaskToExportTaskDtoMapper;
import com.ipr.orderservice.repository.ExportTaskRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ExportTaskServiceImpl implements ExportTaskService {

    private final ExportTaskRepository exportTaskRepository;

    private final OrdersExportFileGenerator ordersExportFileGenerator;

    private final ExportTaskToExportTaskDtoMapper exportTaskToExportTaskDtoMapper;

    public ExportTaskServiceImpl(ExportTaskRepository exportTaskRepository, OrdersExportFileGenerator ordersExportFileGenerator, ExportTaskToExportTaskDtoMapper exportTaskToExportTaskDtoMapper) {
        this.exportTaskRepository = exportTaskRepository;
        this.ordersExportFileGenerator = ordersExportFileGenerator;
        this.exportTaskToExportTaskDtoMapper = exportTaskToExportTaskDtoMapper;
    }

    @Transactional
    @Override
    public ExportTaskDto createExportTask(Long userId) {
        ExportTask exportTask=new ExportTask();
        exportTask.setUserId(userId);
        exportTask.setFileName("orders_export_" + System.currentTimeMillis() + ".csv");
        exportTask.setStatus(ExportTaskStatus.PENDING);
        ExportTask savedExportTask = exportTaskRepository.save(exportTask);
        ordersExportFileGenerator.generateOrdersCsv(savedExportTask.getId());
        return exportTaskToExportTaskDtoMapper.exportTaskToExportTaskDto(savedExportTask);
    }

    @Override
    public ExportTaskDto getExportTaskById(Long taskId) {
        ExportTask exportTask=exportTaskRepository.findById(taskId).orElseThrow(
                ()->new RuntimeException("Export task not found"));
        return exportTaskToExportTaskDtoMapper.exportTaskToExportTaskDto(exportTask);
    }

    @Override
    public List<ExportTaskDto> getExportTasksByUserId(Long userId) {
        return exportTaskToExportTaskDtoMapper.exportTasksToExportTaskDtoAsList(exportTaskRepository.findByUserId(userId));
    }

    @Override
    public ExportFile getExportFile(Long id) {
        ExportTask exportTask=exportTaskRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Export task not found")
        );

        if (exportTask.getStatus() != ExportTaskStatus.DONE) {
            throw new RuntimeException("Export file is not ready");
        }

        Path filePath = Paths.get(exportTask.getFileUrl());

        if (!Files.exists(filePath)) {
            throw new RuntimeException("Export file not found");
        }

        Resource resource = new FileSystemResource(filePath);

        return new ExportFile(
                exportTask.getFileName(),
                resource
        );
    }
}
