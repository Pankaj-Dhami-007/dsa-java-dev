package EngineeringDigest.multithreading.communication;

public class ThreadCommunication {

    public static void main(String[] args) {

    }
}

/*

========================================================================
                    THREAD COMMUNICATION
========================================================================

Thread communication is one of the MOST IMPORTANT
multithreading concepts.

========================================================================
WHY THREAD COMMUNICATION EXISTS?
========================================================================

Synchronization solves:
        data inconsistency problem.

========================================================================

BUT synchronization creates another problem.

========================================================================

PROBLEM
========================================================================

Suppose:
Thread-1 depends on Thread-2 result.

========================================================================

Example:
Producer thread creates data.
Consumer thread consumes data.

========================================================================

QUESTION
========================================================================

What if consumer executes BEFORE producer?

========================================================================

Consumer gets:
- null
- empty data
- incorrect result

========================================================================

So threads sometimes need:
        coordination/communication

========================================================================

THIS IS WHY
========================================================================

Thread communication exists
to coordinate execution between threads.

========================================================================
WHAT IS THREAD COMMUNICATION?
========================================================================

Thread communication is mechanism where
threads communicate with each other
about execution state and resource availability.

========================================================================

SIMPLE DEFINITION
========================================================================

Thread communication allows one thread
to pause execution and another thread
to notify it when required condition becomes true.

========================================================================
MAIN GOAL
========================================================================

1. Coordination between threads
2. Avoid busy waiting
3. Efficient resource sharing
4. Producer-consumer synchronization

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Without communication,
threads may:
- waste CPU
- continuously check condition
- produce wrong results

========================================================================
EXAMPLE PROBLEM
========================================================================

Suppose:

Consumer thread continuously checks:

        while(data == null)

========================================================================

THIS IS BAD
========================================================================

Because:
CPU continuously wasted.

This is called:
        Busy Waiting

========================================================================
THREAD COMMUNICATION SOLVES THIS
========================================================================

Instead of continuous checking:

Consumer thread:
        WAITS

Producer thread:
        NOTIFIES

========================================================================

This is efficient.

========================================================================
HOW THREAD COMMUNICATION ACHIEVED?
========================================================================

Using Object class methods:

1. wait()
2. notify()
3. notifyAll()

========================================================================

VERY IMPORTANT
========================================================================

These methods belong to:
        Object class

NOT Thread class.

========================================================================

Because:
communication happens through shared object monitor.

========================================================================
METHODS USED
========================================================================

1. wait()
2. notify()
3. notifyAll()

========================================================================
1. wait()
========================================================================

        object.wait();

PURPOSE
--------
Makes current thread WAIT.

========================================================================

IMPORTANT
========================================================================

Thread releases lock and enters:
        WAITING state

========================================================================

Thread remains waiting until:
- notify()
- notifyAll()

========================================================================

VERY IMPORTANT
========================================================================

wait() releases monitor lock.

========================================================================

This is HUGE difference from:
        sleep()

========================================================================
2. notify()
========================================================================

        object.notify();

PURPOSE
--------
Wakes ONE waiting thread.

========================================================================

IMPORTANT
========================================================================

notify() does NOT release lock immediately.

Lock released only after synchronized block ends.

========================================================================

Which waiting thread wakes?
========================================================================

Chosen by JVM scheduler.

No guarantee.

========================================================================
3. notifyAll()
========================================================================

        object.notifyAll();

PURPOSE
--------
Wakes ALL waiting threads.

========================================================================

Then threads compete again for lock.

========================================================================

Useful when:
multiple waiting threads exist.

========================================================================
IMPORTANT RULE
========================================================================

wait(), notify(), notifyAll()
must be called inside:
        synchronized block/method

========================================================================

Otherwise:
        IllegalMonitorStateException

========================================================================
WHY synchronized REQUIRED?
========================================================================

Because thread communication uses:
        object monitor lock

Thread must own monitor before communication.

========================================================================
THREAD STATES INVOLVED
========================================================================

wait()
--------
WAITING state

========================================================================

notify()/notifyAll()
---------------------
moves waiting thread to RUNNABLE

========================================================================
IMPORTANT DIFFERENCE
========================================================================

sleep()
--------
Does NOT release lock

========================================================================

wait()
-------
Releases lock

========================================================================
FLOW OF THREAD COMMUNICATION
========================================================================

Consumer thread
        ↓
No data available
        ↓
wait()
        ↓
releases lock and waits

========================================================================

Producer thread
        ↓
creates data
        ↓
notify()
        ↓
consumer wakes up

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
customer waiting for food.

========================================================================

Customer:
        wait()

========================================================================

Chef prepares food:
        notify()

========================================================================

Customer resumes eating.

========================================================================
COMMON USE CASES
========================================================================

1. Producer-Consumer problem
2. Chat systems
3. Messaging systems
4. Task scheduling
5. Thread pools
6. Resource sharing systems

========================================================================
PROBLEMS SOLVED BY THREAD COMMUNICATION
========================================================================

1. Busy Waiting
2. CPU wastage
3. Improper execution order
4. Resource coordination issues

========================================================================
BUSY WAITING
========================================================================

Thread continuously checking condition.

========================================================================

BAD EXAMPLE
========================================================================

while(flag == false) {

}

========================================================================

Consumes CPU unnecessarily.

========================================================================

wait()/notify() solves this efficiently.

========================================================================
IMPORTANT CONCEPT
========================================================================

Producer-Consumer Problem

========================================================================

Producer:
creates data

========================================================================

Consumer:
uses data

========================================================================

Communication coordinates both safely.

========================================================================
notify() vs notifyAll()
========================================================================

notify()
--------
Wakes one waiting thread.

========================================================================

notifyAll()
------------
Wakes all waiting threads.

========================================================================

notifyAll() safer in complex systems.

========================================================================
IMPORTANT INTERVIEW POINT
========================================================================

wait(), notify(), notifyAll()
are final methods of:
        Object class

========================================================================

NOT Thread class.

========================================================================
WHY OBJECT CLASS?
========================================================================

Because every object has:
        monitor lock

Communication tied to monitor.

========================================================================
IMPORTANT DIFFERENCE
========================================================================

wait()
--------
releases lock

sleep()
--------
does not release lock

========================================================================
COMMON ERRORS
========================================================================

1. Calling wait() outside synchronized
2. Forgetting notify()
3. Using notify() wrongly with multiple threads

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is thread communication?

→ Coordination mechanism between threads.

========================================================================

2. Why thread communication needed?

→ To coordinate dependent threads efficiently.

========================================================================

3. Which methods used in thread communication?

→ wait(), notify(), notifyAll()

========================================================================

4. Which class contains wait(), notify(), notifyAll()?

→ Object class

========================================================================

5. Why these methods in Object class not Thread class?

→ Because communication uses object monitor lock.

========================================================================

6. Does wait() release lock?

→ Yes

========================================================================

7. Does sleep() release lock?

→ No

========================================================================

8. Must wait() be inside synchronized block?

→ Yes

========================================================================

9. What happens if wait() called outside synchronized?

→ IllegalMonitorStateException

========================================================================

10. Difference between notify() and notifyAll()?

notify()
---------
wakes one thread

notifyAll()
-------------
wakes all threads

========================================================================

11. What problem does thread communication solve?

→ Busy waiting and thread coordination issues.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Thread communication allows threads
to coordinate execution efficiently
using wait(), notify(), and notifyAll().

========================================================================

*/