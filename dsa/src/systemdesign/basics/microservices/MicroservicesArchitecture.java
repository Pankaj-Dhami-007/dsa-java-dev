package systemdesign.basics.microservices;

public class MicroservicesArchitecture {

}

/*

========================================================================
                MICROSERVICES ARCHITECTURE
========================================================================

Microservices is one of MOST IMPORTANT
modern backend architecture styles.

========================================================================

Most large-scale companies use:
        Microservices

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:0]{index=0}
- :contentReference[oaicite:1]{index=1}
- :contentReference[oaicite:2]{index=2}
- :contentReference[oaicite:3]{index=3}

========================================================================
MAIN QUESTION
========================================================================

Why microservices created?

========================================================================
PROBLEM WITH MONOLITHIC APPLICATION
========================================================================

Initially applications were built as:
        Monolith

========================================================================
WHAT IS MONOLITH?
========================================================================

Entire application exists
inside single codebase and deployment.

========================================================================
EXAMPLE
========================================================================

Single application contains:
- authentication
- payment
- orders
- notifications
- chat
- admin

========================================================================

Everything inside:
        one project

========================================================================
ADVANTAGES OF MONOLITH
========================================================================

1. Simple development
2. Easy deployment initially
3. Easy debugging initially
4. Easy local setup

========================================================================
PROBLEMS OF MONOLITH
========================================================================

As application grows:

========================================================================

1. Huge codebase
2. Slow deployment
3. Scaling difficult
4. Single point of failure
5. Team conflicts
6. Difficult maintenance
7. Technology flexibility limited

========================================================================
EXAMPLE
========================================================================

Suppose:
notification module receives huge traffic.

========================================================================

But monolith forces:
entire application scaling.

========================================================================

Very inefficient.

========================================================================
THIS IS WHY
========================================================================

Microservices architecture created.

========================================================================
WHAT IS MICROSERVICE?
========================================================================

Microservice is small independent service
focused on single business functionality.

========================================================================
SIMPLE IDEA
========================================================================

Break huge application
into smaller independent services.

========================================================================
EXAMPLE
========================================================================

Instead of:
one giant application

========================================================================

Use separate services:
- User Service
- Payment Service
- Order Service
- Notification Service
- Chat Service

========================================================================
MICROSERVICES FLOW
========================================================================

Client
   ↓
API Gateway
   ↓
User Service
Order Service
Payment Service
Notification Service

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Each service:
- runs independently
- deploys independently
- scales independently

========================================================================
MAIN GOALS OF MICROSERVICES
========================================================================

1. Independent scaling
2. Independent deployment
3. Better maintainability
4. Faster development
5. Fault isolation
6. Team independence

========================================================================
IMPORTANT CHARACTERISTICS
========================================================================

1. Small focused services
2. Independent deployment
3. Separate databases often
4. API communication
5. Decentralized architecture

========================================================================
SINGLE RESPONSIBILITY PRINCIPLE
========================================================================

Each microservice should focus
on one business capability.

========================================================================
EXAMPLE
========================================================================

Payment Service handles:
- payments
- refunds
- transactions

========================================================================

NOT:
chat or notifications.

========================================================================
SERVICE COMMUNICATION
========================================================================

Microservices communicate using:
- REST APIs
- gRPC
- Message Queues
- Event Streaming

========================================================================
REST COMMUNICATION
========================================================================

Most common communication style.

========================================================================

Example:
Order Service calls Payment Service API.

========================================================================
ASYNCHRONOUS COMMUNICATION
========================================================================

Services communicate using:
- Kafka
- RabbitMQ

========================================================================

Useful for:
- notifications
- analytics
- background processing

========================================================================
EXAMPLE
========================================================================

Order Created Event
        ↓
Notification Service consumes event
        ↓
Email/SMS sent

========================================================================
DATABASE PER SERVICE
========================================================================

Very important microservices principle.

========================================================================

Each service should ideally own:
        its database

========================================================================
WHY?
========================================================================

Prevents:
tight coupling.

========================================================================
BAD PRACTICE
========================================================================

All services sharing same database.

========================================================================

Creates:
- dependency issues
- deployment problems
- scaling problems

========================================================================
GOOD PRACTICE
========================================================================

User Service → User DB

Order Service → Order DB

Payment Service → Payment DB

========================================================================
API GATEWAY
========================================================================

Clients usually do NOT directly call
all microservices individually.

========================================================================

Instead:
API Gateway used.

========================================================================
WHAT IS API GATEWAY?
========================================================================

Single entry point for clients.

========================================================================
RESPONSIBILITIES
========================================================================

1. Request routing
2. Authentication
3. Rate limiting
4. Load balancing
5. Logging

========================================================================
FLOW
========================================================================

Client
   ↓
API Gateway
   ↓
Microservices

========================================================================
SERVICE DISCOVERY
========================================================================

In microservices,
service instances continuously change.

========================================================================

Need mechanism to locate services dynamically.

========================================================================

THIS IS CALLED:
        Service Discovery

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Microservices are highly dynamic.

========================================================================

Servers may:
- scale up
- scale down
- restart frequently

========================================================================
THIS IS WHY
========================================================================

Static IP management becomes difficult.

========================================================================
LOAD BALANCING IN MICROSERVICES
========================================================================

Each service may have:
multiple instances.

========================================================================

Traffic distributed using:
Load Balancers.

========================================================================
FAULT ISOLATION
========================================================================

Suppose:
Notification Service crashes.

========================================================================

Other services still continue working.

========================================================================

This is HUGE advantage over monolith.

========================================================================
MICROSERVICES + SCALING
========================================================================

Different services scale independently.

========================================================================
EXAMPLE
========================================================================

Chat Service:
100 instances

========================================================================

Payment Service:
10 instances

========================================================================

Scaling optimized according to traffic.

========================================================================
MICROSERVICES + TEAMS
========================================================================

Different teams manage:
different services.

========================================================================
EXAMPLE
========================================================================

Payment Team
-------------
handles payment service

========================================================================

Chat Team
----------
handles chat service

========================================================================

Deployment independence improves productivity.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Microservices improve:
- scalability
- flexibility
- maintainability

========================================================================

BUT ALSO introduce:
distributed system complexity.

========================================================================
COMMON MICROSERVICES CHALLENGES
========================================================================

1. Network latency
2. Distributed debugging
3. Data consistency
4. Service coordination
5. Deployment complexity
6. Monitoring complexity

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Monolith problems become:
distributed systems problems.

========================================================================
DISTRIBUTED TRANSACTIONS
========================================================================

Suppose:
Order Service
and Payment Service
both involved in transaction.

========================================================================

If one succeeds and another fails:
consistency issue occurs.

========================================================================

This becomes complex in microservices.

========================================================================
SOLUTIONS
========================================================================

1. Saga Pattern
2. Event-driven architecture
3. Compensation transactions

========================================================================
MONITORING
========================================================================

Microservices require strong monitoring.

========================================================================
TOOLS
========================================================================

- :contentReference[oaicite:6]{index=6}
- :contentReference[oaicite:7]{index=7}

========================================================================
LOGGING
========================================================================

Distributed logs become difficult.

========================================================================

Need:
centralized logging systems.

========================================================================
REAL WORLD ARCHITECTURE
========================================================================

Client
   ↓
API Gateway
   ↓
Authentication Service
Post Service
Chat Service
Notification Service
Payment Service
   ↓
Redis / Kafka / Databases

========================================================================
WHEN MICROSERVICES SHOULD NOT BE USED?
========================================================================

Small applications.

========================================================================

Early-stage startups often use:
        Monolith

========================================================================

Because microservices introduce:
huge operational complexity.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Microservices are NOT automatically better.

========================================================================

They are useful only when:
system scale and complexity increase.

========================================================================
MONOLITH vs MICROSERVICES
========================================================================

MONOLITH
---------
Simple initially
Easy deployment
Hard scaling

========================================================================

MICROSERVICES
--------------
Complex architecture
Independent scaling
Distributed complexity

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What are microservices?

→ Small independent services
focused on specific business functionality.

========================================================================

2. Why microservices created?

→ Solve monolith scalability and maintainability problems.

========================================================================

3. What is API Gateway?

→ Single entry point for clients.

========================================================================

4. Why database per service important?

→ Prevent tight coupling.

========================================================================

5. Advantages of microservices?

→ Independent scaling and deployment.

========================================================================

6. Challenges of microservices?

→ Distributed systems complexity.

========================================================================

7. What is service discovery?

→ Dynamic identification of service instances.

========================================================================

8. Difference between monolith and microservices?

Monolith
---------
single application

Microservices
--------------
multiple independent services

========================================================================

9. Why monitoring important in microservices?

→ Multiple distributed services are difficult to track.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Microservices architecture breaks
large applications into small
independent deployable services
that communicate over network
to improve scalability and maintainability.

========================================================================

*/