package com.ipr.orderservice.dto;

import org.springframework.core.io.Resource;

public record ExportFile(
        String fileName,
        Resource resource
) {
}
