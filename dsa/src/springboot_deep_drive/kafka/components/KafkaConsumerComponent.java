package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA CONSUMER - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Kafka Consumer?
A: A consumer is a client that READS (subscribes to)
   messages from one or more Kafka topics. It PULLS
   data from brokers at its own pace.

Q: How does a consumer know where to read from?
A: Consumer tracks its OFFSET per partition.
   It commits the offset after processing so that
   on restart, it continues from the last committed
   position.

============================================================
  SIMPLE DEFINITION
============================================================

  Consumer = RECIPIENT in a postal system.

  You subscribe to receive letters from a city (topic).
  The postman delivers letters one by one (or in bundles).
  You track which letter you last read (offset).

  Unlike RabbitMQ (push), Kafka uses PULL model:
    - Consumer ASKS broker for new messages
    - Consumer controls its own reading speed
    - If consumer is slow, messages pile up (lag)

============================================================
  ASCII DIAGRAM - Consumer Internal Flow
============================================================

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void onOrder(String order) { process(order); }

    Spring Kafka internally:

    KafkaMessageListenerContainer
         |
         v
    KafkaConsumer.poll(100ms)  <--- PULL from broker
         |
         v
    ConsumerRecords<String, String> records
    +---------------------------+
    | [msg1, msg2, msg3, ...]  |
    +---------------------------+
         |
         |
    For each record:
         |
         v
    Deserializer.deserialize(value)  (bytes -> String)
         |
         v
    Call @KafkaListener method with the record
         |
         v
    After method returns:
    Commit offset (auto or manual)
         |
         v
    poll() again (infinite loop)

============================================================
  CONSUMER PROPERTIES (Key ones)
============================================================

  bootstrap.servers:
    List of brokers to discover the cluster.

  group.id:
    REQUIRED. Consumer group identifier.
    All consumers with same group.id SHARE work.
    Different group.id = independent consumption.

  key.deserializer / value.deserializer:
    Convert bytes back to Java objects.
    StringDeserializer, JsonDeserializer, etc.

  auto.offset.reset:
    "earliest" = read from beginning (new group).
    "latest"   = read only new messages (default).
    "none"     = throw error if no offset.

  enable.auto.commit:
    true  = auto-commit every auto.commit.interval.ms
    false = manual commit via Acknowledgment

  max.poll.records:
    Max records per poll() call.
    Default: 500. Increase for batch processing.

  session.timeout.ms:
    Default: 45000 (45 sec).
    If consumer does not poll within this, broker
    considers it dead and triggers rebalance.

  heartbeat.interval.ms:
    Default: 3000 (3 sec).
    How frequently consumer sends heartbeat.

============================================================
  CONSUMER LIFECYCLE
============================================================

  1. CONSUMER CREATION
     Consumer created with configuration
     Consumer subscribes to topics

  2. PARTITION ASSIGNMENT
     Group coordinator assigns partitions to consumer
     Based on: group.id, number of consumers in group
     Each partition assigned to ONE consumer in group

  3. POLL LOOP
     Consumer polls broker periodically
     Receives batch of records
     Processes records (business logic)
     Commits offsets

  4. REBALANCE
     New consumer joins group -> partitions reassigned
     Consumer leaves (crash) -> partitions reassigned

  5. CONSUMER CLOSE
     Consumer.shutdown() called
     Final offset commit
     Leaves group -> triggers rebalance

============================================================
  REAL-LIFE SCENARIO - Email Notification Consumer
============================================================

  Topic: notifications (3 partitions)
  Group: email-sender-group
  Consumers: 3 instances

  Each consumer gets one partition:
    Consumer-1 -> Partition-0 (emails for US users)
    Consumer-2 -> Partition-1 (emails for EU users)
    Consumer-3 -> Partition-2 (emails for ASIA users)

  Consumer logic:
    @KafkaListener(topics = "notifications",
                   groupId = "email-sender-group")
    public void sendEmail(Notification notif) {
        // 1. Look up user email from database
        // 2. Call email API (SendGrid, SES)
        // 3. Log success/failure
        // 4. Commit offset (manual ack)
    }

  If Consumer-2 crashes:
    Partition-1 reassigned to Consumer-1 or Consumer-3
    EU emails still get sent (by another consumer)

============================================================
  CONSUMER PULL MODEL (Why pull, not push?)
============================================================

  Kafka uses PULL, not PUSH like RabbitMQ.

  Advantages of PULL:
    - Consumer controls pace (backpressure)
    - Consumer can batch process (efficiency)
    - Consumer can pause/resume (flow control)
    - Works with slow consumers (no broker overload)

  Disadvantages:
    - Slight latency (consumer must poll)
    - Consumer must manage polling loop

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Does a consumer need to poll continuously?
  A: Yes. If consumer stops polling, broker marks it
     as dead after session.timeout.ms. Triggers rebalance.

  Q: Can a consumer read from multiple topics?
  A: Yes. Subscribe to topic list or use regex pattern.

  Q: What is consumer LAG?
  A: Difference between last message in partition and
     consumer current offset. High lag = not keeping up.

  Q: How to handle slow consumers?
  A: Add more partitions + more consumer instances.
     Optimize processing logic.
     Increase max.poll.records for batch efficiency.

============================================================
*/
public class KafkaConsumerComponent {

}
