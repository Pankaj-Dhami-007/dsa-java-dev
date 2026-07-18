package springboot_deep_drive.kafka.components;

/*
============================================================
  ZOOKEEPER vs KRAFT - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What was ZooKeeper in Kafka?
A: ZooKeeper was the external service that managed cluster
   metadata, broker registry, topic configs, and controller
   election. Kafka relied on it for coordination.

Q: What is KRaft?
A: KRaft (Kafka Raft) is the NEW metadata quorum built
   INTO Kafka itself, replacing ZooKeeper. Introduced in
   Kafka 2.8 (experimental), production-ready in 3.3+.

============================================================
  SIMPLE DEFINITION
============================================================

  ZooKeeper = EXTERNAL coordinator (like a separate office).
  KRaft     = INTERNAL coordinator (team leads within Kafka).

  OLD way (ZooKeeper):
    Kafka cluster + Separate ZooKeeper cluster
    2 systems to manage = double the complexity

  NEW way (KRaft):
    Kafka cluster only
    Some brokers act as controllers (voters)
    1 system to manage = simpler

============================================================
  ASCII DIAGRAM - Architecture Comparison
============================================================

  OLD: ZooKeeper-based Kafka

    +---------------------+   +---------------------+
    | ZooKeeper Ensemble  |   | ZooKeeper Ensemble  |
    | (ZK-1, ZK-2, ZK-3)  |   | + Kafka Controller  |
    | Stores:             |   |                     |
    | - Broker metadata   |   | Controller is just  |
    | - Topic configs     |   | ONE of the brokers  |
    | - Controller info   |   |                     |
    | - ACLs, quotas      |   | ZK elects controller|
    +---------------------+   +---------------------+
               |                          |
               v                          v
    +----------+--------+   +------------+--------+
    | Kafka Broker 1    |   | Kafka Broker 2      |
    | + Controller role |   |                     |
    +-------------------+   +---------------------+

    Problem: TWO clusters to manage (ZK + Kafka).
             ZK performance limits Kafka scaling.

  NEW: KRaft-based Kafka

    +---------------------+   +---------------------+
    | KRaft Voters        |   | KRaft Voters        |
    | (Broker 1, 2, 3)    |   |                     |
    | Stores:             |   | Controller is just  |
    | - Metadata log      |   | Raft LEADER among   |
    | - Topic configs     |   | the voters          |
    | - Leader info       |   |                     |
    | - ACLs, quotas      |   | No external system! |
    +---------------------+   +---------------------+
               |                          |
               v                          v
    +----------+--------+   +------------+--------+
    | Kafka Broker 1    |   | Kafka Broker 2      |
    | (Voter + Data)    |   | (Voter + Data)      |
    +-------------------+   +---------------------+

    Benefit: ONE cluster to manage. Simpler, faster.

============================================================
  WHAT ZOOKEEPER DID (Before KRaft)
============================================================

  ZooKeeper stored and managed:

  1. BROKER REGISTRY:
     - Which brokers are alive?
     - Each broker creates ephemeral znode on startup
     - ZK removes it when broker disconnects

  2. CONTROLLER ELECTION:
     - Brokers compete to create /controller znode
     - First one wins = controller
     - If controller fails, ZK deletes ephemeral znode
     - New election triggered

  3. TOPIC CONFIGURATION:
     - Partition assignments
     - Replication factor
     - Topic-level config overrides

  4. ACLs (Access Control):
     - Who can read/write which topics?

  5. QUOTAS:
     - Producer/consumer rate limits

  6. ISR CHANGES:
     - If leader changes, ZK is updated

============================================================
  PROBLEMS WITH ZOOKEEPER
============================================================

  1. TWO SYSTEMS TO MANAGE:
     - Upgrade ZK separately from Kafka
     - ZK has its own configuration
     - ZK has its own scaling limits

  2. SCALING LIMIT:
     - ZK performance degrades with many topics/brokers
     - ZK handles ~10K writes/sec max
     - With 100K+ partitions, ZK becomes bottleneck

  3. CONTROLLER RECOVERY:
     - When controller fails, new controller must load
       ALL metadata from ZK
     - With 100K+ partitions, this takes MINUTES
     - During recovery, leadership changes are blocked

  4. CONSISTENCY ISSUES:
     - ZK and Kafka could have inconsistent views
     - Rare bugs caused metadata inconsistencies

  5. SINGLE POINT OF FAILURE COMPLEXITY:
     - ZK ensemble requires 3 or 5 nodes
     - Each ZK node adds latency

============================================================
  HOW KRAFT FIXES THIS
============================================================

  1. ONE SYSTEM:
     - Kafka manages its own metadata
     - No separate ZK to maintain
     - Same upgrade cycle

  2. SCALABILITY:
     - Metadata stored in Kafka\'s own Raft log
     - Can handle 4M+ partitions (vs ZK\'s 100K limit)
     - Much higher throughput

  3. FAST RECOVERY:
     - New controller loads metadata from Raft log
     - Much faster than loading from ZK
     - Recovery in seconds, not minutes

  4. SINGLE VOTER CHANGE:
     - Add/remove voters dynamically
     - No ZK ensemble to reconfigure

  5. SIMPLER OPERATIONS:
     - No ZK to monitor, backup, or tune
     - One less system in your infrastructure

============================================================
  MIGRATION: ZOOKEEPER TO KRAFT
============================================================

  Kafka 2.8: KRaft introduced as experimental (not prod)
  Kafka 3.3: KRaft production-ready for new clusters
  Kafka 3.5: Migration tool available (ZK to KRaft)
  Kafka 4.0: ZooKeeper REMOVED (KRaft only)

  Migration steps (from ZK to KRaft):

    1. Upgrade Kafka to 3.5+
    2. Run migration tool:
       bin/kafka-storage.sh random-uuid
       bin/kafka-storage.sh format -t <uuid> \
         -c config/kraft/server.properties \
         --release-version 3.5

    3. Start KRaft controllers
    4. Verify metadata migrated
    5. Switch clients to KRaft brokers
    6. Decommission ZK ensemble

============================================================
  KRAFT CONFIGURATION
============================================================

  server.properties (KRaft mode):

    # Role: broker only, controller only, or both
    process.roles=broker,controller

    # Node ID (unique per node, used in Raft voting)
    node.id=1

    # Quorum voters: {node-id}@{host}:{port}
    controller.quorum.voters=1@broker1:9093,\
                             2@broker2:9093,\
                             3@broker3:9093

    # Listeners
    listeners=PLAINTEXT://:9092
    controller.listener.names=CONTROLLER
    listener.security.protocol.map=\
      CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT

    # Metadata log directory
    metadata.log.dir=/tmp/kraft-metadata-logs

  Number of controller voters:
    - 1 node: OK for dev (no fault tolerance)
    - 3 nodes: Minimum for production
    - 5 nodes: Recommended for large clusters

============================================================
  KRAFT ARCHITECTURE (Dedicated vs Combined)
============================================================

  Option 1: Combined (process.roles=broker,controller)
    - Same nodes serve data AND vote on metadata
    - Simpler infrastructure (fewer nodes)
    - Risk: High load on data serving may slow metadata votes
    - Good for: Small to medium clusters

  Option 2: Dedicated (process.roles=controller)
    - Separate nodes for voting only (no data serving)
    - More nodes, but metadata path is isolated
    - Good for: Large clusters (100K+ partitions)

  Option 3: Mixed
    - 3 dedicated controllers + N data-only brokers
    - Best for production at scale

============================================================
  COMPARISON TABLE
============================================================

  +---------------------+----------------------------+----------------------------+
  | Feature             | ZooKeeper (Legacy)         | KRaft (Modern)             |
  +---------------------+----------------------------+----------------------------+
  | External dependency | YES (separate ZK cluster)  | NO (built into Kafka)      |
  +---------------------+----------------------------+----------------------------+
  | Management          | Manage 2 clusters          | Manage 1 cluster           |
  +---------------------+----------------------------+----------------------------+
  | Partition limit     | ~100K partitions           | 4M+ partitions             |
  +---------------------+----------------------------+----------------------------+
  | Recovery time       | Minutes (large clusters)   | Seconds                    |
  +---------------------+----------------------------+----------------------------+
  | Single point of     | ZK ensemble (complex)      | Raft quorum (simple)       |
  | failure?            |                            |                            |
  +---------------------+----------------------------+----------------------------+
  | Kafka 4.0 support   | NO (removed)               | YES (only option)          |
  +---------------------+----------------------------+----------------------------+
  | Setup complexity    | High (ZK + Kafka)          | Low (Kafka only)           |
  +---------------------+----------------------------+----------------------------+
  | Production ready    | YES (stable, mature)       | YES (3.3+, stable)         |
  +---------------------+----------------------------+----------------------------+
  | Recommendation      | Legacy only (migrating)    | All NEW deployments        |
  +---------------------+----------------------------+----------------------------+

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Do I need ZooKeeper for Kafka 4.0?
  A: NO. ZooKeeper was removed in Kafka 4.0.
     KRaft is the only option.

  Q: Can I run Kafka without ZooKeeper in 2.8?
  A: Experimental only. Not production-ready until 3.3.

  Q: How many KRaft voters do I need?
  A: 3 for production. N for N-node fault tolerance.
     Quorum = (N/2 + 1). 3 voters tolerate 1 failure.

  Q: Migration from ZK to KRaft - downtime?
  A: No downtime with Kafka 3.5+ migration tools.
     Rolling upgrade, clients unaffected.

  Q: Is KRaft faster than ZooKeeper?
  A: For metadata operations, yes. Controller recovery
     is much faster. No external RPC latency.

============================================================
*/
public class KafkaZookeeperVsKRaft {

}
