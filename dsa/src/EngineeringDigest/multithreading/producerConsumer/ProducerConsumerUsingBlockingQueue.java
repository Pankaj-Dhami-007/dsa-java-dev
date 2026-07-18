package EngineeringDigest.multithreading.producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerUsingBlockingQueue {

    public static void main(String[] args) {

        // thread-safe blocking queue
        BlockingQueue<Integer> buffer =
                new LinkedBlockingQueue<>(5);

        // producer thread
        Thread producer = new Thread(() -> {

            int value = 1;

            while (true) {

                try {

                    // add item
                    buffer.put(value);

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " produced : "
                                    + value
                    );

                    value++;

                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // consumer thread
        Thread consumer = new Thread(() -> {

            while (true) {

                try {

                    // remove item
                    int item = buffer.take();

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " consumed : "
                                    + item
                    );

                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.setName("Producer-Thread");

        consumer.setName("Consumer-Thread");

        producer.start();

        consumer.start();
    }
}

/*

========================================================================
        PRODUCER CONSUMER USING BLOCKINGQUEUE
========================================================================

This is MODERN Producer Consumer implementation.

========================================================================
WHY BLOCKINGQUEUE IMPORTANT?
========================================================================

Traditional wait()/notify():
- complex
- error-prone
- manual synchronization needed

========================================================================

BlockingQueue simplifies everything.

========================================================================
WHAT IS BLOCKINGQUEUE?
========================================================================

Thread-safe queue that automatically handles:
- waiting
- synchronization
- signaling

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
MAIN IDEA
========================================================================

Producer puts item into queue.

========================================================================

Consumer takes item from queue.

========================================================================

BlockingQueue automatically handles:
- buffer full waiting
- buffer empty waiting

========================================================================
NO MANUAL:
========================================================================

wait()
notify()
synchronized

========================================================================

needed.

========================================================================
IMPORTANT QUEUE
========================================================================

BlockingQueue<Integer> buffer =
        new LinkedBlockingQueue<>(5);

========================================================================

Capacity:
========================================================================

5 items

========================================================================

Maximum:
5 items allowed.

========================================================================
IMPORTANT METHODS
========================================================================

1. put()
2. take()

========================================================================
1. put()
========================================================================

Adds item into queue.

========================================================================

If queue full:
producer automatically waits.

========================================================================
2. take()
========================================================================

Removes item from queue.

========================================================================

If queue empty:
consumer automatically waits.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

BlockingQueue internally handles:
- locking
- waiting
- signaling
- thread safety

========================================================================

Automatically.

========================================================================
PRODUCER FLOW
========================================================================

Producer:
========================================================================

buffer.put(value)

========================================================================

If buffer full:
producer blocks automatically.

========================================================================
CONSUMER FLOW
========================================================================

Consumer:
========================================================================

buffer.take()

========================================================================

If buffer empty:
consumer blocks automatically.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

No explicit:
========================================================================

wait()
notify()

========================================================================

required.

========================================================================

This is why modern systems prefer:
========================================================================

BlockingQueue

========================================================================
STEP-BY-STEP EXECUTION
========================================================================

Producer adds:
========================================================================

1 2 3 4 5

========================================================================

Queue becomes full.

========================================================================

Producer automatically waits.

========================================================================

Consumer removes item:
========================================================================

1

========================================================================

Producer automatically resumes.

========================================================================
OUTPUT CAN BE
========================================================================

Producer-Thread produced : 1

Consumer-Thread consumed : 1

========================================================================

Producer-Thread produced : 2

Producer-Thread produced : 3

========================================================================

Consumer-Thread consumed : 2

========================================================================
VERY IMPORTANT OBSERVATION
========================================================================

Everything coordinated automatically.

========================================================================

Cleaner and safer implementation.

========================================================================
WHY BLOCKINGQUEUE BETTER?
========================================================================

Avoids manual:
- synchronization mistakes
- wait/notify bugs
- deadlock risks

========================================================================
IMPORTANT BLOCKINGQUEUE IMPLEMENTATIONS
========================================================================

1. LinkedBlockingQueue
2. ArrayBlockingQueue
3. PriorityBlockingQueue

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

BlockingQueue is heavily used in:
- thread pools
- ExecutorService
- messaging systems

========================================================================
EXECUTORFRAMEWORK CONNECTION
========================================================================

ThreadPoolExecutor internally uses:
========================================================================

BlockingQueue

========================================================================

for task management.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
smart warehouse shelf.

========================================================================

If shelf full:
supplier automatically waits.

========================================================================

If shelf empty:
delivery worker automatically waits.

========================================================================

System manages coordination automatically.

========================================================================

Exactly same concept.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Kafka-like systems
2. RabbitMQ
3. Task scheduling
4. Thread pools
5. Logging systems
6. Background processing

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Thread-safe
2. Cleaner code
3. Automatic waiting
4. No manual synchronization
5. Better scalability

========================================================================
IMPORTANT LIMITATION
========================================================================

Less low-level control compared to:
manual wait/notify.

========================================================================
BLOCKINGQUEUE vs wait/notify
========================================================================

wait/notify
--------------
Manual synchronization

========================================================================

BlockingQueue
--------------
Automatic synchronization

========================================================================

wait/notify
--------------
Complex

========================================================================

BlockingQueue
--------------
Cleaner and safer

========================================================================

wait/notify
--------------
Error-prone

========================================================================

BlockingQueue
--------------
Production-friendly

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why BlockingQueue preferred?

→ Automatic thread coordination and synchronization.

========================================================================

2. Which method inserts item?

→ put()

========================================================================

3. Which method removes item?

→ take()

========================================================================

4. What happens if queue full?

→ Producer waits automatically.

========================================================================

5. What happens if queue empty?

→ Consumer waits automatically.

========================================================================

6. Does BlockingQueue require synchronized?

→ No

========================================================================

7. Which framework internally uses BlockingQueue?

→ Executor Framework

========================================================================

MOST IMPORTANT INTERVIEW LINE

BlockingQueue simplifies Producer Consumer implementation
by automatically handling
thread synchronization and waiting mechanisms.

========================================================================

*/