package com.ipr.orderservice.controller;

import com.ipr.orderservice.dto.ExportFile;
import com.ipr.orderservice.dto.ExportTaskDto;
import com.ipr.orderservice.service.ExportTaskService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
        return ResponseEntity.ok(exportTaskService.createExportTask(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExportTaskDto> getExportTaskById(@PathVariable Long id){
        return ResponseEntity.ok(exportTaskService.getExportTaskById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ExportTaskDto>> getExportTaskByUserId(@PathVariable Long id){
        return ResponseEntity.ok(exportTaskService.getExportTasksByUserId(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadExportFile(@PathVariable Long id){
        ExportFile exportFile = exportTaskService.getExportFile(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + exportFile.fileName() + "\""
                )
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(exportFile.resource());
    }
}
