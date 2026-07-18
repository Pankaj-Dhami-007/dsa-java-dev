package solidprinciples.dip;

public class DependencyInversionPrinciple {

    /*
        ==========================================================
        DEPENDENCY INVERSION PRINCIPLE (DIP)
        ==========================================================

        FIFTH and MOST IMPORTANT SOLID principle.

        FOUNDATION of:
        - Spring Framework
        - Dependency Injection
        - Clean Architecture
        - Enterprise systems
        - Microservices

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        High-level modules should NOT depend
        on low-level modules.

        Both should depend on abstractions.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Depend on:
            INTERFACE

        NOT:
            concrete class

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Business logic should not tightly depend
        on implementation details.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think mobile charger cable.

        Mobile depends on:
            charging port standard

        NOT:
            specific company wire internally.

        That abstraction gives flexibility.

     */



    /*
        ==========================================================
        BAD DESIGN EXAMPLE
        ==========================================================

        class NotificationService {

            EmailSender sender =
                    new EmailSender();

        }

        Problem:
        tightly coupled.

        Tomorrow:
        SMS needed.

        Must modify service class.

     */



    /*
        ==========================================================
        WHY BAD?
        ==========================================================

        Problems:

        - tight coupling
        - difficult testing
        - difficult extension
        - difficult mocking
        - difficult maintenance

     */



    /*
        ==========================================================
        DIP SOLUTION
        ==========================================================

        Depend on abstraction/interface.

        Example:

            NotificationSender sender;

        Implementations:
        - EmailSender
        - SMSSender
        - PushSender

     */



