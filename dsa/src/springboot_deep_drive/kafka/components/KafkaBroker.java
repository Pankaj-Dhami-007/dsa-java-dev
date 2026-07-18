package springboot_deep_drive.kafka.components;

/**
============================================================
  KAFKA BROKER - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Kafka Broker?
A: A broker is a single Kafka server instance. It stores
   data on disk, handles producer writes, consumer reads,
   and coordinates with other brokers in the cluster.

Q: How many brokers do you need?
A: Minimum 1 (for dev). Production minimum 3 for
   high availability (replication + fault tolerance).

============================================================
  SIMPLE DEFINITION
============================================================

Think of a broker as a WAREHOUSE in a delivery system.

  Producer (Factory) --> Broker (Warehouse) --> Consumer (Customer)

Just like a warehouse:
  - Receives goods (messages) from factories (producers)
  - Stores them on shelves (disk)
  - Ships them to customers (consumers) when requested
  - Multiple warehouses (brokers) = more storage + reliability

============================================================
  ASCII DIAGRAM - Kafka Cluster with Brokers
============================================================

                    KAFKA CLUSTER
    =================================================

                    +-------------------+
                    |   Zookeeper /     |
                    |   KRaft (Quorum)  |
                    +-------------------+
                     |    |    |
          -----------+----+----+-----------
          |           |    |              |
          v           v    v              v
    +----------+ +----------+ +----------+
    | Broker 1 | | Broker 2 | | Broker 3 |
    | PORT:9092| | PORT:9093| | PORT:9094|
    +----------+ +----------+ +----------+
    | Topic-A  | | Topic-A  | | Topic-A  |
    | Part-0   | | Part-1   | | Part-2   |
    | (LEADER) | | (LEADER) | | (LEADER) |
    | Part-1   | | Part-2   | | Part-0   |
    | (FOLLOWER)| | (FOLLOWER)| | (FOLLOWER)|
    +----------+ +----------+ +----------+

    ^                              ^
    |                              |
    v                              v
  Producers                      Consumers
  (write)                        (read)

============================================================
  BROKER RESPONSIBILITIES
============================================================

  1. STORAGE:
     - Stores partitions on disk in log segments
     - Path: /tmp/kafka-logs/{topic}-{partition}/
     - Configurable retention (time or size)

  2. NETWORK HANDLING:
     - Listens on port 9092 (default)
     - Accepts producer write requests
     - Serves consumer read requests
     - Replicates data between brokers

  3. LEADERSHIP:
     - Each broker serves as LEADER for some partitions
     - Each broker serves as FOLLOWER for other partitions
     - Leader handles all reads/writes for its partitions

  4. COORDINATION:
     - Heartbeat with controller broker
     - Reports alive status
     - Participates in leader elections

============================================================
  REAL-LIFE SCENARIO
============================================================

  E-COMMERCE PLATFORM with 3 brokers:

  Scenario: 10,000 orders/minute

  Broker 1:
    - Leader for: orders-p0, payments-p0, notifications-p1
    - Follower for: inventory-p0, shipments-p1

  Broker 2:
    - Leader for: orders-p1, inventory-p0, shipments-p0
    - Follower for: payments-p0, notifications-p0

  Broker 3:
    - Leader for: orders-p2, notifications-p0, shipments-p1
    - Follower for: orders-p0, orders-p1, inventory-p0

  If Broker 1 crashes:
    - Broker 2 or 3 becomes new leader for orders-p0
    - Producers continue writing (no downtime)
    - Consumers continue reading (no interruption)

============================================================
  KEY CONFIGURATIONS
============================================================

  server.properties (per broker):

    broker.id=1                  # Unique ID (0, 1, 2...)
    listeners=PLAINTEXT://:9092  # Port to listen on
    log.dirs=/tmp/kafka-logs     # Data storage location
    num.partitions=3             # Default partitions per topic
    default.replication.factor=3 # Default replication factor
    log.retention.hours=168      # Keep data for 7 days
    log.segment.bytes=1073741824 # 1 GB per segment

============================================================
  BROKER SCALING (How to add more)
============================================================

  1. Configure new broker (broker.id=4, port=9095)
  2. Start broker (connects to cluster automatically)
  3. Move partitions to new broker for load balancing:
     bin/kafka-reassign-partitions.sh

  Result: More throughput, more storage, more reliability.

============================================================
  COMMON INTERVIEW FOLLOW-UP
============================================================

  Q: What happens when a broker goes down?
  A: 1. Controller detects broker failure (misses heartbeats)
     2. Controller reassigns leadership of affected partitions
     3. Followers on other brokers become new leaders
     4. Producers/consumers reconnect to new leaders
     5. If min.insync.replicas not met, producers may get errors

  Q: Can you run Kafka with only 1 broker?
  A: Yes, for development. But replication factor must be 1.
     No fault tolerance. Production needs 3+ brokers.

  Q: What is the ideal number of brokers?
  A: Depends on throughput and retention:
     - 3 brokers: Minimum for HA (can tolerate 1 failure)
     - 5 brokers: Common for medium production
     - 10+ brokers: High throughput (100K+ msgs/sec)

============================================================
*/
public class KafkaBroker {

}
