package com.spring_boot.circuit_breaker.service;

import com.spring_boot.circuit_breaker.response.OrderResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

/**
 * Service to handle Order creation with built-in fault tolerance using Resilience4j.
 * This class uses a programmatic approach to wrap network calls in a Circuit Breaker.
 */
@Service
public class CircuitBreakerService {

    @Value("${services.order-service.url}")
    private String uri;

    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;

    /**
     * Constructor injection for required beans.
     * Initializes the CircuitBreaker instance specifically for 'orderService'
     * using the 'default' configuration profile defined in your Registry.
     */
    public CircuitBreakerService(RestTemplate restTemplate, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("orderServiceCB", "default");
    }

    /**
     * Main entry point to create an order.
     * It decorates the actual HTTP call with Circuit Breaker logic.
     */
    public ResponseEntity<OrderResponse> createOrder() {
        // 1. Decorate the 'callCreateOrderService' method as a Supplier.
        // This ensures the Circuit Breaker tracks the success/failure of this specific logic.
        Supplier<ResponseEntity<OrderResponse>> protectedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                this::callCreateOrderService
        );

        try {
            // 2. Attempt to execute the call through the Circuit Breaker.
            return protectedSupplier.get();
        } catch (Exception e) {
            // 3. If the Circuit is OPEN or the call fails, execute the fallback logic.
            return fallbackCreateOrder(e);
        }
    }

    /**
     * The actual network call logic.
     * Separated so it can be monitored by the Circuit Breaker.
     */
    private ResponseEntity<OrderResponse> callCreateOrderService() {
        // Perform the GET request to the external order-service
        ResponseEntity<OrderResponse> response = restTemplate.getForEntity(uri, OrderResponse.class);

        // Explicitly check for non-2xx codes to trigger a failure in the Circuit Breaker stats
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Service failure: HTTP " + response.getStatusCode());
        }

        return response;
    }

    /**
     * Fallback method that runs when the external service is down or the Circuit is OPEN.
     * Instead of throwing an error to the user, we return a "graceful failure" object.
     */
    private ResponseEntity<OrderResponse> fallbackCreateOrder(Exception ex) {
        // Build a user-friendly error description
        String errorMessage = new StringBuilder("FAILED TO CREATE ORDER: ")
                .append("Order service is temporarily unavailable. ")
                .append("Please try again later. Details: ")
                .append(ex.getMessage())
                .toString();

        OrderResponse fallbackResponse = new OrderResponse();
        fallbackResponse.setId(-1L); // ID -1 indicates a dummy/error record
        fallbackResponse.setStatus(errorMessage);

        // Return a 200 OK or 503 Service Unavailable depending on your API design requirements

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(fallbackResponse);

    }
}