package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA REPLICATION - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is Replication in Kafka?
A: Replication is copying partition data across multiple
   brokers for fault tolerance. If one broker dies,
   another has a copy and takes over without data loss.

Q: How does replication work?
A: Each partition has a LEADER and multiple FOLLOWERS.
   Leader handles all reads/writes. Followers replicate
   data from the leader. If leader fails, a follower
   becomes the new leader.

============================================================
  SIMPLE DEFINITION
============================================================

  Replication = BACKUP copies of your data.

  Imagine you have an important document:
    - Original on your LAPTOP (Leader)
    - Photocopy at OFFICE (Follower 1)
    - Photocopy at HOME (Follower 2)

  If laptop is stolen, you use the office copy.
  If office burns down, you use the home copy.

  In Kafka:
    - Partition data is replicated to N brokers
    - N = replication factor (configurable)
    - If one broker fails, another has the data

============================================================
  ASCII DIAGRAM - Replication Factor 3
============================================================

    Topic: orders, Partition: 0
    Replication Factor: 3

    Broker 1                    Broker 2
    +-----------------------+   +-----------------------+
    | orders-p0 (LEADER)   |   | orders-p0 (FOLLOWER)   |
    | Offset 0: Order-1    |   | Offset 0: Order-1    |
    | Offset 1: Order-2    |   | Offset 1: Order-2    |
    | Offset 2: Order-3    |   | Offset 2: Order-3    |
    +-----------------------+   +-----------------------+
                |                           |
                |  replication               |  replication
                |  (sync)                    |  (sync)
                v                           v
    +-----------------------+
    | orders-p0 (FOLLOWER)  |
    | Offset 0: Order-1     |
    | Offset 1: Order-2     |
    | Offset 2: Order-3     |
    +-----------------------+
    Broker 3

    All writes go to LEADER (Broker 1).
    Followers (Broker 2, 3) pull data from leader.

============================================================
  REPLICATION FLOW (When producer writes)
============================================================

    Producer sends to orders-p0 (Leader = Broker 1)

    Step 1: Producer sends to Broker 1 (leader)
    Step 2: Leader writes to local log (Offset = 3)
    Step 3: Leader sends data to all followers
    Step 4a: Follower 2 acknowledges (ISR = in-sync)
    Step 4b: Follower 3 acknowledges (ISR = in-sync)
    Step 5: Leader checks acks config:
            acks=0 -> respond immediately (no wait)
            acks=1 -> respond after step 2 (leader wrote)
            acks=all -> respond after step 4 (all ISR)
    Step 6: Producer gets response with offset

============================================================
  ISR (In-Sync Replicas) - VERY IMPORTANT
============================================================

  ISR = replicas that are FULLY CAUGHT UP with leader.

  A replica is in ISR if:
    - It has replicated ALL messages from leader
    - It has not fallen behind (replica.lag.time.max.ms)

  What happens if a follower is slow?
    - It is REMOVED from ISR
    - Leader continues without it
    - When follower catches up, it re-enters ISR

  min.insync.replicas:
    - Minimum number of ISR replicas for write to succeed
    - Example: replication.factor=3, min.insync.replicas=2
    - If only 1 replica is in ISR, producer gets error
    - Trade-off: availability vs consistency

============================================================
  REAL-LIFE SCENARIO - Broker Failure
============================================================

    Initial state:
      orders-p0: Leader = Broker 1, Followers = Broker 2, 3
      ISR = [Broker 1, Broker 2, Broker 3]

    Event: Broker 1 (leader) crashes!

    Step 1: Controller detects Broker 1 is down
            (misses 3 heartbeats = 30 seconds)

    Step 2: Controller elects new leader from ISR:
            Broker 2 becomes new leader for orders-p0

    Step 3: Metadata updated:
            All producers/consumers get new leader metadata

    Step 4: Producers resume writing to Broker 2
            Consumers resume reading from Broker 2

    Step 5: Broker 1 comes back!
            It becomes a FOLLOWER (not leader anymore)
            It catches up by replicating from new leader

    DOWNTIME: None. Only slight delay for leader election.

============================================================
  REPLICATION FACTOR GUIDELINES
============================================================

  +-------------------+---------------------------+----------------------+
  | Replication Factor| Brokers Needed            | Tolerates            |
  +-------------------+---------------------------+----------------------+
  | 1                 | 1                         | 0 failures           |
  |                   | (dev only!)               |                      |
  +-------------------+---------------------------+----------------------+
  | 2                 | 2+                        | 1 failure            |
  |                   |                           | (if 1 ISR available) |
  +-------------------+---------------------------+----------------------+
  | 3                 | 3+                        | 1 failure            |
  |                   | (RECOMMENDED)             | (2 ISR = safe)       |
  +-------------------+---------------------------+----------------------+
  | 4+                | 5+                        | 2-3 failures         |
  |                   | (expensive, rare)         |                      |
  +-------------------+---------------------------+----------------------+

  DEFAULT: replication.factor = 1 (BAD for production)
  PRODUCTION: replication.factor = 3, min.insync.replicas = 2

============================================================
  REPLICATION VS BACKUP
============================================================

  Replication is NOT backup.

    Replication:
      - Real-time sync between brokers
      - Protects against broker failure
      - Does NOT protect against:
        * Accidental deletion of topic
        * Corrupted data (if leader writes bad data, all ISR get it)
        * Ransomware (if attacker deletes on one, all ISR deleted)

    Backup:
      - Periodic copy to external storage (S3, HDFS)
      - Protects against ALL disasters
      - Slower, not real-time
      - Recommended: Use Kafka Connect S3 Sink + Replication

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: What is the difference between replication factor 2 and 3?
  A: RF=2 can tolerate 1 broker failure IF the remaining
     replica is in ISR. But if that ISR replica fails before
     the down broker comes back, data is lost.
     RF=3 is safer. Can tolerate 1 failure and still have
     an ISR copy.

  Q: Does replication slow down producers?
  A: Only if acks=all. Producer waits for ALL ISR to
     acknowledge. Trade-off: durability vs latency.
     acks=1 gives moderate durability with low latency.

  Q: Can a follower be a leader?
  A: Yes. Any ISR follower can be elected as leader
     by the controller when current leader fails.

  Q: What is unclean leader election?
  A: If NO ISR replica is available, Kafka can elect
     a non-ISR replica as leader (data may be out of sync).
     Controlled by: unclean.leader.election.enable=false
     DISABLE in production to prevent data loss.

============================================================
*/
public class KafkaReplication {

}
