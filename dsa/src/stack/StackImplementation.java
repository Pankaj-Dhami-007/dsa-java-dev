package stack;

public class StackImplementation {
    public static void main(String[] args) {

        MyStack stack = new MyStack();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        stack.push(80);
        stack.display();

        stack.pop();
        stack.pop();

        System.out.println();
        stack.display();


    }
}
