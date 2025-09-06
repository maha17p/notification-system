package com.notification.order_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.order_service.modal.Order;
import com.notification.order_service.modal.OutboxEvent;
import com.notification.order_service.repository.OrderRepository;
import com.notification.order_service.repository.OutboxRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository){
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    public Order createOrder(String product, int quantity, int price) throws Exception{
        //Save order
        Order order = new Order();
        order.setProduct(product);
        order.setPrice(price);
        order.setQuantity(quantity);
        orderRepository.save(order);

        //Prepare event
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateId(order.getId());
        outboxEvent.setEventType("OrderCreated");
        outboxEvent.setPayload(objectMapper.writeValueAsString(order));
        outboxRepository.save(outboxEvent);

        return order;
    }
}
