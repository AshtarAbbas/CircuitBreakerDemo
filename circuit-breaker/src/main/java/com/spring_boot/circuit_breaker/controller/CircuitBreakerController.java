package com.spring_boot.circuit_breaker.controller;

import com.spring_boot.circuit_breaker.response.OrderResponse;
import com.spring_boot.circuit_breaker.response.OrderResponse;
import com.spring_boot.circuit_breaker.service.CircuitBreakerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class CircuitBreakerController {

    /**
     * Service responsible for handling business logic
     * and invoking circuit breaker‑protected operations.
     */
    private final CircuitBreakerService circuitBreakerService;

    /**
     * Constructor-based dependency injection
     * Ensures immutability and makes the controller easier to test.
     */
    public CircuitBreakerController(CircuitBreakerService circuitBreakerService) {
        this.circuitBreakerService = circuitBreakerService;
    }

    /**
     * Endpoint to create an order.
     *
     * This controller delegates the processing to the service layer,
     * where the Circuit Breaker logic is applied while calling
     * the Order Service.
     *
     * @return ResponseEntity containing OrderResponse
     */
    @GetMapping("/create-order")
    public ResponseEntity<OrderResponse> createOrder(){
        // Delegates API call handling to the service layer
        return circuitBreakerService.createOrder();
    }

}