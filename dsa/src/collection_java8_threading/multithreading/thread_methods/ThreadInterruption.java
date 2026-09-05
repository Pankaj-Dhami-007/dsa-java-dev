package multithreading.thread_methods;

public class ThreadInterruption {

    static void main() throws InterruptedException {

        MyThread3 t1 = new MyThread3();

        t1.start();

        Thread.sleep(1000);

        // Sends an interrupt signal to t1
        t1.interrupt();
    }
}

class MyThread3 extends Thread {

    @Override
    public void run() {

        while (!isInterrupted()) {
            System.out.println("Thread is working...");
        }

        System.out.println("Thread interrupted");
    }
}