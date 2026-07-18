package springboot_deep_drive.kafka.eda;

/**
 * =====================================================================
 * EVENT-DRIVEN ARCHITECTURE (EDA) — COMPLETE INTERVIEW GUIDE
 * =====================================================================
 *
 * 1. WHAT IS EVENT-DRIVEN ARCHITECTURE?
 * ───────────────────────────────────────
 * EDA is an architectural pattern where services communicate by producing
 * and consuming **events** (facts about something that happened) through
 * an intermediate event broker (e.g., Kafka, RabbitMQ, AWS SNS/SQS).
 *
 * Key terms:
 *   • Event Producer  — publishes events (e.g., "OrderCreated")
 *   • Event Broker    — stores & routes events (e.g., Kafka Topic)
 *   • Event Consumer  — subscribes to & processes events
 *
 *
 * 2. TRADITIONAL (SYNCHRONOUS) APPROACH — TIGHT COUPLING
 * ────────────────────────────────────────────────────────
 * Services call each other directly over HTTP/REST/gRPC (request-response).
 *
 *     ┌──────────┐     POST /pay     ┌──────────┐
 *     │  Order   │ ───────────────>  │ Payment  │
 *     │ Service  │ <───────────────  │ Service  │
 *     └──────────┘     200 OK        └──────────┘
 *           │                              │
 *           │ POST /inventory               │ POST /notify
 *           v                              v
 *     ┌──────────┐                  ┌──────────┐
 *     │Inventory │                  │Notify    │
 *     │ Service  │                  │ Service  │
 *     └──────────┘                  └──────────┘
 *
 * PROBLEMS with this approach:
 * ──────────────────────────────
 * 1. TIGHT COUPLING
 *    — OrderService must know the URL, contract, auth mechanism of
 *      PaymentService, InventoryService, NotificationService.
 *    — Changing one service often forces changes in callers.
 *
 * 2. CASCADING FAILURES
 *    — If PaymentService is down, OrderService's HTTP call times out /
 *      throws exception → order creation fails entirely.
 *    — If InventoryService is slow (e.g., DB query takes 10s),
 *      OrderService thread is blocked → connection pool exhaustion →
 *      all order requests fail.
 *    — One slow/down service takes down the entire flow.
 *
 * 3. SCALING ISSUES
 *    — All services must scale together — you cannot scale OrderService
 *      independently if it directly calls PaymentService.
 *    — Peak load on OrderService means proportional load on downstream.
 *
 * 4. HIGH LATENCY
 *    — Total response time = sum of all service latencies (serial calls).
 *    — Latency(Order) = Latency(Payment) + Latency(Inventory) + Latency(Notify)
 *    — If each takes 200ms → user waits 600ms+ before getting response.
 *
 * 5. DATABASE COUPLING (often)
 *    — Services may share a DB or access each other's tables.
 *
 *
 * 3. EDA APPROACH — DECOUPLED SYSTEM
 * ────────────────────────────────────
 * Services communicate through events via a Broker (Kafka).
 *
 *     ┌──────────┐   publishes    ┌──────────────────┐
 *     │  Order   │ ─────────────> │   KAFKA BROKER   │
 *     │ Service  │  OrderCreated  │  (Event Bus)      │
 *     └──────────┘                │                   │
 *                        ┌───────┤  ┌─────────────┐  │
 *                        │       │  │ Topic:       │  │
 *                        │       │  │ order-events │  │
 *                        │       │  └─────────────┘  │
 *                        │       └──────────────────┘
 *                        │                │
 *                        │                │  subscribe
 *                        │                │
 *                        v                v
 *     ┌──────────┐  ┌──────────┐  ┌──────────┐
 *     │ Payment  │  │Inventory │  │ Notify   │
 *     │ Service  │  │ Service  │  │ Service  │
 *     └──────────┘  └──────────┘  └──────────┘
 *
 * FLOW (E-Commerce Example):
 *   1. User places order → OrderService publishes "OrderCreated" event
 *      to Kafka topic `order-events`.
 *   2. PaymentService (consumer) receives event → processes payment →
 *      publishes "PaymentSuccess" event.
 *   3. InventoryService (consumer) receives "PaymentSuccess" →
 *      reserves items → publishes "InventoryReserved" event.
 *   4. NotificationService (consumer) receives any of these events →
 *      sends email/SMS to user.
 *
 *
 * 4. WHAT IS A DECOUPLED SYSTEM?
 * ────────────────────────────────
 * A **decoupled system** is one where components have minimal knowledge
 * of each other. They interact through well-defined interfaces/events
 * without direct dependency.
 *
 * Benefits of Decoupling:
 *   ✓ Independent development — teams own their services
 *   ✓ Independent deployment — deploy PaymentService without touching others
 *   ✓ Independent scaling — scale InventoryService only if inventory load is high
 *   ✓ Fault isolation — PaymentService crash doesn't affect OrderService
 *   ✓ Technology diversity — each service can use different languages/DBs
 *
 * TIGHT COUPLING vs DECOUPLED (Side-by-Side):
 * ──────────────────────────────────────────────
 *  Aspect            │  Tightly Coupled       │  Decoupled (EDA)
 * ───────────────────┼────────────────────────┼─────────────────────────
 *  Communication     │  HTTP/RPC direct       │  Async events via broker
 *  Dependency        │  Knows other URLs      │  Knows only event schema
 *  Failure impact    │  Cascading             │  Isolated
 *  Latency to user   │  Sum of all services   │  Only OrderService latency
 *  Scaling           │  Must scale together   │  Independent per service
 *  Deployment        │  Coordinated deploys   │  Independent deploys
 *  Code change       │  Ripple effect         │  Localized
 *
 *
 * 5. HOW EDA SOLVES EACH PROBLEM
 * ─────────────────────────────────
 * PROBLEM                    │  EDA SOLUTION
 * ───────────────────────────┼─────────────────────────────────────────
 * Tight Coupling             │  Producers only know event schema + topic
 *                            │  Consumers are unknown to producers
 * ───────────────────────────┼─────────────────────────────────────────
 * Cascading Failure          │  If PaymentService is down:
 *                            │    • Broker retains the event (Kafka retention)
 *                            │    • Event is replayed when consumer comes up
 *                            │    • OrderService already responded 200 to user
 *                            │    • No thread blocking, no connection pool drain
 * ───────────────────────────┼─────────────────────────────────────────
 * Scaling                    │  • High inventory load? Scale InventoryService
 *                            │    to 10 instances — OrderService untouched
 *                            │  • Each consumer group has its own offset
 *                            │    → partitions allow parallel consumption
 * ───────────────────────────┼─────────────────────────────────────────
 * Latency                    │  • OrderService returns immediately after publish
 *                            │  • User gets 200 in ~50ms instead of 600ms+
 *                            │  • Downstream processing happens asynchronously
 * ───────────────────────────┼─────────────────────────────────────────
 * "One service down = all    │  • OrderService returns success immediately
 *  fail"                     │  • Downstream services retry/process later
 *                            │  • Event-driven = eventually consistent
 *
 *
 * 6. REAL-LIFE EXAMPLE
 * ─────────────────────
 * TRADITIONAL PROBLEM:
 * ┌──────────────────────────────────────────────────────────────────────┐
 * │  Amazon.com — User clicks "Place Order"                             │
 * │                                                                     │
 * │  REST calls (Synchronous):                                          │
 * │  POST /orders ──> POST /payments ──> POST /inventory ──> POST /sms │
 * │     ↓                 ↓                  ↓                ↓        │
 * │    Wait(200ms)      Wait(300ms)         Wait(250ms)      Wait(100ms)│
 * │    ↓                 ↓                  ↓                ↓        │
 * │  User waits 850ms total for order confirmation                     │
 * │  If Payment Gateway is down → entire order fails (500 error)       │
 * └──────────────────────────────────────────────────────────────────────┘
 *
 * EDA SOLUTION:
 * ┌──────────────────────────────────────────────────────────────────────┐
 * │  Amazon.com — User clicks "Place Order"                             │
 * │                                                                     │
 * │  OrderService publishes "OrderCreated" to Kafka → returns 200 (50ms)│
 * │  User sees "Order Placed Successfully!" immediately                 │
 * │                                                                     │
 * │  Behind the scenes (async):                                         │
 * │    PaymentService consumes → processes (can take 5s, user doesn't   │
 * │    care) → publishes "PaymentSuccess"                               │
 * │    InventoryService consumes → reserves stock                       │
 * │    NotificationService consumes → sends "Order Confirmed" email     │
 * │                                                                     │
 * │  If PaymentService is down for 30 min:                              │
 * │    → Kafka retains the event                                        │
 * │    → When PaymentService recovers, it reads from where it left off  │
 * │    → No orders lost, no errors shown to user                        │
 * └──────────────────────────────────────────────────────────────────────┘
 *
 *
 * 7. KAFKA-BASED EDA FOR E-COMMERCE — FULL ARCHITECTURE DIAGRAM
 * ──────────────────────────────────────────────────────────────
 *
 *                         ┌──────────────────────────────────┐
 *                         │          API GATEWAY             │
 *                         │     (REST / GraphQL)             │
 *                         └───────────┬──────────────────────┘
 *                                     │ POST /orders
 *                                     v
 * ┌────────────────────────────────────────────────────────────────────────┐
 * │                         ORDER SERVICE (Producer)                       │
 * │  Validates order → saves to Order DB → publishes OrderCreated event   │
 * │  Returns HTTP 200 to user immediately                                 │
 * └───────────────────────────────────┬────────────────────────────────────┘
 *                                     │ publish
 *                                     v
 * ┌────────────────────────────────────────────────────────────────────────┐
 * │                       KAFKA CLUSTER                                    │
 * │                                                                        │
 * │  ┌───────────────────────────────────────────────────────────────────┐ │
 * │  │  Topic: order-events (Partitions: 3, Replication: 3)             │ │
 * │  │                                                                   │ │
 * │  │  Partition 0  │  Partition 1  │  Partition 2                     │ │
 * │  │  ┌─────┐      │  ┌─────┐      │  ┌─────┐                         │ │
 * │  │  │Evt-1│      │  │Evt-2│      │  │Evt-3│                         │ │
 * │  │  │Evt-4│      │  │Evt-5│      │  │Evt-6│                         │ │
 * │  │  └─────┘      │  └─────┘      │  └─────┘                         │ │
 * │  └───────────────────────────────────────────────────────────────────┘ │
 * │                                                                        │
 * │  ┌───────────────────────────────────────────────────────────────────┐ │
 * │  │  Topic: payment-events                                            │ │
 * │  │  Topic: inventory-events                                          │ │
 * │  │  Topic: notification-events                                       │ │
 * │  └───────────────────────────────────────────────────────────────────┘ │
 * └────────────────────────────────────────────────────────────────────────┘
 *                │                    │                    │
 *                │ subscribe          │ subscribe          │ subscribe
 *                v                    v                    v
 * ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────────┐
 * │  PAYMENT SERVICE │  │ INVENTORY SERVICE│  │ NOTIFICATION SERVICE │
 * │  (Consumer)      │  │ (Consumer)       │  │ (Consumer)           │
 * │                  │  │                  │  │                      │
 * │  Reads from      │  │ Reads from       │  │ Reads from           │
 * │  order-events    │  │ payment-events   │  │ payment-events       │
 * │  topic           │  │ topic            │  │ + inventory-events   │
 * │                  │  │                  │  │                      │
 * │  Publishes to    │  │ Publishes to     │  │ Sends Email / SMS    │
 * │  payment-events  │  │ inventory-events │  │                      │
 * └──────────────────┘  └──────────────────┘  └──────────────────────┘
 *
 *
 * 8. EVENT STRUCTURE (JSON Example)
 * ─────────────────────────────────
 * {
 *   "eventId": "evt-12345",
 *   "eventType": "OrderCreated",
 *   "source": "order-service",
 *   "timestamp": "2026-06-28T10:30:00Z",
 *   "data": {
 *     "orderId": "ORD-98765",
 *     "userId": "usr-42",
 *     "items": [
 *       { "productId": "p-100", "quantity": 2, "price": 29.99 }
 *     ],
 *     "totalAmount": 59.98,
 *     "shippingAddress": { ... }
 *   }
 * }
 *
 *
 * 9. INTERVIEW TIPS — HOW TO EXPLAIN EDA
 * ────────────────────────────────────────
 *
 * Q: "What is Event-Driven Architecture?"
 * A: "EDA is an architectural style where services communicate by producing
 *     and consuming events asynchronously through a central event broker,
 *     rather than making direct synchronous HTTP calls."
 *
 * Q: "Why use EDA over traditional REST?"
 * A: "Three main reasons: (1) Decoupling — services don't depend on each
 *     other's availability, (2) Resilience — failures are isolated and events
 *     can be replayed, (3) Scalability — each service scales independently."
 *
 * Q: "What are the trade-offs of EDA?"
 * A: "EDA introduces eventual consistency — a user might place an order but
 *     the inventory isn't deducted yet. It also adds complexity: event schema
 *     management, exactly-once vs at-least-once semantics, debugging async
 *     flows is harder, and you need infrastructure like Kafka."
 *
 * Q: "How do you handle failures in EDA?"
 * A: "Using dead-letter queues (DLQ), retry mechanisms with exponential
 *     backoff, idempotent consumers, and Kafka's offset management ensures
 *     events are not lost even if a consumer crashes."
 *
 * Q: "What's the difference between at-least-once and exactly-once?"
 * A: "At-least-once guarantees the event is delivered ≥1 times (may duplicate).
 *     Exactly-once guarantees it's processed exactly 1 time. Kafka provides
 *     exactly-once semantics via idempotent producers + transactional API."
 *
 * Q: "How does EDA help with the 'one service down breaks everything' problem?"
 * A: "In EDA, the producer publishes the event and immediately returns.
 *     The event is stored durably in Kafka. If a consumer is down, the event
 *     stays in the topic. When the consumer recovers, it resumes from its
 *     committed offset — no data loss, no error propagation to the user."
 *
 *
 * 10. KEY TAKEAWAYS FOR CODING ROUNDS
 * ────────────────────────────────────
 *   • Producer: publish to topic (fire-and-forget)
 *   • Consumer: @KafkaListener(topics = "...")
 *   • Event schema: use Avro / Protobuf for type safety + schema registry
 *   • Idempotency: deduplicate by eventId on consumer side
 *   • Ordering: use same partition key (e.g., orderId) to keep related events ordered
 *   • Backpressure: consumer can pause/resume based on processing capacity
 *
 * Example Kafka Producer (Spring Boot):
 *
 *     @Autowired private KafkaTemplate<String, OrderEvent> kafkaTemplate;
 *
 *     public OrderCreatedEvent createOrder(Order order) {
 *         OrderCreatedEvent event = new OrderCreatedEvent(order);
 *         kafkaTemplate.send("order-events", order.getId(), event);
 *         return event;  // user gets response immediately
 *     }
 *
 * Example Kafka Consumer (Spring Boot):
 *
 *     @KafkaListener(topics = "order-events", groupId = "payment-group")
 *     public void handleOrderCreated(OrderCreatedEvent event) {
 *         paymentService.processPayment(event.getOrder());
 *     }
 *
 *
 * 11. THROUGHPUT — DEEP DIVE
 * ──────────────────────────────
 * THROUGHPUT = number of events/messages processed per unit time (e.g., msgs/sec).
 *
 * EDA DRAMATICALLY improves throughput compared to traditional synchronous calls.
 *
 * WHY TRADITIONAL APPROACH HAS LOW THROUGHPUT:
 * ──────────────────────────────────────────────
 *   • Each request consumes ONE thread in the servlet container.
 *   • While waiting for PaymentService HTTP response, the thread is BLOCKED.
 *   • Common Tomcat default: 200 threads. If each request takes 850ms,
 *     max throughput = 200 / 0.85 ≈ 235 requests/sec.
 *   • More threads → context switching overhead → diminishing returns.
 *
 *     Thread Pool (200 threads)
 *     ┌────┬────┬────┬────┬────┐
 *     │ T1 │ T2 │ T3 │ T4 │ T5 │  ... all BLOCKED waiting for HTTP
 *     │ ░░░░░░░░░░░░░░░░░░░  │  responses → contention, low throughput
 *     └────┴────┴────┴────┴────┘
 *
 * WHY EDA HAS HIGH THROUGHPUT:
 * ─────────────────────────────────
 *   • Producer returns immediately (non-blocking) → thread freed in ~50ms.
 *   • Same 200 threads now handle 200 / 0.05 = 4000 requests/sec.
 *   • Consumers process in parallel, independently.
 *   • Kafka itself handles millions of msgs/sec (LinkedIn benchmark: 2M+/sec).
 *
 *     Thread Pool (200 threads)
 *     ┌────┬────┬────┬────┬────┐
 *     │ T1 │ T2 │ T3 │ T4 │ T5 │  ... freed immediately after publish
 *     │ ✔  │ ✔  │ ✔  │ ✔  │ ✔  │  → handles next request right away
 *     └────┴────┴────┴────┴────┘
 *
 * THROUGHPUT FORMULA (THEORETICAL MAX):
 * ────────────────────────────────────────
 *   Throughput = (1 / Latency_per_request) × Concurrency
 *
 *   Traditional:
 *     T = (1 / 0.85s) × 200 threads ≈ 235 req/sec
 *
 *   EDA:
 *     T = (1 / 0.05s) × 200 threads ≈ 4000 req/sec
 *
 *   → EDA gives ~17x more throughput with the SAME hardware
 *
 * KAFKA THROUGHPUT OPTIMIZATION FACTORS:
 * ────────────────────────────────────────
 *   • Partitions = parallelism. More partitions → more concurrent consumers.
 *     Each partition can be consumed by 1 consumer in a group.
 *   • Batch size: Kafka batches messages for efficiency.
 *     Larger batches → higher throughput, higher latency.
 *   • Compression: snappy/zstd reduces network I/O → higher throughput.
 *   • acks setting:
 *       acks=0  → fire-and-forget, max throughput, data loss risk
 *       acks=1  → leader ack, good throughput, minor loss risk
 *       acks=all → full ISR ack, lower throughput, zero loss
 *   • Replication factor: more replicas = more I/O = lower throughput.
 *
 *
 * 12. PARTITIONING & PARALLELISM
 * ───────────────────────────────────
 * Kafka topics are split into PARTITIONS — the unit of parallelism.
 *
 *     Topic: "order-events" (3 partitions)
 *     ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
 *     │ Partition 0 │ │ Partition 1 │ │ Partition 2 │
 *     │             │ │             │ │             │
 *     │  Evt-1      │ │  Evt-2      │ │  Evt-3      │
 *     │  Evt-4      │ │  Evt-5      │ │  Evt-6      │
 *     │  Evt-7      │ │  Evt-8      │ │  Evt-9      │
 *     └─────────────┘ └─────────────┘ └─────────────┘
 *            │               │               │
 *            └───────┬───────┴───────┬───────┘
 *                    │               │
 *              ┌─────┴─────┐   ┌─────┴─────┐
 *              │Consumer-1 │   │Consumer-2 │  (Consumer Group: payment-group)
 *              │(reads P0) │   │(reads P1) │
 *              └───────────┘   └───────────┘
 *            Partition 2 is idle (no consumer assigned) → under-utilized!
 *
 * RULE:
 *   Max parallel consumers in a group = Number of partitions
 *   If you have 3 partitions → max 3 consumers consuming in parallel
 *   Extra consumers will be idle (standby)
 *
 * PARTITION KEY (ROUTING):
 *   producer.send("order-events", orderId, event)
 *   Same orderId → always goes to SAME partition → ORDERED processing
 *
 * KEY-BASED DIAGRAM:
 *   orderId=123 (hash → P0) → all events for order 123 go to P0
 *   orderId=456 (hash → P2) → all events for order 456 go to P2
 *
 * ORDERING GUARANTEES:
 *   • Within a PARTITION: STRICT ORDER is maintained
 *   • Across PARTITIONS: NO ordering guarantee
 *   • Use same key (orderId) to keep related events ordered
 *
 *
 * 13. CONSUMER GROUP REBALANCING
 * ──────────────────────────────────
 * When a consumer joins/leaves a group, Kafka triggers REBALANCE.
 *
 *     Before rebalance (3 consumers, 3 partitions):
 *     C1 ── P0    C2 ── P1    C3 ── P2    ✓ Balanced
 *
 *     Consumer C2 crashes:
 *     C1 ── P0    C2 ✘         C3 ── P2
 *
 *     Rebalance triggered:
 *     C1 ── P0, P1    C3 ── P2    ✓ C1 takes over P1
 *
 *     PROBLEM: During rebalance, ALL consumers STOP processing
 *     (stop-the-world event) → can take seconds for large groups
 *
 *     STATIC GROUP MEMBERSHIP (fix):
 *     consumer instance.id = "instance-1"  // avoids eager rebalance
 *     session.timeout.ms = 45000           // longer timeout before kick
 *
 *
 * 14. THROUGHPUT vs LATENCY TRADE-OFF
 * ──────────────────────────────────────
 * ┌─────────────────────────┬──────────────────────────┬──────────────────┐
 * │       Setting           │    Effect on Throughput   │ Effect on Latency│
 * ├─────────────────────────┼──────────────────────────┼──────────────────┤
 * │ More partitions         │ Increases parallelism ↑  │ No direct effect │
 * │ Large batch.size        │ Higher throughput ↑↑     │ Higher latency ↑ │
 * │ linger.ms > 0           │ Better batching ↑        │ More delay ↑     │
 * │ Compression on          │ Higher throughput ↑      │ CPU overhead ↑   │
 * │ acks=all                │ Lower throughput ↓       │ Safer latency    │
 * │ Replication factor > 3  │ Lower throughput ↓       │ Minor increase   │
 * └─────────────────────────┴──────────────────────────┴──────────────────┘
 *
 * INTERVIEW ANSWER FRAMEWORK:
 *   "Throughput and latency are inversely related in Kafka. To maximize
 *    throughput, I increase partitions, enable compression, and use
 *    larger batches with linger.ms. For low latency, I minimize batches
 *    and use acks=1. For e-commerce orders, I prioritize throughput
 *    (high volume) with acceptable latency. For payment fraud detection,
 *    I prioritize low latency."
 *
 *
 * 15. EVENT PROCESSING SEMANTICS (DEEP DIVE)
 * ─────────────────────────────────────────────
 *
 * AT-MOST-ONCE:    Event delivered 0 or 1 time. Data loss possible.
 *                  Use: Monitoring, logs where loss is acceptable.
 *
 * AT-LEAST-ONCE:   Event delivered ≥1 time. No data loss, but duplicates.
 *                  Use: Most common. Consumer must be IDEMPOTENT.
 *                  Kafka default behavior (auto.commit=true with offsets
 *                  committed AFTER processing).
 *
 * EXACTLY-ONCE:    Event processed exactly 1 time, no duplicates, no loss.
 *                  Requires: idempotent producer + transactions +
 *                  consumer with transactional read_committed.
 *                  Use: Financial transactions, payments.
 *
 * EXACTLY-ONCE DIAGRAM:
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │  Producer (idempotent=true)           Kafka Broker                   │
 * │  ┌──────────────┐     produce tx      ┌──────────────────────────┐  │
 * │  │ enable.idemp │ ─────────────────>  │  Transaction Coordinator │  │
 * │  │ otence=true  │                     │  ┌────────────────────┐  │  │
 * │  │              │                     │  │ __consumer_offsets │  │  │
 * │  │              │                     │  │ + __transaction_   │  │  │
 * │  │              │                     │  │   state            │  │  │
 * │  └──────────────┘                     │  └────────────────────┘  │  │
 * │                                       └──────────────────────────┘  │
 * │                                                │                    │
 * │  Consumer (isolation.level=read_committed)      │                    │
 * │  ┌────────────────┐                            │                    │
 * │  │ Only reads     │ <───────────────────────────┘                    │
 * │  │ committed msgs │                                                  │
 * │  └────────────────┘                                                  │
 * └─────────────────────────────────────────────────────────────────────┘
 *
 *
 * 16. IDEMPOTENCY PATTERNS
 * ──────────────────────────────
 * Since EDA typically uses at-least-once delivery, consumers MUST be idempotent
 * (processing same event twice produces same result).
 *
 * PATTERN 1: Dedup by eventId
 *   if (processedEventsCache.contains(event.eventId)) {
 *       return; // already processed, skip
 *   }
 *   process(event);
 *   processedEventsCache.put(event.eventId);
 *
 * PATTERN 2: Database upsert
 *   INSERT INTO orders (order_id, ...) VALUES (?, ...)
 *   ON CONFLICT (order_id) DO NOTHING;
 *
 * PATTERN 3: Idempotency key
 *   Payment Gateway: idempotencyKey = event.eventId
 *   Same key → same result, no double charge
 *
 *
 * 17. DEAD LETTER QUEUE (DLQ) — FAILURE HANDLING
 * ──────────────────────────────────────────────────
 * When a consumer repeatedly fails to process an event (after N retries),
 * the event is sent to a DLQ topic for manual inspection.
 *
 *     ┌──────────┐     read     ┌─────────────┐    retry × 3
 *     │  Kafka   │ ──────────>  │  Consumer   │ ──────────> FAIL
 *     │  Topic   │              └─────────────┘       │
 *     └──────────┘                                     │
 *         │                                            │ publish
 *         │                                            v
 *         │                                     ┌──────────────┐
 *         │                                     │  DLQ Topic   │
 *         │                                     │ (alerts team)│
 *         │                                     └──────────────┘
 *         │
 *     After fix, replay from DLQ:
 *         │
 *         v
 *     ┌──────────┐     re-publish    ┌─────────────┐
 *     │  DLQ     │ ──────────────>  │  Original    │
 *     │  (replay)│                  │  Topic       │
 *     └──────────┘                  └─────────────┘
 *
 *
 * 18. SAGA PATTERN — DISTRIBUTED TRANSACTIONS IN EDA
 * ─────────────────────────────────────────────────────
 * In e-commerce, an order involves multiple services. If one fails,
 * we need to COMPENSATE (rollback) previous steps. This is a SAGA.
 *
 * CHOREOGRAPHY-BASED SAGA (each service produces events):
 * ┌────────────────────────────────────────────────────────────────────────┐
 * │ OrderService ──> OrderCreated ──> PaymentService                       │
 * │                                      │                                │
 * │                                      ├── Success → PaymentSuccess ──> │
 * │                                      │              InventoryService   │
 * │                                      │                   │             │
 * │                                      │                   ├── Success→ │
 * │                                      │                   │  Notify    │
 * │                                      │                   │             │
 * │                                      └── Fail → PaymentFailed ─────>  │
 * │                                              OrderService (compensate) │
 * │                                              → OrderCancelled         │
 * └────────────────────────────────────────────────────────────────────────┘
 *
 * COMPENSATION FLOW (if inventory fails after payment succeeded):
 *   1. InventoryService fails → publishes "InventoryFailed"
 *   2. PaymentService listens → REFUNDS the payment → publishes "PaymentRefunded"
 *   3. OrderService listens → marks order as CANCELLED
 *   4. NotificationService listens → sends "Order Cancelled" email
 *
 *
 * 19. BACKPRESSURE — CONSUMER OVERLOAD HANDLING
 * ────────────────────────────────────────────────
 * When a consumer cannot keep up with the producer rate.
 *
 * SYMPTOMS:
 *   • Consumer lag keeps increasing (check via: kafka-consumer-groups --describe)
 *   • Processing time > message arrival rate
 *
 * SOLUTIONS:
 *   1. MORE PARTITIONS + MORE CONSUMERS (scale out)
 *   2. BATCH PROCESSING (read 100 events, process in batch)
 *   3. THROTTLE PRODUCER (if possible)
 *   4. PAUSE/RESUME (KafkaConsumer.pause()) — stop reading until caught up
 *   5. AUTO.OFFSET.RESET = "latest" — skip old messages (dangerous!)
 *
 *     Producer rate: 1000 msg/sec
 *     Consumer rate: 500 msg/sec
 *                        ┌──────────────┐
 *     Topic (P0) ──────> │  Lag grows   │  After 60s → 30,000 unprocessed
 *                        │  indefinitely│  Eventually OOM or timeout
 *                        └──────────────┘
 *
 *
 * 20. EVENT SOURCING vs CQRS
 * ──────────────────────────────
 * EVENT SOURCING:
 *   Store all state changes as a sequence of events.
 *   Current state = replay all events from beginning.
 *   • Audit trail built-in (every change is recorded)
 *   • Time travel possible (reconstruct state at any point)
 *   • Complex to implement
 *
 * CQRS (Command Query Responsibility Segregation):
 *   Separate read model from write model.
 *   Commands (writes) go through event bus, Queries (reads) go to read DB.
 *
 *     ┌─────────────┐     Command     ┌──────────────┐
 *     │  User (UI)  │ ──────────────> │  Command Bus │
 *     │             │                 │  (write)     │
 *     │             │                  └──────┬───────┘
 *     │             │                         │
 *     │             │                         v
 *     │             │                  ┌──────────────┐
 *     │             │                  │  Event Store  │
 *     │             │                  │  (source of   │
 *     │             │                  │   truth)      │
 *     │             │                  └──────┬───────┘
 *     │             │                         │
 *     │             │                         v
 *     │             │                  ┌──────────────────┐
 *     │             │ <──────────────│   Query Model     │
 *     │             │    Query      │   (read DB —      │
 *     │             │               │    denormalized)  │
 *     └─────────────┘               └──────────────────┘
 *
 *
 * 21. TRADITIONAL vs EDA — NUMERICAL COMPARISON (E-COMMERCE)
 * ────────────────────────────────────────────────────────────
 *
 * SCENARIO: Flash Sale — 100,000 users trying to buy at same time
 *
 * ┌──────────────────────────┬─────────────────────┬─────────────────────┐
 * │ Metric                   │ Traditional (REST)  │ EDA (Kafka)         │
 * ├──────────────────────────┼─────────────────────┼─────────────────────┤
 * │ Peak throughput          │ ~235 req/sec        │ ~4000 req/sec       │
 * │ Time to complete 100k    │ ~7 minutes          │ ~25 seconds         │
 * │ User response time       │ 850ms               │ 50ms                │
 * │ Error rate (one service  │ ~33% (any 1 down   │ ~0% (queued &       │
 * │   down)                  │   kills all orders) │   processed later)  │
 * │ Servers needed for 100k  │ ~40 instances       │ ~10 instances       │
 * │ Cost                     │ Higher (more infra) │ Lower (efficient)   │
 * └──────────────────────────┴─────────────────────┴─────────────────────┘
 *
 *
 * 22. PERFORMANCE TUNING CHEATSHEET (KAFKA)
 * ─────────────────────────────────────────────
 *
 * HIGH THROUGHPUT (e.g., log ingestion, analytics):
 *   • batch.size = 65536 (64KB)
 *   • linger.ms = 100 (wait 100ms to fill batch)
 *   • compression.type = snappy
 *   • acks = 0 or 1
 *   • Partitions = 3× number of consumers
 *
 * LOW LATENCY (e.g., real-time alerts, payments):
 *   • batch.size = 16384 (16KB)
 *   • linger.ms = 0 (send immediately)
 *   • compression.type = none
 *   • acks = all
 *   • Partitions = 1× number of consumers
 *
 * BALANCED (e-commerce default):
 *   • batch.size = 32768 (32KB)
 *   • linger.ms = 10
 *   • compression.type = lz4
 *   • acks = 1
 *   • Partitions = 2× number of consumers
 *
 *
 * ──────────────────────────────────────────────────────────────────────────
 * SUMMARY:
 * EDA transforms tightly coupled synchronous systems into loosely coupled,
 * resilient, scalable systems where services evolve independently and
 * failures don't cascade. The trade-off is eventual consistency and
 * operational complexity — but for large-scale e-commerce, fintech,
 * IoT, or any distributed system, it's the industry standard.
 *
 * KEY TAKEAWAY: EDA gives ~17x throughput improvement, near-zero cascading
 * failures, independent scaling, and better user experience through async
 * processing — all backed by Kafka's distributed log architecture.
 * ──────────────────────────────────────────────────────────────────────────
 */
public class EDADeepDrive {
}
