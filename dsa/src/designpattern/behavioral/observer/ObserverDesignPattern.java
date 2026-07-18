package designpattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverDesignPattern {

    /*
        ==========================================================
        OBSERVER DESIGN PATTERN
        ==========================================================

        VERY IMPORTANT behavioral design pattern.

        EXTREMELY heavily used in:
        - event-driven systems
        - Kafka
        - RabbitMQ
        - Spring Events
        - Notification systems
        - Real-time applications

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        One object changes state.

        Multiple dependent objects
        automatically get notified.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Observer Pattern creates
        one-to-many dependency between objects.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        One publisher

        Multiple subscribers

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        YouTube Channel.

        One creator uploads video.

        All subscribers get notification automatically.

        ----------------------------------------------------------

        Creator:
            Subject / Publisher

        Subscribers:
            Observers

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Suppose e-commerce product stock changes.

        Multiple systems need updates:
        - mobile notification
        - email notification
        - warehouse system
        - analytics system

        Without Observer Pattern:
        tight coupling everywhere.

     */



    /*
        ==========================================================
        OBSERVER SOLUTION
        ==========================================================

        Subject maintains observer list.

        Whenever state changes:
        all observers automatically notified.

     */



    public static void main(String[] args) {

        /*
            Subject object.
         */
        Product iphone =
                new Product("iPhone 16");



        /*
            Observers subscribe.
         */
        Observer mobileUser =
                new MobileAlertObserver(
                        "Pankaj"
                );



        Observer emailUser =
                new EmailAlertObserver(
                        "Rahul"
                );



        iphone.subscribe(mobileUser);

        iphone.subscribe(emailUser);



        /*
            Product back in stock.

            All observers notified automatically.
         */
        iphone.setInStock(true);



        /*
            OUTPUT:

            Mobile Alert sent to: Pankaj
            Product available: iPhone 16

            Email Alert sent to: Rahul
            Product available: iPhone 16

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Subject does NOT know
            exact observer implementation.

            Subject only knows:

                Observer interface

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Observers subscribe.

            STEP-2:
            Subject state changes.

            STEP-3:
            Subject loops through observer list.

            STEP-4:
            notify/update method called.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Loose coupling.

            Subject independent from
            concrete observers.

         */



        /*
            ======================================================
            EVENT DRIVEN ARCHITECTURE
            ======================================================

            Observer Pattern is foundation of:

            EVENT DRIVEN SYSTEMS

            Example:
            - Kafka consumers
            - RabbitMQ subscribers
            - Spring events

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            E-commerce:
            stock alerts

            Social media:
            follower notifications

            Banking:
            transaction alerts

            Cricket apps:
            score updates

            Weather apps:
            weather alerts

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            Spring Events internally follow
            observer concepts.

            Example:

                ApplicationEventPublisher

                @EventListener

         */



        /*
            ======================================================
            JAVA EXAMPLE
            ======================================================

            Java deprecated Observable class
            was based on Observer Pattern.

         */



        /*
            ======================================================
            PUSH vs PULL MODEL
            ======================================================

            PUSH MODEL:
            subject sends data directly

            PULL MODEL:
            observer pulls data from subject

            Current example:
            PUSH-like approach.

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Subject HAS-A list of observers.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - loose coupling
            - automatic notifications
            - scalable event systems
            - dynamic subscriptions

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - notification chain complexity
            - debugging difficult sometimes
            - memory leak risk if unsubscribe forgotten

         */



        /*
            ======================================================
            OBSERVER vs PUB-SUB
            ======================================================

            Observer:
            direct object references

            Pub-Sub:
            message broker involved

            Kafka/RabbitMQ more advanced Pub-Sub.

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Observer Pattern?

            Answer:
            Automatic notification to dependents.



            Q2:
            Real-world analogy?

            Answer:
            YouTube subscribers.



            Q3:
            Relationship type?

            Answer:
            One-to-many.



            Q4:
            Biggest advantage?

            Answer:
            Loose coupling.



            Q5:
            Which systems heavily use this?

            Answer:
            Event-driven systems.



            Q6:
            Spring example?

            Answer:
            @EventListener



            Q7:
            Difference between Observer and Pub-Sub?

            Answer:

            Observer:
            direct references

            Pub-Sub:
            broker/message queue involved



            Q8:
            Subject stores what?

            Answer:
            List of observers.

         */



        /*
            ======================================================
            IS OBSERVER PATTERN COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next Important Behavioral Pattern:

                COMMAND PATTERN

         */

    }

}



/*
    ==========================================================
    OBSERVER INTERFACE
    ==========================================================

    Common notification contract.
 */
interface Observer {

    void update(String productName);
}



/*
    ==========================================================
    CONCRETE OBSERVER-1
    ==========================================================
 */
class MobileAlertObserver implements Observer {

    private String userName;



    public MobileAlertObserver(String userName) {

        this.userName = userName;
    }



    @Override
    public void update(String productName) {

        System.out.println(
                "Mobile Alert sent to: "
                        + userName
        );

        System.out.println(
                "Product available: "
                        + productName
        );

        System.out.println();
    }

}



/*
    ==========================================================
    CONCRETE OBSERVER-2
    ==========================================================
 */
class EmailAlertObserver implements Observer {

    private String userName;



    public EmailAlertObserver(String userName) {

        this.userName = userName;
    }



    @Override
    public void update(String productName) {

        System.out.println(
                "Email Alert sent to: "
                        + userName
        );

        System.out.println(
                "Product available: "
                        + productName
        );

        System.out.println();
    }

}



/*
    ==========================================================
    SUBJECT
    ==========================================================

    Publisher object.
 */
class Product {

    private String productName;



    /*
        HAS-A relationship.

        Stores observer list.
     */
    private List<Observer> observers =
            new ArrayList<>();



    private boolean inStock;



    public Product(String productName) {

        this.productName = productName;
    }



    /*
        Subscribe observer.
     */
    public void subscribe(Observer observer) {

        observers.add(observer);
    }



    /*
        Remove observer.
     */
    public void unsubscribe(Observer observer) {

        observers.remove(observer);
    }



    /*
        Notify all observers.
     */
    public void notifyObservers() {

        for(Observer observer : observers) {

            observer.update(productName);
        }
    }



    /*
        State change method.
     */
    public void setInStock(boolean inStock) {

        this.inStock = inStock;



        /*
            Whenever state changes,
            notify observers.
         */
        if(inStock) {

            notifyObservers();
        }
    }

}