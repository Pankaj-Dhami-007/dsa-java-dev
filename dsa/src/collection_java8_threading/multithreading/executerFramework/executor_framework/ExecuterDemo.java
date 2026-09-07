package multithreading.executor_framework;

import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExecuterDemo {

    static void main() {

        // normal thread

//        Thread t1 = new Thread(
//                ()-> System.out.println(" run normal thread")
//        );

        Executor executor = command -> {// execute impl
            Thread t1 = new Thread(command);
             t1.start();
        };
        executor.execute(()-> System.out.println("Task is running  "));// runnable impl

        Runnable command = () -> System.out.println("Task is running");
        executor.execute(command);


        Executor executor1 = new Executor() {

            @Override
            public void execute(Runnable command) {

                Thread t1 = new Thread(command);
                t1.start();
            }
        };

        executor1.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task is running");
            }
        });


        Function<Integer, Integer> square = number -> number * number;
        System.out.println(square.apply(5));

        Supplier<String> message = () -> "Hello from Supplier";
        System.out.println(message.get());
    }
}
