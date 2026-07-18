package designpattern.structural.proxy;

public class ProxyDesignPattern {

    /*
        ==========================================================
        PROXY DESIGN PATTERN
        ==========================================================

        ONE OF THE MOST IMPORTANT
        structural design patterns.

        EXTREMELY heavily used in:
        - Spring Framework
        - Spring AOP
        - Hibernate
        - Security systems
        - Remote services
        - Lazy loading
        - Caching systems

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Proxy acts as:

            MIDDLEMAN

        between client and real object.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Proxy Pattern provides
        controlled access to real object.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Client does NOT directly access
        original object.

        Instead:

            Client -> Proxy -> Real Object

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        ATM machine.

        You do not directly access bank server.

        ATM acts as proxy.

        ATM:
        - validates card
        - validates pin
        - controls access
        - forwards request to bank

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Suppose real object operation is:
        - expensive
        - sensitive
        - remote
        - secured

        Direct access becomes dangerous or inefficient.

     */



    /*
        ==========================================================
        PROXY SOLUTION
        ==========================================================

        Add proxy layer.

        Proxy can:
        - control access
        - add security
        - add caching
        - add logging
        - lazy load object

     */



    public static void main(String[] args) {

        /*
            Client uses proxy object.

            NOT real object directly.
         */
        Internet internet =
                new ProxyInternet();



        internet.connectTo("google.com");



        internet.connectTo("openai.com");



        internet.connectTo("blocked.com");



        /*
            OUTPUT:

            Connecting to: google.com

            Connecting to: openai.com

            Access Denied to: blocked.com

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Proxy controls access
            before reaching real object.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client calls proxy.

            STEP-2:
            Proxy validates request.

            STEP-3:
            If allowed:
                forward to real object

            STEP-4:
            Else:
                block access

         */



        /*
            ======================================================
            WHY PROXY IMPORTANT?
            ======================================================

            Real systems need:
            - security
            - logging
            - transaction handling
            - lazy loading
            - remote communication

            Proxy adds these features
            without changing original class.

         */



        /*
            ======================================================
            TYPES OF PROXY
            ======================================================

            1. Virtual Proxy
                lazy loading

            2. Security Proxy
                access control

            3. Remote Proxy
                remote server communication

            4. Caching Proxy
                cache results

            5. Logging Proxy
                log operations

         */



        /*
            ======================================================
            CURRENT EXAMPLE TYPE
            ======================================================

            SECURITY PROXY

            Because proxy controls
            website access permission.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Proxy and Real Object
            implement SAME interface.

            So client cannot differentiate them.

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Proxy usually uses:

                HAS-A relationship

            Example:

                ProxyInternet
                    HAS-A
                RealInternet

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            MOST IMPORTANT REAL EXAMPLE.

            Spring AOP uses proxy internally.

            Example:
            @Transactional

            Spring creates proxy object.

            Proxy handles:
            - transaction start
            - commit
            - rollback

            Then calls actual method.

         */



        /*
            ======================================================
            HIBERNATE USAGE
            ======================================================

            Lazy loading uses proxy.

            Real DB object loaded only
            when actually needed.

         */



        /*
            ======================================================
            REAL COMPANY USAGE
            ======================================================

            Netflix:
            API gateway proxies

            Banking:
            security proxies

            Cloud systems:
            remote service proxies

            E-commerce:
            caching proxies

         */



        /*
            ======================================================
            PROXY vs DECORATOR
            ======================================================

            Decorator:
            adds extra behavior

            Proxy:
            controls access

         */



        /*
            ======================================================
            PROXY vs ADAPTER
            ======================================================

            Adapter:
            converts interfaces

            Proxy:
            controls access to same interface

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - security
            - lazy loading
            - logging
            - caching
            - controlled access
            - separation of concerns

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - extra abstraction layer
            - more classes
            - slightly increased complexity

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Proxy Pattern?

            Answer:
            Controlled access to real object.



            Q2:
            Proxy acts as?

            Answer:
            Middleman.



            Q3:
            Real-world analogy?

            Answer:
            ATM machine.



            Q4:
            Which relationship mostly used?

            Answer:
            Composition (HAS-A).



            Q5:
            Proxy and real object must share?

            Answer:
            Same interface.



            Q6:
            Most important Spring usage?

            Answer:
            Spring AOP proxies.



            Q7:
            Hibernate uses proxy for?

            Answer:
            Lazy loading.



            Q8:
            Difference from Decorator?

            Answer:

            Decorator:
            functionality enhancement

            Proxy:
            access control



            Q9:
            Current example proxy type?

            Answer:
            Security Proxy.

         */



        /*
            ======================================================
            IS PROXY PATTERN COMPLETE?
            ======================================================

            BASIC TO INTERMEDIATE:
            YES COMPLETE.

            Next Important Pattern:

                FACADE PATTERN

         */

    }

}



/*
    ==========================================================
    SUBJECT INTERFACE
    ==========================================================

    Common interface for proxy and real object.
 */
interface Internet {

    void connectTo(String website);
}



/*
    ==========================================================
    REAL OBJECT
    ==========================================================

    Actual service implementation.
 */
class RealInternet implements Internet {

    @Override
    public void connectTo(String website) {

        System.out.println(
                "Connecting to: " + website
        );
    }

}



/*
    ==========================================================
    PROXY CLASS
    ==========================================================

    Controls access to real object.
 */
class ProxyInternet implements Internet {

    /*
        HAS-A relationship.
     */
    private RealInternet realInternet =
            new RealInternet();



    @Override
    public void connectTo(String website) {

        /*
            Access control logic.
         */
        if(website.equals("blocked.com")) {

            System.out.println(
                    "Access Denied to: " + website
            );
        }

        else {

            /*
                Forward request to real object.
             */
            realInternet.connectTo(website);
        }
    }

}