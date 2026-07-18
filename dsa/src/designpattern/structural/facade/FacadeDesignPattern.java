package designpattern.structural.facade;

public class FacadeDesignPattern {

    /*
        ==========================================================
        FACADE DESIGN PATTERN
        ==========================================================

        VERY IMPORTANT structural design pattern.

        Heavily used in:
        - Spring Framework
        - Banking systems
        - Microservices
        - Enterprise applications
        - SDK libraries

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Hide complex subsystem logic
        behind one simplified interface.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Facade Pattern provides
        one simplified interface
        to complex subsystems.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Facade means:

            SIMPLIFIED ENTRY POINT

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Real enterprise systems contain:
        - many classes
        - many services
        - many workflows

        Client may need to call:
        - authentication service
        - payment service
        - inventory service
        - notification service

        Very complex for client.

     */



    /*
        ==========================================================
        FACADE SOLUTION
        ==========================================================

        Create one Facade class.

        Client calls only facade.

        Facade internally handles:
        - workflow
        - subsystem coordination
        - execution order

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Restaurant waiter.

        Customer does NOT talk to:
        - chef
        - billing team
        - cleaners
        - kitchen manager

        Customer talks only to waiter.

        Waiter coordinates everything.

        Waiter = Facade

     */



    public static void main(String[] args) {

        /*
            Client interacts only
            with facade.

            Client unaware of internal complexity.
         */
        FoodDeliveryFacade facade =
                new FoodDeliveryFacade();



        facade.placeOrder();



        /*
            OUTPUT:

            User Authenticated

            Restaurant Preparing Food

            Payment Completed

            Delivery Assigned

            Notification Sent

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Client uses ONE simple method:

                placeOrder()

            But internally many systems work together.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client calls facade.

            STEP-2:
            Facade coordinates subsystems.

            STEP-3:
            Services execute in sequence.

            STEP-4:
            Final process completes.

         */



        /*
            ======================================================
            WHY FACADE IMPORTANT?
            ======================================================

            Enterprise systems become huge.

            Direct subsystem interaction creates:
            - tight coupling
            - complexity
            - difficult usage

            Facade simplifies everything.

         */



        /*
            ======================================================
            REAL WORLD EXAMPLES
            ======================================================

            Banking Apps:
            one transferMoney() method internally:
            - authentication
            - validation
            - DB update
            - transaction logging
            - notification

            ------------------------------------------------------

            E-commerce checkout:
            - inventory
            - payment
            - shipping
            - invoice
            - notification

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            Spring provides simplified APIs.

            Example:

                JdbcTemplate

            Internally handles:
            - connection
            - statement
            - resultset
            - exception handling

            Client sees simple interface only.

         */



        /*
            ======================================================
            MICROSERVICES USAGE
            ======================================================

            API Gateway acts like facade.

            Client calls gateway.

            Gateway coordinates multiple services.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Facade does NOT hide subsystem existence.

            It simply provides:
                simplified usage.

         */



        /*
            ======================================================
            FACADE vs ADAPTER
            ======================================================

            Adapter:
            converts incompatible interfaces

            Facade:
            simplifies complex subsystem

         */



        /*
            ======================================================
            FACADE vs PROXY
            ======================================================

            Proxy:
            controls access

            Facade:
            simplifies usage

         */



        /*
            ======================================================
            FACADE vs DECORATOR
            ======================================================

            Decorator:
            adds behavior dynamically

            Facade:
            provides simplified interface

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Facade heavily uses:

                HAS-A relationship

            Facade contains subsystem objects.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - simplified client usage
            - reduced complexity
            - loose coupling
            - cleaner APIs
            - subsystem isolation

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - facade may become god class
            - too much dependency on facade

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Facade Pattern?

            Answer:
            Simplify complex subsystem access.



            Q2:
            Facade acts like?

            Answer:
            Simplified entry point.



            Q3:
            Real-world analogy?

            Answer:
            Restaurant waiter.



            Q4:
            Which relationship mostly used?

            Answer:
            Composition (HAS-A).



            Q5:
            Biggest advantage?

            Answer:
            Reduces subsystem complexity.



            Q6:
            Spring example?

            Answer:
            JdbcTemplate.



            Q7:
            Microservice example?

            Answer:
            API Gateway.



            Q8:
            Difference from Proxy?

            Answer:

            Proxy:
            controls access

            Facade:
            simplifies workflow



            Q9:
            Difference from Adapter?

            Answer:

            Adapter:
            compatibility conversion

            Facade:
            simplified interface

         */



        /*
            ======================================================
            IS FACADE PATTERN COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            MOST IMPORTANT STRUCTURAL PATTERNS DONE:

            - Adapter
            - Decorator
            - Proxy
            - Facade

            These four are MOST USED in industry.

            NEXT CATEGORY:

                BEHAVIORAL DESIGN PATTERNS

            MOST IMPORTANT there:
            - Strategy
            - Observer
            - Command
            - Chain Of Responsibility
            - Template Method

         */

    }

}



/*
    ==========================================================
    SUBSYSTEM-1
    ==========================================================
 */
class AuthenticationService {

    public void authenticate() {

        System.out.println(
                "User Authenticated"
        );
    }

}



/*
    ==========================================================
    SUBSYSTEM-2
    ==========================================================
 */
class RestaurantService {

    public void prepareFood() {

        System.out.println(
                "Restaurant Preparing Food"
        );
    }

}



/*
    ==========================================================
    SUBSYSTEM-3
    ==========================================================
 */
class PaymentService {

    public void makePayment() {

        System.out.println(
                "Payment Completed"
        );
    }

}



/*
    ==========================================================
    SUBSYSTEM-4
    ==========================================================
 */
class DeliveryService {

    public void assignDeliveryBoy() {

        System.out.println(
                "Delivery Assigned"
        );
    }

}



/*
    ==========================================================
    SUBSYSTEM-5
    ==========================================================
 */
class NotificationService {

    public void sendNotification() {

        System.out.println(
                "Notification Sent"
        );
    }

}



/*
    ==========================================================
    FACADE CLASS
    ==========================================================

    Simplified entry point.
 */
class FoodDeliveryFacade {

    /*
        HAS-A relationship.

        Facade contains subsystem objects.
     */
    private AuthenticationService authenticationService =
            new AuthenticationService();



    private RestaurantService restaurantService =
            new RestaurantService();



    private PaymentService paymentService =
            new PaymentService();



    private DeliveryService deliveryService =
            new DeliveryService();



    private NotificationService notificationService =
            new NotificationService();



    /*
        One simplified method
        hiding internal workflow complexity.
     */
    public void placeOrder() {

        authenticationService.authenticate();

        restaurantService.prepareFood();

        paymentService.makePayment();

        deliveryService.assignDeliveryBoy();

        notificationService.sendNotification();
    }

}