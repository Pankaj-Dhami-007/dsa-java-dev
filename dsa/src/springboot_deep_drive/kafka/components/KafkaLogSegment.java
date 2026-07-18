package springboot_deep_drive.kafka.components;

/*
============================================================
  KAFKA LOG SEGMENT - Complete Component Explanation
============================================================

============================================================
  INTERVIEW Q&A (Tell interviewer in 1-2 lines)
============================================================

Q: What is a Log Segment in Kafka?
A: A log segment is a FILE on disk that stores messages
   for a partition. When a segment reaches a size/time
   limit, a new segment is created. Old segments can be
   deleted or compacted.

Q: Why does Kafka use segments instead of one big file?
A: For EFFICIENCY. Segments make it easy to:
   - Delete old data (delete entire file)
   - Clean up compacted topics
   - Control disk usage per partition
   - Enable fast crash recovery

============================================================
  SIMPLE DEFINITION
============================================================

  Log Segment = CHAPTER in a book.

  A book (partition) has multiple chapters (segments).
  When a chapter fills up, start a new chapter.
  Old chapters can be archived or thrown away.

  On disk:
    /tmp/kafka-logs/orders-0/   (partition 0 of orders topic)
      00000000000000000000.log   (segment 1: offsets 0-999)
      00000000000000001000.log   (segment 2: offsets 1000-1999)
      00000000000000002000.log   (segment 3: offsets 2000-2999)
      00000000000000003000.log   (segment 4: current, active)

  The ACTIVE segment is the one being written to.
  Older segments are read-only (can be deleted/compacted).

============================================================
  ASCII DIAGRAM - Segments on Disk
============================================================

    Partition 0 of "orders" topic

    +============================================+
    |  ACTIVE SEGMENT (being written to)          |
    |  00000000000000003000.log                   |
    |  +------+------+------+------+------+      |
    |  |3000  |3001  |3002  |3003  |3004  | ...  |
    |  +------+------+------+------+------+      |
    +============================================+
         |
         | (when size reaches 1 GB or 7 days old)
         v
    +============================================+
    |  SEALED SEGMENT (read-only, can be deleted)  |
    |  00000000000000002000.log                   |
    |  +------+------+------+------+------+      |
    |  |2000  |2001  |2002  |2003  |... 2999|    |
    |  +------+------+------+------+------+      |
    +============================================+

    +============================================+
    |  SEALED SEGMENT (read-only)                 |
    |  00000000000000001000.log                   |
    +============================================+

    +============================================+
    |  SEALED SEGMENT (read-only)                 |
    |  00000000000000000000.log                   |
    +============================================+

============================================================
  SEGMENT FILES ON DISK
============================================================

  Each segment produces MULTIPLE files:

    orders-0/
      00000000000000000000.log       # Actual message data
      00000000000000000000.index     # Offset -> file position mapping
      00000000000000000000.timeindex # Timestamp -> offset mapping

  .log file:
    Stores the actual message data.
    Messages are appended sequentially (fast I/O).

  .index file:
    Maps OFFSET to FILE POSITION.
    Allows CONSUMER to seek to a specific offset quickly.
    Sparse index (every N bytes, not every message).

  .timeindex file:
    Maps TIMESTAMP to OFFSET.
    Allows seeking by timestamp.
    Example: "Give me messages after 2:30 PM yesterday"

============================================================
  SEGMENT CONFIGURATION
============================================================

  log.segment.bytes:
    Max size of a segment (default: 1 GB = 1073741824)
    When active segment reaches this, a new segment starts.

  log.segment.ms:
    Max time before a segment is closed (default: 7 days)
    Even if not full, segment is sealed after this time.

  log.retention.bytes:
    Max total size of ALL segments for a partition.
    Oldest segments deleted when exceeded.
    Default: -1 (unlimited)

  log.retention.ms:
    Max age of a segment before deletion.
    Default: 168 hours (7 days)

  log.retention.check.interval.ms:
    How often Kafka checks for deletable segments.
    Default: 5 minutes

============================================================
  HOW RETENTION WORKS (Segment Deletion)
============================================================

  Kafka does NOT delete individual messages.
  It deletes WHOLE SEGMENT FILES.

  Scenario: log.retention.ms = 604800000 (7 days)

    Segment 1: Created June 1  -> Contains offsets 0-999
    Segment 2: Created June 5  -> Contains offsets 1000-1999
    Segment 3: Created June 10 -> Contains offsets 2000-2999 (active)

    On June 12:
      Segment 1 = 11 days old > 7 days -> DELETED entirely
      Segment 2 = 7 days old = 7 days -> KEPT (exactly at limit)
      Segment 3 = 2 days old -> KEPT (active)

    After deletion, offsets 0-999 are GONE forever.
    Consumer cannot read them.
    This is how Kafka manages disk space!

============================================================
  LOG COMPACTION (Alternative to Deletion)
============================================================

  Instead of deleting by TIME, compaction keeps the LATEST
  value for each KEY.

  Use case: Database changelog, user profile updates.

  Before compaction:
    Key="user-1", Value="{name:Alice}"  (old, offset 0)
    Key="user-2", Value="{name:Bob}"    (old, offset 1)
    Key="user-1", Value="{name:Alice2}" (latest, offset 2)

  After compaction:
    Key="user-2", Value="{name:Bob}"    (kept, only entry)
    Key="user-1", Value="{name:Alice2}" (kept, latest version)
    Key="user-1", Value="{name:Alice}"  (DELETED - older version)

  TOMBSTONE (null value) removes the key entirely:
    Key="user-1", Value=null -> After compaction, user-1 is gone.

============================================================
  DISK PERFORMANCE (Why Kafka is fast)
============================================================

  1. SEQUENTIAL I/O:
     Writes are APPEND-ONLY (no random writes).
     Sequential disk I/O is 1000x faster than random I/O.

  2. O/S PAGE CACHE:
     Kafka writes to page cache (memory), NOT directly to disk.
     O/S flushes to disk asynchronously.
     Reads served from page cache when possible.

  3. ZERO COPY:
     When consumer reads, data goes:
       Disk -> Page Cache -> Network (via sendfile())
     WITHOUT copying to Kafka application memory.
     Saves CPU and memory bandwidth.

  4. BATCHING:
     Producers batch messages before writing.
     Consumers receive messages in batches.
     Each I/O operation handles many messages.

============================================================
  INTERVIEW FOLLOW-UP
============================================================

  Q: Why is Kafka fast with disk storage?
  A: Sequential I/O + page cache + zero-copy + batching.
     Disk is not the bottleneck; network usually is.

  Q: What are the two retention strategies?
  A: DELETE: Old segments deleted based on time/size.
     COMPACT: Keep latest value per key, delete older.

  Q: Can you recover deleted segments?
  A: No. Once a segment file is deleted, it is gone.
     Use backups (S3 sink) if you need long-term storage.

  Q: What happens if disk is full?
  A: Kafka stops accepting writes. Consumers can still
     read. Delete old topics or increase retention settings
     to free space.

============================================================
*/
public class KafkaLogSegment {

}
