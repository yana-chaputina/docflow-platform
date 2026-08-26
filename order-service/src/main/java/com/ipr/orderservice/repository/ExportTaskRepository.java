package com.ipr.orderservice.repository;

import com.ipr.orderservice.entity.ExportTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportTaskRepository extends JpaRepository<ExportTask, Long> {
    List<ExportTask> findByUserId(Long userId);
}
