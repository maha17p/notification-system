package com.notification.order_service.controller;

import com.notification.order_service.modal.Order;
import com.notification.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam String product, @RequestParam int quantity , @RequestParam int price) throws Exception {
        Order order = orderService.createOrder(product,quantity,price);
        return ResponseEntity.status(HttpStatus.CREATED).body(order) ;
    }
}
