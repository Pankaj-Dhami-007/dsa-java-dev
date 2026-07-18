package designpattern.creational.factory;

public class FactoryDesignPatternDemo {

    // The Factory Design Pattern is a creational design pattern that
    // provides an interface for creating objects without exposing the exact instantiation logic.
    // Car car = new BMW(); we do this Car car = CarFactory.getCar("BMW");
    // Why we use it
    // Code is tightly coupled, You use new everywhere, Hard to extend
    // With factory:  Loose coupling, Centralized creation, Easy to extend (Open/Closed Principle)

    // Types of Factory Patterns
    // 1. Simple Factory
    // 2. Factory Method Pattern
    // 3. Abstract Factory Pattern

    // steps: basic
    // Common Interface
    // Concrete Classes
    // Factory Class

    // Creator (Abstract)
    // Concrete Factories
    // Usage

    // Real-Life Use Cases
    // Spring Framework uses factory heavily
    // ApplicationContext.getBean()
    // Beans are created using factories internally
    // You don’t use new

    // Payment Gateway Systems
    // Notification Systems


    // Use it when:
    // Object creation is complex
    // You have multiple implementations
    // You want to hide creation logic
    // You follow SOLID principles

    // Factory Pattern encapsulates object creation logic and promotes loose coupling
    // by returning objects through a common interface instead of direct instantiation.


    public static void main(String[] args) {

        Payment payment = PaymentFactory.getPayment("UPI");
        payment.pay(500);
    }
}


/*

basic

public interface Car {
    void drive();
}

public class BMW implements Car {
    public void drive() {
        System.out.println("Driving BMW");
    }
}

public class Audi implements Car {
    public void drive() {
        System.out.println("Driving Audi");
    }
}


public class CarFactory {

    public static Car getCar(String type) {
        if (type.equalsIgnoreCase("BMW")) {
            return new BMW();
        } else if (type.equalsIgnoreCase("AUDI")) {
            return new Audi();
        }
        throw new IllegalArgumentException("Invalid car type");
    }
}


public class Main {
    public static void main(String[] args) {
        Car car = CarFactory.getCar("BMW");
        car.drive();
    }
}

 */
