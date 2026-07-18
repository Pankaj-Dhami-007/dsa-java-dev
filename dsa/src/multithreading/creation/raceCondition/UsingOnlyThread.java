package multithreading.creation.raceCondition;

class CounterClass{
    private int count;
    public void increment(){
        count++;
    }

    public int getCount(){
        return count;
    }
}

class MyThread extends Thread{
    private CounterClass counter;
    public MyThread(CounterClass counter){
        this.counter = counter;
    }
    
    @Override
    public void run(){
        for (int i = 1; i <= 1000; i++) {
            counter.increment();
        }
    }
}

public class UsingOnlyThread {

    public static void main(String[] args) throws InterruptedException{
        CounterClass counterClass = new CounterClass();
        MyThread t1 = new MyThread(counterClass);
        MyThread t2 = new MyThread(counterClass);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + counterClass.getCount());

    }
}
