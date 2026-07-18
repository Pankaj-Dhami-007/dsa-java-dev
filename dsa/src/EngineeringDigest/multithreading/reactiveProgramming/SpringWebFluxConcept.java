package EngineeringDigest.multithreading.reactiveProgramming;

public class SpringWebFluxConcept {
}

/*

========================================================================
                    SPRING WEBFLUX
========================================================================

Spring WebFlux is Spring's
reactive non-blocking web framework.

========================================================================
WHY SPRING WEBFLUX EXISTS?
========================================================================

Traditional Spring MVC uses:
========================================================================

thread-per-request model

========================================================================

Meaning:
========================================================================

1 request
        ↓
1 thread

========================================================================

Large concurrent users
        ↓
Large blocked threads

========================================================================

Scalability limitations occur.

========================================================================

Need:
- non-blocking APIs
- fewer threads
- reactive systems
- better scalability

========================================================================

This is why Spring WebFlux introduced.

========================================================================
WHAT IS SPRING WEBFLUX?
========================================================================

Spring WebFlux is reactive
non-blocking asynchronous web framework
built on Reactive Programming principles.

========================================================================
SIMPLE DEFINITION
========================================================================

WebFlux handles requests asynchronously
without blocking threads continuously.

========================================================================
MAIN IDEA
========================================================================

Instead of:
blocking request-response model

========================================================================

WebFlux uses:
- event loop
- reactive streams
- non-blocking I/O

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Spring WebFlux designed for:
========================================================================

high-concurrency scalable systems

========================================================================

especially:
I/O-heavy applications.

========================================================================
IMPORTANT FOUNDATION
========================================================================

WebFlux built on:
========================================================================

Reactive Streams API

========================================================================

using:
========================================================================

Project Reactor

========================================================================
IMPORTANT REACTIVE TYPES
========================================================================

1. Mono
2. Flux

========================================================================
1. MONO
========================================================================

Represents:
0 or 1 async result.

========================================================================

Example:
single API response.

========================================================================
2. FLUX
========================================================================

Represents:
multiple async data stream.

========================================================================

Example:
chat messages
live events
streaming data

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Mono and Flux are:
reactive publishers.

========================================================================

They emit data asynchronously.

========================================================================
WEBFLUX SERVER OPTIONS
========================================================================

1. Netty (default)
2. Tomcat
3. Jetty
4. Undertow

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

WebFlux commonly uses:
========================================================================

Netty Event Loop

========================================================================

for non-blocking processing.

========================================================================
TRADITIONAL SPRING MVC FLOW
========================================================================

Request
        ↓
Dedicated thread assigned
        ↓
Thread waits for DB/API response
        ↓
Response returned

========================================================================

Thread blocked entire time.

========================================================================
WEBFLUX FLOW
========================================================================

Request arrives
        ↓
Async operation starts
        ↓
Thread released
        ↓
Response event arrives later
        ↓
Reactive pipeline processes response

========================================================================

Thread not wasted.

========================================================================
WHY WEBFLUX SCALABLE?
========================================================================

Few threads can handle:
thousands of concurrent requests.

========================================================================

Because:
threads not blocked continuously.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

WebFlux best for:
========================================================================

I/O-bound applications

========================================================================

Examples:
- API gateway
- streaming systems
- chat applications
- reactive microservices

========================================================================
NOT IDEAL FOR
========================================================================

Heavy CPU-intensive computations.

========================================================================

Because:
event loop threads may block.

========================================================================
SPRING MVC vs WEBFLUX
========================================================================

SPRING MVC
------------
Blocking model

========================================================================

WEBFLUX
---------
Non-blocking reactive model

========================================================================

SPRING MVC
------------
Thread-per-request

========================================================================

WEBFLUX
---------
Event-loop architecture

========================================================================

SPRING MVC
------------
Simpler debugging

========================================================================

WEBFLUX
---------
Higher scalability

========================================================================

SPRING MVC
------------
Better for traditional applications

========================================================================

WEBFLUX
---------
Better for high concurrency

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

WebFlux does NOT automatically make application faster.

========================================================================

Main advantage:
better scalability and resource utilization.

========================================================================
IMPORTANT REACTIVE OPERATORS
========================================================================

1. map()
2. flatMap()
3. filter()
4. zip()

========================================================================

Used for:
reactive data transformations.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

WebFlux programming style:
different mindset from traditional MVC.

========================================================================

Everything becomes:
asynchronous data pipeline.

========================================================================
BACKPRESSURE SUPPORT
========================================================================

WebFlux supports:
========================================================================

Backpressure

========================================================================

Meaning:
consumer controls data flow speed.

========================================================================

Prevents overload.

========================================================================
REAL LIFE ANALOGY
========================================================================

Spring MVC:
one waiter dedicated per customer.

========================================================================

WebFlux:
few smart waiters manage
many customers asynchronously.

========================================================================

Much more scalable.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Streaming APIs
2. Chat systems
3. Notification systems
4. API gateways
5. High-concurrency microservices
6. Reactive event systems

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Massive scalability
2. Better resource utilization
3. Fewer threads required
4. Non-blocking architecture
5. Reactive stream support

========================================================================
IMPORTANT LIMITATION
========================================================================

1. More complex debugging
2. Harder learning curve
3. Reactive mindset required
4. Not ideal for CPU-heavy tasks

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is Spring WebFlux?

→ Reactive non-blocking Spring web framework.

========================================================================

2. Which reactive library used by WebFlux?

→ Project Reactor

========================================================================

3. Which reactive types used in WebFlux?

→ Mono and Flux

========================================================================

4. Which server commonly used by WebFlux?

→ Netty

========================================================================

5. Difference between Spring MVC and WebFlux?

Spring MVC
------------
Blocking

WebFlux
---------
Non-blocking reactive

========================================================================

6. Best use case of WebFlux?

→ High-concurrency I/O-bound applications.

========================================================================

7. Does WebFlux use Event Loop?

→ Yes

========================================================================

MOST IMPORTANT INTERVIEW LINE

Spring WebFlux is reactive non-blocking web framework
built on Project Reactor
for highly scalable concurrent applications.

========================================================================

*/