package designpattern.structural.adapter;

public class AdapterDesignPattern {

    /*
        ==========================================================
        ADAPTER DESIGN PATTERN
        ==========================================================

        MOST IMPORTANT structural design pattern.

        VERY heavily used in:
        - payment integrations
        - third-party APIs
        - legacy system integration
        - Spring Framework
        - enterprise applications

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Two classes want to work together.

        BUT interfaces are incompatible.

        Meaning:
        method structure different.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Adapter Pattern converts
        one interface into another interface
        that client expects.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Adapter acts as:
            TRANSLATOR

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Mobile charger adapter.

        Indian socket:
            different

        US charger pin:
            different

        They cannot connect directly.

        Adapter converts compatibility.

     */



    /*
        ==========================================================
        REAL SOFTWARE PROBLEM
        ==========================================================

        Suppose your application supports:

            PaymentProcessor

        But new third-party payment gateway gives:

            RazorpayAPI

        Existing system expects:

            pay()

        Razorpay provides:

            makePayment()

        Interfaces mismatch.

     */



    /*
        ==========================================================
        ADAPTER SOLUTION
        ==========================================================

        Create Adapter class.

        Adapter converts:

            pay()

        into:

            makePayment()

     */



    public static void main(String[] args) {

        /*
            Third-party class object.
         */
        RazorpayAPI razorpayAPI =
                new RazorpayAPI();



        /*
            Adapter wraps third-party object.
         */
        PaymentProcessor paymentProcessor =
                new RazorpayAdapter(razorpayAPI);



        /*
            Client uses expected interface.

            Client unaware of third-party API details.
         */
        paymentProcessor.pay(5000);



        /*
            OUTPUT:

            Payment done using Razorpay: 5000

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Client expects:

                pay()

            Third-party provides:

                makePayment()

            Adapter converts between them.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client calls:

                pay()

            STEP-2:
            Adapter receives request.

            STEP-3:
            Adapter internally calls:

                makePayment()

            STEP-4:
            Third-party API executes.

         */



        /*
            ======================================================
            WHY ADAPTER IMPORTANT?
            ======================================================

            Real projects frequently integrate:

            - payment gateways
            - external APIs
            - legacy systems
            - cloud SDKs

            Interfaces often mismatch.

            Adapter solves compatibility problem.

         */



        /*
            ======================================================
            REAL WORLD EXAMPLES
            ======================================================

            Payment systems:
            - Razorpay
            - Stripe
            - PayPal

            Logging integrations

            Cloud providers:
            - AWS
            - Azure
            - GCP

            Different database drivers

         */



        /*
            ======================================================
            SPRING FRAMEWORK EXAMPLE
            ======================================================

            Spring MVC internally uses
            adapter-like concepts.

            Example:
            HandlerAdapter

            Different controllers adapted
            to common execution flow.

         */



        /*
            ======================================================
            KEY PLAYERS
            ======================================================

            1. Client
                expects target interface

            2. Target Interface
                expected interface

            3. Adaptee
                incompatible existing class

            4. Adapter
                converts interfaces

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Adapter usually uses:

                HAS-A relationship

            Example:

                RazorpayAdapter
                    HAS-A
                RazorpayAPI

         */



        /*
            ======================================================
            OBJECT ADAPTER
            ======================================================

            Current example:
            OBJECT ADAPTER

            Because adapter contains object reference.

         */



        /*
            ======================================================
            CLASS ADAPTER
            ======================================================

            Another type exists:
            CLASS ADAPTER

            Uses inheritance.

            Less common in Java
            because Java lacks multiple inheritance.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - integrates incompatible systems
            - reusable code
            - loose coupling
            - legacy system support
            - cleaner integration

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - extra abstraction layer
            - slightly more complexity

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Adapter Pattern?

            Answer:
            Convert incompatible interfaces.



            Q2:
            Real-world analogy?

            Answer:
            Mobile charger adapter.



            Q3:
            Adapter acts like?

            Answer:
            Translator.



            Q4:
            Which relationship mostly used?

            Answer:
            Composition (HAS-A).



            Q5:
            Main problem solved?

            Answer:
            Interface incompatibility.



            Q6:
            Real-world software usage?

            Answer:
            Third-party API integrations.



            Q7:
            Which Spring class related?

            Answer:
            HandlerAdapter.



            Q8:
            Difference between Adapter and Facade?

            Answer:

            Adapter:
            compatibility conversion

            Facade:
            simplified interface

         */



        /*
            ======================================================
            IS ADAPTER PATTERN COMPLETE?
            ======================================================

            BASIC TO INTERMEDIATE:
            YES COMPLETE.

            Next Important Structural Pattern:

                DECORATOR PATTERN

         */

    }

}



/*
    ==========================================================
    TARGET INTERFACE
    ==========================================================

    Client expects this interface.
 */
interface PaymentProcessor {

    void pay(int amount);
}



/*
    ==========================================================
    ADAPTEE
    ==========================================================

    Third-party incompatible API.
 */
class RazorpayAPI {

    public void makePayment(int money) {

        System.out.println(
                "Payment done using Razorpay: " + money
        );
    }

}



/*
    ==========================================================
    ADAPTER
    ==========================================================

    Converts target interface
    to adaptee interface.
 */
class RazorpayAdapter implements PaymentProcessor {

    /*
        HAS-A relationship.
     */
    private RazorpayAPI razorpayAPI;



    public RazorpayAdapter(RazorpayAPI razorpayAPI) {

        this.razorpayAPI = razorpayAPI;
    }



    @Override
    public void pay(int amount) {

        /*
            Convert method call.
         */
        razorpayAPI.makePayment(amount);
    }

}