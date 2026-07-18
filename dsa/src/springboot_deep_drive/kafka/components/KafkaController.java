package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA CONTROLLER - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is the Controller in Kafka?
A: The controller is ONE special broker in the cluster
   responsible for managing PARTITION LEADERSHIP and
   handling broker failures. It is the "cluster manager."

Q: How is the controller elected?
A: The first broker to register with ZooKeeper/KRaft
   becomes the controller. If it fails, another broker
   is elected automatically.

============================================================
  SIMPLE DEFINITION
============================================================

  Controller = TEAM LEAD of the broker team.

  The team lead does NOT do the actual work (serving data).
  Instead, they decide WHO does what.

  Controller responsibilities:
    - Who is the leader for each partition?
    - What happens when a broker crashes?
    - What happens when a new broker joins?
    - Topic creation/deletion

  The actual data serving is done by ALL brokers.

============================================================
  CONTROLLER RESPONSIBILITIES
============================================================

  1. LEADER ELECTION:
     - When a broker fails, controller decides which
       follower becomes the new leader for each partition
     - Ensures min.insync.replicas is maintained

  2. PARTITION LEADERSHIP:
     - Tracks which broker is leader for each partition
     - Updates metadata when leadership changes

  3. TOPIC MANAGEMENT:
     - Handles topic creation (partition assignment)
     - Handles topic deletion
     - Handles partition reassignment

  4. BROKER WATCH:
     - Monitors broker heartbeats
     - Detects broker failures
     - Triggers rebalance when needed

  5. METADATA MANAGEMENT:
     - Maintains cluster metadata
     - Sends metadata updates to all brokers
     - Brokers cache this metadata locally

============================================================
  CONTROLLER ELECTION PROCESS
============================================================

  In Zookeeper-based Kafka:

    1. All brokers try to create an EPHEMERAL node:
       /controller

    2. The FIRST broker to create it becomes the CONTROLLER

    3. Other brokers become "regular" brokers

    4. Controller sends periodic heartbeats to Zookeeper
       (by keeping the ephemeral node alive)

    5. If controller broker CRASHES:
       - Zookeeper detects ephemeral node deletion
       - All brokers try to create the node again
       - First one succeeds -> NEW CONTROLLER

  In KRaft-based Kafka (Kafka 3.x+):

    1. Some brokers are designated as CONTROLLERS
       (configured in process.roles)

    2. Controllers form a Raft QUORUM (voting group)

    3. They elect a LEADER among themselves using Raft

    4. The Raft leader acts as the Kafka controller

    5. No Zookeeper needed!

============================================================
  ASCII DIAGRAM - Controller in Action
============================================================

              +-------------------+
              |   ZOOKEEPER /     |
              |   KRaft QUORUM    |
              +-------------------+
                    |    |
          +---------+    +---------+
          |                        |
          v                        v
    +----------+          +------------------+
    | Controller|          | Other Brokers     |
    | (Broker 1)|--------->| (Broker 2, 3, 4, 5)|
    |           |  watches |                  |
    +----------+          +------------------+
          |
          | Manages:
          | - Partition leadership
          | - Broker failures
          | - Topic creation
          |
          v
    Internal Controller State:
    +----------------------------------------+
    | Partitions managed by controller:      |
    | orders-p0  -> Leader: Broker 2         |
    | orders-p1  -> Leader: Broker 3         |
    | payments-p0 -> Leader: Broker 1        |
    | payments-p1 -> Leader: Broker 2        |
    | users-p0   -> Leader: Broker 3         |
    +----------------------------------------+

============================================================
  WHAT HAPPENS WHEN CONTROLLER FAILS?
============================================================

    Before failure:
      Broker 1 = Controller

    Event: Broker 1 crashes

    Step 1: ZooKeeper/KRaft detects loss of ephemeral node
    Step 2: New controller election begins
    Step 3: Broker 3 wins (first to create ephemeral node)
    Step 4: New controller (Broker 3) loads all metadata
            from ZK/KRaft
    Step 5: New controller sends metadata to all brokers
    Step 6: Normal operation resumes

    Key insight: While controller is recovering:
      - Existing leaders CONTINUE serving reads/writes
      - But NO leader changes can happen
      - If a broker fails during this time, its partitions
        become unavailable until new controller is ready

============================================================
  REAL-LIFE SCENARIO - Controller Managing Failure
============================================================

  Initial state:
    =============================================
    Cluster: 5 brokers
    Controller: Broker 3
    Partitions: 100
    =============================================

  Event: Broker 1 fails (was leader for 20 partitions)

  Controller (Broker 3) actions:
    1. Detects Broker 1 heartbeat timeout
    2. Queries metadata: which partitions had Broker 1 as leader?
    3. For each of the 20 partitions:
       a. Check ISR list
       b. Pick first ISR replica as new leader
       c. Send LeaderAndIsrRequest to new leader
       d. Send metadata update to ALL brokers
    4. Updates internal state
    5. If preferred leader rebalance enabled, queues
       leader movement for later

  Time taken: ~5-10 seconds for 20 partitions
  During this time:
    - 20 partitions were briefly unavailable
    - Other 80 partitions continued normally

============================================================
  CONTROLLER CONFIGURATIONS
============================================================

  server.properties (all brokers - not just controller):

    # Zookeeper connection (if using ZK-based)
    zookeeper.connect=localhost:2181

    # KRaft mode (Kafka 3.x+)
    process.roles=broker,controller
    controller.quorum.voters=1@broker1:9093,\
                             2@broker2:9093,\
                             3@broker3:9093

    # Controller settings
    controller.quorum.election.timeout.ms=1000
    controller.quorum.fetch.timeout.ms=2000

  These are usually default. Tune only if needed.

============================================================
  CONTROLLER VS REGULAR BROKER
============================================================

  +------------------+---------------------------+---------------------------+
  | Role             | Controller                | Regular Broker            |
  +------------------+---------------------------+---------------------------+
  | Serves data      | YES (also regular broker) | YES                       |
  | Manages leaders  | YES                       | NO                        |
  | Handles failures | YES                       | NO                        |
  | Topic creation   | YES                       | NO (asks controller)      |
  | Metadata updates | YES (sends to all)        | NO (receives from ctrlr)  |
  +------------------+---------------------------+---------------------------+

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Is the controller a separate process?
  A: No. The controller is just one of the brokers that
     also handles controller duties. It serves data too.

  Q: What happens if controller is overloaded?
  A: Multiple controller failures may cascade. Rare.
     In KRaft mode, you can have dedicated controller nodes
     (process.roles=controller) that do NOT serve data.

  Q: Can there be multiple controllers?
  A: Only ONE active controller at a time.
     In KRaft, there is one Raft leader (the controller)
     and other Raft voters (standby controllers).

  Q: How to monitor the controller?
  A: Metrics:
     kafka.controller:type=KafkaController,name=ActiveControllerCount
     Should be 1. If 0 or >1, something is wrong.

============================================================
*/
public class KafkaController {

}
