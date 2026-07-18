package EngineeringDigest.collections.list.linkedList;

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListExamples {

    public static void main(String[] args) {
        // ll -- like multiple locker each locker contain another locker address
        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.get(2);
        ll.get(0);

        ll.addFirst(0);
        ll.addLast(100);
        ll.getFirst();
        ll.getLast();

        System.out.println(ll);

        LinkedList<String> ll2 = new LinkedList<>(Arrays.asList("cat", "dog", "lion"));
        System.out.println(ll2);
    }
}
