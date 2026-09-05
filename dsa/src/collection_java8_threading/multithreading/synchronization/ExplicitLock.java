package EngineeringDigest.multithreading.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {

    private int balance = 100;

    // explicit lock
    private final Lock lock = new ReentrantLock();

    // ================= PROBLEM CODE =================
    /*
    public synchronized void withdraw(int amount) {

        System.out.println(
                Thread.currentThread().getName()
                        + " attempting to withdraw "
                        + amount
        );

        // heavy task
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (balance >= amount) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " processing withdrawal"
            );

            balance = balance - amount;

            System.out.println(
                    Thread.currentThread().getName()
                            + " completed withdrawal"
            );

            System.out.println(
                    "Remaining Balance : " + balance
            );

        } else {

            System.out.println(
                    Thread.currentThread().getName()
                            + " insufficient balance"
            );
        }
    }
    */

    // ================= FIXED CODE USING EXPLICIT LOCK =================
    public void withdraw(int amount) {

        System.out.println(
                Thread.currentThread().getName()
                        + " attempting to withdraw "
                        + amount
        );

        // tryLock prevents unnecessary waiting
        if (lock.tryLock()) {

            try {

                System.out.println(
                        Thread.currentThread().getName()
                                + " acquired lock"
                );

                // heavy task
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (balance >= amount) {

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " processing withdrawal"
                    );

                    balance = balance - amount;

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " completed withdrawal"
                    );

                    System.out.println(
                            "Remaining Balance : "
                                    + balance
                    );

                } else {

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " insufficient balance"
                    );
                }

            } finally {

                System.out.println(
                        Thread.currentThread().getName()
                                + " released lock"
                );

                lock.unlock();
            }

        } else {

            System.out.println(
                    Thread.currentThread().getName()
                            + " could not acquire lock"
            );

            System.out.println(
                    Thread.currentThread().getName()
                            + " doing other work instead of waiting"
            );
        }
    }
}

public class ExplicitLock {

    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        Runnable task = new Runnable() {

            @Override
            public void run() {

                account.withdraw(70);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}

/*

========================================================================
                    PROBLEM WITH synchronized
========================================================================

Suppose withdraw() is synchronized.

========================================================================

Thread-1 enters withdraw()
        ↓
acquires object lock
        ↓
sleep(5000)
        ↓
Thread-2 tries withdraw()
        ↓
BLOCKED for 5 seconds

========================================================================

PROBLEM
========================================================================

Even if Thread-2 wants to do something else,
it must WAIT.

This reduces:
- concurrency
- performance
- responsiveness

========================================================================

WHY THIS HAPPENS?
========================================================================

synchronized uses:
        intrinsic lock

Thread waits indefinitely until lock released.

========================================================================

HEAVY TASK ISSUE
========================================================================

Suppose:
- database call
- API request
- file processing

takes long time.

Other threads remain blocked.

========================================================================

FIX USING EXPLICIT LOCK
========================================================================

We use:
        ReentrantLock

========================================================================

ADVANTAGE
========================================================================

Instead of waiting forever,
thread can:

        tryLock()

========================================================================

IF LOCK AVAILABLE
========================================================================

Thread acquires lock
and executes critical section.

========================================================================

IF LOCK NOT AVAILABLE
========================================================================

Thread immediately continues other work.

No blocking.

========================================================================

IMPORTANT METHOD
========================================================================

        lock.tryLock()

========================================================================

RETURNS
========================================================================

true
------
lock acquired

false
-------
lock unavailable

========================================================================

IMPORTANT BENEFIT
========================================================================

Improves:
- responsiveness
- concurrency
- flexibility

========================================================================

WHY finally BLOCK USED?
========================================================================

Lock MUST always release.

Otherwise:
        deadlock risk

========================================================================

IMPORTANT RULE
========================================================================

Whenever lock.lock() OR tryLock() used,
unlock() should be inside finally block.

========================================================================

FLOW OF FIXED CODE
========================================================================

Thread-1
--------
acquires lock
        ↓
sleep(5 sec)
        ↓
withdraw money
        ↓
unlock

========================================================================

Thread-2
--------
tries lock
        ↓
lock unavailable
        ↓
does not wait forever
        ↓
continues other work

========================================================================

OUTPUT CAN BE
========================================================================

Thread-1 attempting to withdraw 70
Thread-1 acquired lock

Thread-2 attempting to withdraw 70
Thread-2 could not acquire lock
Thread-2 doing other work instead of waiting

Thread-1 processing withdrawal
Thread-1 completed withdrawal
Remaining Balance : 30
Thread-1 released lock

========================================================================

IMPORTANT DIFFERENCE
========================================================================

synchronized
--------------
Thread waits indefinitely.

ReentrantLock + tryLock()
--------------------------
Thread can avoid waiting.

========================================================================

WHY EXPLICIT LOCK IS POWERFUL?
========================================================================

Provides:
- manual locking
- tryLock()
- interruptible locking
- fairness
- timeout locking

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. Why explicit lock used?

→ More flexible control than synchronized.

========================================================================

2. Which class used here?

→ ReentrantLock

========================================================================

3. What is advantage of tryLock()?

→ Avoids indefinite waiting.

========================================================================

4. Difference between synchronized and ReentrantLock?

synchronized
--------------
Automatic lock management

ReentrantLock
---------------
Manual lock management

========================================================================

5. Why unlock() inside finally block?

→ Ensures lock always releases.

========================================================================

6. What happens if unlock() forgotten?

→ Deadlock risk.

========================================================================

7. What does tryLock() return?

→ true or false

========================================================================

8. Which package contains Lock interface?

→ java.util.concurrent.locks

========================================================================

9. Is ReentrantLock reentrant?

→ Yes

========================================================================

10. What is major problem solved here?

→ Long waiting/blocking issue.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Explicit locks provide advanced synchronization
features like tryLock(),
which improves concurrency and avoids unnecessary blocking.

========================================================================

*/