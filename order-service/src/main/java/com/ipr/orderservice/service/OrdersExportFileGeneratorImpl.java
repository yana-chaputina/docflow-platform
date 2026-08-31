package com.ipr.orderservice.service;

import com.ipr.orderservice.entity.ExportTask;
import com.ipr.orderservice.entity.ExportTaskStatus;
import com.ipr.orderservice.entity.Order;
import com.ipr.orderservice.repository.ExportTaskRepository;
import com.ipr.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrdersExportFileGeneratorImpl implements OrdersExportFileGenerator {

    private final OrderRepository orderRepository;
    private final ExportTaskRepository exportTaskRepository;

    public OrdersExportFileGeneratorImpl(OrderRepository orderRepository, ExportTaskRepository exportTaskRepository) {
        this.orderRepository = orderRepository;
        this.exportTaskRepository = exportTaskRepository;
    }

    @Async
    @Transactional
    @Override
    public void generateOrdersCsv(Long taskId) {
        log.info("Starting async report generation for taskId: {}", taskId);
        log.info(
                "Task {} started on thread: {}, virtual: {}",
                taskId,
                Thread.currentThread(),
                Thread.currentThread().isVirtual()
        );
        try {
            ExportTask task = exportTaskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Export task not found"));
            task.setStatus(ExportTaskStatus.PROCESSING);
            exportTaskRepository.save(task);

            String filePath = generateCSVFile(
                    task.getUserId(),
                    task.getFileName()
            );
            task.setFileUrl(filePath);
            task.setStatus(ExportTaskStatus.DONE);
            task.setCompletedAt(LocalDateTime.now());
            exportTaskRepository.save(task);

            log.info("Report generation completed for taskId: {}", taskId);
            log.info(
                    "Task {} finished on thread: {}, virtual: {}",
                    taskId,
                    Thread.currentThread(),
                    Thread.currentThread().isVirtual()
            );
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

    private String generateCSVFile(Long userId, String fileName){
        List<Order> orders = orderRepository.findByUserId(userId);

        Path exportDirectory = Paths.get("order-service/exports");
        Path filePath = exportDirectory.resolve(fileName);

        try {
            Files.createDirectories(exportDirectory);

            try (BufferedWriter writer = Files.newBufferedWriter(
                    filePath,
                    StandardCharsets.UTF_8
            )) {

                writer.write(
                        "id,title,description,status,priority,userId,assigneeId," +
                                "createdAt,updatedAt,resolvedAt,rejectionReason"
                );
                writer.newLine();

                for (Order order : orders) {
                    writer.write(toCsvRow(order));
                    writer.newLine();
                }
            }

            return filePath.toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV file", e);
        }
    }

    private String toCsvRow(Order order) {
        return String.join(",",
                escape(order.getId()),
                escape(order.getTitle()),
                escape(order.getDescription()),
                escape(order.getStatus()),
                escape(order.getPriority()),
                escape(order.getUserId()),
                escape(order.getAssigneeId()),
                escape(order.getCreatedAt()),
                escape(order.getUpdatedAt()),
                escape(order.getResolvedAt()),
                escape(order.getRejectionReason())
        );
    }

    private String escape(Object value) {
        if (value == null) {
            return "";
        }

        String text = value.toString();

        if (text.contains(",") || text.contains("\"") || text.contains("\n")) {
            return "\"" + text.replace("\"", "\"\"") + "\"";
        }

        return text;
    }
}

