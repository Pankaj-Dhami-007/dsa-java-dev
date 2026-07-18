package systemdesign.basics.websocket;

public class WebSocketAndRealtimeCommunication {

}

/*

========================================================================
        WEBSOCKET AND REAL-TIME COMMUNICATION
========================================================================

WebSocket is one of MOST IMPORTANT
technologies for real-time applications.

========================================================================

Modern applications requiring instant updates
heavily use:
        WebSocket

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

Why WebSocket needed?

========================================================================
PROBLEM WITH HTTP
========================================================================

HTTP follows:
        Request-Response model

========================================================================

Meaning:
client must request first.

========================================================================

Server cannot independently send updates.

========================================================================
EXAMPLE
========================================================================

Suppose:
WhatsApp message arrives.

========================================================================

How mobile app instantly receives message?

========================================================================

HTTP alone cannot efficiently provide:
real-time bidirectional communication.

========================================================================
TRADITIONAL HTTP FLOW
========================================================================

Client
   ↓ Request
Server
   ↓ Response
Client

========================================================================
PROBLEM
========================================================================

To check new messages:

Client repeatedly sends:
        polling requests

========================================================================
EXAMPLE
========================================================================

Every 1 second:
"Any new messages?"

========================================================================
PROBLEMS WITH POLLING
========================================================================

1. Huge unnecessary requests
2. CPU wastage
3. Network overhead
4. High latency
5. Poor scalability

========================================================================
THIS IS WHY
========================================================================

WebSocket created.

========================================================================
WHAT IS WEBSOCKET?
========================================================================

WebSocket is full-duplex
persistent communication protocol
between client and server.

========================================================================
SIMPLE IDEA
========================================================================

Single connection remains open.

========================================================================

Both:
- client
- server

can send data anytime.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

HTTP is:
        stateless and short-lived

========================================================================

WebSocket is:
        persistent and bidirectional

========================================================================
WEBSOCKET FLOW
========================================================================

Client
   ↕
Persistent Connection
   ↕
Server

========================================================================
MAIN FEATURES
========================================================================

1. Persistent connection
2. Real-time communication
3. Bidirectional messaging
4. Low latency
5. Efficient communication

========================================================================
PERSISTENT CONNECTION
========================================================================

Connection remains open continuously.

========================================================================

No repeated connection setup required.

========================================================================
ADVANTAGE
========================================================================

Reduces:
- latency
- overhead
- repeated handshakes

========================================================================
BIDIRECTIONAL COMMUNICATION
========================================================================

Both client and server
can send messages independently.

========================================================================
EXAMPLE
========================================================================

WhatsApp Server:
instantly pushes new message
to mobile app.

========================================================================
REAL-TIME COMMUNICATION
========================================================================

Data transmitted instantly
without polling delays.

========================================================================
REAL WORLD EXAMPLES
========================================================================

1. Chat applications
2. Live notifications
3. Multiplayer games
4. Stock market updates
5. Live tracking systems
6. Live sports scores

========================================================================
WEBSOCKET CONNECTION ESTABLISHMENT
========================================================================

Initially:
HTTP request sent.

========================================================================

Then:
connection upgraded to WebSocket.

========================================================================
THIS IS CALLED
========================================================================

WebSocket Handshake.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

WebSocket starts as:
HTTP

========================================================================

Then upgrades protocol.

========================================================================
LOW LATENCY
========================================================================

No repeated request-response cycles.

========================================================================

Instant communication possible.

========================================================================
THIS IS WHY
========================================================================

WebSocket ideal for:
real-time systems.

========================================================================
LONG POLLING vs WEBSOCKET
========================================================================

LONG POLLING
-------------
Client repeatedly requests updates.

========================================================================

WEBSOCKET
-----------
Single persistent connection.

========================================================================

Server pushes updates directly.

========================================================================
WHY WEBSOCKET SCALING DIFFICULT?
========================================================================

Because connections remain open.

========================================================================

Millions of active users:
millions of active connections.

========================================================================
PROBLEMS
========================================================================

1. Memory consumption
2. Connection management
3. Distributed coordination
4. Stateful connections

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

WebSocket servers become:
stateful systems.

========================================================================

Because active connections maintained.

========================================================================
STATEFUL vs STATELESS
========================================================================

STATELESS
-----------
No client session memory stored.

========================================================================

STATEFUL
----------
Connection/session maintained.

========================================================================
EXAMPLE
========================================================================

WebSocket server maintains:
connected client sessions.

========================================================================
SCALING WEBSOCKET SYSTEMS
========================================================================

Large-scale systems use:
- distributed WebSocket servers
- pub/sub systems
- message brokers

========================================================================
REAL WORLD FLOW
========================================================================

Client
   ↕
WebSocket Gateway
   ↕
Kafka / Redis PubSub
   ↕
Application Services

========================================================================
WHY REDIS/KAFKA USED?
========================================================================

Suppose:
user connected to Server-1

========================================================================

Message arrives through:
Server-5

========================================================================

Need distributed communication
between servers.

========================================================================
PUB/SUB MODEL
========================================================================

Publish-Subscribe communication.

========================================================================
FLOW
========================================================================

Publisher sends event
to channel/topic.

========================================================================

Subscribers receive updates instantly.

========================================================================
REDIS PUB/SUB
========================================================================

Redis supports:
real-time pub/sub messaging.

========================================================================

Useful for:
chat and notifications.

========================================================================
WEBSOCKET USE CASES
========================================================================

1. Chat systems
2. Online gaming
3. Notification systems
4. Live dashboards
5. Collaborative editing
6. Ride tracking

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

WebSocket not ideal for:
simple REST APIs.

========================================================================

Because persistent connections
consume resources.

========================================================================
WHEN TO USE HTTP?
========================================================================

Use HTTP for:
- CRUD operations
- REST APIs
- normal backend communication

========================================================================
WHEN TO USE WEBSOCKET?
========================================================================

Use WebSocket for:
- instant updates
- live communication
- bidirectional communication

========================================================================
WEBSOCKET CHALLENGES
========================================================================

1. Scaling connections
2. Stateful architecture
3. Reconnection handling
4. Load balancing complexity
5. Distributed messaging

========================================================================
LOAD BALANCING WEBSOCKET
========================================================================

WebSocket requires:
sticky sessions often.

========================================================================

Because connection tied
to specific server.

========================================================================
HEARTBEAT MECHANISM
========================================================================

WebSocket systems periodically check:
whether client still connected.

========================================================================

This called:
        heartbeat/ping-pong

========================================================================
WHY IMPORTANT?
========================================================================

Detect disconnected clients.

========================================================================
SECURITY CONSIDERATIONS
========================================================================

Use:
        WSS

========================================================================

WSS =
        Secure WebSocket

========================================================================

Encrypted communication.

========================================================================
REAL WORLD ARCHITECTURE
========================================================================

Users
   ↕
WebSocket Gateway
   ↕
Redis Pub/Sub or Kafka
   ↕
Chat/Notification Services

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Real-time systems introduce:
connection management complexity.

========================================================================

Scaling real-time systems
is much harder than REST APIs.

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is WebSocket?

→ Persistent bidirectional communication protocol.

========================================================================

2. Why WebSocket needed?

→ Real-time communication without polling.

========================================================================

3. Difference between HTTP and WebSocket?

HTTP
------
request-response

WebSocket
-----------
persistent bidirectional communication

========================================================================

4. What is polling?

→ Client repeatedly requesting updates.

========================================================================

5. Why polling inefficient?

→ Too many unnecessary requests.

========================================================================

6. Why WebSocket scaling difficult?

→ Millions of persistent connections.

========================================================================

7. What is Pub/Sub?

→ Publish-Subscribe messaging model.

========================================================================

8. Why Redis/Kafka used with WebSocket?

→ Distributed real-time communication.

========================================================================

9. What is WSS?

→ Secure encrypted WebSocket communication.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

WebSocket enables low-latency
real-time bidirectional communication
through persistent client-server connections.

========================================================================

*/