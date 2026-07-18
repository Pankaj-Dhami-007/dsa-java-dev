package interviewQ.ListInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListCreation {

    static void ArraysDotAsList(){
        /*
        modifiable list
        Converts an array to a List.
        The returned list is fixed-size (cannot add/remove elements).UnsupportedOperationException
        But you can modify elements inside it (because it’s backed by the original array).
        changes do in the arr ----> changes in list
         */
        String[] courses = new String[] {"java", "springboot", "angular"};
       List<String> list = Arrays.asList(courses);

    }

    static void ListDotOf(){

        /*
        Creates an immutable list directly.
        No modifications allowed (no add, remove, set).
        Null elements are not allowed (throws NullPointerException).
         */

        List<String> list = List.of("A", "B", "C");
        // list.set(1, "X");   // ❌ UnsupportedOperationException
        // list.add("D");      // ❌ UnsupportedOperationException
    }

    static void unmodifiableList(){

        /*
        Wraps an existing list in an unmodifiable view.
        The wrapper list itself cannot be modified (no add, remove, set).
        But if the underlying list changes, the unmodifiable list reflects the change.
         */

        List<String> base = new ArrayList<>();
        base.add("A");
        base.add("B");
        List<String> unmodifiable = Collections.unmodifiableList(base);
        // unmodifiable.add("C");   // ❌ UnsupportedOperationException
        base.set(1, "X");   // ✅ allowed
        System.out.println(unmodifiable); // [A, X] → reflects base changes
    }
    public static void main(String[] args) {

      unmodifiableList();
    }
}
