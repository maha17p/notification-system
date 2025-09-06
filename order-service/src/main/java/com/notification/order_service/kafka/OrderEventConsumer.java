package com.notification.order_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {
    @KafkaListener(topics = "order-events", groupId = "order-service-group")
    public void consume(String message) {
        System.out.println("📩 Received event from Kafka: " + message);
    }
}
