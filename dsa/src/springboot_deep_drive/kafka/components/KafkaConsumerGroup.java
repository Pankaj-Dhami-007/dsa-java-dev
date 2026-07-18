package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA CONSUMER GROUP - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Consumer Group?
A: A consumer group is a set of consumers that SHARE
   the work of reading from a topic. Each partition
   is assigned to ONE consumer in the group.

Q: Why use consumer groups?
A: For SCALABILITY. If one consumer processes 1000 msgs/sec
   and you need 10,000 msgs/sec, add 9 more consumers
   in the same group. Work is distributed automatically.

============================================================
  SIMPLE DEFINITION
============================================================

  Consumer Group = TEAM of workers.

  Imagine a team of workers unloading boxes from trucks:

    - Team A (group A): 3 workers, each unloads 1 truck
    - 3 trucks can be unloaded simultaneously

    - Team B (group B): Different team, unloads SAME trucks
      again for quality check

  In Kafka:
    - Topic has partitions (trucks)
    - Consumer group has consumers (workers)
    - Each partition assigned to ONE consumer in group
    - Multiple groups can read same topic independently

============================================================
  ASCII DIAGRAM - Consumer Group Distribution
============================================================

    Topic: orders (4 partitions)

    Partition-0  Partition-1  Partition-2  Partition-3
        |            |            |            |
        |            |            |            |
        +-------+----+       +----+-------+
                |                  |
                v                  v
        +----------------+ +----------------+
        |  Consumer A    | |  Consumer B    |
        |  (Group: g1)   | |  (Group: g1)   |
        |  Partitions:   | |  Partitions:   |
        |  0, 1          | |  2, 3          |
        +----------------+ +----------------+

    Another consumer group reads same data independently:

    Partition-0  Partition-1  Partition-2  Partition-3
        |            |            |            |
        |            |            |            |
        v            v            v            v
    +--------+  +--------+  +--------+  +--------+
    | Cons-X |  | Cons-Y |  | Cons-Z |  | Cons-W |
    | Group: |  | Group: |  | Group: |  | Group: |
    | "audit"|  | "audit"|  | "audit"|  | "audit"|
    +--------+  +--------+  +--------+  +--------+

    Group "audit" has 4 consumers = one per partition.

============================================================
  PARTITION ASSIGNMENT STRATEGIES
============================================================

  When a consumer joins/leaves, partitions are reassigned.
  Strategy = HOW partitions are distributed.

  RangeAssignor (DEFAULT):
    Assigns contiguous ranges per topic.
    Topic A (0,1,2) -> Consumer A, Topic A (3,4,5) -> Con B
    Problem: May cause uneven distribution with many topics.

  RoundRobinAssignor:
    Distributes partitions round-robin across consumers.
    More balanced than Range.
    Example: P0->A, P1->B, P2->C, P3->A, P4->B...

  StickyAssignor:
    Like RoundRobin but MINIMIZES rebalance changes.
    When rebalance happens, keeps as many assignments as
    possible. Less disruption.

  CooperativeStickyAssignor (RECOMMENDED):
    Uses COOPERATIVE rebalance (not eager).
    Consumers continue working during rebalance.
    Partitions revoked one by one (not all at once).

============================================================
  REBALANCE - What happens when consumer joins/leaves
============================================================

  EAGER REBALANCE (old, default with Range/RoundRobin):
    1. All consumers STOP consuming
    2. All consumers REVOKE all partitions
    3. Group coordinator reassigns all partitions
    4. All consumers resume with new assignments
    Problem: FULL STOP during rebalance (Stop-The-World)

  COOPERATIVE REBALANCE (with StickyAssignor):
    1. Only excess partitions are revoked
    2. Remaining consumers continue working
    3. Only revoked partitions are reassigned
    4. Much smoother, minimal disruption

  Triggered when:
    - Consumer joins the group
    - Consumer leaves (crash/close)
    - Partition count changes
    - Topic is deleted

============================================================
  REAL-LIFE SCENARIO - Scaling Notification System
============================================================

  Initial setup:
    Topic "notifications" has 3 partitions
    1 consumer instance -> processes ALL notifications
    Processing rate: 1000/sec

  Problem: Traffic grows to 5000/sec
    Consumer is overwhelmed -> LAG grows
    LAG = 1,000,000 messages (30 min delay!)

  Solution: Scale consumers
    Add 2 more consumer instances (total 3)
    Each now handles ONE partition
    Processing rate: 3000/sec

  Problem: Still not enough (need 5000/sec)
    Solution: Increase partitions to 6, add 6 consumers
    Wait... you CANNOT increase throughput beyond #partitions!

  CORRECT SOLUTION:
    1. First, increase partitions to 6 (or more)
    2. Then, add up to 6 consumers
       Each consumer handles one partition
       Processing rate: 6000/sec

============================================================
  IMPORTANT RULE (Interview favorite)
============================================================

  MAX CONSUMERS IN A GROUP = NUMBER OF PARTITIONS

  If topic has 3 partitions:
    - 3 consumers -> each gets 1 partition (ideal)
    - 5 consumers -> 2 consumers are IDLE (wasteful)
    - 2 consumers -> 1 consumer gets 2 partitions (ok)

  To increase parallelism:
    1. Add partitions to the topic
    2. Add more consumers to the group

============================================================
  MULTIPLE CONSUMER GROUPS (Pub-Sub Pattern)
============================================================

    +---------------------------------------------+
    |              ORDERS TOPIC                     |
    +---------------------------------------------+
         |           |           |
         v           v           v
    +---------+ +---------+ +---------+
    | Group:  | | Group:  | | Group:  |
    | "email" | | "audit" | | "fraud" |
    | (2 cons)| | (1 cons)| | (2 cons)|
    +---------+ +---------+ +---------+
         |           |           |
         v           v           v
    Email Svc   Audit DB    Fraud Check

  Each group gets ALL messages independently.
  Group "email" reads at its own pace.
  Group "audit" reads at its own pace.
  Groups do NOT interfere with each other.

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Can two consumers in same group read same partition?
  A: NO. Each partition belongs to ONE consumer in group.
     This guarantees order within the partition.

  Q: What if consumer count > partition count?
  A: Extra consumers are IDLE. They sit idle until a
     partition becomes free (rebalance).

  Q: What happens when a consumer crashes?
  A: 1. Group coordinator detects missed heartbeats
     2. Triggers rebalance
     3. Partitions reassigned to remaining consumers
     4. Consumer resumes reading from last committed offset

  Q: Can you add consumers without downtime?
  A: Yes. New consumer joins -> rebalance triggers.
     With CooperativeStickyAssignor, minimal disruption.

  Q: What is the ideal consumer count?
  A: Equal to number of partitions. Not more, not less.

============================================================
*/
public class KafkaConsumerGroup {

}
