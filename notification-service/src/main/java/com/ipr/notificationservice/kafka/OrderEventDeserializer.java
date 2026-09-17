package com.ipr.notificationservice.kafka;

import com.ipr.notificationservice.event.OrderEvent;
import org.apache.kafka.common.serialization.Deserializer;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class OrderEventDeserializer implements Deserializer<OrderEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Initialization logic if needed
    }

    @Override
    public OrderEvent deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(data, OrderEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing byte[] to OrderEvent object", e);
        }
    }

    @Override
    public void close() {
        // Cleanup logic if needed
    }
}
