package com.ipr.orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="export_tasks")
@Getter
@Setter
@NoArgsConstructor
public class ExportTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ExportTaskStatus status;

    @Column(length = 500)
    private String fileUrl;

    private String errorMessage;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime requestedAt;

    private LocalDateTime completedAt;
}
