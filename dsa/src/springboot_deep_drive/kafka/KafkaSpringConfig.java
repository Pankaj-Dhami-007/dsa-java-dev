package springboot_deep_drive.kafka;

/**
============================================================
  SPRING KAFKA CONFIG - Setting up @Configuration
============================================================

  In a non-Spring-Boot project, you must create these beans
  manually. Spring Boot auto-configures if you use
  spring-boot-starter-kafka + application.properties.

  What each bean does:
    ProducerFactory     -> Creates KafkaProducer objects
    KafkaTemplate       -> Easy way to send messages
    ConsumerFactory     -> Creates KafkaConsumer objects
    ListenerContainerFactory -> Makes @KafkaListener work

============================================================
  FULL CONFIG CODE (Place in a @Configuration class)
============================================================

  import org.apache.kafka.clients.producer.ProducerConfig;
  import org.apache.kafka.clients.consumer.ConsumerConfig;
  import org.apache.kafka.common.serialization.StringSerializer;
  import org.apache.kafka.common.serialization.StringDeserializer;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.kafka.annotation.EnableKafka;
  import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
  import org.springframework.kafka.core.*;
  import org.springframework.kafka.listener.ContainerProperties;
  import java.util.HashMap;
  import java.util.Map;

  @Configuration
  @EnableKafka  // WITHOUT THIS, @KafkaListener IS IGNORED
  public class KafkaSpringConfig {

============================================================
  1. PRODUCER SETUP (For sending messages)
============================================================

  These beans let you use KafkaTemplate to send messages.

  @Bean
  public ProducerFactory<String, String> producerFactory() {
      Map<String, Object> props = new HashMap<>();

      // Required: Where is Kafka?
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
              "localhost:9092");

      // Required: How to convert keys/values to bytes?
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
              StringSerializer.class);
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
              StringSerializer.class);

      // Optional: Safe production settings
      props.put(ProducerConfig.ACKS_CONFIG, "all");
      props.put(ProducerConfig.RETRIES_CONFIG, 3);

      return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
      // KafkaTemplate = easy send() method
      // Instead of creating KafkaProducer manually,
      // you just call: kafkaTemplate.send("topic", "value")
      return new KafkaTemplate<>(producerFactory());
  }

============================================================
  2. CONSUMER SETUP (For @KafkaListener to work)
============================================================

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
      Map<String, Object> props = new HashMap<>();

      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
              "localhost:9092");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
              StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
              StringDeserializer.class);
      props.put(ConsumerConfig.GROUP_ID_CONFIG,
              "spring-group");
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
              "earliest");
      props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
              false);  // Manual ack is safer

      return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String>
          kafkaListenerContainerFactory() {
      // Creates background threads that poll Kafka
      // and call @KafkaListener methods.

      ConcurrentKafkaListenerContainerFactory<String, String> factory =
              new ConcurrentKafkaListenerContainerFactory<>();

      factory.setConsumerFactory(consumerFactory());
      factory.setConcurrency(3);  // Should match partition count
      factory.getContainerProperties().setAckMode(
              ContainerProperties.AckMode.MANUAL_IMMEDIATE);

      return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String>
          batchFactory() {
      // For batch listeners (receives List of records)
      ConcurrentKafkaListenerContainerFactory<String, String> factory =
              new ConcurrentKafkaListenerContainerFactory<>();

      factory.setConsumerFactory(consumerFactory());
      factory.setBatchListener(true);
      factory.getContainerProperties().setAckMode(
              ContainerProperties.AckMode.MANUAL_IMMEDIATE);

      return factory;
  }

  // End of @Configuration class
  }

============================================================
  HOW TO USE THIS CONFIG
============================================================

  1. Add to pom.xml:
     spring-kafka, spring-context, kafka-clients

  2. Start Kafka: docker-compose up -d

  3. Create topic:
     docker exec kafka kafka-topics --create \\
       --topic my-topic --bootstrap-server localhost:9092

  4. Create ApplicationContext:
     AnnotationConfigApplicationContext ctx =
         new AnnotationConfigApplicationContext(
             KafkaSpringConfig.class);

  5. Send a message:
     KafkaTemplate<String, String> template =
         ctx.getBean(KafkaTemplate.class);
     template.send("my-topic", "Hello!");

  6. Receive with @KafkaListener:
     See SpringKafkaConsumer.java

============================================================
  IF YOU USE SPRING BOOT
============================================================

  You DON'"'"'T need this config at all.
  Just add spring-boot-starter-kafka to pom.xml
  and set in application.properties:

    spring.kafka.bootstrap-servers=localhost:9092
    spring.kafka.consumer.group-id=my-group
    spring.kafka.consumer.auto-offset-reset=earliest
    spring.kafka.producer.acks=all

  Spring Boot auto-creates all these beans.

============================================================
*/
public class KafkaSpringConfig {

}
