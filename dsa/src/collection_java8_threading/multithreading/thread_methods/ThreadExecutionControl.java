package multithreading.thread_methods;

public class ThreadExecutionControl {
    static void main() {

        System.out.println("Main thread is started ");
        MyThread t1 = new MyThread();

        // Starts a new thread and internally calls run()
        t1.start();

        try {
            // Pauses the current thread for the specified time
            Thread.sleep(1000);

            // Waits until t1 completes
            //t1.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Sends an interrupt signal to t1
        //t1.interrupt();

        System.out.println("Main thread is finished ");
    }
}

class MyThread extends Thread {

    @Override
    public void run() {
        // Contains the task executed by the thread
        System.out.println("new Thread is running");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("new Thread is finished ");
    }
}