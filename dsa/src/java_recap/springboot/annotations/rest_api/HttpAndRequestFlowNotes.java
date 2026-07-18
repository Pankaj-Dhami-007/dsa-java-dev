package java_recap.springboot.annotations.rest_api;

/*

=================================================================
            COMPLETE HTTP + HTTPS + REQUEST FLOW
=================================================================

Topics:
--------
1. What is HTTP?
2. Why HTTP invented?
3. Request-Response Model
4. Complete Browser to Backend Flow
5. HTTPS Internal Working
6. SSL/TLS Handshake
7. DNS Flow
8. Load Balancer
9. API Gateway
10. Reverse Proxy
11. Headers
12. Cookies vs Sessions vs JWT
13. Stateless Architecture
14. HTTP/1.1 vs HTTP/2 vs HTTP/3
15. Keep Alive
16. REST Request Lifecycle
17. Spring Boot Request Flow

=================================================================
                1. WHY HTTP CREATED?
=================================================================

Early internet needed standard communication.

Problem:
---------

How browser communicates with server?

Different systems needed:
--------------------------

1. Common protocol
2. Standard rules
3. Platform-independent communication

=================================================================
                SOLUTION
=================================================================

HTTP protocol invented.

HTTP =
-------
HyperText Transfer Protocol

=================================================================
                IMPORTANT
=================================================================

HTTP is:
----------
Application Layer Protocol

=================================================================
                TCP/IP LAYERS
=================================================================

Application Layer
    ↓
Transport Layer (TCP)
    ↓
Internet Layer (IP)
    ↓
Network Layer

=================================================================
                VERY IMPORTANT
=================================================================

HTTP works on top of:
----------------------

TCP

=================================================================
                WHY TCP?
=================================================================

Because TCP provides:
----------------------

1. Reliable communication
2. Ordered delivery
3. Error checking
4. Retransmission

=================================================================
                2. HTTP REQUEST RESPONSE MODEL
=================================================================

Client sends request.
Server returns response.

=================================================================
                FLOW
=================================================================

Browser
   ↓
HTTP Request
   ↓
Server
   ↓
HTTP Response
   ↓
Browser renders UI

=================================================================
                HTTP IS STATELESS
=================================================================

Most important concept.

=================================================================
                MEANING
=================================================================

Server does NOT remember previous requests.

Each request independent.

=================================================================
                EXAMPLE
=================================================================

GET /profile

Request must contain:
----------------------

Authentication token every time.

=================================================================
                BENEFITS
=================================================================

1. Scalability
2. Load balancing
3. Simplicity
4. Fault tolerance

=================================================================
                3. COMPLETE REQUEST FLOW
=================================================================

MOST IMPORTANT BACKEND INTERVIEW TOPIC

=================================================================
                STEP 1
=================================================================

User enters:

https://google.com

=================================================================
                STEP 2 — DNS RESOLUTION
=================================================================

Browser needs IP address.

Because internet works using IP,
NOT domain names.

=================================================================
                DNS ROLE
=================================================================

Converts:
----------

google.com
      ↓
142.250.xx.xx

=================================================================
                FLOW
=================================================================

Browser Cache
   ↓
OS Cache
   ↓
Router Cache
   ↓
ISP DNS
   ↓
Root DNS
   ↓
TLD DNS (.com)
   ↓
Authoritative DNS
   ↓
IP Returned

=================================================================
                STEP 3 — TCP CONNECTION
=================================================================

Browser creates TCP connection.

=================================================================
                TCP 3-WAY HANDSHAKE
=================================================================

Client → SYN
Server → SYN ACK
Client → ACK

Connection established.

=================================================================
                WHY IMPORTANT?
=================================================================

Reliable communication established.

=================================================================
                STEP 4 — HTTPS TLS HANDSHAKE
=================================================================

If HTTPS enabled:
------------------

TLS handshake happens.

=================================================================
                GOAL
=================================================================

1. Encryption
2. Authentication
3. Secure communication

=================================================================
                STEP 5 — HTTP REQUEST
=================================================================

Browser sends request.

=================================================================
                EXAMPLE
=================================================================

GET /users HTTP/1.1

Host: api.com
Authorization: Bearer xyz
Content-Type: application/json

=================================================================
                REQUEST COMPONENTS
=================================================================

1. HTTP Method
2. URL
3. Headers
4. Body
5. Query Params

=================================================================
                STEP 6 — LOAD BALANCER
=================================================================

Request usually first reaches:
-------------------------------

Load Balancer

=================================================================
                WHY?
=================================================================

One server cannot handle millions of users.

=================================================================
                LOAD BALANCER ROLE
=================================================================

Distributes traffic across servers.

=================================================================
                EXAMPLE
=================================================================

Request 1 → Server A
Request 2 → Server B
Request 3 → Server C

=================================================================
                BENEFITS
=================================================================

1. Scalability
2. High availability
3. Fault tolerance

=================================================================
                TYPES
=================================================================

1. Round Robin
2. Least Connections
3. IP Hash
4. Weighted

=================================================================
                STEP 7 — API GATEWAY
=================================================================

In microservices architecture:

Client
  ↓
API Gateway
  ↓
Microservices

=================================================================
                API GATEWAY ROLE
=================================================================

1. Authentication
2. Routing
3. Rate limiting
4. Logging
5. Monitoring
6. Aggregation

=================================================================
                EXAMPLE
=================================================================

/users/*
    ↓
User Service

/orders/*
    ↓
Order Service

=================================================================
                WHY IMPORTANT?
=================================================================

Client interacts with ONE entry point only.

=================================================================
                STEP 8 — SPRING BOOT APPLICATION
=================================================================

Request reaches embedded Tomcat.

=================================================================
                EMBEDDED SERVER
=================================================================

Spring Boot internally runs:
-----------------------------

Tomcat (default)

=================================================================
                REQUEST FLOW INSIDE SPRING
=================================================================

Client Request
    ↓
Tomcat
    ↓
DispatcherServlet
    ↓
Filter Chain
    ↓
Interceptor
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
Database

=================================================================
                DISPATCHERSERVLET
=================================================================

Front Controller of Spring MVC.

Every request passes through it.

=================================================================
                FILTERS
=================================================================

Used before request reaches controller.

=================================================================
                EXAMPLES
=================================================================

1. JWT Authentication
2. Logging
3. CORS
4. Compression

=================================================================
                INTERCEPTORS
=================================================================

Used around controller execution.

=================================================================
                DIFFERENCE
==============================================================

Filter:
--------
Servlet-level

Interceptor:
-------------
Spring MVC-level

=================================================================
                CONTROLLER
=================================================================

Receives HTTP request.

=================================================================
                SERVICE
=================================================================

Contains business logic.

=================================================================
                REPOSITORY
=================================================================

Database interaction layer.

=================================================================
                STEP 9 — DATABASE
=================================================================

SQL query executes.

=================================================================
                STEP 10 — RESPONSE
=================================================================

Database Result
    ↓
Repository
    ↓
Service
    ↓
Controller
    ↓
Jackson Serialization
    ↓
JSON Response
    ↓
Browser

=================================================================
                4. HTTPS INTERNALS
=================================================================

HTTPS =
--------
HTTP + SSL/TLS

=================================================================
                PROBLEM WITH HTTP
=================================================================

HTTP data plain text.

Hackers can read everything.

=================================================================
                HTTPS SOLUTION
=================================================================

Encryption.

=================================================================
                TLS HANDSHAKE FLOW
=================================================================

Client Hello
    ↓
Server sends certificate
    ↓
Client verifies certificate
    ↓
Session key generated
    ↓
Encrypted communication starts

=================================================================
                SSL CERTIFICATE
=================================================================

Contains:
-----------

1. Public Key
2. Domain Info
3. CA Signature

=================================================================
                PUBLIC KEY CRYPTOGRAPHY
=================================================================

Public Key:
------------
Encrypt

Private Key:
-------------
Decrypt

=================================================================
                WHY HTTPS IMPORTANT?
=================================================================

1. Security
2. Data privacy
3. Prevent MITM attack
4. SEO benefits
5. Browser trust

=================================================================
                5. COOKIES VS SESSION VS JWT
=================================================================

Most important authentication topic.

=================================================================
                COOKIES
=================================================================

Small data stored in browser.

=================================================================
                SESSION
=================================================================

Server stores user state.

Browser stores session ID.

=================================================================
                PROBLEM
=================================================================

Sessions difficult in distributed systems.

=================================================================
                WHY?
=================================================================

Request may hit different servers.

=================================================================
                JWT SOLUTION
=================================================================

Stateless authentication.

Token itself contains user data.

=================================================================
                FLOW
=================================================================

Login
  ↓
JWT generated
  ↓
Client stores token
  ↓
Token sent in every request

=================================================================
                BENEFITS
=================================================================

1. Stateless
2. Scalable
3. Distributed-system friendly

=================================================================
                6. HTTP HEADERS
=================================================================

Metadata of request/response.

=================================================================
                COMMON HEADERS
=================================================================

Authorization
Content-Type
Accept
Cookie
Cache-Control

=================================================================
                CONTENT-TYPE
=================================================================

Defines request body format.

=================================================================
                EXAMPLE
=================================================================

application/json

=================================================================
                AUTHORIZATION HEADER
=================================================================

Authorization: Bearer token

=================================================================
                7. HTTP/1.1 VS HTTP/2 VS HTTP/3
=================================================================

=================================================================
                HTTP/1.1 PROBLEMS
=================================================================

1. Head-of-line blocking
2. Multiple TCP connections
3. Slow performance

=================================================================
                HTTP/2 SOLUTIONS
=================================================================

1. Multiplexing
2. Header compression
3. Single TCP connection
4. Parallel streams

=================================================================
                HTTP/3
=================================================================

Uses QUIC protocol over UDP.

=================================================================
                BENEFITS
=================================================================

1. Faster connection setup
2. Better mobile performance
3. Reduced latency

=================================================================
                KEEP ALIVE
=================================================================

Reuses TCP connection.

Avoids reconnect overhead.

=================================================================
                WHY IMPORTANT?
=================================================================

TCP handshake expensive.

=================================================================
                REAL INTERVIEW QUESTION
=================================================================

Q:
---
Explain complete request flow
from browser to Spring Boot application.

=================================================================
                ANSWER
=================================================================

1. User enters URL
2. DNS resolves IP
3. TCP connection established
4. TLS handshake happens
5. HTTP request sent
6. Load balancer distributes traffic
7. API gateway routes request
8. Tomcat receives request
9. DispatcherServlet handles request
10. Controller executes
11. Service layer processes logic
12. Repository interacts with DB
13. Response serialized into JSON
14. HTTP response returned

=================================================================
                ANOTHER QUESTION
=================================================================

Q:
---
Why REST APIs prefer stateless architecture?

=================================================================
                ANSWER
=================================================================

1. Horizontal scaling
2. Fault tolerance
3. Easier load balancing
4. Better distributed architecture

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Difference between HTTP and HTTPS?

=================================================================
                ANSWER
=================================================================

HTTP:
-------
Plain-text communication

HTTPS:
--------
Encrypted communication using TLS/SSL

=================================================================
                MOST IMPORTANT TAKEAWAY
=================================================================

Modern Backend Request Flow:
----------------------------

Browser
  ↓
DNS
  ↓
TCP/TLS
  ↓
Load Balancer
  ↓
API Gateway
  ↓
Spring Boot
  ↓
Database
  ↓
JSON Response

=================================================================

*/

public class HttpAndRequestFlowNotes {
}