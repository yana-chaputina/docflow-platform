package com.ipr.orderservice.controller;

import com.ipr.orderservice.dto.ExportTaskDto;
import com.ipr.orderservice.service.ExportTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/export-task")
public class ExportTaskController {

    private final ExportTaskService exportTaskService;

    public ExportTaskController(ExportTaskService exportTaskService) {
        this.exportTaskService = exportTaskService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<ExportTaskDto> createExportTask(@PathVariable Long id){
        ExportTaskDto exportTaskDto = exportTaskService.createExportTask(id);
        exportTaskService.generateExportTask(exportTaskDto.id());
        return ResponseEntity.ok(exportTaskDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExportTaskDto> getExportTaskById(@PathVariable Long id){
        return ResponseEntity.ok(exportTaskService.getExportTaskById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ExportTaskDto>> getExportTaskByUserId(@PathVariable Long id){
        return ResponseEntity.ok(exportTaskService.getExportTasksByUserId(id));
    }
}
