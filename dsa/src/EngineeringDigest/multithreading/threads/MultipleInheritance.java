package EngineeringDigest.multithreading.threads;

// *  Case 1 — B is Normal Class
// Need threading + already extending another class

// Why Runnable is preferred over Thread?

//Because:
//Java does not support multiple inheritance
//Runnable separates task from thread
//Better design
//Allows extending another class

class B {

    void show() {
        System.out.println("B class");
    }
}

class A extends B implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

public class MultipleInheritance {
    public static void main(String[] args) {

        A obj = new A();
        Thread t1 = new Thread(obj);
        t1.start();
    }
}

/*

Case 2 — B extends Thread

class B extends Thread {

}

class A extends B {

}

now A automatically becomes a Thread class child

class B extends Thread {

}

class A extends B {

    @Override
    public void run() {
        System.out.println("A thread running");
    }
}

public class Demo {

    public static void main(String[] args) {

        A t1 = new A();

        t1.start();
    }
}

 */
