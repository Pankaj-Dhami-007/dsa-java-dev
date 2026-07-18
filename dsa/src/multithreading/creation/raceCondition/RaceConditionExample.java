package multithreading.creation.raceCondition;

class Counter{
    private int count;
    public void increment(){
        count++;
    }
    public int getCount(){
        return count;
    }
}

//Solution 1: Synchronized Method
class SynchronizedCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}


//Solution 2: Synchronized Block
class BlockSynchronizedCounter {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized(lock) {
            count++;
        }
    }

    public int getCount() {
        synchronized(lock) {
            return count;
        }
    }
}

public class RaceConditionExample {

    public static void main(String[] args) throws InterruptedException{

        Counter counter = new Counter();

        Thread t1 = new Thread(
                ()->{
                    for (int i =0; i< 1000; i++){
                        counter.increment();
                    }
                }
        );

        Thread t2 = new Thread(
                ()->{
                    for (int i =0; i< 1000; i++){
                        counter.increment();
                    }
                }
        );

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Expected: 2000, but might get less due to race condition
        System.out.println("Final count: " + counter.getCount());

    }
}
