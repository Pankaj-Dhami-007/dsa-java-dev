package stack;

public class MyStack {
    private int[] arr;
    private int top = -1;

    public MyStack(){
        arr = new int[5];
    }
    public MyStack(int n){
        arr = new int[n];
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public boolean isFull(){
        return top == arr.length;
    }

    public void push(int item){
        if(isFull()){
            throw new RuntimeException("Stack is full you cant push item now");
        }
        top++;
        arr[top] = item;
    }

    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("Stack is Empty");
        }
        int rm = arr[top];
        top--;
        return rm;
    }

    public int peek(){
        if(isEmpty()){
            throw new RuntimeException("Stack is Empty");
        }
        return arr[top];
    }

    public void display(){
        for (int i = top; i > -1; i--) {
            System.out.println(arr[i]);
        }
    }
}
