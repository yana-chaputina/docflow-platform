package com.ipr.orderservice.controller;

import com.ipr.orderservice.dto.StatusHistoryDto;
import com.ipr.orderservice.service.StatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/status-history")
public class StatusHistoryController {
    private final StatusHistoryService statusHistoryService;

    @Autowired
    public StatusHistoryController(StatusHistoryService statusHistoryService) {
        this.statusHistoryService = statusHistoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<StatusHistoryDto>> getStatusHistoryByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(statusHistoryService.getStatusHistoryByOrderId(id));
    }

}
