package com.circuit_breaker.order_service.response;

public class OrderResponse {
    private Long id;
    private String status;

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public OrderResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}
