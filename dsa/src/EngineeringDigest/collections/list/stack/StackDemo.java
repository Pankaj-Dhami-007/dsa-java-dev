package EngineeringDigest.collections.list.stack;

import java.util.LinkedList;
import java.util.Stack;

// Stack extend Vector
// LIFO
// since stack extend vector so it is synchronized making it thread safe
// inheritance -> stack is subclass of vector
/*
arrayList and list -> sequential o(1)
LinkedList -> insertion and deletion
Vector - > sequential o(1)
 */
public class StackDemo {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        Integer removedElement = stack.pop();
        System.out.println(stack);

        Integer top = stack.peek();
        System.out.println(top);

        // ll as stack
        LinkedList<Integer> linkedList = new LinkedList<>();// doubly ll def
        linkedList.addLast(1);
        linkedList.addLast(2);
        linkedList.addLast(3);// push
        linkedList.getLast(); // peek
        linkedList.removeLast(); // pop
    }
}
