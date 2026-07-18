package designpattern.behavioral.strategy.v2;

public class StrategyDesignPattern {

    /*
        ==========================================================
        STRATEGY DESIGN PATTERN
        ==========================================================

        MOST IMPORTANT behavioral design pattern.

        EXTREMELY heavily used in:
        - payment systems
        - Spring Framework
        - sorting systems
        - authentication systems
        - enterprise applications

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Different algorithms/behaviors
        can be selected at runtime.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Strategy Pattern defines
        multiple algorithms separately
        and allows switching them at runtime.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Strategy means:

            interchangeable behavior

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Suppose payment system supports:

        - Credit Card
        - UPI
        - PayPal

        WITHOUT strategy pattern:

            if(type.equals("UPI"))

            else if(type.equals("CARD"))

            else if(type.equals("PAYPAL"))

        Huge if-else chains.

     */



    /*
        ==========================================================
        STRATEGY SOLUTION
        ==========================================================

        Separate each algorithm/behavior
        into separate class.

        Runtime decides:
        which strategy to use.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Google Maps.

        Different route strategies:
        - shortest path
        - fastest path
        - avoid tolls

        User selects strategy dynamically.

     */



    public static void main(String[] args) {

        /*
            ======================================================
            USING UPI STRATEGY
            ======================================================
         */

        PaymentStrategy paymentStrategy =
                new UPIPayment();



        ShoppingCart cart =
                new ShoppingCart(paymentStrategy);



        cart.checkout(5000);



        /*
            OUTPUT:

            Payment done using UPI: 5000

         */



        /*
            ======================================================
            CHANGE STRATEGY AT RUNTIME
            ======================================================
         */

        paymentStrategy =
                new CreditCardPayment();



        cart =
                new ShoppingCart(paymentStrategy);



        cart.checkout(10000);



        /*
            OUTPUT:

            Payment done using Credit Card: 10000

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Same checkout process.

            Only payment behavior changes dynamically.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client selects strategy object.

            STEP-2:
            Context receives strategy.

            STEP-3:
            Context delegates work
            to selected strategy.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Context class does NOT know
            actual payment implementation.

            It only knows:

                PaymentStrategy interface

            This creates:
            loose coupling.

         */



        /*
            ======================================================
            WHY STRATEGY PATTERN IMPORTANT?
            ======================================================

            Real systems often have:
            multiple interchangeable algorithms.

            Example:
            - payment methods
            - sorting algorithms
            - authentication methods
            - compression techniques

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Strategy Pattern heavily uses:

                HAS-A relationship

            Example:

                ShoppingCart
                    HAS-A
                PaymentStrategy

         */



        /*
            ======================================================
            RUNTIME POLYMORPHISM
            ======================================================

            PaymentStrategy strategy =
                    new UPIPayment();

            Actual method execution
            decided at runtime.

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            Payment gateways:
            - UPI
            - Wallet
            - Card

            Authentication:
            - OAuth
            - JWT
            - LDAP

            Sorting:
            - quick sort
            - merge sort
            - bubble sort

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            Spring internally uses
            strategy-like architecture.

            Example:
            Different Bean initialization strategies.

         */



        /*
            ======================================================
            JAVA EXAMPLE
            ======================================================

            Comparator interface.

            Different comparison strategies.

            Example:

                Collections.sort(list, comparator)

         */



        /*
            ======================================================
            STRATEGY vs FACTORY
            ======================================================

            Factory:
            creates object

            Strategy:
            changes behavior/algorithm

         */



        /*
            ======================================================
            STRATEGY vs STATE
            ======================================================

            Strategy:
            behavior selected externally

            State:
            behavior changes internally
            based on object state

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - removes huge if-else
            - runtime flexibility
            - loosely coupled
            - scalable
            - reusable algorithms

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - many strategy classes
            - client must know strategies

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Strategy Pattern?

            Answer:
            Runtime behavior selection.



            Q2:
            Real-world analogy?

            Answer:
            Google Maps route selection.



            Q3:
            Which relationship mostly used?

            Answer:
            Composition (HAS-A).



            Q4:
            Biggest advantage?

            Answer:
            Removes huge conditional logic.



            Q5:
            Context depends on?

            Answer:
            Strategy abstraction/interface.



            Q6:
            Which Java interface is common example?

            Answer:
            Comparator.



            Q7:
            Difference from Factory?

            Answer:

            Factory:
            object creation

            Strategy:
            behavior selection



            Q8:
            Runtime or compile-time behavior change?

            Answer:
            Runtime.

         */



        /*
            ======================================================
            IS STRATEGY PATTERN COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next Important Behavioral Pattern:

                OBSERVER PATTERN

         */

    }

}



/*
    ==========================================================
    STRATEGY INTERFACE
    ==========================================================

    Common behavior contract.
 */
interface PaymentStrategy {

    void pay(int amount);
}



/*
    ==========================================================
    CONCRETE STRATEGY-1
    ==========================================================
 */
class UPIPayment implements PaymentStrategy {

    @Override
    public void pay(int amount) {

        System.out.println(
                "Payment done using UPI: " + amount
        );
    }

}



/*
    ==========================================================
    CONCRETE STRATEGY-2
    ==========================================================
 */
class CreditCardPayment implements PaymentStrategy {

    @Override
    public void pay(int amount) {

        System.out.println(
                "Payment done using Credit Card: " + amount
        );
    }

}



/*
    ==========================================================
    CONTEXT CLASS
    ==========================================================

    Uses strategy dynamically.
 */
class ShoppingCart {

    /*
        HAS-A relationship.
     */
    private PaymentStrategy paymentStrategy;



    public ShoppingCart(
            PaymentStrategy paymentStrategy
    ) {

        this.paymentStrategy = paymentStrategy;
    }



    /*
        Delegates behavior
        to selected strategy.
     */
    public void checkout(int amount) {

        paymentStrategy.pay(amount);
    }

}