package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA RECORD - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Kafka Record (Message)?
A: A record is the actual unit of data in Kafka. It is a
   key-value pair with metadata (timestamp, headers) that
   gets appended to a partition.

Q: What does a Kafka record contain?
A: A record has: Topic, Partition, Key, Value, Timestamp,
   Headers, and Offset. The VALUE is the actual data,
   KEY determines which partition it goes to.

============================================================
  SIMPLE DEFINITION
============================================================

  A record is like an ENVELOPE in a postal system.

    +-----------------------------------------+
    |  ENVELOPE (Kafka Record)                |
    |                                         |
    |  To: New York   (Topic)                 |
    |  Slot: Section A (Partition)            |
    |  Stamp #: 12345  (Offset)               |
    |  Postmark: 10:30AM (Timestamp)          |
    |  CC: Urgent     (Headers)               |
    |                                         |
    |  Inside: Letter content (Value)         |
    |  Sender ID: John  (Key)                 |
    +-----------------------------------------+

============================================================
  ASCII DIAGRAM - Kafka Record Structure
============================================================

    +==================================================+
    |                 KAFKA RECORD                      |
    +==================================================+
    | HEADER (8 bytes)                                  |
    | - Record format version                          |
    | - Attribute flags (compression, timestamp type)   |
    | - Timestamp (create time / log append time)       |
    +--------------------------------------------------+
    | KEY (optional, can be null)                       |
    | - Used for partitioning                          |
    | - Same key = same partition = order guaranteed  |
    | - Stored as bytes (serialized)                   |
    +--------------------------------------------------+
    | VALUE (the actual message)                        |
    | - The business data                              |
    | - JSON, Avro, Protobuf, String, or bytes         |
    | - Can be null (tombstone for compacted topics)   |
    +--------------------------------------------------+
    | HEADERS (optional)                                |
    | - Key-value metadata pairs                       |
    | - Example: "event-type" -> "CREATED"             |
    | - Example: "trace-id" -> "abc-123"               |
    | - Example: "version" -> "1.0"                    |
    +--------------------------------------------------+

============================================================
  RECORD COMPONENTS IN DETAIL
============================================================

  TOPIC:     Which logical stream this record belongs to
             Set when producer sends: template.send("orders", value)

  PARTITION: Which physical shard this record is in
             Determined by: hash(key) % numPartitions
             Or explicitly set by producer

  KEY:       Partition routing + deduplication (compaction)
             null = round-robin distribution
             String = hash-based partition assignment
             Avro = complex type key

  VALUE:     The actual message payload
             Can be String, JSON, Avro, Protobuf, bytes
             null value = tombstone (for log compaction)

  TIMESTAMP: When the record was created or appended
             Type set by: message.timestamp.type
             CreateTime = producer set time
             LogAppendTime = broker append time

  HEADERS:   Custom metadata key-value pairs
             Not indexed, just stored with record
             Useful for routing, tracing, debugging

  OFFSET:    Unique position in partition
             Assigned by broker when record is appended
             Never changes after assignment

============================================================
  REAL-LIFE SCENARIO - Order Record
============================================================

  Producer sends an order event:

    Topic:      "orders"
    Key:        "order-789"
    Value:      "{"orderId":789,"userId":456,"total":99.99,
                  "items":[{"productId":"P1","qty":2}]}"
    Timestamp:  2026-06-28T14:30:00Z
    Headers:
      event-type  = "ORDER_CREATED"
      trace-id    = "trace-xyz-789"
      version     = "1.0"

  Kafka assigns:
    Partition:  0   (hash("order-789") % 3)
    Offset:     42  (42nd message in partition 0)

  Consumer receives:
    record.key()     = "order-789"
    record.value()   = "{"orderId":789,...}"
    record.partition() = 0
    record.offset()  = 42
    record.timestamp() = 1719582600000
    record.headers() = [event-type, trace-id, version]

============================================================
  RECORD SIZE LIMITS
============================================================

  Default max record size: 1 MB

  Config (server.properties):
    max.message.bytes=1048578       # 1 MB (broker level)
    replica.fetch.max.bytes=1048578 # Must be >= above

  Config (producer):
    max.request.size=1048578        # 1 MB

  For larger messages (rare - bad practice):
    - Store large data in S3, put URL in Kafka record
    - Or increase the limit (NOT recommended)
    - Large records slow down everything

============================================================
  TOMBSTONE RECORDS (null value)
============================================================

  When value = null, it is a TOMBSTONE.
  Used with COMPACTED topics to delete keys.

  Example:
    Producer sends:
      key="user-123", value=null
      key="user-456", value=null

    Compaction removes these keys from the log.
    After retention, only latest non-null values remain.

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Can a record have null key?
  A: Yes. null key means round-robin partitioning.
     No order guarantee across messages.

  Q: Can a record have null value?
  A: Yes. That is a tombstone - used for deletion
     in compacted topics.

  Q: What is the max size of a record?
  A: Default 1 MB. Configurable but not recommended
     to increase.

  Q: Can headers be used for routing?
  A: No. Headers are metadata. Routing is done via
     key (partition) and topic.

============================================================
*/
public class KafkaRecord {

}
