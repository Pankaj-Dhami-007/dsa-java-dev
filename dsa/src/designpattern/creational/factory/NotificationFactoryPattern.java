package designpattern.creational.factory;

public class NotificationFactoryPattern {

    /*
        ==========================================================
        FACTORY DESIGN PATTERN
        REAL IMPLEMENTATION
        ==========================================================

        Now we will build REAL factory pattern.

        MOST IMPORTANT understanding:
        Factory Pattern mainly used when:

        Multiple child classes exist
        and object creation depends on runtime input.

     */



    /*
        ==========================================================
        PROBLEM STATEMENT
        ==========================================================

        Suppose company has notification system.

        Notifications can be:
        - Email
        - SMS
        - Push Notification

        ----------------------------------------------------------

        Client only wants:

            "Send Notification"

        Client should NOT care:
        - which class object created
        - object creation logic
        - constructor details

     */



    /*
        ==========================================================
        WITHOUT FACTORY
        ==========================================================

        Client code would look like:

            if(type.equals("EMAIL")) {
                EmailNotification n =
                        new EmailNotification();
            }

            else if(type.equals("SMS")) {
                SMSNotification n =
                        new SMSNotification();
            }

        Problems:
        - too many if-else
        - tight coupling
        - messy code
        - difficult maintenance

     */



    /*
        ==========================================================
        FACTORY SOLUTION
        ==========================================================

        Create dedicated Factory class.

        Client simply asks:

            NotificationFactory
                    .getNotification("EMAIL");

        Factory decides:
        which child object to create.

     */



    public static void main(String[] args) {

        /*
            Parent reference

            Factory returns child object
            as parent type.
         */
        Notification notification1 =
                NotificationFactory
                        .getNotification("EMAIL");



        notification1.send();



        /*
            Runtime object selection.

            Different child object returned.
         */
        Notification notification2 =
                NotificationFactory
                        .getNotification("SMS");



        notification2.send();



        Notification notification3 =
                NotificationFactory
                        .getNotification("PUSH");



        notification3.send();



        /*
            OUTPUT:

            Sending EMAIL Notification

            Sending SMS Notification

            Sending PUSH Notification

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Client only knows:

                Notification

            Client does NOT know:
            - EmailNotification
            - SMSNotification
            - PushNotification

            This is called:
            LOOSE COUPLING

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client requests object from factory.

            STEP-2:
            Factory checks input type.

            STEP-3:
            Factory creates proper child object.

            STEP-4:
            Object returned as parent type.

            STEP-5:
            Runtime polymorphism executes
            child implementation.

         */



        /*
            ======================================================
            RUNTIME POLYMORPHISM
            ======================================================

            Notification n =
                    new EmailNotification();

            Parent reference:
                Notification

            Actual object:
                EmailNotification

            Method execution decided at runtime.

         */



        /*
            ======================================================
            WHY FACTORY PATTERN POWERFUL?
            ======================================================

            Tomorrow company adds:

                WhatsAppNotification

            Client code remains SAME.

            Only factory changes.

         */



        /*
            ======================================================
            BEFORE FACTORY
            ======================================================

            Client responsible for:
            - selecting class
            - creating object
            - managing object logic

         */



        /*
            ======================================================
            AFTER FACTORY
            ======================================================

            Factory responsible for:
            - class selection
            - object creation
            - object initialization

            Client only uses object.

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            VERY heavily used.

            Examples:

            Payment Systems:
            - UPI
            - Card
            - Wallet

            Cloud Storage:
            - AWS
            - Azure
            - GCP

            Logging:
            - File Logger
            - DB Logger
            - Console Logger

            Database Drivers:
            - MySQL
            - PostgreSQL
            - Oracle

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            Spring internally uses factory concepts
            for bean creation.

            BeanFactory
            ApplicationContext

            are based on factory principles.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - loose coupling
            - cleaner code
            - centralized object creation
            - scalable
            - maintainable
            - flexible

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - more classes
            - extra abstraction
            - code complexity increases slightly

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Which OOP concept used heavily?

            Answer:
            Runtime Polymorphism.



            Q2:
            Main purpose of factory?

            Answer:
            Hide object creation logic.



            Q3:
            Client depends on?

            Answer:
            Parent abstraction/interface.



            Q4:
            Biggest advantage?

            Answer:
            Loose coupling.



            Q5:
            What changes when new type added?

            Answer:
            Factory class changes.



            Q6:
            Which SOLID principle supported?

            Answer:
            Open Closed Principle.



            Q7:
            Why factory better than new keyword everywhere?

            Answer:
            Centralized and manageable object creation.



            Q8:
            What pattern does Spring use internally?

            Answer:
            Factory concepts for bean creation.

         */



        /*
            ======================================================
            IS FACTORY DESIGN PATTERN COMPLETE?
            ======================================================

            BASIC FACTORY:
            YES COMPLETE.

            Still remaining advanced topics:

            - Factory Method Pattern
            - Abstract Factory Pattern
            - Factory vs Abstract Factory
            - Dynamic Factory Registration
            - Reflection-based Factory
            - Spring Bean Factory Internals
            - Real Enterprise Factory Architecture

         */

    }

}



/*
    ==========================================================
    PARENT INTERFACE
    ==========================================================

    Common contract for all notifications.
 */
interface Notification {

    void send();
}



/*
    ==========================================================
    CHILD-1
    ==========================================================
 */
class EmailNotification implements Notification {

    @Override
    public void send() {

        System.out.println("Sending EMAIL Notification");
    }
}



/*
    ==========================================================
    CHILD-2
    ==========================================================
 */
class SMSNotification implements Notification {

    @Override
    public void send() {

        System.out.println("Sending SMS Notification");
    }
}



/*
    ==========================================================
    CHILD-3
    ==========================================================
 */
class PushNotification implements Notification {

    @Override
    public void send() {

        System.out.println("Sending PUSH Notification");
    }
}



/*
    ==========================================================
    FACTORY CLASS
    ==========================================================

    Centralized object creation logic.
 */
class NotificationFactory {

    public static Notification getNotification(String type) {

        if(type.equals("EMAIL")) {

            return new EmailNotification();
        }

        else if(type.equals("SMS")) {

            return new SMSNotification();
        }

        else if(type.equals("PUSH")) {

            return new PushNotification();
        }

        return null;
    }

}