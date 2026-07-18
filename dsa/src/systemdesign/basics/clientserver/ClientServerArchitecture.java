package systemdesign.basics.clientserver;

public class ClientServerArchitecture {

}

/*

========================================================================
                CLIENT SERVER ARCHITECTURE
========================================================================

Client-server architecture is foundation
of modern internet applications.

Almost every application uses this architecture.

Examples:
- Instagram
- WhatsApp
- Netflix
- YouTube
- Amazon

========================================================================
WHAT IS CLIENT SERVER ARCHITECTURE?
========================================================================

Client-server architecture is communication model
where:

Client
--------
requests service/data

Server
--------
processes request and returns response

========================================================================
SIMPLE FLOW
========================================================================

Client  -------- Request -------->  Server

Client  <------- Response -------- Server

========================================================================
MAIN IDEA
========================================================================

Client asks for resource.

Server provides resource.

========================================================================
WHAT IS CLIENT?
========================================================================

Client is entity that requests services/resources.

========================================================================
EXAMPLES OF CLIENT
========================================================================

1. Browser
2. Mobile application
3. Postman
4. Frontend application

========================================================================
WHAT IS SERVER?
========================================================================

Server is entity that:
- receives requests
- processes requests
- returns responses

========================================================================
EXAMPLES OF SERVER
========================================================================

1. Spring Boot application
2. Node.js application
3. Django server
4. Netflix backend server

========================================================================
REAL WORLD EXAMPLE
========================================================================

Suppose user opens:

        youtube.com

========================================================================
ACTUAL FLOW
========================================================================

Browser
    ↓
Request sent over internet
    ↓
YouTube backend server
    ↓
Database query
    ↓
Response generated
    ↓
Browser displays UI

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Server NEVER starts communication first.

========================================================================

Client always initiates communication.

========================================================================

This is called:
        Request-Response Model

========================================================================
MAIN COMPONENTS
========================================================================

1. Client
2. Server
3. Request
4. Response
5. Network

========================================================================
WHAT IS REQUEST?
========================================================================

Request is message sent from client to server.

========================================================================
REQUEST CONTAINS
========================================================================

1. Endpoint
2. HTTP method
3. Headers
4. Body

========================================================================
EXAMPLE REQUEST
========================================================================

        GET /users

========================================================================
WHAT IS RESPONSE?
========================================================================

Response is data returned by server.

========================================================================
RESPONSE CONTAINS
========================================================================

1. Status code
2. Data
3. Headers

========================================================================
EXAMPLE RESPONSE
========================================================================

{
    "name": "Pankaj"
}

========================================================================
COMMON HTTP METHODS
========================================================================

GET
-----
Fetch data

========================================================================

POST
------
Create data

========================================================================

PUT
-----
Update complete resource

========================================================================

PATCH
-------
Partial update

========================================================================

DELETE
--------
Delete resource

========================================================================
COMMON STATUS CODES
========================================================================

200
-----
Success

========================================================================

201
-----
Resource created

========================================================================

404
-----
Resource not found

========================================================================

500
-----
Internal server error

========================================================================
WHY CLIENT SERVER ARCHITECTURE IMPORTANT?
========================================================================

Because it separates:

1. UI
2. Business logic
3. Data storage

========================================================================

This makes systems:
- scalable
- maintainable
- secure

========================================================================
3-TIER ARCHITECTURE
========================================================================

Modern applications mostly use:

Client
   ↓
Application Server
   ↓
Database

========================================================================
EXAMPLE
========================================================================

Frontend
---------
React/Angular

========================================================================

Backend
--------
Spring Boot

========================================================================

Database
---------
MySQL/PostgreSQL

========================================================================
VERY IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

As users increase,
single server becomes overloaded.

========================================================================

This creates problems:
- slow response
- server crash
- high latency
- database bottleneck

========================================================================

These problems lead to advanced concepts:
- Load Balancer
- Cache
- CDN
- Microservices
- Distributed Systems

========================================================================
REAL WORLD INSTAGRAM FLOW
========================================================================

Mobile App
    ↓
API Gateway
    ↓
Authentication Service
    ↓
Post Service
    ↓
Database

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is client-server architecture?

→ Architecture where client requests services
and server processes and returns responses.

========================================================================

2. Who initiates communication?

→ Client

========================================================================

3. What does server do?

→ Processes request and returns response.

========================================================================

4. Why client-server architecture important?

→ Separation of concerns and scalability.

========================================================================

5. What is 3-tier architecture?

Client
↓
Application Server
↓
Database

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Client-server architecture is foundation
of modern distributed applications
where clients request services
and servers process and return responses.

========================================================================

*/