package com.circuit_breaker.order_service.controller;

import com.circuit_breaker.order_service.response.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    @GetMapping("/create-order")
    public ResponseEntity<OrderResponse> createOrder(){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new OrderResponse(1L, "Order Created Successfully"));
    }
}
