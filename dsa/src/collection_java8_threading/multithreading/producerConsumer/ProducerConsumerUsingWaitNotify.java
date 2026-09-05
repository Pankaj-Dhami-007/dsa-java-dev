package EngineeringDigest.multithreading.producerConsumer;

import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer {

    private Queue<Integer> buffer =
            new LinkedList<>();

    private final int CAPACITY = 5;

    // producer method
    public synchronized void produce(int item)
            throws InterruptedException {

        // buffer full
        while (buffer.size() == CAPACITY) {

            System.out.println(
                    "Buffer Full. Producer waiting..."
            );

            wait();
        }

        // add item
        buffer.add(item);

        System.out.println(
                Thread.currentThread().getName()
                        + " produced : "
                        + item
        );

        // notify consumer
        notify();
    }

    // consumer method
    public synchronized void consume()
            throws InterruptedException {

        // buffer empty
        while (buffer.isEmpty()) {

            System.out.println(
                    "Buffer Empty. Consumer waiting..."
            );

            wait();
        }

        // remove item
        int item = buffer.poll();

        System.out.println(
                Thread.currentThread().getName()
                        + " consumed : "
                        + item
        );

        // notify producer
        notify();
    }
}

public class ProducerConsumerUsingWaitNotify {

    public static void main(String[] args) {

        SharedBuffer buffer =
                new SharedBuffer();

        // producer thread
        Thread producer = new Thread(() -> {

            int value = 1;

            while (true) {

                try {

                    buffer.produce(value++);

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

                    buffer.consume();

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
        PRODUCER CONSUMER USING wait() notify()
========================================================================

This is CLASSIC Producer Consumer implementation.

========================================================================
MAIN IDEA
========================================================================

Producer:
adds items into shared buffer.

========================================================================

Consumer:
removes items from shared buffer.

========================================================================

Need proper coordination.

========================================================================
SHARED RESOURCE
========================================================================

SharedBuffer object shared between:
- producer thread
- consumer thread

========================================================================
IMPORTANT BUFFER
========================================================================

Queue<Integer> buffer

========================================================================

Acts as:
shared storage area.

========================================================================
BUFFER CAPACITY
========================================================================

CAPACITY = 5

========================================================================

Maximum:
5 items allowed.

========================================================================
PRODUCER FLOW
========================================================================

Producer thread:
========================================================================

1. checks buffer full or not
2. if full → wait()
3. else add item
4. notify consumer

========================================================================
CONSUMER FLOW
========================================================================

Consumer thread:
========================================================================

1. checks buffer empty or not
2. if empty → wait()
3. else remove item
4. notify producer

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Both methods are:
========================================================================

synchronized

========================================================================

Why?
========================================================================

Because both threads access:
same shared buffer.

========================================================================

Need thread safety.

========================================================================
WHY while LOOP USED?
========================================================================

VERY IMPORTANT INTERVIEW POINT.

========================================================================

Never use:
========================================================================

if(condition)

========================================================================

Use:
========================================================================

while(condition)

========================================================================

Because:
thread may wake up incorrectly
(spurious wakeup).

========================================================================

After waking:
condition must recheck.

========================================================================
PRODUCER FULL BUFFER CASE
========================================================================

while(buffer.size() == CAPACITY)

========================================================================

If buffer full:
========================================================================

wait()

========================================================================

Producer thread releases lock
and waits.

========================================================================
CONSUMER EMPTY BUFFER CASE
========================================================================

while(buffer.isEmpty())

========================================================================

If buffer empty:
========================================================================

wait()

========================================================================

Consumer thread waits.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

wait() does TWO things:
========================================================================

1. releases monitor lock
2. puts thread into waiting state

========================================================================
WHY notify() USED?
========================================================================

After producer adds item:
========================================================================

notify()

========================================================================

Signals waiting consumer.

========================================================================

After consumer removes item:
========================================================================

notify()

========================================================================

Signals waiting producer.

========================================================================
STEP-BY-STEP EXECUTION
========================================================================

Producer adds items:
========================================================================

1 2 3 4 5

========================================================================

Buffer becomes full.

========================================================================

Producer waits.

========================================================================

Consumer removes item:
========================================================================

1

========================================================================

Producer notified.

========================================================================

Producer resumes production.

========================================================================
OUTPUT CAN BE
========================================================================

Producer-Thread produced : 1

Consumer-Thread consumed : 1

========================================================================

Producer-Thread produced : 2

Producer-Thread produced : 3

========================================================================

Buffer Full. Producer waiting...

========================================================================

Consumer-Thread consumed : 2

========================================================================
VERY IMPORTANT OBSERVATION
========================================================================

Producer and consumer coordinate safely.

========================================================================

No buffer overflow.

========================================================================

No buffer underflow.

========================================================================
WHY THIS EXAMPLE IMPORTANT?
========================================================================

This is FOUNDATION of:
- Kafka
- RabbitMQ
- async systems
- messaging queues

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

wait(), notify(), notifyAll()
must be called inside:
========================================================================

synchronized block/method

========================================================================

Otherwise:
IllegalMonitorStateException.

========================================================================
IMPORTANT LIMITATION
========================================================================

Manual wait/notify handling:
- complex
- error-prone

========================================================================

Modern systems prefer:
========================================================================

BlockingQueue

========================================================================
REAL LIFE ANALOGY
========================================================================

Restaurant kitchen:
========================================================================

Cook prepares food.

========================================================================

If kitchen shelf full:
cook waits.

========================================================================

Delivery boy takes food.

========================================================================

Cook resumes.

========================================================================

Exactly same concept.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Messaging systems
2. Task queues
3. Streaming systems
4. Async event processing
5. Background workers

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Safe thread coordination
2. Efficient waiting mechanism
3. No busy waiting
4. Better producer-consumer balancing

========================================================================
IMPORTANT LIMITATION
========================================================================

Manual synchronization complexity.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why synchronized used here?

→ Shared buffer accessed by multiple threads.

========================================================================

2. Why while used instead of if?

→ To handle spurious wakeups safely.

========================================================================

3. What does wait() do?

→ Releases lock and waits.

========================================================================

4. What does notify() do?

→ Wakes one waiting thread.

========================================================================

5. Why Producer waits?

→ Buffer full.

========================================================================

6. Why Consumer waits?

→ Buffer empty.

========================================================================

7. Which exception occurs without synchronized?

→ IllegalMonitorStateException.

========================================================================

8. Modern replacement of wait/notify Producer Consumer?

→ BlockingQueue

========================================================================

MOST IMPORTANT INTERVIEW LINE

Producer Consumer using wait/notify
coordinates producer and consumer threads
through synchronized shared buffer communication.

========================================================================

*/