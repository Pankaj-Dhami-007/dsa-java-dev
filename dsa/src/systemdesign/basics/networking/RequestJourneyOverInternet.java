package systemdesign.basics.networking;

public class RequestJourneyOverInternet {

}

/*

========================================================================
                REQUEST JOURNEY OVER INTERNET
========================================================================

Whenever user opens website:

        google.com
        youtube.com
        instagram.com

a complete journey happens behind the scenes.

========================================================================

This journey is one of MOST IMPORTANT
backend and system design concepts.

========================================================================
MAIN QUESTION
========================================================================

How request travels from browser
to actual backend server?

========================================================================
COMPLETE FLOW
========================================================================

Browser
    ↓
DNS Lookup
    ↓
IP Address Found
    ↓
Internet Routing
    ↓
Server
    ↓
Database
    ↓
Response Returned
    ↓
Browser Displays Result

========================================================================
STEP 1 : USER ENTERS DOMAIN
========================================================================

Suppose user enters:

        youtube.com

inside browser.

========================================================================

Browser still DOES NOT know:
where YouTube server exists.

========================================================================

Because internet works using:
        IP addresses

NOT domain names.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Humans remember:
        domain names

Computers understand:
        IP addresses

========================================================================
EXAMPLE
========================================================================

Human friendly:
        google.com

========================================================================

Computer friendly:
        142.250.183.14

========================================================================
STEP 2 : DNS LOOKUP
========================================================================

DNS stands for:
        Domain Name System

========================================================================

DNS converts:
        domain name → IP address

========================================================================
SIMPLE DEFINITION
========================================================================

DNS is internet phonebook.

========================================================================
EXAMPLE
========================================================================

youtube.com
        ↓
142.xx.xx.xx

========================================================================
WHY DNS REQUIRED?
========================================================================

Because humans cannot remember:
- numeric IP addresses
- millions of servers

========================================================================

So domain names are used.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Without DNS,
internet would require users
to remember IP addresses manually.

========================================================================
STEP 3 : CONNECTION ESTABLISHED
========================================================================

After IP found,
browser tries connecting to server.

========================================================================

Connection established using:
        TCP

========================================================================
WHAT IS TCP?
========================================================================

TCP stands for:
        Transmission Control Protocol

========================================================================

TCP provides:
- reliable communication
- ordered data transfer
- error checking

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Most web applications use:
        TCP

========================================================================

Because data loss is unacceptable
for websites and APIs.

========================================================================
STEP 4 : PORT NUMBER
========================================================================

IP identifies:
        machine/server

========================================================================

Port identifies:
        specific application inside server

========================================================================
EXAMPLE
========================================================================

Single machine may run:
- Spring Boot app
- MySQL
- Redis
- NGINX

========================================================================

Port helps OS identify
which application should receive request.

========================================================================
COMMON PORTS
========================================================================

80
----
HTTP

========================================================================

443
-----
HTTPS

========================================================================

3306
------
MySQL

========================================================================

6379
------
Redis

========================================================================

8080
------
Spring Boot default

========================================================================
IMPORTANT ANALOGY
========================================================================

IP Address
-----------
Apartment Building

========================================================================

Port
-----
Flat number

========================================================================
STEP 5 : HTTP REQUEST SENT
========================================================================

Browser sends:
        HTTP request

========================================================================
WHAT IS HTTP?
========================================================================

HTTP stands for:
        HyperText Transfer Protocol

========================================================================

HTTP is communication protocol
between client and server.

========================================================================
EXAMPLE REQUEST
========================================================================

GET /videos

========================================================================

POST /users

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

HTTP is stateless.

========================================================================

Meaning:
server does NOT remember previous requests automatically.

========================================================================
WHAT IS STATELESS?
========================================================================

Each request treated independently.

========================================================================
EXAMPLE
========================================================================

Server does not automatically remember:
- previous login
- previous request
- previous action

========================================================================

This is why:
- Sessions
- JWT
- Cookies

exist.

========================================================================
STEP 6 : SERVER PROCESSES REQUEST
========================================================================

Backend server receives request.

========================================================================

Then:
- validates request
- executes business logic
- queries database
- prepares response

========================================================================
EXAMPLE
========================================================================

Request:
        GET /users/1

========================================================================

Server may:
- check authentication
- query database
- fetch user
- create JSON response

========================================================================
STEP 7 : DATABASE COMMUNICATION
========================================================================

Most applications store data in database.

========================================================================
DATABASE STORES
========================================================================

- users
- posts
- messages
- products
- transactions

========================================================================
EXAMPLES
========================================================================

MySQL
PostgreSQL
MongoDB

========================================================================
STEP 8 : RESPONSE RETURNED
========================================================================

Server sends response back to browser.

========================================================================

Response may contain:
- HTML
- JSON
- images
- videos

========================================================================
EXAMPLE RESPONSE
========================================================================

{
    "id": 1,
    "name": "Pankaj"
}

========================================================================
STEP 9 : BROWSER DISPLAYS RESULT
========================================================================

Browser renders response into:
- webpage
- UI
- application screen

========================================================================
HTTPS
========================================================================

Modern applications use:
        HTTPS

NOT HTTP.

========================================================================
WHAT IS HTTPS?
========================================================================

HTTPS =
        HTTP + SSL/TLS Encryption

========================================================================

HTTPS encrypts communication.

========================================================================
WHY HTTPS IMPORTANT?
========================================================================

Prevents:
- password theft
- data interception
- man-in-middle attacks

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Without HTTPS,
data travels in plain text.

========================================================================

Anyone could read:
- passwords
- banking details
- tokens

========================================================================
LATENCY
========================================================================

Latency means:
        time taken for request-response cycle

========================================================================
HIGH LATENCY CAUSES
========================================================================

1. Slow network
2. Server overload
3. Long geographical distance
4. Slow database
5. Large response size

========================================================================
WHY SYSTEM DESIGN CARES ABOUT THIS?
========================================================================

Because millions of users generate:
millions of requests.

========================================================================

System design focuses on:
- reducing latency
- improving scalability
- handling high traffic

========================================================================
IMPORTANT REAL WORLD FLOW
========================================================================

Browser
    ↓
DNS
    ↓
Load Balancer
    ↓
Application Server
    ↓
Cache
    ↓
Database
    ↓
Response

========================================================================

Later we will study:
- Load Balancer
- Cache
- CDN
- API Gateway
- Microservices

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is DNS?

→ Converts domain name into IP address.

========================================================================

2. Why DNS required?

→ Humans remember names easier than IP addresses.

========================================================================

3. What is port?

→ Identifies specific application inside machine.

========================================================================

4. Difference between HTTP and HTTPS?

HTTP
------
not encrypted

HTTPS
-------
encrypted using SSL/TLS

========================================================================

5. What is stateless protocol?

→ Server does not automatically remember previous requests.

========================================================================

6. Why HTTPS important?

→ Secure communication and encryption.

========================================================================

7. What causes latency?

→ Network delay, server overload,
database slowness, geographical distance.

========================================================================

8. What does TCP provide?

→ Reliable and ordered communication.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

A web request travels from browser
through DNS resolution and internet routing
to backend server where request is processed
and response is returned to client.

========================================================================

*/