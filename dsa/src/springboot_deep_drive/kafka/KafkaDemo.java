package springboot_deep_drive.kafka;

/*
============================================================
  KAFKA - WHAT IT IS AND WHY WE USE IT
============================================================

  Apache Kafka is a platform for streaming data.
  Think of it as a high-speed post office for your
  applications.

  Normal flow without Kafka:
    Service A -> HTTP -> Service B
    Service A -> HTTP -> Service C
    Service A -> HTTP -> Service D

    Problem: Service A talks to everyone directly.
    If B is down, A fails. If we add Service E,
    we must change A\'s code.

  Flow with Kafka:
    Service A -> Kafka (writes once)
    Service B <- Kafka (reads)
    Service C <- Kafka (reads)
    Service D <- Kafka (reads)

    Benefit: Service A writes ONCE.
    Anyone can read. Add/remove readers anytime.

============================================================
  WHAT PROBLEM DOES KAFKA SOLVE?
============================================================

  1. TIGHT COUPLING
     Without Kafka: Services call each other directly.
     With Kafka: Services only talk to Kafka.

  2. DATA REPLAY
     Without Kafka: Read a message, it is gone.
     With Kafka: Messages stay for days. Read again.

  3. SCALING
     Without Kafka: 1 service processes 1 msg at a time.
     With Kafka: 10 services read 10 msgs in parallel.

============================================================
  MAIN PARTS OF KAFKA
============================================================

  (See kafka/components/ for detailed explanation)

  BROKER      - A Kafka server. Runs on port 9092.
  TOPIC       - A category for messages (like a folder).
  PARTITION   - A split of a topic (for parallel reading).
  PRODUCER    - An app that sends messages to a topic.
  CONSUMER    - An app that reads messages from a topic.
  OFFSET      - A number that tracks which msg was read.
  RECORD      - One message (key + value + headers).

============================================================
  HOW A MESSAGE MOVES THROUGH KAFKA
============================================================

  Producer -> Topic -> Partition -> Consumer

  1. Producer sends a message to a topic
  2. Topic has 1+ partitions (shards)
  3. Message goes to one partition (based on key)
  4. Consumer reads from that partition
  5. Consumer saves its position (offset)

============================================================
  REAL EXAMPLE
============================================================

  You run an e-commerce site.

  Topics you create:
    orders     - When a customer places an order
    payments   - When payment is processed
    emails     - When email should be sent

  Producers (writes):
    Order Service -> writes to "orders" topic
    Payment Service -> writes to "payments" topic

  Consumers (reads):
    Email Service <- reads from "orders" and "emails"
    Analytics Service <- reads from "orders" and "payments"
    Warehouse Service <- reads from "orders"

  If you build a new Fraud Detection Service tomorrow:
    Just add a new consumer reading "payments" topic.
    No changes needed in Payment Service.

============================================================
  WHAT YOU NEED TO START
============================================================

  Option 1: Docker (easiest)
    docker-compose.yml:
      version: "3"
      services:
        zookeeper:
          image: confluentinc/cp-zookeeper:latest
          environment:
            ZOOKEEPER_CLIENT_PORT: 2181
        kafka:
          image: confluentinc/cp-kafka:latest
          depends_on: [zookeeper]
          ports:
            - "9092:9092"
          environment:
            KAFKA_BROKER_ID: 1
            KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
            KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
            KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

    Run: docker-compose up -d

  Option 2: Download from https://kafka.apache.org/downloads
    bin/zookeeper-server-start.sh config/zookeeper.properties
    bin/kafka-server-start.sh config/server.properties

  Create a topic:
    bin/kafka-topics.sh --create \\
      --topic my-topic \\
      --bootstrap-server localhost:9092 \\
      --partitions 3

============================================================
  TWO WAYS TO USE KAFKA IN JAVA
============================================================

  1. RAW KAFKA CLIENTS (kafka-clients library)
     - Direct API: KafkaProducer, KafkaConsumer
     - More code, more control
     - See: KafkaProducerExample.java, KafkaConsumerExample.java

  2. SPRING KAFKA (spring-kafka library)
     - Uses annotations: @KafkaListener, @Autowired KafkaTemplate
     - Less code, Spring manages things
     - See: SpringKafkaProducer.java, SpringKafkaConsumer.java
     - See: KafkaSpringConfig.java (setup), SpringKafkaAnnotationsOverview.java

  For beginners: Start with RAW KAFKA to understand how it works.
  Then use SPRING KAFKA for real projects (less boilerplate).

============================================================
*/
public class KafkaDemo {

}
