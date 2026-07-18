package interviewQ.java8;

import java.util.Arrays;
import java.util.List;

public class ForEachMethod {


    /*
    forEach--->
    Order is NOT guaranteed when used with parallel streams.

    forEachOrdered()------>
Ensures encounter order is maintained, even in parallel streams.
Slightly slower than forEach in parallel because it has to preserve order.
     */
    public static void printList(){
       List<Character> list =  Arrays.asList('A', 'B', 'C','D');
       list.stream()
               .forEach(System.out :: print);
        System.out.println();
        list.parallelStream()
                .forEach(System.out::print);

    }
    public static void main(String[] args) {

        printList();
    }
}
