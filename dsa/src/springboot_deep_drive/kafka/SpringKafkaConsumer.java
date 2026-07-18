package springboot_deep_drive.kafka;

/**
============================================================
  SPRING KAFKA CONSUMER - Receiving Messages with @KafkaListener
============================================================

  Instead of writing a poll loop manually, Spring gives
  you @KafkaListener. Just add it to any method and
  Spring calls it when messages arrive.

  What Spring handles:
    - Creates the KafkaConsumer
    - Subscribes to topics
    - Polls continuously in background
    - Calls your method for each message
    - Commits offsets
    - Handles rebalance

  Prerequisites:
    1. KafkaSpringConfig loaded (provides beans)
    2. Kafka is running
    3. Topic exists (my-topic)

============================================================
  METHOD 1: SIMPLE LISTENER (Just the message)
============================================================

  Simplest way. Spring calls this method for each message.

  import org.springframework.kafka.annotation.KafkaListener;
  import org.springframework.stereotype.Component;

  @Component
  public class MyConsumer {

      @KafkaListener(topics = "my-topic",
                     groupId = "simple-group")
      public void listen(String message) {
          System.out.println("Received: " + message);
      }
  }

============================================================
  METHOD 2: FULL RECORD (key, value, partition, offset)
============================================================

  Use ConsumerRecord parameter to get all details.

  import org.apache.kafka.clients.consumer.ConsumerRecord;

  @KafkaListener(topics = "my-topic",
                 groupId = "record-group")
  public void listenRecord(
          ConsumerRecord<String, String> record) {
      System.out.printf(
          "Key=%s, Value=%s, Partition=%d, Offset=%d%n",
          record.key(), record.value(),
          record.partition(), record.offset());
  }

============================================================
  METHOD 3: WITH HEADERS AND PAYLOAD
============================================================

  Extract specific parts using annotations.

  import org.springframework.kafka.support.KafkaHeaders;
  import org.springframework.messaging.handler.annotation.Header;
  import org.springframework.messaging.handler.annotation.Payload;

  @KafkaListener(topics = "my-topic",
                 groupId = "header-group")
  public void listenWithHeaders(
          @Payload String message,
          @Header(KafkaHeaders.RECEIVED_KEY) String key,
          @Header(KafkaHeaders.RECEIVED_PARTITION) int part) {
      System.out.printf("Msg=%s, Key=%s, Partition=%d%n",
          message, key, part);
  }

============================================================
  METHOD 4: MANUAL ACKNOWLEDGMENT (Production safe)
============================================================

  Without this: if app crashes after receiving but before
  processing, the message is LOST (auto-commit already saved).

  With manual ack: you commit ONLY after processing succeeds.

  import org.springframework.kafka.support.Acknowledgment;

  @KafkaListener(topics = "my-topic",
                 groupId = "manual-ack-group")
  public void listenWithAck(
          String message,
          Acknowledgment ack) {

      try {
          System.out.println("Processing: " + message);
          // saveToDatabase(message);  // Your logic

          ack.acknowledge();  // Only commit after success
      } catch (Exception e) {
          // Don'"'"'t acknowledge - message will come again
          System.err.println("Failed: " + e.getMessage());
      }
  }

  Requires in config: ackMode = MANUAL_IMMEDIATE

============================================================
  METHOD 5: BATCH LISTENER (Multiple records at once)
============================================================

  For bulk processing (e.g., insert 100 DB rows at once).

  import java.util.List;

  @KafkaListener(topics = "my-topic",
                 groupId = "batch-group",
                 containerFactory = "batchFactory")
  public void listenBatch(
          List<ConsumerRecord<String, String>> records,
          Acknowledgment ack) {

      System.out.println("Batch of " + records.size() + " msgs");

      for (ConsumerRecord<String, String> r : records) {
          System.out.println("  " + r.value());
      }

      ack.acknowledge();  // Commit once for all
  }

  Config needed: batchFactory bean (see KafkaSpringConfig)

============================================================
  METHOD 6: RETRY + DEAD LETTER TOPIC
============================================================

  If your method throws:
    1. Kafka retries (with delay)
    2. After max retries, sends to DLT
    3. @DltHandler handles the final failure

  import org.springframework.kafka.annotation.RetryableTopic;
  import org.springframework.kafka.annotation.DltHandler;

  @Component
  public class RetryConsumer {

      @RetryableTopic(
          attempts = "4",
          backoff = @Backoff(delay = 2000, multiplier = 2.0)
      )
      @KafkaListener(topics = "my-topic",
                     groupId = "retry-group")
      public void listen(String msg) {
          if (msg.contains("fail")) {
              throw new RuntimeException("Failed");
          }
      }

      @DltHandler
      public void onDlt(String msg) {
          System.err.println("All retries failed: " + msg);
      }
  }

  Auto-creates: my-topic-retry-0, -retry-1, -retry-2, -dlt

============================================================
  METHOD 7: TYPE-BASED ROUTING (@KafkaHandler)
============================================================

  Routes to methods based on message TYPE.
  @KafkaListener on the CLASS level.

  import org.springframework.kafka.annotation.KafkaHandler;

  @Component
  @KafkaListener(topics = "my-topic",
                 groupId = "handler-group")
  public class TypeRouter {

      @KafkaHandler
      public void onString(String msg) {
          System.out.println("String: " + msg);
      }

      @KafkaHandler(isDefault = true)
      public void onDefault(Object msg) {
          System.out.println("Unknown: "
              + msg.getClass().getName());
      }
  }

============================================================
  METHOD 8: REQUEST-REPLY (@SendTo)
============================================================

  Process a message and send the result to another topic.

  import org.springframework.kafka.annotation.SendTo;

  @Component
  public class ReplyConsumer {

      @KafkaListener(topics = "my-topic",
                     groupId = "reply-group")
      @SendTo("my-topic-responses")
      public String listenAndReply(@Payload String msg,
              @Header(KafkaHeaders.RECEIVED_KEY) String key) {

          System.out.println("Processing: " + msg);
          return "Processed: " + msg.toUpperCase();
          // Return value goes to "my-topic-responses"
      }
  }

============================================================
  WHICH METHOD SHOULD I USE?
============================================================

  +------------------+------------------------------------------+
  | If you want...   | Use method...                           |
  +------------------+------------------------------------------+
  | Simple read      | Method 1 (just String param)             |
  | Read key+value   | Method 2 (ConsumerRecord)                |
  | Read headers     | Method 3 (@Header @Payload)              |
  | Safe commits     | Method 4 (Acknowledgment)                |
  | Bulk processing  | Method 5 (batch listener)                |
  | Auto retry       | Method 6 (@RetryableTopic + @DltHandler) |
  | Type routing     | Method 7 (@KafkaHandler)                 |
  | Request-reply    | Method 8 (@SendTo)                       |
  +------------------+------------------------------------------+

  Start with Method 1 (simplest).
  Add Method 4 when you need reliability.
  Add Method 6 when errors need handling.

============================================================
  NOT USING SPRING?
============================================================

  See KafkaConsumerExample.java in the same package.
  It uses kafka-clients directly.

============================================================
*/
public class SpringKafkaConsumer {

}
