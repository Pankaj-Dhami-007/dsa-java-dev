package systemdesign.basics.messagequeue;

public class MessageQueueInSystemDesign {

}

/*

========================================================================
                MESSAGE QUEUE IN SYSTEM DESIGN
========================================================================

Message Queue is one of MOST IMPORTANT
concepts in scalable distributed systems.

========================================================================

Modern applications heavily use:
        Message Queues

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

Why Message Queue needed?

========================================================================
PROBLEM
========================================================================

Suppose user places order.

========================================================================

Order Service must:
- save order
- process payment
- send email
- send SMS
- update inventory
- generate invoice

========================================================================

If everything happens synchronously:

========================================================================

User waits for:
all operations to complete.

========================================================================
PROBLEMS
========================================================================

1. Slow response
2. High latency
3. Service dependency
4. Failure propagation
5. Poor scalability

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Not every task needs:
immediate execution.

========================================================================

Some tasks can execute:
        asynchronously

========================================================================
THIS IS WHY
========================================================================

Message Queue exists.

========================================================================
WHAT IS MESSAGE QUEUE?
========================================================================

Message Queue is asynchronous communication
mechanism between services/components.

========================================================================
SIMPLE IDEA
========================================================================

Producer sends message
to queue.

========================================================================

Consumer processes message later.

========================================================================
MAIN COMPONENTS
========================================================================

1. Producer
2. Queue
3. Consumer

========================================================================
1. PRODUCER
========================================================================

Service/application producing message.

========================================================================
EXAMPLE
========================================================================

Order Service creates:
"Order Created" event.

========================================================================
2. QUEUE
========================================================================

Temporary storage for messages.

========================================================================

Messages wait until consumed.

========================================================================
3. CONSUMER
========================================================================

Service/application processing message.

========================================================================
EXAMPLE
========================================================================

Notification Service consumes:
"Order Created" event
and sends email.

========================================================================
SIMPLE FLOW
========================================================================

Producer
   ↓
Message Queue
   ↓
Consumer

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Producer and Consumer become:
        loosely coupled

========================================================================

Meaning:
they do not directly depend on each other.

========================================================================
ADVANTAGES OF MESSAGE QUEUE
========================================================================

1. Asynchronous processing
2. Better scalability
3. Fault tolerance
4. Load smoothing
5. Loose coupling
6. Improved responsiveness

========================================================================
ASYNCHRONOUS PROCESSING
========================================================================

Client receives response immediately.

========================================================================

Background tasks processed later.

========================================================================
EXAMPLE
========================================================================

Order placed successfully.

========================================================================

Email notification may send:
few seconds later.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

User should not wait for:
non-critical operations.

========================================================================
REAL WORLD EXAMPLE
========================================================================

Suppose:
Instagram user uploads post.

========================================================================

Background tasks:
- thumbnail generation
- notification sending
- recommendation updates

========================================================================

These happen asynchronously
using message queues.

========================================================================
LOAD SMOOTHING
========================================================================

Suppose sudden traffic spike occurs.

========================================================================

Queue temporarily stores messages.

========================================================================

Consumers process gradually.

========================================================================

Prevents system overload.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Queues absorb traffic spikes.

========================================================================
EXAMPLE
========================================================================

Black Friday sale on Amazon.

========================================================================

Millions of orders arrive suddenly.

========================================================================

Queue helps process requests safely.

========================================================================
FAULT TOLERANCE
========================================================================

Suppose consumer crashes.

========================================================================

Messages remain safely inside queue.

========================================================================

Consumer can resume processing later.

========================================================================
THIS IMPROVES
========================================================================

Reliability and durability.

========================================================================
COMMON MESSAGE QUEUE TECHNOLOGIES
========================================================================

- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}
- :contentReference[oaicite:6]{index=6}

========================================================================
RABBITMQ
========================================================================

Traditional message broker.

========================================================================

Good for:
- task queues
- job processing
- reliable delivery

========================================================================
KAFKA
========================================================================

Distributed event streaming platform.

========================================================================

Designed for:
- massive scale
- high throughput
- event streaming

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Kafka is heavily used
in modern large-scale systems.

========================================================================
MESSAGE
========================================================================

Message contains:
- event data
- payload
- metadata

========================================================================
EXAMPLE MESSAGE
========================================================================

{
    "orderId": 101,
    "userId": 5,
    "status": "CREATED"
}

========================================================================
QUEUE TYPES
========================================================================

1. Point-to-Point
2. Publish-Subscribe

========================================================================
1. POINT-TO-POINT
========================================================================

One message consumed by:
one consumer.

========================================================================
EXAMPLE
========================================================================

Order processing system.

========================================================================
2. PUBLISH-SUBSCRIBE
========================================================================

One message consumed by:
multiple consumers.

========================================================================
EXAMPLE
========================================================================

"User Registered" event:
- Email Service
- Analytics Service
- Notification Service

========================================================================

All consume same event.

========================================================================
IMPORTANT CONCEPT
========================================================================

Event-Driven Architecture

========================================================================

Services communicate using:
events/messages

========================================================================

Instead of direct API calls.

========================================================================
ADVANTAGE
========================================================================

Highly scalable and loosely coupled systems.

========================================================================
MESSAGE RETRY
========================================================================

Suppose message processing fails.

========================================================================

Queue systems support:
automatic retries.

========================================================================
EXAMPLE
========================================================================

Email service temporarily down.

========================================================================

Message retried later automatically.

========================================================================
DEAD LETTER QUEUE (DLQ)
========================================================================

Messages repeatedly failing
moved to special queue.

========================================================================

This queue called:
        Dead Letter Queue

========================================================================
WHY DLQ IMPORTANT?
========================================================================

Prevents infinite retry loops.

========================================================================

Helps debugging failed messages.

========================================================================
MESSAGE ORDERING
========================================================================

Some systems require:
strict ordering.

========================================================================
EXAMPLE
========================================================================

Bank transactions.

========================================================================

Message sequence matters.

========================================================================
KAFKA PARTITIONS
========================================================================

Kafka divides data into:
        partitions

========================================================================

Enables:
parallel processing and scalability.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

More partitions:
more parallelism.

========================================================================

But ordering becomes partition-specific.

========================================================================
CONSUMER GROUP
========================================================================

Multiple consumers work together
to process messages faster.

========================================================================

Each message processed by:
one consumer within group.

========================================================================
VERY IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Queues help systems become:
- scalable
- resilient
- asynchronous

========================================================================

Modern distributed systems depend heavily
on asynchronous communication.

========================================================================
COMMON USE CASES
========================================================================

1. Email sending
2. Notification systems
3. Chat systems
4. Payment processing
5. Logging
6. Analytics pipelines
7. Video processing

========================================================================
REAL WORLD ARCHITECTURE
========================================================================

Client
   ↓
API Gateway
   ↓
Order Service
   ↓
Kafka/RabbitMQ
   ↓
Notification Service
Analytics Service
Inventory Service

========================================================================
QUEUE vs SYNCHRONOUS API CALL
========================================================================

Synchronous
--------------
Immediate response required.

========================================================================

Asynchronous
--------------
Task processed later.

========================================================================
WHEN TO USE MESSAGE QUEUE?
========================================================================

Use when:
- tasks are asynchronous
- high scalability needed
- loose coupling desired
- background processing required

========================================================================
CHALLENGES OF MESSAGE QUEUES
========================================================================

1. Message duplication
2. Ordering complexity
3. Retry management
4. Distributed debugging
5. Eventual consistency

========================================================================
EVENTUAL CONSISTENCY
========================================================================

Data may not update immediately
across all services.

========================================================================

Eventually all services become consistent.

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is Message Queue?

→ Asynchronous communication mechanism
between services.

========================================================================

2. Why Message Queue needed?

→ Improve scalability and asynchronous processing.

========================================================================

3. Difference between synchronous and asynchronous?

Synchronous
-------------
wait for completion

Asynchronous
--------------
process later

========================================================================

4. What is Producer?

→ Component sending messages.

========================================================================

5. What is Consumer?

→ Component processing messages.

========================================================================

6. What is Dead Letter Queue?

→ Queue storing repeatedly failed messages.

========================================================================

7. Difference between Kafka and RabbitMQ?

Kafka
------
high throughput event streaming

RabbitMQ
---------
traditional message broker

========================================================================

8. What is Event-Driven Architecture?

→ Services communicate using events/messages.

========================================================================

9. What is Consumer Group?

→ Multiple consumers processing messages together.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Message Queue enables asynchronous,
loosely coupled,
and scalable communication
between distributed services.

========================================================================

*/