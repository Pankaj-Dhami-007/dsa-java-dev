package com.dhami.java_dev.microservices;

public class ApiGatewayDeepDrive {
}

/**

What is an API Gateway?
An API Gateway is a server that acts as a single entry point for all client requests
 into a backend system.
 Instead of clients calling multiple microservices directly, they call the API Gateway,
 which routes requests to the appropriate services, handles cross-cutting concerns,
 and returns responses.

 Think of it as a reverse proxy + traffic cop + bouncer + translator for your APIs.

                     ┌─────────────────┐
   Mobile App ─────►│                 │─────► User Service
   Web App ────────►│   API GATEWAY   │─────► Order Service
   Partner API ────►│                 │─────► Payment Service
                    └─────────────────┘─────► Inventory Service
 */

/**

 In a monolithic architecture, clients call one backend. Simple.
 But in microservices, you have dozens or hundreds of services. Without a gateway,
 clients would need to:

Know every service's address
Handle authentication with each service separately
Deal with different protocols/formats per service
Make many round trips (chatty communication)
Handle failures, retries, and rate limits themselves
The API Gateway solves this by centralizing these concerns.

 */

/**

Problems the API Gateway Solves ->>>

 1. Client Complexity (Chattiness)
Problem: A single mobile screen might need data from 5 services → 5 network calls.
Solution: Gateway aggregates calls into one and returns a single response.

 Without Gateway:  Client → 5 services (5 round trips)
With Gateway:     Client → Gateway → 5 services (1 round trip)

 2. Cross-Cutting Concerns Duplication
Every microservice would otherwise need to implement:
Authentication / Authorization
Rate limiting / Throttling
Logging / Monitoring
SSL termination
CORS handling
Gateway centralizes these — services focus only on business logic.

 3. Protocol Translation
Backend services might use gRPC, Thrift, WebSockets, or SOAP. Clients may only speak REST/HTTP.
Gateway translates between protocols.

 4. Security
Hides internal service topology from external clients
Single place to enforce auth (JWT validation, OAuth, API keys)
Protects against DDoS, injection, malformed requests

 5. Service Discovery & Routing
Services scale up/down dynamically. Gateway integrates with service registries
 (Eureka, Consul, Kubernetes DNS) to route to healthy instances.

 6. Resilience
Gateway can implement:
Circuit breakers
Retries with backoff
Timeouts
Fallbacks (return cached/stale data)

 7. Versioning & Backward Compatibility
Old clients hit /v1/..., new clients hit /v2/.... Gateway routes them to the correct service versions.

8. Observability
Centralized logging, tracing (correlation IDs), and metrics — one place to see all traffic.
 */

/**
 * ============================================================
 *  DEEP DIVE: API GATEWAY CONCEPTS
 * ============================================================
 *
 * ------------------------------------------------------------
 *  CORE RESPONSIBILITIES
 * ------------------------------------------------------------
 *
 *  Responsibility        | Description
 *  ----------------------|------------------------------------
 *  Routing               | Map request path/method -> backend service
 *  Aggregation           | Combine multiple service responses into one
 *  Authentication        | Verify identity (JWT, OAuth2, API keys)
 *  Authorization         | Enforce access policies (RBAC, scopes)
 *  Rate Limiting         | Prevent abuse (e.g., 100 req/min per user)
 *  Load Balancing        | Distribute traffic across instances
 *  Caching               | Cache responses to reduce backend load
 *  Transformation        | Modify request/response (headers, body, format)
 *  Circuit Breaking      | Stop calling failing services
 *  Monitoring            | Metrics, logs, traces
 *
 *
 * ------------------------------------------------------------
 *  API GATEWAY vs LOAD BALANCER vs REVERSE PROXY
 * ------------------------------------------------------------
 *
 *  Feature                  | Load Balancer | Reverse Proxy | API Gateway
 *  -------------------------|---------------|---------------|-------------
 *  Distributes traffic      |     YES       |     YES       |    YES
 *  SSL termination          |     YES       |     YES       |    YES
 *  Auth                     |     NO        |   Sometimes   |    YES
 *  Rate limiting            |     NO        |   Sometimes   |    YES
 *  Request transformation   |     NO        |     NO        |    YES
 *  Service aggregation      |     NO        |     NO        |    YES
 *  Protocol translation     |     NO        |     NO        |    YES
 *  Business logic awareness |     NO        |     NO        |    YES
 *
 *  API Gateway = Reverse Proxy + Smart Application-Aware Logic
 *
 */

/**

                API GATEWAY
                     |
       ┌─────────────┼─────────────┐
       │             │             │
    Routing       Security      Traffic
       │             │             │
       v             v             v
    /leave         JWT          Rate Limit
    /employee      CORS         Load Balance
    /salary        Headers      Logging
                     |
                     v
              MICROSERVICES
                     |
                     v
              BUSINESS LOGIC
 */

