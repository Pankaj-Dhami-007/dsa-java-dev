package EngineeringDigest.collections.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Use removeIf for in-place removal, streams for transformation, and iterators when
// you need fine-grained control during iteration.

public class IteratorDemo {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : list) {
            System.out.println(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
//        for (Integer number : numbers) {
//            if (number % 2 == 0) {
//                numbers.remove(number);
//            }
//        }

        Iterator<Integer> itr = numbers.iterator();
        while (itr.hasNext()) {
            Integer number = itr.next();
            if (number % 2 == 0) {
                itr.remove();
            }
        }
        System.out.println(numbers);
        ListIterator<Integer> listIterator = numbers.listIterator();
        while (listIterator.hasNext()){
            listIterator.next();
//            listIterator.set();
        }
    }
}


// ITERATOR

// Purpose:
// Traverse and safely remove elements from a collection

// Key Methods:
// hasNext()
// next()
// remove() → removes last returned element

// Important:
// - Safe removal during iteration
// - Prevents ConcurrentModificationException
// - Only forward traversal


// LISTITERATOR (only for List)

// Features:
// - Bidirectional traversal
// - Modify elements during iteration

// Key Methods:
// hasNext(), next()
// hasPrevious(), previous()
// set(e) → update element
// add(e) → insert element
// remove() → remove element


// REMOVEIF (Java 8+)

// Purpose:
// Remove elements based on condition (internally uses iterator)

// Example:
// list.removeIf(n -> n % 2 == 0);

// Features:
// - In-place modification
// - Safe and concise
// - Preferred over manual iterator removal


// STREAM API

// Purpose:
// Functional-style processing of collections

// Features:
// - Does NOT modify original collection
// - Returns new collection
// - Supports map, filter, reduce

// Example:
// list = list.stream()
//            .filter(n -> n % 2 != 0)
//            .toList();


// DIFFERENCE SUMMARY:

// Iterator → safe removal (manual)
// removeIf → safe removal (modern, preferred)
// Stream → transformation (not mutation)
// ListIterator → advanced modification (bidirectional)
