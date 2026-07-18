package java_recap.springboot.annotations.rest_api;

/*

=================================================================
                COMPLETE API ARCHITECTURE NOTES
=================================================================

Topics:
--------
1. What is API?
2. Why APIs were invented?
3. API communication evolution
4. Monolith vs Distributed systems
5. REST Architecture
6. SOAP Architecture
7. GraphQL Architecture
8. gRPC Architecture
9. WebSocket Architecture
10. Internal Working
11. Serialization
12. HTTP/1.1 vs HTTP/2
13. Why REST became famous?
14. Why gRPC becoming popular?
15. Real-world Architecture decisions
16. Enterprise interview questions

=================================================================
                1. WHAT IS API?
=================================================================

API =
------
Application Programming Interface

=================================================================
                SIMPLE MEANING
=================================================================

API is a contract/interface through which
two software systems communicate.

=================================================================
                VERY IMPORTANT
=================================================================

Most beginners think:
----------------------

API = HTTP endpoint

WRONG.

HTTP API is only ONE TYPE of API.

=================================================================
                REAL DEFINITION
=================================================================

API defines:
-------------

1. How systems communicate
2. What request format expected
3. What response format returned
4. Rules/protocols of communication

=================================================================
                REAL LIFE EXAMPLE
=================================================================

Swiggy App
    ↓
Calls Restaurant Service API
    ↓
Restaurant system returns menu

Swiggy does NOT know:
----------------------

1. Restaurant database structure
2. Internal business logic
3. Technology stack

API hides internal complexity.

=================================================================
                WHY APIs WERE INVENTED?
=================================================================

Imagine:
---------

Frontend directly accessing database.

Problems:
----------

1. Security disaster
2. Tight coupling
3. No scalability
4. No abstraction
5. No centralized business logic

=================================================================
                SOLUTION
=================================================================

API Layer introduced.

Frontend
    ↓
API Layer
    ↓
Business Logic
    ↓
Database

=================================================================
                BENEFITS OF APIs
=================================================================

1. Abstraction
2. Security
3. Reusability
4. Scalability
5. Centralized logic
6. Cross-platform communication

=================================================================
                API EVOLUTION HISTORY
=================================================================

1. Function Calls
2. Library APIs
3. RPC APIs
4. SOAP
5. REST
6. GraphQL
7. gRPC
8. Event-driven APIs

=================================================================
                2. MONOLITHIC ARCHITECTURE
=================================================================

Initially applications were monoliths.

Meaning:
---------

Everything inside ONE application.

Example:
---------

User Module
Order Module
Payment Module
Inventory Module

All inside single codebase.

=================================================================
                PROBLEMS
=================================================================

1. Huge codebase
2. Difficult deployment
3. Difficult scaling
4. Slow development
5. Technology lock-in

=================================================================
                MICROSERVICES ARRIVED
=================================================================

Applications split into small services.

User Service
Order Service
Payment Service
Notification Service

=================================================================
                NEW PROBLEM
=================================================================

How services communicate?

=================================================================
                SOLUTION
=================================================================

APIs.

=================================================================
                3. WHAT IS REST?
=================================================================

REST =
-------
Representational State Transfer

Introduced by:
---------------
Roy Fielding (2000)

=================================================================
                MOST IMPORTANT
=================================================================

REST is NOT protocol.

REST is:
----------
Architectural Style.

=================================================================
                WHAT DOES THAT MEAN?
=================================================================

REST gives architectural guidelines/rules
for designing scalable distributed systems.

=================================================================
                WHY REST CREATED?
=================================================================

Before REST:
-------------

SOAP systems were:
-------------------

1. Heavy
2. Complex
3. XML based
4. Difficult to scale

Web needed:
------------

1. Simpler communication
2. Lightweight payloads
3. Stateless architecture
4. Internet scalability

REST solved this.

=================================================================
                CORE IDEA OF REST
=================================================================

Everything is RESOURCE.

Examples:
-----------

/users
/orders
/products

=================================================================
                VERY IMPORTANT
=================================================================

REST focuses on:
-----------------

Resources

NOT actions.

=================================================================
                WRONG REST DESIGN
=================================================================

/getUsers
/createOrder
/deleteProduct

=================================================================
                CORRECT REST DESIGN
=================================================================

GET /users
POST /orders
DELETE /products/10

=================================================================
                WHY?
=================================================================

HTTP method should define action.

URL should define resource.

=================================================================
                REST ARCHITECTURE CONSTRAINTS
=================================================================

MOST IMPORTANT INTERVIEW TOPIC.

=================================================================
                1. CLIENT SERVER
=================================================================

Frontend and backend separated.

Benefits:
----------

1. Independent development
2. Scalability
3. Technology flexibility

Frontend:
-----------
React/Angular/Mobile

Backend:
----------
Java/Node/Python

=================================================================
                2. STATELESS
=================================================================

Most important REST constraint.

=================================================================
                MEANING
=================================================================

Server should NOT remember client state.

Each request must contain:
---------------------------

Complete information.

=================================================================
                EXAMPLE
=================================================================

JWT token sent in every request.

=================================================================
                WHY STATELESS IMPORTANT?
=================================================================

Allows horizontal scaling.

=================================================================
                EXAMPLE
=================================================================

Request 1
    ↓
Server A

Request 2
    ↓
Server B

Still works because no server memory required.

=================================================================
                BENEFITS
=================================================================

1. Scalability
2. Load balancing
3. Fault tolerance
4. Easy deployment

=================================================================
                3. CACHEABLE
=================================================================

Responses should support caching.

=================================================================
                WHY?
=================================================================

Reduces:
---------

1. Server load
2. Database calls
3. Latency

=================================================================
                EXAMPLE
=================================================================

GET /products

Can be cached for 5 minutes.

=================================================================
                4. UNIFORM INTERFACE
=================================================================

Standardized communication rules.

=================================================================
                EXAMPLE
=================================================================

GET /users
GET /users/1
POST /users

Easy to understand universally.

=================================================================
                5. LAYERED SYSTEM
=================================================================

Client may not know actual backend.

=================================================================
                EXAMPLE
=================================================================

Client
  ↓
Load Balancer
  ↓
API Gateway
  ↓
Microservice
  ↓
Database

=================================================================
                BENEFITS
=================================================================

1. Security
2. Scalability
3. Maintainability

=================================================================
                6. CODE ON DEMAND
=================================================================

Optional REST constraint.

Server can send executable code.

Rarely used today.

=================================================================
                HOW REST WORKS INTERNALLY
=================================================================

Client
  ↓
HTTP Request
  ↓
Server Controller
  ↓
Business Logic
  ↓
Database
  ↓
JSON Response

=================================================================
                WHY JSON WON?
=================================================================

Compared to XML:
----------------

1. Lightweight
2. Human readable
3. Faster parsing
4. Smaller payload

=================================================================
                SERIALIZATION
=================================================================

Java Object
    ↓
JSON Conversion

Called:
---------

Serialization

=================================================================
                DESERIALIZATION
=================================================================

JSON
  ↓
Java Object

Called:
---------

Deserialization

=================================================================
                REST HTTP METHODS
=================================================================

GET
POST
PUT
PATCH
DELETE

=================================================================
                GET
=================================================================

Fetch data.

Must NOT modify server state.

=================================================================
                POST
=================================================================

Create new resource.

=================================================================
                PUT
=================================================================

Complete replacement.

=================================================================
                PATCH
=================================================================

Partial update.

=================================================================
                DELETE
=================================================================

Delete resource.

=================================================================
                IDEMPOTENCY
=================================================================

Very important interview concept.

=================================================================
                MEANING
=================================================================

Multiple identical requests
produce same final result.

=================================================================
                EXAMPLE
=================================================================

PUT /users/1

Repeated multiple times:
-------------------------

Final state remains same.

=================================================================
                REST STATUS CODES
=================================================================

200 → Success
201 → Created
204 → No Content
400 → Bad Request
401 → Unauthorized
403 → Forbidden
404 → Not Found
409 → Conflict
500 → Internal Server Error

=================================================================
                WHY REST BECAME POPULAR?
=================================================================

1. Simplicity
2. JSON support
3. Internet scalability
4. Stateless design
5. Easy frontend integration
6. Mobile-friendly
7. Browser-friendly

=================================================================
                REST PROBLEMS
=================================================================

1. Over-fetching
2. Under-fetching
3. Multiple round trips
4. Weak contracts
5. Large payloads

=================================================================
                EXAMPLE
=================================================================

GET /users/1

Returns:
---------
name
email
phone
address
salary
department

Frontend only needed:
----------------------

name

Extra data wasted.

=================================================================
                THIS CREATED
=================================================================

GraphQL

=================================================================
                4. SOAP ARCHITECTURE
=================================================================

SOAP =
-------
Simple Object Access Protocol

=================================================================
                IMPORTANT
=================================================================

SOAP is:
----------
Protocol

REST is:
----------
Architecture Style

=================================================================
                SOAP FEATURES
=================================================================

1. Strict contract
2. XML only
3. Enterprise security
4. Reliable transactions
5. WS-* standards

=================================================================
                SOAP REQUEST
=================================================================

XML envelope structure.

=================================================================
                WHY SOAP CREATED?
=================================================================

Enterprise systems needed:
---------------------------

1. Reliability
2. Security
3. Standard contracts
4. Formal communication

=================================================================
                WSDL
=================================================================

Web Service Description Language

Defines:
---------

1. Available APIs
2. Request schema
3. Response schema

=================================================================
                SOAP BENEFITS
=================================================================

1. Strong contract
2. Enterprise-grade security
3. ACID-like reliability
4. Built-in standards

=================================================================
                SOAP PROBLEMS
=================================================================

1. Huge XML payload
2. Slow parsing
3. Complex development
4. Difficult frontend integration

=================================================================
                USED IN
=================================================================

1. Banking
2. Insurance
3. Telecom
4. Government systems

=================================================================
                5. GRAPHQL
=================================================================

Created by:
-------------
Meta (Facebook)

=================================================================
                WHY GRAPHQL CREATED?
=================================================================

Facebook mobile apps suffered from:

1. Over-fetching
2. Under-fetching
3. Slow mobile networks

=================================================================
                CORE IDEA
=================================================================

Client decides response shape.

=================================================================
                REST
=================================================================

Server decides response.

=================================================================
                GRAPHQL
=================================================================

Client requests exact fields.

=================================================================
                EXAMPLE
=================================================================

query {

  user(id:1) {

      name
      email

  }

}

=================================================================
                BENEFITS
=================================================================

1. Exact data fetching
2. Reduced payload
3. Single endpoint
4. Better frontend control

=================================================================
                PROBLEMS
=================================================================

1. Complex caching
2. Expensive queries
3. Difficult monitoring
4. N+1 query problem

=================================================================
                USED IN
=================================================================

Frontend-heavy applications.

=================================================================
                6. gRPC
=================================================================

Created by:
-------------
Google

=================================================================
                FULL FORM
=================================================================

Google Remote Procedure Call

=================================================================
                WHY gRPC CREATED?
=================================================================

REST became slow for:
----------------------

1. Internal microservices
2. High throughput systems
3. Real-time communication

=================================================================
                CORE IDEA
=================================================================

Very fast binary communication.

=================================================================
                PROTOCOL
=================================================================

Uses:
------
HTTP/2

=================================================================
                DATA FORMAT
=================================================================

Protocol Buffers (protobuf)

Binary format.

=================================================================
                WHY BINARY FAST?
=================================================================

JSON:
------
Text-based parsing

Protobuf:
-----------
Compact binary serialization

Less CPU + less network bandwidth.

=================================================================
                HTTP/2 BENEFITS
=================================================================

1. Multiplexing
2. Header compression
3. Parallel streams
4. Better performance

=================================================================
                MULTIPLEXING
=================================================================

Multiple requests over single connection.

=================================================================
                HUGE ADVANTAGE
=================================================================

Low latency communication.

=================================================================
                STREAMING SUPPORT
=================================================================

1. Client streaming
2. Server streaming
3. Bidirectional streaming

=================================================================
                USED IN
=================================================================

1. Internal microservices
2. Real-time systems
3. High-performance systems

=================================================================
                WHY gRPC NOT USED PUBLICLY MUCH?
=================================================================

1. Browser limitations
2. Difficult debugging
3. Binary not human-readable

=================================================================
                7. WEBSOCKET
=================================================================

HTTP request-response model has limitation.

=================================================================
                PROBLEM
=================================================================

Real-time apps need continuous communication.

Examples:
----------

1. Chat apps
2. Live games
3. Trading systems

=================================================================
                WEBSOCKET SOLUTION
=================================================================

Persistent full-duplex connection.

=================================================================
                FULL DUPLEX
=================================================================

Client and server both send messages anytime.

=================================================================
                NORMAL HTTP
=================================================================

Request
  ↓
Response
  ↓
Connection closed

=================================================================
                WEBSOCKET
=================================================================

Connection remains open.

=================================================================
                USED IN
=================================================================

1. WhatsApp
2. Multiplayer games
3. Live sports
4. Trading systems

=================================================================
                API STYLE COMPARISON
=================================================================

REST:
------
Simple public APIs

SOAP:
------
Enterprise secure systems

GraphQL:
---------
Frontend optimized APIs

gRPC:
------
Fast internal microservices

WebSocket:
-----------
Real-time systems

=================================================================
                WHAT BIG TECH USES?
=================================================================

Google:
---------
gRPC internally

Netflix:
----------
REST + gRPC

Meta:
-------
GraphQL heavily

Banks:
-------
SOAP still common

=================================================================
                REAL INTERVIEW QUESTION
=================================================================

Q:
---
Why REST became industry standard?

Answer:
--------
1. Lightweight
2. Stateless
3. Internet scalable
4. JSON support
5. Simpler than SOAP
6. Easy frontend/mobile integration

=================================================================
                ANOTHER INTERVIEW QUESTION
=================================================================

Q:
---
When would you choose gRPC over REST?

Answer:
--------
1. Internal microservices
2. Low latency requirements
3. High throughput systems
4. Streaming requirements
5. Service-to-service communication

=================================================================
                ANOTHER QUESTION
=================================================================

Q:
---
When should GraphQL be avoided?

Answer:
--------
1. Simple APIs
2. Heavy caching requirements
3. Small backend teams
4. Simple CRUD systems

=================================================================
                MOST IMPORTANT TAKEAWAY
=================================================================

REST:
------
Internet/public APIs

gRPC:
------
Internal high-speed communication

GraphQL:
---------
Flexible frontend data fetching

SOAP:
------
Enterprise-grade secure systems

WebSocket:
-----------
Real-time bidirectional communication

=================================================================

*/

public class ApiArchitectureDeepNotes {
}