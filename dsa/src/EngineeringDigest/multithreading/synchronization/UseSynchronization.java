package EngineeringDigest.multithreading.synchronization;

// shared resource
class Counter {
    private int count = 0;

    // synchronized method
    public synchronized void increment() {
        count++;
    }
    public int getCount() {
        return count;
    }
}

// thread class
class MyThread extends Thread {

    // HAS-A relationship
    private Counter counter;

    // constructor injection
    public MyThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {

        // task
        for (int i = 1; i <= 1000; i++) {
            counter.increment();
        }
    }
}

public class UseSynchronization {

    public static void main(String[] args)
            throws InterruptedException {

        // one shared resource
        Counter counter = new Counter();

        // multiple threads sharing same counter
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);

        t1.start();
        t2.start();

        // wait for both threads
        t1.join();
        t2.join();

        System.out.println(counter.getCount());
    }
}

/*

========================================================================
                    SYNCHRONIZATION EXAMPLE
========================================================================

Counter class
--------------
Shared Resource

MyThread class
---------------
Worker threads

========================================================================

VERY IMPORTANT UNDERSTANDING
========================================================================

Both threads share SAME Counter object.

        Counter counter = new Counter();

        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);

========================================================================

HAS-A RELATIONSHIP
========================================================================

MyThread HAS-A Counter object.

        private Counter counter;

========================================================================

WITHOUT SYNCHRONIZATION
========================================================================

increment() becomes unsafe.

Multiple threads may update count simultaneously.

========================================================================

EXAMPLE
========================================================================

Suppose count = 5

Thread-1 reads:
        5

Thread-2 reads:
        5

Thread-1 writes:
        6

Thread-2 writes:
        6

EXPECTED:
        7

ACTUAL:
        6

========================================================================

This problem is:
        Race Condition

========================================================================

WHY synchronized USED?
========================================================================

        public synchronized void increment()

Only one thread can execute increment()
at one time.

========================================================================

INTERNAL WORKING
========================================================================

Thread-1 enters increment()
        ↓
acquires object lock
        ↓
Thread-2 waits
        ↓
Thread-1 completes
        ↓
lock released
        ↓
Thread-2 enters

========================================================================

LOCK USED HERE
========================================================================

Object Lock of:
        Counter object

========================================================================

IMPORTANT
========================================================================

Since both threads share SAME object,
same lock is shared.

========================================================================

EXPECTED OUTPUT
========================================================================

2000

========================================================================

WHY 2000?
========================================================================

Thread-1 increments:
        1000 times

Thread-2 increments:
        1000 times

Total:
        2000

========================================================================

WITHOUT synchronized
========================================================================

Output may be:
- 1890
- 1975
- 1992

because updates get lost.

========================================================================

THREAD SAFETY
========================================================================

Synchronization makes increment():
        Thread Safe

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. Why Counter object shared?

→ To demonstrate synchronization on shared resource.

========================================================================

2. Which object lock used here?

→ Counter object lock.

========================================================================

3. Why synchronized needed?

→ To prevent race condition.

========================================================================

4. What happens without synchronized?

→ Data inconsistency.

========================================================================

5. Which relationship used between MyThread and Counter?

→ HAS-A relationship.

========================================================================

6. Why join() used?

→ Main thread waits for t1 and t2 completion.

========================================================================

7. Is count++ atomic?

→ No

Internally:
1. read
2. increment
3. write

========================================================================

8. Which thread gets lock first?

→ Depends on OS Scheduler.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Synchronization ensures only one thread
accesses shared critical section at a time,
preventing race conditions.

========================================================================

*/