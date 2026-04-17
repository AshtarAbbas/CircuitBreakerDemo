# Circuit Breaker Service (Spring Boot + Resilience4j)

This project demonstrates a **Circuit Breaker implementation using Resilience4j** in a Spring Boot microservice. The service acts as a **consumer** that calls an external **Order Service** and provides fault tolerance with graceful fallbacks.

---

## 🧩 Architecture Overview

```
Client
  │
  ▼
Circuit-Breaker Service (8080)
  │  (protected by Resilience4j Circuit Breaker)
  ▼
Order Service (8081)
```

- If the **Order Service** becomes unstable or unavailable, the circuit breaker prevents cascading failures.
- A **fallback response** is returned instead of propagating exceptions.

---

## 📁 Project Structure

```
com.spring_boot.circuit_breaker
│
├── config
│   ├── CircuitBreakerConfiguration.java
│   └── RestClientConfig.java
│
├── controller
│   └── CircuitBreakerController.java
│
├── response
│   └── OrderResponse.java
│
├── service
│   └── CircuitBreakerService.java
│
└── CircuitBreakerApplication.java
```

---

## 🚀 REST API

### Create Order (Circuit Breaker Protected)

**Endpoint**
```
GET /api/create-order
```

**Success Response (Order Service UP)**
```json
{
  "id": 1,
  "status": "Order Created Successfully"
}
```

**Fallback Response (Order Service DOWN / Circuit OPEN)**
```json
{
  "id": -1,
  "status": "FAILED TO CREATE ORDER: Order service is temporarily unavailable. Please try again later." 
}
```

---

## ⚙️ Circuit Breaker Configuration

Multiple circuit breaker profiles are defined programmatically:

| Profile   | Sliding Window | Failure Threshold | Open State Duration |
|----------|----------------|-------------------|---------------------|
| default  | Resilience4j defaults | Default | Default |
| external | 5 calls | 40% | 60 seconds |
| internal | 20 calls | 70% | 15 seconds |

These configurations are registered in a `CircuitBreakerRegistry` and referenced by name in the service layer.

---

## 🛡️ How Circuit Breaker Works Here

1. Incoming request hits `CircuitBreakerController`
2. Controller delegates to `CircuitBreakerService`
3. Remote call is wrapped using `CircuitBreaker.decorateSupplier(...)`
4. On repeated failures:
   - Circuit state transitions: **CLOSED → OPEN → HALF_OPEN**
5. While OPEN, calls are short-circuited and fallback is executed

---

## 📊 Actuator & Monitoring

Actuator endpoints are fully exposed for monitoring:

```
/actuator/health
/actuator/metrics
/actuator/circuitbreakers
/actuator/circuitbreakerevents
```

Health details are always visible for debugging and observability.

---

## 🧪 How to Run

### Prerequisites
- Java 17+
- Maven

### Steps

1. Start **Order Service** on port `8081`
2. Start this service

```bash
mvn spring-boot:run
```

3. Call API
```
http://localhost:8080/api/create-order
```

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Resilience4j
- Spring Web (RestTemplate)
- Spring Actuator
- Maven

---

## ✅ Key Highlights

- Programmatic Circuit Breaker (no annotations)
- Centralized CircuitBreakerRegistry
- Graceful fallback handling
- Production-ready configuration
- Interview-ready example

---

## 📌 Future Enhancements

- Switch to **WebClient** (Reactive)
- Add **Retry** and **RateLimiter**
- Externalize CB config to `application.yml`
- Add Prometheus + Grafana dashboards

---

## 📄 License

This project is intended for educational and demonstration purposes.
