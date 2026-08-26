package com.ipr.orderservice.service;

import com.ipr.orderservice.dto.ExportTaskDto;
import com.ipr.orderservice.entity.ExportTask;
import com.ipr.orderservice.entity.ExportTaskStatus;
import com.ipr.orderservice.mapper.ExportTaskToExportTaskDtoMapper;
import com.ipr.orderservice.repository.ExportTaskRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ExportTaskServiceImpl implements ExportTaskService {

    private final ExportTaskRepository exportTaskRepository;

    private final ExportTaskToExportTaskDtoMapper exportTaskToExportTaskDtoMapper;

    public ExportTaskServiceImpl(ExportTaskRepository exportTaskRepository, ExportTaskToExportTaskDtoMapper exportTaskToExportTaskDtoMapper) {
        this.exportTaskRepository = exportTaskRepository;
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
        return exportTaskToExportTaskDtoMapper.exportTaskToExportTaskDto(savedExportTask);
    }

    @Async
    @Transactional
    public void generateExportTask(Long taskId) {
        log.info("Starting async report generation for taskId: {}", taskId);
        try {
            // Шаг 1: обновить статус на PROCESSING
            ExportTask task = exportTaskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Export task not found"));
            task.setStatus(ExportTaskStatus.PROCESSING);
            exportTaskRepository.save(task);

            // Шаг 2: имитация долгой генерации (3 секунды)
            Thread.sleep(3000);

            // Шаг 3: генерация "файла" — здесь просто формируем URL
            String fileUrl = "/exports/" + task.getFileName();
            task.setFileUrl(fileUrl);
            task.setStatus(ExportTaskStatus.DONE);
            task.setCompletedAt(LocalDateTime.now());
            exportTaskRepository.save(task);

            log.info("Report generation completed for taskId: {}", taskId);
        } catch (Exception e) {
            log.error("Error generating report for taskId: {}", taskId, e);
            // Обновляем статус на FAILED
            ExportTask task = exportTaskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            task.setStatus(ExportTaskStatus.FAILED);
            task.setErrorMessage(e.getMessage());
            exportTaskRepository.save(task);
        }
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
}
