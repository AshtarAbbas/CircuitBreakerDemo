# CircuitBreakerDemo

A **complete Spring Boot microservices demo project** showcasing how to implement the **Circuit Breaker design pattern using Resilience4j**.

This repository is **interview‑ready**, **production‑aligned**, and structured for **clear understanding of fault tolerance in distributed systems**.

---

## 📁 Repository Structure

```
CircuitBreakerDemo
│
├── order-service
│   ├── src/main/java
│   │   └── com.circuit_breaker.order_service
│   │       ├── controller
│   │       │   └── OrderController.java
│   │       └── response
│   │           └── OrderResponse.java
│   └── src/main/resources
│       └── application.properties
│
├── circuit-breaker-service
│   ├── src/main/java
│   │   └── com.spring_boot.circuit_breaker
│   │       ├── config
│   │       │   ├── CircuitBreakerConfiguration.java
│   │       │   └── RestClientConfig.java
│   │       ├── controller
│   │       │   └── CircuitBreakerController.java
│   │       ├── service
│   │       │   └── CircuitBreakerService.java
│   │       ├── response
│   │       │   └── OrderResponse.java
│   │       └── CircuitBreakerApplication.java
│   └── src/main/resources
│       └── application.yml
│
├── INTERVIEW.md
└── README.md   ← (this file)
```

---

## 🎯 Purpose of This Project

This demo helps you understand:

- ✅ Why **Circuit Breakers** are needed in microservices
- ✅ How to implement them using **Resilience4j**
- ✅ How to protect services from cascading failures
- ✅ How to design **graceful fallbacks**
- ✅ How to explain this clearly in **interviews**

---

## 🧩 Services Overview

### 1️⃣ Order Service (`order-service`)

- Runs on **port 8081**
- Simple REST service
- Simulates an external/downstream dependency
- Returns order creation response

**Endpoint**
```
GET /api/create-order
```

**Sample Response**
```json
{
  "id": 1,
  "status": "Order Created Successfully"
}
```

---

### 2️⃣ Circuit Breaker Service (`circuit-breaker-service`)

- Runs on **port 8080**
- Calls Order Service using `RestTemplate`
- Wraps calls with **Resilience4j Circuit Breaker**
- Returns fallback response when Order Service fails

**Protected Endpoint**
```
GET /api/create-order
```

**Fallback Response (when circuit is open)**
```json
{
  "id": -1,
  "status": "FAILED TO CREATE ORDER: Order service is temporarily unavailable"
}
```

---

## 🔁 Request Flow

```
Client
  │
  ▼
CircuitBreakerController
  │
  ▼
CircuitBreakerService
  │  (Resilience4j Circuit Breaker)
  ▼
Order Service
```

If failures exceed threshold:

```
Circuit changes to OPEN → calls are short-circuited → fallback executed
```

---

## 🛡️ Circuit Breaker Configuration

Multiple strategies are defined programmatically:

| Config Type | Use Case | Sliding Window | Failure Threshold | Open State |
|-----------|---------|---------------|------------------|-----------|
| default | General | Resilience4j defaults | Default | Default |
| external | Unstable services | 5 calls | 40% | 60 sec |
| internal | Internal services | 20 calls | 70% | 15 sec |

Configurations are registered in a **CircuitBreakerRegistry** and referenced dynamically.

---

## ⚙️ application.yml (Circuit Breaker Service)

```yaml
server:
  port: 8080

spring:
  application:
    name: circuit-breaker

management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always

services:
  order-service:
    url: http://localhost:8081/api/create-order
```

---

## 📊 Actuator & Monitoring

Fully exposed actuator endpoints:

```
/actuator/health
/actuator/metrics
/actuator/circuitbreakers
/actuator/circuitbreakerevents
```

Useful for:
- Observing circuit state transitions
- Debugging failures
- Production monitoring

---

## ▶️ How to Run the Demo

### Prerequisites

- Java 17+
- Maven

### Steps

1. Start **Order Service**

```bash
cd order-service
mvn spring-boot:run
```

2. Start **Circuit Breaker Service**

```bash
cd circuit-breaker-service
mvn spring-boot:run
```

3. Call API

```
http://localhost:8080/api/create-order
```

4. Stop Order Service and retry to see fallback behavior

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Resilience4j
- Spring Web (RestTemplate)
- Spring Actuator
- Maven

---

## 📄 INTERVIEW.md

The `INTERVIEW.md` file contains:

- Circuit Breaker theory
- CLOSED / OPEN / HALF_OPEN states
- Difference between **Resilience4j vs Hystrix**
- Real-world failure scenarios
- Common interview questions & answers

Use it for **quick interview revision**.

---

## ✅ Why This Repo Is Interview‑Ready

- ✔ Clean architecture
- ✔ Realistic microservice interaction
- ✔ Production-style fault handling
- ✔ Strong conceptual clarity
- ✔ Easy to explain in system‑design rounds

---

## 🚀 Future Enhancements

- switch to **WebClient + Reactive**
- add **Retry & Rate Limiter**
- externalize CB config to YML
- Prometheus + Grafana dashboards
- Docker Compose setup

---

## 📜 License

This project is intended for **learning and interview preparation** purposes.
