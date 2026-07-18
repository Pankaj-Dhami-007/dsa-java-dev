package springboot_deep_drive.kafka;

/**
============================================================
  SPRING KAFKA PRODUCER - Sending Messages with @Autowired
============================================================

  Instead of creating KafkaProducer manually, Spring gives
  you KafkaTemplate. Just @Autowired it and call send().

  Prerequisites:
    1. KafkaSpringConfig loaded (provides the beans)
    2. Kafka is running (localhost:9092)
    3. Topic exists (my-topic)

============================================================
  WHAT IS KAFKA TEMPLATE?
============================================================

  KafkaTemplate is like a remote control for Kafka.
  It has these useful methods:

    send(topic, value)          -> Send to topic (key = null)
    send(topic, key, value)     -> Send with key for partitioning
    send(ProducerRecord)        -> Full control (headers, partition)
    send(Message<?>)            -> Send a Spring Message object

  All return CompletableFuture<SendResult>.
  You can:
    - Ignore it (fire and forget)
    - Call .get() to wait (sync)
    - Add .whenComplete() for async callback

============================================================
  USAGE IN A SPRING COMPONENT
============================================================

  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.kafka.core.KafkaTemplate;
  import org.springframework.stereotype.Component;

  @Component
  public class OrderProducer {

      @Autowired
      private KafkaTemplate<String, String> kafkaTemplate;

============================================================
  METHOD 1: FIRE AND FORGET
============================================================

  public void send(String message) {
      kafkaTemplate.send("my-topic", message);
  }

  Just send. No confirmation. Fastest.

============================================================
  METHOD 2: SEND WITH KEY
============================================================

  public void sendWithKey(String key, String value) {
      kafkaTemplate.send("my-topic", key, value);
  }

  Same key = same partition = messages are ordered.

============================================================
  METHOD 3: SEND WITH CALLBACK
============================================================

  public void sendWithCallback(String value) {
      CompletableFuture<SendResult<String, String>> future =
              kafkaTemplate.send("my-topic", value);

      future.whenComplete((result, exception) -> {
          if (exception == null) {
              // Success! Get partition and offset
              System.out.printf(
                  "Sent to partition %d, offset %d%n",
                  result.getRecordMetadata().partition(),
                  result.getRecordMetadata().offset()
              );
          } else {
              // Failure
              System.err.println(
                  "Send failed: " + exception.getMessage());
          }
      });
  }

============================================================
  METHOD 4: SEND WITH HEADERS
============================================================

  public void sendWithHeaders(String key, String value) {
      ProducerRecord<String, String> record =
              new ProducerRecord<>("my-topic", key, value);

      record.headers().add("event-type",
              "ORDER_CREATED".getBytes());
      record.headers().add("version",
              "1.0".getBytes());

      kafkaTemplate.send(record);
  }

============================================================
  METHOD 5: SEND JSON OBJECT
============================================================

  To send POJOs instead of Strings:

  1. Change KafkaTemplate type:
     KafkaTemplate<String, OrderEvent>

  2. Use JsonSerializer in ProducerFactory:
     props.put("value.serializer",
         "org.springframework.kafka.support.serializer.JsonSerializer");

  3. Send objects directly:
     OrderEvent order = new OrderEvent("123", "CREATED");
     kafkaTemplate.send("orders", order.getId(), order);

============================================================
  FULL EXAMPLE CLASS
============================================================

  Put it all together:

    @Component
    public class OrderProducer {

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        public void sendOrder(Order order) {
            String json = convertToJson(order);
            kafkaTemplate.send("orders", order.getId(), json)
                .whenComplete((res, ex) -> {
                    if (ex == null) {
                        log.info("Order sent: partition={}, offset={}",
                            res.getRecordMetadata().partition(),
                            res.getRecordMetadata().offset());
                    } else {
                        log.error("Order send failed", ex);
                    }
                });
        }
    }

============================================================
  NOT USING SPRING?
============================================================

  See KafkaProducerExample.java in the same package.
  It uses kafka-clients directly.

============================================================
*/
public class SpringKafkaProducer {

}
