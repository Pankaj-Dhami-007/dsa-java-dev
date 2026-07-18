package systemdesign.basics.cdn;

public class CDNInSystemDesign {

}

/*

========================================================================
                CDN IN SYSTEM DESIGN
========================================================================

CDN is one of MOST IMPORTANT
performance optimization systems
used in modern internet applications.

========================================================================

Almost every global-scale application uses:
        CDN

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

Why CDN needed?

========================================================================
PROBLEM
========================================================================

Suppose:
main server located in USA.

========================================================================

User from India opens:
Netflix video.

========================================================================

Request must travel:
India → USA → India

========================================================================
PROBLEMS
========================================================================

1. High latency
2. Slow loading
3. Network congestion
4. Poor user experience

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Long geographical distance
increases response time.

========================================================================
THIS IS WHY
========================================================================

CDN created.

========================================================================
WHAT IS CDN?
========================================================================

CDN stands for:
        Content Delivery Network

========================================================================

CDN is geographically distributed
network of servers
used to deliver content faster
to users.

========================================================================
SIMPLE IDEA
========================================================================

Instead of serving content
from one distant server:

Serve content from:
nearest server.

========================================================================
MAIN GOAL
========================================================================

Reduce:
- latency
- server load
- bandwidth usage

========================================================================

Improve:
- speed
- availability
- user experience

========================================================================
CDN FLOW
========================================================================

User
   ↓
Nearest CDN Server
   ↓
Origin Server (if needed)

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

CDN acts like:
        distributed cache

========================================================================

Content copied to:
multiple geographical locations.

========================================================================
WHAT TYPE OF CONTENT CDN STORES?
========================================================================

Mostly:
static content.

========================================================================
EXAMPLES
========================================================================

1. Images
2. Videos
3. CSS
4. JavaScript
5. Audio
6. Documents

========================================================================
STATIC CONTENT
========================================================================

Content that changes rarely.

========================================================================
EXAMPLE
========================================================================

Instagram profile image.

========================================================================
DYNAMIC CONTENT
========================================================================

Content generated dynamically.

========================================================================
EXAMPLE
========================================================================

Bank account balance.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

CDNs mainly optimize:
static content delivery.

========================================================================
ORIGIN SERVER
========================================================================

Main backend server
where original content exists.

========================================================================
FLOW
========================================================================

If CDN has content:
        return immediately

========================================================================

Else:
fetch from Origin Server
and cache locally.

========================================================================
THIS IS CALLED
========================================================================

Cache Miss.

========================================================================
CACHE HIT
========================================================================

Content already available
inside CDN edge server.

========================================================================

Very fast response.

========================================================================
EDGE SERVER
========================================================================

CDN servers located near users.

========================================================================
EXAMPLE
========================================================================

CDN servers in:
- India
- USA
- Europe
- Japan

========================================================================
WHY EDGE SERVERS IMPORTANT?
========================================================================

Reduce physical distance
between user and content.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Lower distance:
lower latency.

========================================================================
REAL WORLD EXAMPLE
========================================================================

Suppose:
Netflix movie cached
inside Indian CDN server.

========================================================================

Indian users stream directly
from India server.

========================================================================

No need to contact USA server repeatedly.

========================================================================
THIS IMPROVES
========================================================================

1. Faster streaming
2. Lower latency
3. Reduced origin server load

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Without CDN,
large-scale media systems
would struggle massively.

========================================================================
CDN + CACHING
========================================================================

CDN is specialized form
of distributed caching.

========================================================================

Cache distributed globally.

========================================================================
POPULAR CDN PROVIDERS
========================================================================

- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}
- :contentReference[oaicite:6]{index=6}

========================================================================
CACHE EXPIRATION IN CDN
========================================================================

CDN content should not remain forever.

========================================================================

Old content may become stale.

========================================================================

TTL used for expiration.

========================================================================
EXAMPLE
========================================================================

Cache image for:
        24 hours

========================================================================
CDN INVALIDATION
========================================================================

Suppose:
new image uploaded.

========================================================================

Old CDN cache must be removed.

========================================================================

This process called:
        Cache Invalidation

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Cache invalidation is difficult
in distributed systems.

========================================================================
CDN BENEFITS
========================================================================

1. Reduced latency
2. Faster loading
3. Reduced bandwidth cost
4. Better scalability
5. Reduced server load
6. Improved availability

========================================================================
LATENCY REDUCTION
========================================================================

Nearby servers provide:
faster response.

========================================================================
SERVER LOAD REDUCTION
========================================================================

Origin server receives:
fewer requests.

========================================================================

CDN handles majority traffic.

========================================================================
BANDWIDTH SAVINGS
========================================================================

Repeated large file transfers
from origin server reduced.

========================================================================
HIGH AVAILABILITY
========================================================================

If one CDN server fails:
another edge server serves content.

========================================================================
IMPORTANT REAL WORLD EXAMPLE
========================================================================

YouTube videos served
through global CDN infrastructure.

========================================================================

Otherwise:
single datacenter would collapse.

========================================================================
CDN + DDOS PROTECTION
========================================================================

Many CDNs provide:
security protection.

========================================================================
EXAMPLE
========================================================================

- traffic filtering
- DDOS mitigation
- bot protection

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Modern CDNs often act as:
- cache
- security layer
- traffic optimizer

========================================================================
PULL CDN
========================================================================

CDN fetches content automatically
from origin server when needed.

========================================================================

Most common approach.

========================================================================
PUSH CDN
========================================================================

Origin server explicitly uploads
content into CDN.

========================================================================
WHEN CDN NOT VERY USEFUL?
========================================================================

Highly dynamic data.

========================================================================
EXAMPLES
========================================================================

- live banking balance
- real-time stock prices

========================================================================

Because data changes continuously.

========================================================================
REAL WORLD ARCHITECTURE
========================================================================

Users
   ↓
CDN Edge Server
   ↓
Load Balancer
   ↓
Application Servers
   ↓
Database

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

As global users increase,
content delivery becomes
major performance challenge.

========================================================================

CDN solves:
global content distribution problem.

========================================================================
COMMON USE CASES
========================================================================

1. Video streaming
2. Image delivery
3. Website acceleration
4. Software downloads
5. Gaming assets
6. Static web hosting

========================================================================
CHALLENGES OF CDN
========================================================================

1. Cache invalidation
2. Storage cost
3. Dynamic content handling
4. Cache consistency

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is CDN?

→ Distributed network of servers
used to deliver content faster.

========================================================================

2. Why CDN needed?

→ Reduce latency and improve performance.

========================================================================

3. What is Edge Server?

→ CDN server located near users.

========================================================================

4. Difference between cache hit and miss?

Hit
-----
content already cached

Miss
------
content fetched from origin server

========================================================================

5. What is Origin Server?

→ Main server storing original content.

========================================================================

6. Why CDN important for Netflix/YouTube?

→ Fast global media delivery.

========================================================================

7. What type of content CDN stores mostly?

→ Static content.

========================================================================

8. What is CDN invalidation?

→ Removing outdated cached content.

========================================================================

9. How CDN reduces server load?

→ Edge servers handle majority traffic.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

CDN improves global application performance
by distributing cached content
across geographically distributed edge servers
closer to users.

========================================================================

*/