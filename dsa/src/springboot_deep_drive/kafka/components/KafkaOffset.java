package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA OFFSET - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is an Offset?
A: An offset is a unique, ever-increasing integer that
   identifies each message WITHIN a partition. It is like
   a BOOKMARK that tells the consumer "you have read up to
   position X, next time start from X+1."

Q: How does offset help in reprocessing?
A: A consumer can RESET its offset to any earlier position
   and REPLAY all messages. This is critical for error
   recovery and data reprocessing.

============================================================
  SIMPLE DEFINITION
============================================================

  Offset = PAGE NUMBER in a book.

  Imagine a BOOK (partition) with numbered pages:
    Page 0: Chapter 1
    Page 1: Chapter 2
    Page 2: Chapter 3

  You place a BOOKMARK at page 2 (current offset).
  Next time you read, you start from page 3.
  You can move the bookmark BACK to page 0 to re-read.

  In Kafka:
    - Offset 0 = first message in this partition
    - Offset increases by 1 for each new message
    - Consumer commits offset = "processed up to here"
    - If consumer crashes, restarts from last committed offset

============================================================
  ASCII DIAGRAM - Offsets in Partition
============================================================

    Partition 0 of "orders" topic:
    +----------+----------+----------+----------+----------+
    | Offset 0 | Offset 1 | Offset 2 | Offset 3 | Offset 4 |
    | Order-1  | Order-2  | Order-3  | Order-4  | Order-5  |
    +----------+----------+----------+----------+----------+
         ^                          ^
         |                          |
    Consumer A\\'"'"'s                Consumer A\\'"'"'s
    CURRENT offset              COMMITTED offset
    = 2 (reading msg 2)         = 1 (saved in Kafka)

    If Consumer A crashes now,
    restarts from offset 1+1 = 2.
    Re-reads msgs 2, 3, 4, 5.

============================================================
  HOW OFFSET COMMIT WORKS
============================================================

  Consumer commits offset to INTERNAL TOPIC:
    __consumer_offsets

  AUTO COMMIT (every 5s by default):
    Time 0s: Consume msg 0, 1, 2, 3, 4
    Time 5s: Auto-commit offset = 5
    Time 6s: Consume msg 5, 6, 7
    Time 7s: CRASH (before auto-commit)
    Time 8s: Restart -> starts from offset 5
             Re-reads msgs 5, 6, 7 (DUPLICATES!)

  MANUAL COMMIT (production):
    Consume msg 0 -> process -> commit 0
    Consume msg 1 -> process -> commit 1
    Consume msg 2 -> CRASH (no commit)
    Restart -> starts from offset 1
    No duplication of msg 0, 1

============================================================
  REAL-LIFE SCENARIO - Fraud Detection Recovery
============================================================

  Consumer processes payment events with offset.

  Normal:
    Offset 1000 -> Processed -> Committed 1000
    Offset 1001 -> Processed -> Committed 1001

  Bug discovered: "Between offsets 950-980, fraud undetected"

  Recovery:
    1. Fix the bug
    2. Deploy updated consumer
    3. RESET offset to 950
    4. Consumer re-reads 950-1000
    5. Fraud detection re-runs correctly

  THIS IS ONLY POSSIBLE WITH KAFKA.
  RabbitMQ cannot do this (messages deleted after consume).

============================================================
  OFFSET COMMANDS
============================================================

  # Get current offset / lag for consumer group
  bin/kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group my-group --describe

  Output:
    TOPIC  PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG
    orders 0          50              100             50

  LAG = LOG-END-OFFSET - CURRENT-OFFSET
  High lag = consumer is falling behind.

  # Reset offset to beginning (reprocess all)
  bin/kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group my-group --topic orders \
    --reset-offsets --to-earliest --execute

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Where does Kafka store committed offsets?
  A: In an internal topic called __consumer_offsets.
     It has 50 partitions by default.

  Q: Can two consumer groups have different offsets?
  A: Yes. Each CONSUMER GROUP has its OWN offset.
     Group A at offset 100, Group B at offset 0.
     Both read the same partition independently.

  Q: What is offset LAG?
  A: Difference between last message (LOG-END-OFFSET)
     and consumer current offset. High lag = falling behind.

  Q: How to reduce lag?
  A: Add more consumer instances (increase partitions)
     or optimize processing logic.

============================================================
*/
public class KafkaOffset {

}