/**

                       ┌──────────────┐
                       │ Flutter App  │
                       └──────┬───────┘
                              │
                              │ HTTPS
                              ▼
                     ┌──────────────────┐
                     │  Load Balancer   │
                     └────────┬─────────┘
                              │
                 ┌────────────┴────────────┐
                 │                         │
          ┌──────▼──────┐           ┌──────▼──────┐
          │ API Gateway │           │ API Gateway │
          │   Instance 1│           │   Instance 2│
          └──────┬──────┘           └──────┬──────┘
                 │                         │
                 └────────────┬────────────┘
                              │
          ┌───────────────────┼────────────────────┐
          │                   │                    │
          ▼                   ▼                    ▼
   ┌─────────────┐     ┌─────────────┐      ┌─────────────┐
   │ Auth Service │     │Leave Service│      │Attendance   │
   │ Spring Boot  │     │Spring Boot  │      │Service      │
   └─────────────┘     └─────────────┘      └─────────────┘
          │                   │                    │
          ▼                   ▼                    ▼
        DB/Auth             Leave DB          Attendance DB
 */

/**

                    Flutter
                       |
                       v
                API Gateway
                       |
      -------------------------------------
      |          |          |              |
      v          v          v              v
 Auth       Employee      Leave        Attendance
 Service    Service       Service        Service
                                             |
                                             v
                                         Salary
                                         Service

 Your Flutter application can use: BASE_URL = https://hrms.company.com/api

 Then:

POST /auth/login
GET  /employees
GET  /employees/{id}
GET  /leaves
POST /leaves
GET  /attendance
GET  /salary

The Flutter app doesn't care where the services actually run.
 */

/*

Without API Gateway

Auth Service        → localhost:8081
Employee Service    → localhost:8082
Leave Service       → localhost:8083
Attendance Service  → localhost:8084

now frontend side

const authBaseUrl = "http://localhost:8081";
const employeeBaseUrl = "http://localhost:8082";
const leaveBaseUrl = "http://localhost:8083";
const attendanceBaseUrl = "http://localhost:8084";

Then API calls:

// Login
POST http://localhost:8081/auth/login

// Employee
GET http://localhost:8082/employees/10

// Leave
GET http://localhost:8083/leaves

// Attendance
GET http://localhost:8084/attendance/today

So Flutter has to know:

8081 → Auth
8082 → Employee
8083 → Leave
8084 → Attendance


With API Gateway >>>>>

Now introduce one Gateway: API Gateway → localhost:8080

                    Flutter
                       |
                       |
              localhost:8080
                       |
                API Gateway
                       |
        ┌──────────────┼──────────────┐
        │              │              │
        ▼              ▼              ▼
   Auth :8081     Employee :8082   Leave :8083


   Now Flutter only knows one URL:
   const baseUrl = "http://localhost:8080";
 */

/**
 * ============================================================
 *   API COMPOSITION (Aggregation vs Orchestration)
 * ============================================================
 *
 *  API Composition = Gateway calling MULTIPLE services and
 *  presenting a UNIFIED response to the client.
 *
 *  Three styles:
 *
 *  (A) AGGREGATION (Parallel Composition)
 *      - Calls services in parallel
 *      - Merges responses into one
 *      - Gateway-friendly (classic pattern)
 *
 *      Client --/dash--> [GATEWAY]
 *                          |   |   |
 *                          v   v   v
 *                      [User][Order][Notify]
 *                          \   |   /
 *                           v  v  v
 *                        Merged JSON
 *
 *  (B) CHOREOGRAPHY (Event-driven)
 *      - Services react to each other's events
 *      - No central orchestrator
 *      - Broker: Kafka / RabbitMQ
 *      - Gateway = just entry point
 *
 *      [Order] --event--> [Inventory] --event--> [Payment]
 *
 *  (C) ORCHESTRATION (Sequential / Dependent)
 *      - Central orchestrator calls services in ORDER
 *      - Each step depends on previous output
 *      - Should NOT live inside the gateway if complex
 *      - Use dedicated Orchestrator / Saga service
 *
 *      1. Create Order
 *      2. Reserve Inventory  (uses orderId)
 *      3. Charge Payment     (uses reservationId)
 *      4. Send Notification
 *
 *
 *  AGGREGATION vs ORCHESTRATION
 *  ----------------------------
 *  Feature            | Aggregation   | Orchestration
 *  -------------------|---------------|-----------------
 *  Calls              | Parallel      | Sequential
 *  Dependency         | Independent   | Dependent
 *  Gateway fit        | YES           | NO (if complex)
 *  Latency            | Slowest call  | Sum of all calls
 *  Failure handling   | Partial OK    | Rollback (Saga)
 *  Where to put it    | API Gateway   | Orchestrator/Saga
 *
 *
 *  INTERVIEW LINE:
 *    "Gateway handles aggregation natively, but complex
 *     orchestration belongs to a dedicated orchestrator or
 *     Saga pattern - otherwise the gateway becomes a Fat
 *     Gateway anti-pattern."
 *
 * ============================================================
 */