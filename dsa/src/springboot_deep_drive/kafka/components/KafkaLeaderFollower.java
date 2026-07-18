package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA LEADER & FOLLOWER - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Leader in Kafka?
A: For each partition, ONE broker is the leader. The leader
   handles ALL read and write requests for that partition.
   All other replicas of that partition are FOLLOWERS.

Q: Why have leader/follower split?
A: For CONSISTENCY. All reads/writes go through single
   leader = no conflicts. Followers just replicate data
   passively. If leader fails, a follower takes over.

============================================================
  SIMPLE DEFINITION
============================================================

  Leader = MANAGER of a team.
  Follower = TEAM MEMBER who shadows the manager.

  The manager (leader):
    - Makes all decisions (processes all requests)
    - Has the latest information (all data)
    - Everyone talks to the manager

  The team members (followers):
    - Copy everything the manager does
    - Ready to become manager if current one leaves
    - Stay in sync with manager

============================================================
  ASCII DIAGRAM - Leader/Follower for Partitions
============================================================

    Topic: notifications (3 partitions, RF=3, 3 brokers)

    +------------------+------------------+------------------+
    | Broker 1         | Broker 2         | Broker 3         |
    +------------------+------------------+------------------+
    | notif-p0 LEADER  | notif-p0 FOLLOWER| notif-p0 FOLLOWER|
    | notif-p1 FOLLOWER| notif-p1 LEADER  | notif-p1 FOLLOWER|
    | notif-p2 FOLLOWER| notif-p2 FOLLOWER| notif-p2 LEADER  |
    +------------------+------------------+------------------+

    All reads/writes for notif-p0 -> Broker 1
    All reads/writes for notif-p1 -> Broker 2
    All reads/writes for notif-p2 -> Broker 3

    This distributes LOAD across all 3 brokers.
    Each broker is leader for SOME partitions.
    Each broker is follower for OTHER partitions.

============================================================
  LEADER ELECTION PROCESS
============================================================

  When does leader election happen?
    - Current leader broker crashes
    - Current leader is demoted (out of ISR)
    - Controlled shutdown of leader broker
    - Admin triggers preferred leader election

  ELECTION STEPS:

    1. Controller detects leader failure
       (misses 3 consecutive heartbeats = ~30 sec)

    2. Controller checks ISR (In-Sync Replicas) list
       ISR = replicas that are fully caught up

    3. Controller picks new leader from ISR
       Priority: First replica in ISR list (usually)

    4. Controller sends LeaderAndIsr request to
       the new leader and all followers

    5. New leader starts accepting reads/writes
       Followers start replicating from new leader

  What if NO ISR replica is available?
    - unclean.leader.election.enable=false (PRODUCTION)
      -> Partition becomes UNAVAILABLE until old leader is back
      -> Better than data loss
    - unclean.leader.election.enable=true (DANGEROUS)
      -> Pick any replica (may have outdated data)
      -> Data INCONSISTENCY possible

============================================================
  PREFERRED LEADER
============================================================

  When a topic is created, partition leaders are distributed
  round-robin across brokers. This is the "preferred leader."

  After a failure and re-election, the new leader may be
  on a different broker than the preferred one.

  This can cause IMBALANCE (one broker handling more load).

  Solution: Auto leader rebalance
    auto.leader.rebalance.enable=true
    Controller periodically checks if preferred leaders
    are actual leaders. If not, triggers preferred leader
    election -> moves leadership back to preferred brokers.

============================================================
  REAL-LIFE SCENARIO - Leader Failover
============================================================

  Setup:
    Topic: payments (3 partitions, RF=3)
    Broker 1: leader for p0, follower for p1, p2
    Broker 2: leader for p1, follower for p0, p2
    Broker 3: leader for p2, follower for p0, p1

  Event: Broker 1 crashes

    Impact on payments-p0:
      - Leader (Broker 1) is down
      - Followers (Broker 2, 3) are in ISR
      - Controller elects Broker 2 as new leader
      - Downtime: ~5-10 seconds (detection + election)
      - No data loss (both followers in ISR)

    Impact on Broker 1 as follower for p1, p2:
      - p1 leader (Broker 2): Loses one follower
      - p2 leader (Broker 3): Loses one follower
      - No impact on reads/writes (leader still up)
      - min.insync.replicas may be violated if set to 3

  Recovery:
    Broker 1 restarts -> rejoins as follower for p0, p1, p2
    Auto leader rebalance may move p0 back to Broker 1
    (if configured)

============================================================
  LEADER VS FOLLOWER RESPONSIBILITIES
============================================================

  +------------------+------------------------------------------+
  | LEADER           | - Handles ALL produce requests           |
  |                  | - Handles ALL fetch requests (consumers)  |
  |                  | - Appends to local log                   |
  |                  | - Sends data to followers                |
  |                  | - Tracks ISR list                        |
  |                  | - Sends periodic leader epoch to ISR     |
  +------------------+------------------------------------------+
  | FOLLOWER         | - Sends FetchRequest to leader           |
  |                  | - Replicates data to local log           |
  |                  | - Stays in ISR (must not lag behind)     |
  |                  | - Does NOT serve client requests         |
  |                  | - Becomes leader if elected              |
  +------------------+------------------------------------------+

============================================================
  LEADER EPOCH
============================================================

  Each leader change increments the LEADER EPOCH.
  This helps detect stale data.

  Example:
    Epoch 0: Broker 1 is leader
    Epoch 1: Broker 2 elected leader (Broker 1 failed)
    Epoch 2: Broker 3 elected leader (Broker 2 failed)

  If Broker 1 comes back with Epoch 0 data:
    - New leader (epoch 2) knows Broker 1 is stale
    - Broker 1 must truncate to match epoch 2

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Can a broker be leader for one partition and follower
     for another?
  A: YES. This is normal. Each broker is typically leader
     for some partitions and follower for others to
     distribute load.

  Q: What determines which broker becomes leader?
  A: The CONTROLLER picks the new leader from ISR.
     The first replica in ISR list usually gets picked.
     Preferred leader is the original leader when topic
     was created.

  Q: How long does leader election take?
  A: Typically 5-30 seconds. Controlled by:
     zookeeper.session.timeout.ms (ZK) or
     controller.quorum.election.timeout.ms (KRaft)

  Q: Can a follower serve read requests?
  A: No, by default. But Kafka 2.4+ allows follower
     replication for read requests (rack-aware reads).
     Not commonly used.

============================================================
*/
public class KafkaLeaderFollower {

}