    public static void main(String[] args) {

        /*
            ======================================================
            USING EMAIL IMPLEMENTATION
            ======================================================
         */

        NotificationSender sender =
                new EmailSender();



        NotificationService service =
                new NotificationService(
                        sender
                );



        service.sendNotification();



        /*
            OUTPUT:

            Sending Email Notification

         */



        /*
            ======================================================
            CHANGE IMPLEMENTATION
            ======================================================
         */

        sender =
                new SMSSender();



        service =
                new NotificationService(
                        sender
                );



        service.sendNotification();



        /*
            OUTPUT:

            Sending SMS Notification

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            NotificationService depends on:

                NotificationSender

            NOT:
                EmailSender
                SMSSender

         */



        /*
            ======================================================
            MOST IMPORTANT KEYWORD
            ======================================================

                LOOSE COUPLING

         */



        /*
            ======================================================
            WHY DIP IMPORTANT?
            ======================================================

            Enterprise systems continuously change:
            - databases
            - payment gateways
            - notification systems
            - authentication providers

            DIP makes changes easy.

         */



        /*
            ======================================================
            VERY IMPORTANT UNDERSTANDING
            ======================================================

            HIGH LEVEL MODULE:
                business logic

            LOW LEVEL MODULE:
                implementation details

            Both should depend on abstraction.

         */



        /*
            ======================================================
            SPRING FRAMEWORK FOUNDATION
            ======================================================

            MOST IMPORTANT REAL UNDERSTANDING.

            Spring Dependency Injection
            exists because of DIP.

         */



        /*
            ======================================================
            SPRING EXAMPLE
            ======================================================

            @Autowired

            Spring injects implementation
            through interface abstraction.

            Example:

                PaymentService
                    depends on
                PaymentGateway interface

         */



        /*
            ======================================================
            DEPENDENCY INJECTION CONNECTION
            ======================================================

            DIP:
                DESIGN PRINCIPLE

            Dependency Injection:
                IMPLEMENTATION TECHNIQUE

         */



        /*
            ======================================================
            TESTING BENEFIT
            ======================================================

            MOST IMPORTANT advantage.

            Easy mocking possible.

            Example:

                FakeNotificationSender

            used during unit testing.

         */



        /*
            ======================================================
            DESIGN PATTERN CONNECTION
            ==========================================================

            MOST patterns use DIP.

            Examples:
            - Strategy
            - Factory
            - Observer
            - Decorator
            - Proxy

         */



        /*
            ======================================================
            REAL WORLD COMPANY EXAMPLE
            ======================================================

            E-commerce Payment System.

            High-level:
                OrderService

            Low-level:
                Razorpay
                Stripe
                PayPal

            OrderService should depend on:
                PaymentGateway interface

            NOT concrete gateways.

         */



        /*
            ======================================================
            BAD DIP SYMPTOMS
            ======================================================

            Signs of violation:

            - new keyword everywhere
            - tightly coupled classes
            - difficult mocking
            - direct implementation dependency

         */



        /*
            ======================================================
            CONSTRUCTOR INJECTION
            ======================================================

            BEST practice for DIP.

            Dependencies passed through constructor.

            Current example uses:
                constructor injection.

         */



        /*
            ======================================================
            FIELD INJECTION vs CONSTRUCTOR
            ======================================================

            Constructor Injection preferred:
            - immutable dependencies
            - easier testing
            - better design

         */



        /*
            ======================================================
            DIP vs OCP
            ======================================================

            OCP:
            extend without modification

            DIP:
            depend on abstractions

            Both heavily connected.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - loose coupling
            - easy testing
            - scalable architecture
            - flexible implementations
            - maintainable systems

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - more interfaces
            - increased abstraction
            - initial complexity

         */



        /*
            ======================================================
            MOST ASKED INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Definition of DIP?

            Answer:
            Depend on abstractions,
            not concrete implementations.



            Q2:
            Most important keyword?

            Answer:
            Loose Coupling.



            Q3:
            Which framework heavily based on DIP?

            Answer:
            Spring Framework.



            Q4:
            DIP foundation of what?

            Answer:
            Dependency Injection.



            Q5:
            High-level module means?

            Answer:
            Business logic layer.



            Q6:
            Low-level module means?

            Answer:
            Implementation details.



            Q7:
            Why DIP improves testing?

            Answer:
            Mock implementations possible.



            Q8:
            Biggest symptom of DIP violation?

            Answer:
            new keyword everywhere.



            Q9:
            Constructor Injection preferred why?

            Answer:
            Better immutability and testing.



            Q10:
            DIP vs Dependency Injection?

            Answer:

            DIP:
            principle

            DI:
            implementation technique



            Q11:
            Which design patterns heavily use DIP?

            Answer:
            Strategy, Factory, Decorator, Proxy.



            Q12:
            Real-world example?

            Answer:
            Payment gateway integrations.

         */



        /*
            ======================================================
            VERY IMPORTANT SENIOR LEVEL THINKING
            ======================================================

            Good architecture means:

            Business logic should survive
            even if implementation changes.

         */



        /*
            ======================================================
            IS DIP COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            ------------------------------------------------------

            ALL SOLID PRINCIPLES COMPLETED:

            ✔ SRP
            ✔ OCP
            ✔ LSP
            ✔ ISP
            ✔ DIP

            ------------------------------------------------------

            You now understand:
            - clean architecture basics
            - Spring design philosophy
            - enterprise-level design thinking
            - low-level design foundations

         */

    }

}



/*
    ==========================================================
    ABSTRACTION
    ==========================================================

    Common contract.
 */
interface NotificationSender {

    void send();
}



/*
    ==========================================================
    LOW LEVEL IMPLEMENTATION-1
    ==========================================================
 */
class EmailSender implements NotificationSender {

    @Override
    public void send() {

        System.out.println(
                "Sending Email Notification"
        );
    }

}



/*
    ==========================================================
    LOW LEVEL IMPLEMENTATION-2
    ==========================================================
 */
class SMSSender implements NotificationSender {

    @Override
    public void send() {

        System.out.println(
                "Sending SMS Notification"
        );
    }

}



/*
    ==========================================================
    HIGH LEVEL MODULE
    ==========================================================

    Depends on abstraction,
    NOT concrete class.
 */
class NotificationService {

    private NotificationSender sender;



    /*
        Constructor Injection.

        Dependency passed externally.
     */
    public NotificationService(
            NotificationSender sender
    ) {

        this.sender = sender;
    }



    public void sendNotification() {

        sender.send();
    }

}