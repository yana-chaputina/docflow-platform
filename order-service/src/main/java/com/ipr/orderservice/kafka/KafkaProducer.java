package com.ipr.orderservice.kafka;

import com.ipr.orderservice.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, OrderEvent orderEvent){
        logger.info("Kafka producer is sending message : {} to the topic : {}", orderEvent.message(), topicName);
        kafkaTemplate.send(topicName,key,orderEvent);
    }
}
