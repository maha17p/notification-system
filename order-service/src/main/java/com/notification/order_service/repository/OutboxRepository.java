package com.notification.order_service.repository;

import com.notification.order_service.modal.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent,String> {
    List<OutboxEvent> findByProcessedFalse();
}
