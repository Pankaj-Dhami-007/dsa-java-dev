package springboot_deep_drive.kafka.components;

/**
============================================================
  KAFKA TOPIC - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Kafka Topic?
A: A topic is a logical channel or category where messages
   are published. Think of it like a MAILBOX or a FOLDER
   that holds related messages.

Q: Is a topic like a database table?
A: No. A database table has schema, indexes, and supports
   random reads. A topic is an append-only log - you can
   only ADD messages and READ them in order.

============================================================
  SIMPLE DEFINITION
============================================================

                +-------------------+
                |    KAFKA CLUSTER   |
                +-------------------+
                  |    |    |    |
        +---------+----+----+----+---------+
        |         |         |              |
     ORDERS    PAYMENTS  USERS        NOTIFICATIONS
     Topic     Topic     Topic        Topic

  REAL-LIFE ANALOGY:
  Think of a POST OFFICE:

    Topic = CITY NAME (destination)
    Message = LETTER
    Producer = PERSON DROPPING LETTER
    Consumer = PERSON RECEIVING LETTER

  - All letters for "New York" go in ONE bucket (topic)
  - Multiple people can send to "New York" (multi-producer)
  - Multiple people can receive from "New York" (multi-consumer)
  - "New York" bucket is different from "Chicago" bucket

============================================================
  ASCII DIAGRAM - Multi-Topic Architecture
============================================================

                       PRODUCERS
                    |    |    |    |
         +----------+----+----+----+----------+
         |          |    |    |             |
         v          v    v    v             v
    +---------+ +---------+ +---------+
    | ORDERS  | |PAYMENTS | | USERS   |   <--- TOPICS
    | Topic   | | Topic   | | Topic   |
    +---------+ +---------+ +---------+
    | Part-0  | | Part-0  | | Part-0  |
    | Part-1  | | Part-1  | | Part-1  |
    | Part-2  | | Part-2  | |         |
    +---------+ +---------+ +---------+
         |          |           |
         v          v           v
    +---------+ +---------+ +---------+
    | Order   | |Payment  | |User     |  <--- CONSUMERS
    | Service | |Service  | |Service  |
    +---------+ +---------+ +---------+

============================================================
  TOPIC CHARACTERISTICS
============================================================

  1. IMMUTABLE:
     - Once written, a message CANNOT be changed
     - No UPDATE, no DELETE (except by retention)
     - Append-only log

  2. PERSISTENT:
     - Messages stay on disk based on retention config
     - Default: 7 days or 1 GB per partition
     - NOT deleted after consumption (unlike RabbitMQ)

  3. MULTI-SUBSCRIBER:
     - Multiple independent consumer groups can read
       the SAME topic at their own pace
     - Each group gets ALL messages

  4. PARTITIONED:
     - Each topic splits into multiple partitions
     - Partitions enable parallelism

============================================================
  REAL-LIFE SCENARIO - E-Commerce Event Topics
============================================================

  +----------------+-----------------------------------------+
  | Topic          | Messages                                |
  +----------------+-----------------------------------------+
  | orders         | OrderCreated, OrderShipped, Delivered   |
  | payments       | PaymentReceived, PaymentFailed, Refund  |
  | inventory      | StockUpdated, LowStockWarning, OutOfStock|
  | notifications  | EmailSent, SMSSent, PushSent            |
  | analytics      | PageViewed, SearchPerformed, Clicked    |
  +----------------+-----------------------------------------+

  Producers:
    Order Service -> orders, payments
    Payment Service -> payments
    Frontend -> analytics

  Consumers:
    Email Service <- notifications
    Fraud Detection <- payments
    Warehouse <- orders, inventory

============================================================
  CREATING A TOPIC (Command)
============================================================

  bin/kafka-topics.sh --create \
    --topic orders \
    --bootstrap-server localhost:9092 \
    --partitions 3 \
    --replication-factor 3

============================================================
  TOPIC NAMING CONVENTIONS
============================================================

  GOOD: orders, payment.transactions, user.created.v1
  BAD: my-topic-1, data, test, a

  Recommendation: {domain}.{event-type}.{version}

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Can you have too many topics?
  A: Yes. 100-1000 is normal. 10000+ causes performance issues.

  Q: Can you rename a topic?
  A: No. You must create a new topic and migrate data.

  Q: Topic vs Queue (RabbitMQ)?
  A: RabbitMQ deletes after consumption. Kafka persists.
     Multiple consumers can read same Kafka message.

  Q: What if topic does not exist?
  A: If auto.create.topics.enable=true, Kafka auto-creates it.
     In production, DISABLE this to avoid accidental topics.

============================================================
*/
public class KafkaTopic {

}
