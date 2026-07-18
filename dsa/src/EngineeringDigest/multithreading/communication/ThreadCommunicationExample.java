package EngineeringDigest.multithreading.communication;

// shared resource
class SharedResource {

    private int data;

    // condition
    private boolean hasData = false;

    // producer method
    public synchronized void produce(int value) {

        // if data already available
        // producer waits
        while (hasData) {

            try {

                System.out.println(
                        Thread.currentThread().getName()
                                + " waiting because data already exists"
                );

                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // producing data
        data = value;

        System.out.println(
                Thread.currentThread().getName()
                        + " produced : "
                        + data
        );

        hasData = true;

        // notify consumer
        notify();

        System.out.println(
                Thread.currentThread().getName()
                        + " notified consumer"
        );
    }

    // consumer method
    public synchronized void consume() {

        // if no data available
        // consumer waits
        while (!hasData) {

            try {

                System.out.println(
                        Thread.currentThread().getName()
                                + " waiting because no data available"
                );

                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // consuming data
        System.out.println(
                Thread.currentThread().getName()
                        + " consumed : "
                        + data
        );

        hasData = false;

        // notify producer
        notify();

        System.out.println(
                Thread.currentThread().getName()
                        + " notified producer"
        );
    }
}

public class ThreadCommunicationExample {

    public static void main(String[] args) {

        SharedResource resource =
                new SharedResource();

        // producer thread
        Thread producer = new Thread(new Runnable() {

            @Override
            public void run() {

                for (int i = 1; i <= 5; i++) {

                    resource.produce(i);

                    try {

                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // consumer thread
        Thread consumer = new Thread(new Runnable() {

            @Override
            public void run() {

                for (int i = 1; i <= 5; i++) {

                    resource.consume();

                    try {

                        Thread.sleep(1500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        producer.setName("Producer");
        consumer.setName("Consumer");

        producer.start();
        consumer.start();
    }
}

/*

========================================================================
                THREAD COMMUNICATION EXAMPLE
========================================================================

This is classic:
        Producer-Consumer Problem

========================================================================

Producer
---------
creates data

========================================================================

Consumer
---------
uses data

========================================================================

SHARED RESOURCE
========================================================================

SharedResource object shared between:
- Producer thread
- Consumer thread

========================================================================
IMPORTANT VARIABLES
========================================================================

data
------
actual shared data

========================================================================

hasData
---------
condition variable

true
------
data available

false
-------
no data available

========================================================================
FLOW OF PRODUCER
========================================================================

Producer checks:

        while(hasData)

========================================================================

IF TRUE
========================================================================

Means:
old data not consumed yet.

========================================================================

So producer:
        wait()

========================================================================

IMPORTANT
========================================================================

wait() releases lock.

========================================================================

Producer enters:
        WAITING state

========================================================================
WHEN PRODUCER RESUMES?
========================================================================

Consumer calls:
        notify()

========================================================================
FLOW OF CONSUMER
========================================================================

Consumer checks:

        while(!hasData)

========================================================================

IF TRUE
========================================================================

Means:
no data available.

========================================================================

Consumer waits using:
        wait()

========================================================================

Producer later creates data
and calls:
        notify()

========================================================================

Consumer wakes up.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Producer and Consumer communicate
through:
        shared object monitor

========================================================================

Communication methods:
- wait()
- notify()

========================================================================
WHY synchronized USED?
========================================================================

Because:
wait() and notify()
require monitor ownership.

========================================================================

Without synchronized:
        IllegalMonitorStateException

========================================================================
IMPORTANT CONCEPT
========================================================================

wait()
--------
releases lock and waits

========================================================================

notify()
---------
wakes waiting thread

========================================================================

VERY IMPORTANT
========================================================================

notify() does NOT immediately release lock.

Lock releases only after synchronized block/method ends.

========================================================================
WHY while USED INSTEAD OF if?
========================================================================

IMPORTANT INTERVIEW QUESTION

========================================================================

Because:
thread should re-check condition
after waking up.

========================================================================

This prevents:
        Spurious Wakeup issues

========================================================================
OUTPUT CAN BE
========================================================================

Producer produced : 1
Producer notified consumer

Consumer consumed : 1
Consumer notified producer

Producer produced : 2
Producer notified consumer

========================================================================

Sequence may vary slightly.

========================================================================
THREAD STATES HERE
========================================================================

wait()
--------
WAITING

========================================================================

notify()
---------
WAITING → RUNNABLE

========================================================================
PROBLEM SOLVED HERE
========================================================================

Without communication:
consumer may consume before producer.

========================================================================

OR

Producer may overwrite old data.

========================================================================

Communication coordinates execution safely.

========================================================================
REAL LIFE ANALOGY
========================================================================

Chef = Producer

========================================================================

Customer = Consumer

========================================================================

Customer waits for food.
Chef notifies when food ready.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Which methods used for thread communication?

→ wait() and notify()

========================================================================

2. Which thread produces data?

→ Producer

========================================================================

3. Which thread consumes data?

→ Consumer

========================================================================

4. Does wait() release lock?

→ Yes

========================================================================

5. Why synchronized required?

→ wait()/notify() require monitor ownership.

========================================================================

6. Why while used instead of if?

→ To re-check condition after wakeup.

========================================================================

7. Which object used for communication?

→ SharedResource object

========================================================================

8. Which thread wakes consumer?

→ Producer using notify()

========================================================================

9. What problem solved here?

→ Thread coordination and busy waiting.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Producer-consumer communication uses
wait() and notify()
to coordinate dependent thread execution efficiently.

========================================================================

*/