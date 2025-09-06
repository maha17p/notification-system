package com.notification.order_service.kafka;

import com.notification.order_service.modal.OutboxEvent;
import com.notification.order_service.repository.OutboxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;

    public OutboxPublisher(OutboxRepository outboxRepository, KafkaTemplate<String,String> kafkaTemplate){
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void publishEvents(){
        List<OutboxEvent> outboxEvents = outboxRepository.findByProcessedFalse();
        for(OutboxEvent event:outboxEvents){
            try {
                kafkaTemplate.send("order-events", event.getAggregateId(), event.getPayload());
                System.out.println("✅ Sent event to Kafka: " + event.getPayload());

                event.setProcessed(true);
                outboxRepository.save(event);
            } catch (Exception e) {
                System.err.println("❌ Failed to send event: " + e.getMessage());
            }
        }
    }
}
