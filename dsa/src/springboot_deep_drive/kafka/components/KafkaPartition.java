package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA PARTITION - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Partition?
A: A partition is a division of a topic into ordered,
   immutable sequences of messages. Partitions enable
   PARALLELISM and SCALABILITY.

Q: Why do topics need partitions?
A: Without partitions, a topic lives on ONE broker and
   ONE consumer reads it. With partitions, a topic
   SPREADS across multiple brokers and MULTIPLE consumers
   read in parallel.

============================================================
  SIMPLE DEFINITION
============================================================

  A topic is like a BOOK.
  A partition is like a VOLUME of that book.

  ORDERS topic (the full book):
    Volume 1 (Partition 0): Orders from users A-M
    Volume 2 (Partition 1): Orders from users N-Z
    Volume 3 (Partition 2): Orders from mobile app

  KEY INSIGHT:
  - Messages within a PARTITION are ordered (guaranteed)
  - Messages across PARTITIONS have no order guarantee
  - Same KEY always goes to SAME partition

============================================================
  ASCII DIAGRAM - Topic with Partitions
============================================================

                     ORDERS TOPIC

    Partition 0 (on Broker 1):
    +------+------+------+------+------+------+
    | Msg1 | Msg2 | Msg3 | Msg4 | Msg5 | Msg6 |  --> ORDERED
    |Key=A |Key=A |Key=B |Key=A |Key=B |Key=A |
    +------+------+------+------+------+------+
    Offset: 0      1      2      3      4      5

    Partition 1 (on Broker 2):
    +------+------+------+------+
    | Msg7 | Msg8 | Msg9 | Msg10|
    |Key=C |Key=D |Key=C |Key=D |
    +------+------+------+------+
    Offset: 0      1      2      3

    Partition 2 (on Broker 3):
    +------+------+------+
    | Msg11| Msg12| Msg13|
    |Key=E |Key=E |Key=F |
    +------+------+------+
    Offset: 0      1      2

============================================================
  HOW PARTITIONING WORKS
============================================================

  Producer sends: ("order-123", "Order data")

    Step 1: hash("order-123") = 987654321
    Step 2: 987654321 % 3 = 0 (partition 0)
    Step 3: Message goes to partition 0
    Step 4: All messages with key "order-123" go to
            partition 0 = ORDER GUARANTEED

  WITHOUT KEY (null key):
    Messages distributed round-robin = NO order guarantee

============================================================
  REAL-LIFE SCENARIO - Partition Strategy
============================================================

  Scenario: Ride-hailing app (Uber/Ola)

  Topic: rider-locations

  Option A: rider_id as key (RECOMMENDED)
    - All updates for rider-123 go to SAME partition
    - Consumer can track exact path (order preserved)

  Option B: null key (BAD)
    - Rider-123 updates go to random partitions
    - OUT OF ORDER location data - cannot track path

  Option C: city as key (RISKY)
    - NYC riders -> Partition 0, LON riders -> Partition 1
    - NYC may have 100x more riders than LON
    - DATA SKEW (one partition overwhelmed)

============================================================
  HOW MANY PARTITIONS?
============================================================

  RULE: partitions >= max(consumers in any group)

  5 consumers, 3 partitions -> 2 consumers are IDLE
  3 consumers, 5 partitions -> balanced load

  Guideline:
    - Start with 3-6 partitions per topic
    - Max recommended: 1000 partitions per broker
    - Too many = high metadata overhead

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Can you add partitions after creation?
  A: Yes. But existing messages stay in old partitions.
     Key routing changes (hash % new count).
     Order BREAKS across the change boundary.

  Q: Can you decrease partitions?
  A: No. Kafka does not support partition reduction.

============================================================
*/
public class KafkaPartition {

}
