package com.ipr.notificationservice.kafka;

import com.ipr.notificationservice.event.OrderEvent;
import org.apache.kafka.common.serialization.Serializer;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class OrderEventSerializer implements Serializer<OrderEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Initialization logic if needed
    }

    @Override
    public byte[] serialize(String topic, OrderEvent orderEvent) {
        if (orderEvent == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(orderEvent);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing OrderEvent object to byte[]", e);
        }
    }

    @Override
    public void close() {
        // Cleanup logic if needed
    }
}
