package EngineeringDigest.java8.lamda;

import java.util.Arrays;
import java.util.List;

interface MyInterface {
    void sayHello();
}

interface Add {
    int sum(int a, int b);
}

// Lambda expression is a short way to implement functional interfaces using anonymous functions,
// reducing boilerplate code and improving readability.

public class LamdaExpressionDemo {
    public static void main(String[] args) {

        // 1. USING IMPLEMENTATION CLASS
        MyInterface obj1 = new MyInterfaceImpl();
        obj1.sayHello();

        Add addImpl = new AddImpl();

        // 2. USING ANONYMOUS INNER CLASS
        MyInterface obj2 = new MyInterface() {
            @Override
            public void sayHello() {
                System.out.println("Hello using Anonymous Class");
            }
        };
        obj2.sayHello();

        Add adding = new Add() {
            public int sum(int a, int b) {
                return a + b;
            }
        };

        // 3. USING LAMBDA EXPRESSION
        MyInterface obj3 = () -> {
            System.out.println("Hello using Lambda");
        };
        obj3.sayHello();

        // 4. LAMBDA WITH PARAMETERS
        Add add1 = (a, b) -> {
            return a + b;
        };
        System.out.println("Sum: " + add1.sum(5, 3));

        // 5. SHORT LAMBDA (NO RETURN KEYWORD)
        Add add2 = (a, b) -> a + b;
        System.out.println("Sum: " + add2.sum(10, 20));

        // 6. SINGLE PARAMETER (NO BRACKET)
        MyInterface obj4 = () -> System.out.println("Short Lambda");
        obj4.sayHello();

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        list.stream()
                .filter(n -> n % 2 == 0)
                .forEach(n -> System.out.println(n));
    }
}

// ================================
// IMPLEMENTATION CLASS (Before Java 8)
// ================================
class MyInterfaceImpl implements MyInterface {
    @Override
    public void sayHello() {
        System.out.println("Hello using Implementation Class");
    }
}

class AddImpl implements Add {
    public int sum(int a, int b) {
        return a + b;
    }
}
/*

What is Lambda Expression?
Lambda Expression = short/ concise way to write anonymous function
It is used to implement functional interface (interface with one abstract method)
Why Lambda -
Before Java 8, we used:
Implementation class , Anonymous inner class Both were lengthy / boilerplate code

Functional Interface - Interface with only one abstract method

syntax - (parameters) -> { body }

 */
