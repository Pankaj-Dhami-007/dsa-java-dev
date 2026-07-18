package xyz;

interface Add{
    int sum(int a, int b);
}

interface Print{
    void print();
}

public class XYZ {

    public static void main(String[] args) {
        Add add = new AddImpl();
        System.out.println(add.sum(3, 5));

        Add add1 = new Add() {
            @Override
            public int sum(int a, int b) {
                return a + b;
            }
        };
        System.out.println(add1.sum(10, 10));

        Print print1 = new Print() {
            @Override
            public void print() {
                System.out.println("hello from anonymous inner class");
            }
        };
        print1.print();

        Print print2 = ()-> System.out.println(" hellow from lamda");
        print2.print();
    }
}

class AddImpl implements Add{

    @Override
    public int sum(int a, int b) {
        return a+b;
    }
}
