package EngineeringDigest.multithreading.synchronization;

import java.util.concurrent.locks.ReentrantReadWriteLock;

// shared resource
class Database {

    private int value = 10;

    // ReadWriteLock object
    private final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();

    // read operation
    public void readData() {

        // acquire read lock
        lock.readLock().lock();

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " started READING value : "
                            + value
            );

            // simulate reading time
            Thread.sleep(3000);

            System.out.println(
                    Thread.currentThread().getName()
                            + " completed READING"
            );

        } catch (InterruptedException e) {

            e.printStackTrace();

        } finally {

            // release read lock
            lock.readLock().unlock();
        }
    }

    // write operation
    public void writeData(int newValue) {

        // acquire write lock
        lock.writeLock().lock();

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " started WRITING"
            );

            // simulate writing time
            Thread.sleep(3000);

            value = newValue;

            System.out.println(
                    Thread.currentThread().getName()
                            + " updated value to : "
                            + value
            );

        } catch (InterruptedException e) {

            e.printStackTrace();

        } finally {

            // release write lock
            lock.writeLock().unlock();
        }
    }
}

public class ReadWriteLock {

    public static void main(String[] args) {

        Database db = new Database();

        // reader task
        Runnable readTask = new Runnable() {

            @Override
            public void run() {

                db.readData();
            }
        };

        // writer task
        Runnable writeTask = new Runnable() {

            @Override
            public void run() {

                db.writeData(100);
            }
        };

        Thread t1 = new Thread(readTask);
        Thread t2 = new Thread(readTask);
        Thread t3 = new Thread(writeTask);

        t1.setName("Reader-1");
        t2.setName("Reader-2");
        t3.setName("Writer-1");

        t1.start();
        t2.start();
        t3.start();
    }
}

/*

========================================================================
                        READ WRITE LOCK
========================================================================

ReadWriteLock is advanced locking mechanism
used when:

        READ operations are frequent
        and
        WRITE operations are less frequent.

========================================================================

MAIN IDEA
========================================================================

Multiple threads can READ simultaneously.

BUT

Only one thread can WRITE at a time.

========================================================================

VERY IMPORTANT RULES
========================================================================

READ LOCK RULE
========================================================================

Multiple readers allowed together.

========================================================================

WRITE LOCK RULE
========================================================================

Only one writer allowed.

========================================================================

ALSO IMPORTANT
========================================================================

When writer active:
- no readers allowed
- no other writers allowed

========================================================================

WHY THIS EXISTS?
========================================================================

Suppose:
100 threads only reading data.

Using synchronized:
        only one thread reads at one time.

========================================================================

THIS REDUCES PERFORMANCE
========================================================================

Because reading usually does NOT modify data.

Multiple reads are safe together.

========================================================================

ReadWriteLock solves this problem.

========================================================================
FLOW OF YOUR PROGRAM
========================================================================

Reader-1 starts reading
        ↓
Reader-2 also starts reading

========================================================================

IMPORTANT
========================================================================

Both readers execute simultaneously.

========================================================================

Now Writer-1 tries writing.

========================================================================

Writer-1 must WAIT
until all readers finish.

========================================================================

WHY?
========================================================================

Writing modifies shared data.

So exclusive access required.

========================================================================

OUTPUT CAN BE
========================================================================

Reader-1 started READING value : 10
Reader-2 started READING value : 10

Reader-1 completed READING
Reader-2 completed READING

Writer-1 started WRITING
Writer-1 updated value to : 100

========================================================================

VERY IMPORTANT UNDERSTANDING
========================================================================

Readers can work together.

Writers always need exclusive access.

========================================================================

LOCKS USED HERE
========================================================================

1. readLock()
2. writeLock()

========================================================================

READ LOCK
========================================================================

        lock.readLock().lock();

Allows:
        multiple readers together

========================================================================

WRITE LOCK
========================================================================

        lock.writeLock().lock();

Allows:
        only one writer

========================================================================

IMPORTANT DIFFERENCE
========================================================================

synchronized
--------------
Only one thread total.

ReadWriteLock
--------------
Multiple readers allowed.

========================================================================

PERFORMANCE BENEFIT
========================================================================

Huge improvement in:
        read-heavy applications.

========================================================================

REAL INDUSTRY USE CASES
========================================================================

1. Cache systems
2. Configuration systems
3. Banking reports
4. Inventory lookup
5. Product catalog systems
6. Analytics dashboards

========================================================================

REAL LIFE ANALOGY
========================================================================

Library Example

READERS
--------
Many students can read books together.

WRITER
--------
Only librarian updates records alone.

========================================================================

IMPORTANT CLASS
========================================================================

        ReentrantReadWriteLock

Package:
        java.util.concurrent.locks

========================================================================

WHY "REENTRANT"?
========================================================================

Same thread can re-acquire same lock multiple times.

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. What is ReadWriteLock?

→ Lock separating read and write operations.

========================================================================

2. Why ReadWriteLock used?

→ Improve performance in read-heavy systems.

========================================================================

3. Can multiple threads read simultaneously?

→ Yes

========================================================================

4. Can multiple threads write simultaneously?

→ No

========================================================================

5. Can read and write happen together?

→ No

========================================================================

6. Which class implements ReadWriteLock?

→ ReentrantReadWriteLock

========================================================================

7. Major advantage over synchronized?

→ Better concurrency for read operations.

========================================================================

8. When should ReadWriteLock be used?

→ When reads are frequent and writes are less frequent.

========================================================================

MOST IMPORTANT INTERVIEW LINE

ReadWriteLock improves concurrency
by allowing multiple readers simultaneously
while maintaining exclusive access for writers.

========================================================================

*/