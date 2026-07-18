package multithreading.creation;


class MyThread extends Thread{

    public String threadName;
    public MyThread(String name){
        this.threadName = name;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(threadName+"- count: "+i);
            try{
                Thread.sleep(2000);// Pause for 1 second
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted");
            }
        }
    }
}

public class UsingThreadClass {

    public static void main(String[] args) {

        Thread mainThread = Thread.currentThread();
        System.out.println("Current thread: " + mainThread.getName());
        System.out.println("Thread ID: " + mainThread.getId());
        System.out.println("Priority: " + mainThread.getPriority());

       MyThread thread1 = new MyThread("Thread-1");
       MyThread thread2 = new MyThread("Thread-2");

        thread1.start(); // Start thread1
        thread2.start(); // Start thread2


    }
}
