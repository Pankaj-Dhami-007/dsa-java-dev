package springboot_deep_drive.kafka;

/**
============================================================
  KAFKA CONSUMER - Step by Step (kafka-clients)
============================================================

  This file shows how to read messages from Kafka using
  the raw kafka-clients library.

  What you learn:
    - How to configure a consumer
    - How to subscribe to a topic
    - How to poll for messages in a loop
    - How to commit offsets

============================================================
  STEP 0: ADD DEPENDENCY
============================================================

  Same as producer: kafka-clients

  Maven:

    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>3.6.0</version>
    </dependency>

============================================================
  STEP 1: START KAFKA + CREATE TOPIC
============================================================

  docker-compose up -d

  bin/kafka-topics.sh --create \\
    --topic my-topic \\
    --bootstrap-server localhost:9092 \\
    --partitions 3

============================================================
  STEP 2: CREATE AND RUN CONSUMER
============================================================

  Code (put inside a main method):

  // ===== 1. Configure =====
  Properties props = new Properties();
  props.put("bootstrap.servers", "localhost:9092");
  props.put("key.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer");
  props.put("value.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer");
  props.put("group.id", "my-consumer-group");
  props.put("auto.offset.reset", "earliest");  // Start from beginning

  // ===== 2. Create Consumer =====
  KafkaConsumer<String, String> consumer =
      new KafkaConsumer<>(props);

  // ===== 3. Subscribe to topic =====
  consumer.subscribe(Arrays.asList("my-topic"));

  System.out.println("Consumer started. Waiting for messages...");

  // ===== 4. Poll loop =====
  // Think of this like checking your mailbox every second.
  while (true) {
      ConsumerRecords<String, String> records =
          consumer.poll(Duration.ofMillis(1000));

      for (ConsumerRecord<String, String> record : records) {
          System.out.printf(
              "Partition=%d | Offset=%d | Key=%s | Value=%s%n",
              record.partition(), record.offset(),
              record.key(), record.value());
      }
  }

  // ===== 5. Cleanup =====
  // consumer.close();  // In a real app, add shutdown hook

============================================================
  UNDERSTANDING THE POLL LOOP
============================================================

  Think of it like checking your mailbox:

    while (true) {
        checkMailbox()           <- consumer.poll()
        if mail exists:
            readEachLetter()     <- for loop
        wait 1 second            <- Duration.ofMillis(1000)
    }

  Key points:
    - You MUST poll continuously.
    - If you stop polling for 45 seconds, Kafka thinks
      you died and gives your partitions to someone else.
    - poll() returns all new messages since last poll.

============================================================
  CONSUMER GROUP EXPLAINED
============================================================

  Setup: Topic has 3 partitions.

  1 consumer (group = "g1")
    -> Consumer reads partition 0, 1, 2 (all)

  2 consumers (same group "g1")
    -> Consumer 1: partition 0
    -> Consumer 2: partition 1, 2

  3 consumers (same group "g1")
    -> Consumer 1: partition 0
    -> Consumer 2: partition 1
    -> Consumer 3: partition 2    (ideal)

  10 consumers (same group "g1")
    -> Only 3 are working
    -> 7 are idle (waste)

  3 consumers (different group "g2")
    -> Each reads ALL partitions independently
    -> Group "g1" and Group "g2" don'"'"'t interfere

============================================================
  MANUAL OFFSET COMMIT (For production)
============================================================

  Auto-commit is easy but has a problem:

    Receive msg 5 -> Auto-commit -> Receive msg 6,7,8
    -> CRASH before auto-commit
    -> Restart: starts from msg 6 (re-processes 6,7,8)

  To avoid duplicates, use manual commit:

    props.put("enable.auto.commit", "false");

    while (true) {
        records = consumer.poll(1000);
        for (record : records) {
            process(record);        // Your business logic
        }
        consumer.commitSync();      // Only commit AFTER success
    }

  If crash: nothing is committed, messages come again.

============================================================
  OFFSET RESET OPTIONS
============================================================

  auto.offset.reset controls where a NEW group starts:

    "earliest" = from the beginning (read ALL messages)
    "latest"   = only new messages going forward (default)
    "none"     = error if no offset found

  Use "earliest" for: testing, reprocessing, new consumers
  Use "latest" for:   production (don'"'"'t need old data)

============================================================
*/
public class KafkaConsumerExample {

}
