package com.ipr.notificationservice.kafka;

import com.ipr.notificationservice.event.OrderEvent;
import com.ipr.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;

@Service
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    private final NotificationService notificationService;

    public KafkaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    @RetryableTopic(
            attempts = "2",
            backOff = @BackOff(delay = 2000L),
            include = SocketTimeoutException.class)
    public void consume(OrderEvent orderEvent) throws SocketTimeoutException {
        logger.info("Kafka consumer belongs to the group {} has received message : {} to the topic : {}",
                groupId,orderEvent.message(), topicName);
        notificationService.saveNotificationFromEvent(orderEvent);
    }

    @DltHandler
    public void handleDltOrderEvent(
            OrderEvent orderEvent,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        logger.error(
                "Event moved to DLT: topic={}, payload={}",
                topic,
                orderEvent
        );

    }
}
