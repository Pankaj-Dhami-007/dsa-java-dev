package multithreading.thread_methods;

public class YieldVsThreadPriority {

    static void main() {

        Thread t1 = new Thread(() -> {
            Thread.yield();
            System.out.println("Thread-1 is running");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Thread-2 is running");
        });

        // Sets higher priority for t1
        t1.setPriority(Thread.MAX_PRIORITY);

        // Returns priority
        System.out.println("t1 Priority: " + t1.getPriority());

        t1.start();
        t2.start();
    }
}

/**

| `yield()`                                                      | `setPriority()`                                          |
| -------------------------------------------------------------- | -------------------------------------------------------- |
| Current thread voluntarily gives other threads a chance to run | Sets a relative scheduling priority for a thread         |
| Called while thread is executing                               | Usually set before starting the thread                   |
| A scheduler hint; not guaranteed                               | Also a scheduler hint/platform-dependent; not guaranteed |
| `Thread.yield()`                                               | `thread.setPriority()`                                   |


 */