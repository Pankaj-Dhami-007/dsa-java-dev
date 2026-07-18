package springboot_deep_drive.kafka;

/**
============================================================
  KAFKA PRODUCER - Step by Step (kafka-clients)
============================================================

  This file shows how to send messages to Kafka using
  the raw kafka-clients library.

  What you learn:
    - How to configure a producer
    - How to send messages (fire-and-forget, sync, async)
    - How to handle success and failure

============================================================
  STEP 0: ADD DEPENDENCY
============================================================

  Maven:

    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>3.6.0</version>
    </dependency>

  Gradle:

    implementation "org.apache.kafka:kafka-clients:3.6.0"

============================================================
  STEP 1: START KAFKA
============================================================

  Make sure Kafka is running before running this code.

  Using Docker:  docker-compose up -d
  Or manually:   bin/kafka-server-start.sh config/server.properties

  Create topic:

    bin/kafka-topics.sh --create \\
      --topic my-topic \\
      --bootstrap-server localhost:9092 \\
      --partitions 3

============================================================
  STEP 2: CREATE PRODUCER AND SEND MESSAGES
============================================================

  Step-by-step code (put this inside a main method):

  // ===== 1. Configure =====
  Properties props = new Properties();
  props.put("bootstrap.servers", "localhost:9092");
  props.put("key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer");
  props.put("value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer");
  props.put("acks", "all");          // Wait for confirmation
  props.put("retries", 3);           // Retry on failure

  // ===== 2. Create Producer =====
  KafkaProducer<String, String> producer =
      new KafkaProducer<>(props);

  // ===== 3. Send messages =====

  // METHOD 1: Fire and Forget
  // You don'"'"'t care if it succeeded.
  ProducerRecord<String, String> r1 =
      new ProducerRecord<>("my-topic", "Hello Kafka!");
  producer.send(r1);

  // METHOD 2: Synchronous (waits for response)
  ProducerRecord<String, String> r2 =
      new ProducerRecord<>("my-topic", "key-1", "Sync msg");
  RecordMetadata metadata = producer.send(r2).get();
  System.out.println("Partition: " + metadata.partition()
      + ", Offset: " + metadata.offset());

  // METHOD 3: Asynchronous with callback
  ProducerRecord<String, String> r3 =
      new ProducerRecord<>("my-topic", "key-2", "Async msg");
  producer.send(r3, (RecordMetadata meta, Exception ex) -> {
      if (ex == null) {
          System.out.println("Sent to partition " + meta.partition());
      } else {
          System.err.println("Failed: " + ex.getMessage());
      }
  });

  // ===== 4. Cleanup =====
  producer.close();  // Always close! Flushes + frees resources.

============================================================
  EXPLANATION FOR BEGINNERS
============================================================

  ProducerRecord = One message with:
    - topic:    Where to send it
    - key:      Determines partition (null = random)
    - value:    The actual data

  producer.send() = Send the message.
    Returns a Future:
      .get()          = wait for response (sync)
      callback        = handle later (async)

  ProducerConfig = Settings:
    bootstrap.servers = Where is Kafka?
    serializer        = How to convert data to bytes
    acks              = How many confirmations needed

  producer.close() = Always call this!
    Flushes buffered messages, frees connections.

============================================================
  THREE PRODUCER PATTERNS
============================================================

  +------------------+----------+-----------------------------+
  | Pattern          | Speed    | Use When                    |
  +------------------+----------+-----------------------------+
  | Fire and Forget  | Fastest  | Logs, metrics (ok to lose) |
  | Sync (.get())    | Slowest  | Must know it succeeded      |
  | Async (callback) | Medium   | Production (best balance)  |
  +------------------+----------+-----------------------------+

============================================================
**/
public class KafkaProducerExample {

}
