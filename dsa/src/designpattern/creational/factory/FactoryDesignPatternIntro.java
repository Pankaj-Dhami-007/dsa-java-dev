package designpattern.creational.factory;

public class FactoryDesignPatternIntro {

    /*
        ==========================================================
        FACTORY DESIGN PATTERN
        ==========================================================

        Factory Design Pattern belongs to:

        CREATIONAL DESIGN PATTERNS

        Because:
        It deals with object creation.

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Instead of creating objects directly using:

            new Object()

        We delegate object creation responsibility
        to a FACTORY class.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Factory Pattern creates objects
        without exposing exact creation logic
        to client.

     */



    /*
        ==========================================================
        WHY FACTORY PATTERN NEEDED?
        ==========================================================

        Suppose:

        You are building notification system.

        Notifications can be:
        - Email
        - SMS
        - Push Notification

     */



    /*
        ==========================================================
        WITHOUT FACTORY PATTERN
        ==========================================================

        Client code:

            if(type.equals("EMAIL")) {
                EmailNotification n =
                        new EmailNotification();
            }

            else if(type.equals("SMS")) {
                SMSNotification n =
                        new SMSNotification();
            }

            else if(type.equals("PUSH")) {
                PushNotification n =
                        new PushNotification();
            }

     */



    /*
        ==========================================================
        PROBLEMS HERE
        ==========================================================

        1. Too many if-else conditions

        2. Tight coupling

           Client knows every concrete class.

        3. Hard to maintain

        4. Hard to extend

        5. Violates Open Closed Principle

        6. Object creation logic scattered everywhere

     */



    /*
        ==========================================================
        REAL WORLD ANALOGY
        ==========================================================

        Think about restaurant.

        Customer says:

            "I want burger"

        Customer does NOT know:
        - ingredients
        - cooking process
        - kitchen workflow

        Kitchen handles object creation.

        Kitchen = Factory

     */



    /*
        ==========================================================
        FACTORY PATTERN SOLUTION
        ==========================================================

        Create dedicated Factory class.

        Client simply asks:

            NotificationFactory.getNotification("EMAIL")

        Factory internally decides:
        which object to create.

     */



    /*
        ==========================================================
        BENEFITS
        ==========================================================

        1. Centralized object creation

        2. Loose coupling

        3. Better maintainability

        4. Easier extension

        5. Cleaner code

        6. Better abstraction

     */



    /*
        ==========================================================
        WHERE USED IN REAL PROJECTS?
        ==========================================================

        VERY COMMON in industry.

        Used in:
        - Spring Framework
        - Hibernate
        - JDBC
        - Logging Frameworks
        - Payment Systems
        - Notification Systems
        - Cloud SDKs

     */



    /*
        ==========================================================
        REAL COMPANY EXAMPLES
        ==========================================================

        Amazon:
        Different payment handlers

        Uber:
        Different ride allocators

        Netflix:
        Different video stream processors

        Banking Apps:
        Different transaction processors

     */



    /*
        ==========================================================
        IMPORTANT CONCEPT
        ==========================================================

        Factory Pattern hides:

        OBJECT CREATION COMPLEXITY

        Client focuses only on:
        object usage.

     */



    /*
        ==========================================================
        BEFORE FACTORY
        ==========================================================

        Client responsible for:
        - choosing class
        - creating object
        - managing object creation logic

     */



    /*
        ==========================================================
        AFTER FACTORY
        ==========================================================

        Client only requests object.

        Factory handles:
        - class selection
        - object creation
        - initialization

     */



    /*
        ==========================================================
        INTERVIEW QUESTIONS
        ==========================================================

        Q1:
        What type of pattern is Factory Pattern?

        Answer:
        Creational Design Pattern.



        Q2:
        Main purpose of Factory Pattern?

        Answer:
        Centralize and abstract object creation.



        Q3:
        Biggest advantage?

        Answer:
        Loose coupling.



        Q4:
        Why better than direct object creation?

        Answer:
        Cleaner, scalable, maintainable code.



        Q5:
        Real-world analogy?

        Answer:
        Restaurant kitchen / car factory.



        Q6:
        What problem does it solve?

        Answer:
        Complex and scattered object creation logic.



        Q7:
        Which SOLID principle improved?

        Answer:
        Open Closed Principle
        and Dependency Inversion Principle.

     */



    /*
        ==========================================================
        IS FACTORY DESIGN PATTERN COMPLETE?
        ==========================================================

        NO.

        This was only INTRODUCTION.

        Still remaining:

        - basic factory implementation
        - notification example
        - payment example
        - factory flow
        - interface usage
        - loose coupling
        - runtime polymorphism
        - factory vs abstract factory
        - factory in Spring
        - interview problems

        Next:
        REAL Factory Pattern implementation.
     */



    public static void main(String[] args) {

        System.out.println("Factory Design Pattern Started");

    }

}