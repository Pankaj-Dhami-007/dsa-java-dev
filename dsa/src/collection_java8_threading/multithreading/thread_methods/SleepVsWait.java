package multithreading.thread_methods;

// sleep() pauses a thread for a specified time without releasing its lock,
// whereas wait() releases the object's lock and waits until notified (or interrupted/timed out).
public class SleepVsWait {

    private static final Object lock = new Object();

    static void main() throws InterruptedException {

        // Pauses the current thread for 2 seconds
        Thread.sleep(2000);

        synchronized (lock) {
            // Releases the lock and waits for notification
            lock.wait();
        }
    }
}