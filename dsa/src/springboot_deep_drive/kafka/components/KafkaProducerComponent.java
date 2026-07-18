package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA PRODUCER - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Kafka Producer?
A: A producer is a client that PUBLISHES (writes) messages
   to one or more Kafka topics. It is responsible for
   choosing which partition a message goes to.

Q: How does a producer work internally?
A: Producer serializes the key+value, determines partition,
   buffers records in batches, and sends batches to the
   leader broker for the target partition.

============================================================
  SIMPLE DEFINITION
============================================================

  Producer = SENDER in a postal system.

  You write a letter (message), put it in an envelope
  (record), address it to a city (topic), and drop it
  at the post office (broker).

  The post office (Kafka) ensures delivery.

  Key responsibilities:
    - Serialize: Convert Java object to bytes
    - Partition: Choose which partition (which shard)
    - Batch: Group messages for efficiency
    - Send: Transmit to the correct broker
    - Retry: Handle failures gracefully

============================================================
  ASCII DIAGRAM - Producer Internal Flow
============================================================

    Your Application
         |
         v
    KafkaTemplate.send("topic", key, value)
         |
         v
    Serializer.serialize(key)    (String -> bytes)
    Serializer.serialize(value)  (JSON -> bytes)
         |
         v
    Partitioner.partition()
    hash(key) % numPartitions
         |
         v
    RecordAccumulator
    +-------------------------------+
    | Batch for Partition 0         |
    | [msg1, msg2, msg3, ..., msgN] |
    +-------------------------------+
    | Batch for Partition 1         |
    | [msg1, msg2, ..., msgN]       |
    +-------------------------------+
         |
         v  (when batch is full or linger.ms reached)
    Sender Thread
         |
         v
    Network Client -> Broker (Leader for partition)
         |
         v
    Broker appends to log, assigns offset
         |
         v
    Response back to producer
    (success = partition + offset)
    (failure = error code)

============================================================
  PRODUCER CONFIGURATIONS (Key ones)
============================================================

  bootstrap.servers:
    Broker address. List multiple for fault tolerance.
    Example: "broker1:9092,broker2:9092,broker3:9092"

  acks:
    "0"  = Fire and forget. Fastest. Data loss possible.
    "1"  = Leader acknowledges. Default. Moderate safety.
    "all" = All in-sync replicas acknowledge. Safest.

  compression.type:
    none, gzip, snappy, lz4, zstd
    Reduces network bandwidth. Increases CPU.

  batch.size:
    Default 16 KB. Larger = higher throughput, more memory.

  linger.ms:
    Default 0. Wait time to fill a batch.
    5ms = good balance of latency vs throughput.

  retries:
    Default 2147483647 (Integer.MAX_VALUE).
    How many times to retry on transient errors.

  max.in.flight.requests.per.connection:
    Default 5. Set to 1 to guarantee order on retries.

============================================================
  PRODUCER DELIVERY SEMANTICS
============================================================

  At-most-once (acks=0):
    - Fire and forget
    - Message may be lost
    - Fastest, least reliable

  At-least-once (acks=1 or all + retries):
    - Message delivered at least once
    - May have duplicates (on retry)
    - Standard for most apps

  Exactly-once (acks=all + idempotent + transactional):
    - Message delivered exactly once
    - No duplicates, no loss
    - Slowest, most reliable
    - Enable: enable.idempotence=true

============================================================
  REAL-LIFE SCENARIO - E-Commerce Order Producer
============================================================

  Application: Order Service
  Sends: 500 orders/second
  Topic: orders (3 partitions)

  Producer config:
    acks=all          # No data loss for orders
    retries=5         # Retry on transient errors
    compression.type=gzip  # Reduce size of JSON
    linger.ms=5       # Wait 5ms to batch orders
    batch.size=32768  # 32 KB batches
    enable.idempotence=true  # Exactly-once per partition

  Flow:
    1. Order placed -> JSON serialized
    2. Key = orderId -> Partition = hash(orderId) % 3
    3. Batches with other orders for 5ms
    4. Batch of ~50 orders sent to leader broker
    5. Leader writes to log, replicates to 2 followers
    6. All 3 replicas acknowledge -> producer gets success
    7. If broker fails -> retry, another broker takes over

============================================================
  PRODUCER VS CONSUMER (Quick Comparison)
============================================================

  +------------------+---------------------------+---------------------------+
  | Feature          | Producer                  | Consumer                  |
  +------------------+---------------------------+---------------------------+
  | Direction        | Writes to Kafka           | Reads from Kafka          |
  +------------------+---------------------------+---------------------------+
  | Push/Pull        | PUSH data to broker       | PULL data from broker     |
  +------------------+---------------------------+---------------------------+
  | Acknowledgment   | acks config               | Offset commit             |
  +------------------+---------------------------+---------------------------+
  | Serialization    | Serializer classes        | Deserializer classes      |
  +------------------+---------------------------+---------------------------+
  | Batching         | Batches before send       | Receives batches          |
  +------------------+---------------------------+---------------------------+
  | Key role         | Partition routing         | Not used (routing done)   |
  +------------------+---------------------------+---------------------------+

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Can a producer send to multiple topics?
  A: Yes. One producer can send to many topics.

  Q: What happens if the leader broker is down?
  A: Producer retries. Metadata refreshed -> new leader.
     If acks=all, wait for new leader to be elected.

  Q: What is idempotent producer?
  A: Producer with enable.idempotence=true.
     Kafka detects duplicate sends (same sequence number).
     Prevents duplicates caused by retries.
     Recommended for production.

  Q: How does producer handle broker failures?
  A: 1. Timeout/error from current leader
     2. Refresh metadata from cluster
     3. Find new leader for partition
     4. Retry the send to new leader
     5. If all retries exhausted -> callback with error

============================================================
*/
public class KafkaProducerComponent {

}
