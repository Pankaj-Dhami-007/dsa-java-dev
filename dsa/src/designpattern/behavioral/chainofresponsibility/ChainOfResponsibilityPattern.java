package designpattern.behavioral.chainofresponsibility;

public class ChainOfResponsibilityPattern {

    /*
        ==========================================================
        CHAIN OF RESPONSIBILITY DESIGN PATTERN
        ==========================================================

        VERY IMPORTANT behavioral design pattern.

        EXTREMELY heavily used in:
        - Spring Security
        - Servlet Filters
        - Logging frameworks
        - API Gateways
        - Middleware systems

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Request passes through
        multiple handlers sequentially.

        Each handler decides:

        - handle request
        OR
        - pass request forward

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Chain Of Responsibility Pattern
        passes request through chain of handlers
        until one handles it.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Like approval chain.

            Employee
                ->
            Manager
                ->
            Director
                ->
            CEO

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Customer support escalation.

        Level-1 Support
            cannot solve?

        Forward to:
            Level-2

        Still not solved?

        Forward to:
            Senior Engineer

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Without chain pattern:

        Huge if-else logic.

        Example:

            if(authentication)

            if(validation)

            if(logging)

            if(authorization)

        Tight coupling + messy code.

     */



    /*
        ==========================================================
        CHAIN SOLUTION
        ==========================================================

        Create separate handlers.

        Request flows handler-by-handler.

     */



    public static void main(String[] args) {

        /*
            ======================================================
            CREATE HANDLERS
            ======================================================
         */

        Handler authHandler =
                new AuthenticationHandler();



        Handler authorizationHandler =
                new AuthorizationHandler();



        Handler loggingHandler =
                new LoggingHandler();



        /*
            ======================================================
            BUILD CHAIN
            ======================================================

            auth -> authorization -> logging
         */

        authHandler.setNextHandler(
                authorizationHandler
        );



        authorizationHandler.setNextHandler(
                loggingHandler
        );



        /*
            ======================================================
            SEND REQUEST
            ======================================================
         */

        authHandler.handleRequest("VALID_USER");



        /*
            OUTPUT:

            Authentication Successful

            Authorization Successful

            Request Logged

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Each handler processes request
            then forwards to next handler.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Request enters first handler.

            STEP-2:
            Handler processes logic.

            STEP-3:
            Handler forwards request.

            STEP-4:
            Next handler processes.

            STEP-5:
            Chain continues.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Handlers are loosely coupled.

            Each handler knows only:
                next handler

            NOT complete chain.

         */



        /*
            ======================================================
            WHY CHAIN PATTERN IMPORTANT?
            ======================================================

            Real enterprise systems need:
            multiple processing steps.

            Example:
            - authentication
            - authorization
            - logging
            - validation
            - rate limiting

         */



        /*
            ======================================================
            SPRING SECURITY EXAMPLE
            ======================================================

            MOST IMPORTANT REAL EXAMPLE.

            Spring Security Filter Chain.

            Request passes through:
            - authentication filter
            - authorization filter
            - JWT filter
            - CSRF filter

         */



        /*
            ======================================================
            SERVLET FILTERS
            ======================================================

            Servlet filters also behave like chain.

            Request flows through
            multiple filters.

         */



        /*
            ======================================================
            API GATEWAY USAGE
            ======================================================

            API request passes through:
            - authentication
            - logging
            - throttling
            - validation

         */



        /*
            ======================================================
            KEY PLAYERS
            ======================================================

            1. Handler
                common abstraction

            2. Concrete Handler
                actual processing logic

            3. Client
                sends request

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Handler HAS-A next handler.

         */



        /*
            ======================================================
            CHAIN vs COMMAND
            ======================================================

            Command:
            encapsulates action

            Chain:
            sequential request processing

         */



        /*
            ======================================================
            CHAIN vs OBSERVER
            ======================================================

            Observer:
            broadcast notifications

            Chain:
            sequential request forwarding

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - loose coupling
            - flexible processing pipeline
            - dynamic chain creation
            - cleaner code
            - scalable workflow

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - debugging chain difficult
            - request may go unhandled
            - chain complexity

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Chain Pattern?

            Answer:
            Sequential request processing.



            Q2:
            Real-world analogy?

            Answer:
            Support escalation chain.



            Q3:
            Biggest advantage?

            Answer:
            Loose coupling between handlers.



            Q4:
            Most important Spring example?

            Answer:
            Spring Security Filter Chain.



            Q5:
            Handler knows complete chain?

            Answer:
            No.

            Only next handler.



            Q6:
            Which relationship used?

            Answer:
            Composition (HAS-A).



            Q7:
            Difference from Observer?

            Answer:

            Observer:
            broadcast event

            Chain:
            sequential forwarding



            Q8:
            Common enterprise usage?

            Answer:
            Middleware/filter systems.

         */



        /*
            ======================================================
            IS CHAIN PATTERN COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            Next Important Behavioral Pattern:

                TEMPLATE METHOD PATTERN

         */

    }

}



/*
    ==========================================================
    HANDLER ABSTRACT CLASS
    ==========================================================

    Common handler structure.
 */
abstract class Handler {

    /*
        HAS-A next handler.
     */
    protected Handler nextHandler;



    public void setNextHandler(
            Handler nextHandler
    ) {

        this.nextHandler = nextHandler;
    }



    /*
        Common processing contract.
     */
    abstract void handleRequest(String request);

}



/*
    ==========================================================
    CONCRETE HANDLER-1
    ==========================================================
 */
class AuthenticationHandler extends Handler {

    @Override
    void handleRequest(String request) {

        if(request.equals("VALID_USER")) {

            System.out.println(
                    "Authentication Successful"
            );



            /*
                Forward request.
             */
            if(nextHandler != null) {

                nextHandler.handleRequest(request);
            }
        }

        else {

            System.out.println(
                    "Authentication Failed"
            );
        }
    }

}



/*
    ==========================================================
    CONCRETE HANDLER-2
    ==========================================================
 */
class AuthorizationHandler extends Handler {

    @Override
    void handleRequest(String request) {

        System.out.println(
                "Authorization Successful"
        );



        if(nextHandler != null) {

            nextHandler.handleRequest(request);
        }
    }

}



/*
    ==========================================================
    CONCRETE HANDLER-3
    ==========================================================
 */
class LoggingHandler extends Handler {

    @Override
    void handleRequest(String request) {

        System.out.println(
                "Request Logged"
        );



        if(nextHandler != null) {

            nextHandler.handleRequest(request);
        }
    }

}