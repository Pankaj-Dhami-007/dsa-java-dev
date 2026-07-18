package springboot_deep_drive.kafka;

/*
============================================================
  SPRING KAFKA ANNOTATIONS - Quick Reference
============================================================

  Spring Kafka provides annotations that make it easier
  to work with Kafka. Instead of writing poll loops,
  you just add @KafkaListener and Spring handles the rest.

  Annotations covered:
    @EnableKafka        - Turn on Kafka support
    @KafkaListener      - Listen to a topic
    @KafkaHandler       - Route by message type
    @Header             - Read message headers
    @Payload            - Read message body
    @SendTo             - Send reply to another topic
    @RetryableTopic     - Auto retry on failure
    @DltHandler         - Handle failed messages

============================================================
  1. @EnableKafka
============================================================

  Put on any @Configuration class.
  Tells Spring: "Scan for @KafkaListener methods."
  Without this, all @KafkaListener are ignored.

  Example:

    @Configuration
    @EnableKafka
    public class KafkaConfig {
        // beans here
    }

============================================================
  2. @KafkaListener
============================================================

  Put on a method that should receive messages.
  Spring creates a background thread that polls Kafka
  and calls this method when messages arrive.

  Simplest use:

    @Component
    public class MyConsumer {

        @KafkaListener(topics = "my-topic",
                       groupId = "my-group")
        public void listen(String message) {
            System.out.println("Got: " + message);
        }
    }

  Spring handles:
    - Creating the KafkaConsumer
    - The poll loop + subscribing
    - Committing offsets
    - Thread management

  Common attributes:

    topics      = Which topics to listen to (String array)
    groupId     = Consumer group (for parallel consumption)
    concurrency = How many threads (max = partition count)
    containerFactory = Custom factory (e.g., batchFactory)

  Different method parameters:

    @KafkaListener(topics = "my-topic")
    public void onMsg(String value)
      // just the value

    @KafkaListener(topics = "my-topic")
    public void onRecord(ConsumerRecord<String, String> r)
      // full record: r.key(), r.value(), r.partition()

    @KafkaListener(topics = "my-topic")
    public void onAck(String value, Acknowledgment ack)
      // manual commit: ack.acknowledge()

============================================================
  3. @KafkaHandler
============================================================

  Routes to different methods based on message TYPE.
  @KafkaListener must be on the CLASS level.

  Example:

    @Component
    @KafkaListener(topics = "events", groupId = "evt-group")
    public class EventHandler {

        @KafkaHandler
        public void onOrder(OrderEvent order) { ... }

        @KafkaHandler
        public void onPayment(PaymentEvent payment) { ... }

        @KafkaHandler(isDefault = true)
        public void onUnknown(Object obj) { ... }
    }

============================================================
  4. @Header
============================================================

  Reads a specific header from the Kafka message.
  Headers are metadata like event-type, trace-id, etc.

  Common headers from KafkaHeaders class:

    RECEIVED_KEY       = message key
    RECEIVED_PARTITION = partition number
    OFFSET             = offset
    RECEIVED_TOPIC     = topic name
    TIMESTAMP          = timestamp
    GROUP_ID           = consumer group

  Example:

    @KafkaListener(topics = "orders")
    public void onOrder(
            @Payload String json,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int part) {
        System.out.println("Key: " + key + ", Partition: " + part);
    }

============================================================
  5. @Payload
============================================================

  Explicitly marks a parameter as the message body.
  Usually optional (if one param, it is the payload).

  Can auto-deserialize JSON to POJO:

    @KafkaListener(topics = "orders")
    public void onOrder(@Payload OrderEvent order) {
        // order is already a Java object
    }

  For JSON deserialization, use JsonDeserializer:

    props.put("value.deserializer",
        "org.springframework.kafka.support.serializer.JsonDeserializer");
    props.put("json.trusted.packages", "*");

============================================================
  6. @SendTo
============================================================

  Sends the return value to another topic.
  Used for request-reply pattern.

  Example:

    @Component
    public class PaymentProcessor {

        @KafkaListener(topics = "payment-requests")
        @SendTo("payment-responses")
        public String process(String request) {
            String result = processPayment(request);
            return result;  // goes to "payment-responses" topic
        }
    }

============================================================
  7. @RetryableTopic + @DltHandler
============================================================

  When your listener throws an exception:
    1. Kafka retries with delay between attempts
    2. After all retries fail, message goes to DLT
    3. @DltHandler method handles the final failure

  Example:

    @Component
    public class OrderConsumer {

        @RetryableTopic(
            attempts = "4",           // 1 original + 3 retries
            backoff = @Backoff(
                delay = 2000,         // 2 sec initial delay
                multiplier = 2.0)     // double each time
        )
        @KafkaListener(topics = "orders",
                       groupId = "retry-group")
        public void onOrder(String order) {
            // If this throws, Kafka retries 3 times
            // Then sends to "orders-dlt"
            process(order);
        }

        @DltHandler
        public void onDlt(String order) {
            // All retries failed
            System.err.println("Failed: " + order);
            // Save to error DB, send alert
        }
    }

  Auto-created topics:
    orders-retry-0  (2s delay)
    orders-retry-1  (4s delay)
    orders-retry-2  (8s delay)
    orders-dlt      (permanent failure)

============================================================
  SUMMARY TABLE
============================================================

  +-------------------+------------------------------------------+
  | Annotation        | What it does                             |
  +-------------------+------------------------------------------+
  | @EnableKafka      | Required on @Config. Enables listeners. |
  +-------------------+------------------------------------------+
  | @KafkaListener    | Marks a method as a message listener.    |
  +-------------------+------------------------------------------+
  | @KafkaHandler     | Routes by payload type (with class-level |
  |                   | @KafkaListener).                         |
  +-------------------+------------------------------------------+
  | @Header           | Extracts a header from the message.      |
  +-------------------+------------------------------------------+
  | @Payload          | Marks the parameter as message body.     |
  +-------------------+------------------------------------------+
  | @SendTo           | Sends return value to another topic.     |
  +-------------------+------------------------------------------+
  | @RetryableTopic   | Auto retry with backoff, then DLT.       |
  +-------------------+------------------------------------------+
  | @DltHandler       | Handles messages that failed all retries. |
  +-------------------+------------------------------------------+

============================================================
  HOW TO CHOOSE
============================================================

  Just starting?     -> @KafkaListener with simple String
  Need reliability?  -> Add Acknowledgment for manual commit
  Need error handling? -> Add @RetryableTopic + @DltHandler
  Need reply?        -> Add @SendTo
  Need type routing? -> Add @KafkaHandler

============================================================
*/
public class SpringKafkaAnnotationsOverview {

}
