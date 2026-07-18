package designpattern.behavioral.strategy;

import designpattern.behavioral.strategy.impl.CardPayment;
import designpattern.behavioral.strategy.impl.PaypalPayment;
import designpattern.behavioral.strategy.impl.UpiPayment;

public class StrategyDesignPattern {
    public static void main(String[] args) {

        // The Strategy Design Pattern is a behavioral design pattern used when:
        //You have multiple ways to perform one task
        //You want to switch behavior dynamically
        //You want to avoid large if-else or switch statements

        // Strategy Pattern Solution
        // We separate:
        //Common behavior interface
        //Different algorithms/strategies
        //Context class using strategies

        // You can replace behavior at runtime.

        // Strategy Pattern uses:
        //
        //Interface
        //Polymorphism
        //Composition over inheritance


        PaymentStrategy upi = new UpiPayment();
        PaymentService service1 = new PaymentService(upi);
        service1.processPayment(1000);

        PaymentStrategy card = new CardPayment();
        PaymentService service2 = new PaymentService(card);
        service2.processPayment(2000);

        PaymentStrategy paypal = new PaypalPayment();
        PaymentService service3 = new PaymentService(paypal);
        service3.processPayment(3000);

        // Factory -> Which object should I create?  CREATE object
        // Strategy -> How should this task execute?  USE object behavior
    }
}

/*

User selects UPI
        ↓
Factory creates UpiPayment object
        ↓
PaymentService receives strategy
        ↓
Strategy executes payment logic


public class PaymentFactory {

    public static PaymentStrategy getPayment(String type) {

        if(type.equals("UPI")) {
            return new UpiPayment();
        }

        else if(type.equals("CARD")) {
            return new CardPayment();
        }

        throw new RuntimeException("Invalid payment");
    }
}


public class Main {

    public static void main(String[] args) {

        PaymentStrategy strategy =
            PaymentFactory.getPayment("UPI");

        PaymentService service =
            new PaymentService(strategy);

        service.processPayment(1000);
    }
}
 */
