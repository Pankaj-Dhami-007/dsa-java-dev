package EngineeringDigest.multithreading.threadlocal;

public class ThreadLocalExample {

    // thread local variable
    private static ThreadLocal<String> userContext =
            new ThreadLocal<>();

    public static void main(String[] args)
            throws InterruptedException {

        // user 1 thread
        Thread t1 = new Thread(() -> {

            // storing thread-specific value
            userContext.set("Pankaj");

            System.out.println(
                    Thread.currentThread().getName()
                            + " stored user : "
                            + userContext.get()
            );

            try {

                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " retrieved user : "
                            + userContext.get()
            );

            // cleanup
            userContext.remove();
        });

        // user 2 thread
        Thread t2 = new Thread(() -> {

            // storing thread-specific value
            userContext.set("Rahul");

            System.out.println(
                    Thread.currentThread().getName()
                            + " stored user : "
                            + userContext.get()
            );

            try {

                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " retrieved user : "
                            + userContext.get()
            );

            // cleanup
            userContext.remove();
        });

        t1.setName("Request-Thread-1");
        t2.setName("Request-Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println();
        System.out.println(
                "Main thread completed"
        );
    }
}

/*

========================================================================
                    THREADLOCAL EXAMPLE
========================================================================

This is MOST IMPORTANT practical example
for understanding ThreadLocal.

========================================================================
MAIN IDEA
========================================================================

Each thread stores
its OWN separate data.

========================================================================

Data NOT shared between threads.

========================================================================
THREADLOCAL VARIABLE
========================================================================

ThreadLocal<String> userContext

========================================================================

Meaning:
every thread stores
its own String value separately.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

ThreadLocal object shared.

========================================================================

BUT:
stored values separate per thread.

========================================================================
THREAD-1 FLOW
========================================================================

Request-Thread-1:
========================================================================

userContext.set("Pankaj")

========================================================================

Stores:
========================================================================

"Pankaj"

========================================================================

ONLY for:
Request-Thread-1

========================================================================
THREAD-2 FLOW
========================================================================

Request-Thread-2:
========================================================================

userContext.set("Rahul")

========================================================================

Stores:
========================================================================

"Rahul"

========================================================================

ONLY for:
Request-Thread-2

========================================================================
VERY IMPORTANT OBSERVATION
========================================================================

Both threads use:
========================================================================

same variable name

========================================================================

BUT:
values remain separate.

========================================================================
WHY?
========================================================================

Because ThreadLocal creates:
thread-specific storage.

========================================================================
OUTPUT CAN BE
========================================================================

Request-Thread-1 stored user : Pankaj

Request-Thread-2 stored user : Rahul

========================================================================

(after 3 seconds)

Request-Thread-1 retrieved user : Pankaj

Request-Thread-2 retrieved user : Rahul

========================================================================
IMPORTANT OBSERVATION
========================================================================

No data mixing occurs.

========================================================================

Thread-1 NEVER sees:
========================================================================

Rahul

========================================================================

Thread-2 NEVER sees:
========================================================================

Pankaj

========================================================================
WHY NO SYNCHRONIZATION NEEDED?
========================================================================

Because data NOT shared.

========================================================================

Each thread accesses:
its own private copy.

========================================================================
IMPORTANT METHODS
========================================================================

1. set()
========================================================================

Stores value for current thread.

========================================================================
2. get()
========================================================================

Returns current thread value.

========================================================================
3. remove()
========================================================================

Removes thread-specific value.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

Always call:
========================================================================

remove()

========================================================================

Especially in:
thread pools.

========================================================================

Otherwise:
memory leaks possible.

========================================================================
WHY MEMORY LEAK POSSIBLE?
========================================================================

Thread pool threads reused.

========================================================================

Old thread-local data may remain attached
to reused thread.

========================================================================

Next request may accidentally see:
old leftover data.

========================================================================
THIS IS VERY IMPORTANT
========================================================================

ThreadLocal cleanup mandatory
in enterprise applications.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
office employees have:
personal lockers.

========================================================================

Same locker type,
but separate contents.

========================================================================

Employees cannot see others' items.

========================================================================

Exactly same concept.

========================================================================
WHY THREADLOCAL IMPORTANT IN SPRING?
========================================================================

Spring internally uses ThreadLocal for:
- transactions
- security context
- request context
- Hibernate session

========================================================================
VERY IMPORTANT ENTERPRISE UNDERSTANDING
========================================================================

One request usually handled by:
one worker thread.

========================================================================

ThreadLocal stores:
request-specific data safely.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Logged-in user info
2. Transaction context
3. Request tracing
4. Logging correlation ID
5. Database session handling

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Thread isolation
2. No synchronization required
3. Cleaner request context handling
4. Better enterprise architecture

========================================================================
IMPORTANT LIMITATION
========================================================================

Improper cleanup causes:
memory leaks.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why ThreadLocal used?

→ To maintain separate data per thread.

========================================================================

2. Is ThreadLocal shared between threads?

→ No

========================================================================

3. Which methods commonly used?

→ set(), get(), remove()

========================================================================

4. Why remove() important?

→ Prevent memory leaks in thread pools.

========================================================================

5. Does ThreadLocal require synchronization?

→ No

========================================================================

6. Real-world use case of ThreadLocal?

→ Request context management.

========================================================================

7. Which frameworks heavily use ThreadLocal?

→ Spring and Hibernate.

========================================================================

MOST IMPORTANT INTERVIEW LINE

ThreadLocal provides thread-confined storage
where every thread maintains
its own independent copy of data.

========================================================================

*/