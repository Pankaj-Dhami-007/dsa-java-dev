package multithreading.thread_methods;

public class ThreadCommunication {

    private static final Object lock = new Object();

    static void main() {

        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Waiting...");
                    lock.wait();

                    System.out.println("Resumed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread notifyingThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Notifying...");
                lock.notify();
            }
        });

        waitingThread.start();

        notifyingThread.start();
    }
}

// wait()      → Thread ko wait karwata hai
// notify()    → Ek waiting thread ko signal deta hai
// notifyAll() → Saare waiting threads ko signal deta hai
// Ye teen methods Object class ke hain, Thread class ke nahi.