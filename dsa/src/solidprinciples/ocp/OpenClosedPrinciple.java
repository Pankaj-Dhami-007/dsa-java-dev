package solidprinciples.ocp;

public class OpenClosedPrinciple {

    /*
        ==========================================================
        OPEN CLOSED PRINCIPLE (OCP)
        ==========================================================

        SECOND principle of SOLID.

        ONE OF THE MOST IMPORTANT principles.

        EXTREMELY heavily used in:
        - Spring Framework
        - Enterprise systems
        - Design Patterns
        - Plugin architectures

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Software entities should be:

            OPEN FOR EXTENSION
            CLOSED FOR MODIFICATION

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Add new features
        WITHOUT changing old code.

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Existing stable code
        should not break frequently.

        Instead:
        extend behavior using abstraction.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think mobile charger socket.

        New chargers supported
        without changing wall socket design.

        Extension possible
        without modifying existing system.

     */



    /*
        ==========================================================
        BAD DESIGN EXAMPLE
        ==========================================================

        Suppose payment system:

            if(type.equals("UPI"))

            else if(type.equals("CARD"))

            else if(type.equals("PAYPAL"))

        Every new payment type:
            MODIFY existing code.

     */



    /*
        ==========================================================
        WHY BAD?
        ==========================================================

        Existing code changes repeatedly.

        Risks:
        - old bugs return
        - testing increases
        - fragile system
        - merge conflicts

     */



    /*
        ==========================================================
        OCP SOLUTION
        ==========================================================

        Use abstraction/interfaces.

        Add new implementation classes
        instead of modifying old code.

     */



    public static void main(String[] args) {

        /*
            ======================================================
            USING UPI PAYMENT
            ======================================================
         */

        Payment payment =
                new UPIPayment();



        PaymentService paymentService =
                new PaymentService();



        paymentService.processPayment(
                payment
        );



        /*
            OUTPUT:

            Payment using UPI

         */



        /*
            ======================================================
            NOW ADD NEW FEATURE
            ======================================================

            Add CreditCardPayment class.

            Existing code unchanged.
         */

        payment =
                new CreditCardPayment();



        paymentService.processPayment(
                payment
        );



        /*
            OUTPUT:

            Payment using Credit Card

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            PaymentService never changes.

            New behavior added by:
                new implementation classes.

         */



        /*
            ======================================================
            INTERNAL THINKING
            ======================================================

            Instead of:

                MODIFYING existing logic

            We:

                EXTEND functionality

         */



        /*
            ======================================================
            WHY OCP IMPORTANT?
            ======================================================

            Enterprise systems constantly evolve.

            Example:
            - new payment methods
            - new notification types
            - new authentication systems

            OCP prevents touching stable code.

         */



        /*
            ======================================================
            MOST IMPORTANT CONCEPT
            ======================================================

            OCP mainly achieved using:

            - interfaces
            - abstraction
            - polymorphism

         */



        /*
            ======================================================
            DESIGN PATTERN CONNECTION
            ==========================================================

            MOST design patterns exist
            because of OCP.

            Examples:

            Strategy Pattern
            Factory Pattern
            Decorator Pattern
            Observer Pattern

            All support extension
            without modifying existing code.

         */



        /*
            ======================================================
            SPRING FRAMEWORK CONNECTION
            ======================================================

            Spring architecture heavily follows OCP.

            Example:

            Add new bean implementation
            without changing framework code.

         */



        /*
            ======================================================
            REAL WORLD COMPANY EXAMPLE
            ======================================================

            Amazon Payment System:

            Existing:
            - UPI
            - Card

            Tomorrow:
            - Crypto Payment

            Add:
                CryptoPayment class

            Existing payment flow unchanged.

         */



        /*
            ======================================================
            PLUGIN ARCHITECTURE
            ======================================================

            OCP foundation of plugin systems.

            Example:
            IDE plugins
            browser extensions
            payment plugins

         */



        /*
            ======================================================
            BAD OCP SYMPTOMS
            ======================================================

            Signs of OCP violation:

            - huge if-else chains
            - switch statements everywhere
            - frequent modification
            - fragile old code

         */



        /*
            ======================================================
            VERY IMPORTANT UNDERSTANDING
            ======================================================

            OCP does NOT mean:
                never modify code

            It means:
                stable core should remain stable.

         */



        /*
            ======================================================
            STRATEGY PATTERN CONNECTION
            ======================================================

            BEST example of OCP.

            Add new strategy class
            without changing context logic.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - scalable architecture
            - safer code changes
            - reusable system
            - easier maintenance
            - flexible extensions

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - more abstraction
            - more interfaces/classes
            - initial design complexity

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Definition of OCP?

            Answer:
            Open for extension,
            closed for modification.



            Q2:
            Main goal of OCP?

            Answer:
            Add features safely
            without modifying stable code.



            Q3:
            Which concepts mainly used?

            Answer:
            Abstraction and polymorphism.



            Q4:
            Biggest symptom of OCP violation?

            Answer:
            Huge if-else chains.



            Q5:
            Which design pattern strongly follows OCP?

            Answer:
            Strategy Pattern.



            Q6:
            Real-world example?

            Answer:
            Payment systems/plugins.



            Q7:
            Why important in enterprise systems?

            Answer:
            Systems evolve continuously.



            Q8:
            Does OCP mean never modifying code?

            Answer:
            No.

         */



        /*
            ======================================================
            IS OCP COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next:
                Liskov Substitution Principle (LSP)

            MOST misunderstood SOLID principle.

         */

    }

}



/*
    ==========================================================
    ABSTRACTION
    ==========================================================

    Extension point.
 */
interface Payment {

    void pay();
}



/*
    ==========================================================
    IMPLEMENTATION-1
    ==========================================================
 */
class UPIPayment implements Payment {

    @Override
    public void pay() {

        System.out.println(
                "Payment using UPI"
        );
    }

}



/*
    ==========================================================
    IMPLEMENTATION-2
    ==========================================================
 */
class CreditCardPayment implements Payment {

    @Override
    public void pay() {

        System.out.println(
                "Payment using Credit Card"
        );
    }

}



/*
    ==========================================================
    STABLE SERVICE CLASS
    ==========================================================

    Closed for modification.

    Open for extension via interface.
 */
class PaymentService {

    public void processPayment(
            Payment payment
    ) {

        payment.pay();
    }

}