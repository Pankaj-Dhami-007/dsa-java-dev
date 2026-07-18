package designpattern.behavioral.templatemethod;

public class TemplateMethodPattern {

    /*
        ==========================================================
        TEMPLATE METHOD DESIGN PATTERN
        ==========================================================

        VERY IMPORTANT behavioral design pattern.

        Heavily used in:
        - Spring Framework
        - Hibernate
        - Servlet APIs
        - enterprise workflows
        - framework architectures

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Define overall workflow in parent class.

        Allow child classes to customize
        specific steps.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Template Method Pattern defines
        skeleton of algorithm in parent class
        while child classes override specific steps.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Fixed process.

        Customizable steps.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Making tea vs coffee.

        Common steps:
        - boil water
        - pour into cup

        Different steps:
        - add tea
        - add coffee

        Overall workflow same.

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Multiple classes contain:
        duplicated workflow code.

        Example:

        Tea preparation:
        - boil water
        - add tea
        - pour cup

        Coffee preparation:
        - boil water
        - add coffee
        - pour cup

        Duplicate logic everywhere.

     */



    /*
        ==========================================================
        TEMPLATE METHOD SOLUTION
        ==========================================================

        Parent class defines common workflow.

        Child classes customize only
        variable steps.

     */



    public static void main(String[] args) {

        /*
            ======================================================
            TEA WORKFLOW
            ======================================================
         */

        Beverage tea =
                new Tea();



        tea.prepareRecipe();



        /*
            OUTPUT:

            Boiling Water

            Adding Tea

            Pouring Into Cup

         */



        System.out.println();



        /*
            ======================================================
            COFFEE WORKFLOW
            ======================================================
         */

        Beverage coffee =
                new Coffee();



        coffee.prepareRecipe();



        /*
            OUTPUT:

            Boiling Water

            Adding Coffee

            Pouring Into Cup

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Overall process fixed in parent.

            Specific steps customized by children.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Parent template method called.

            STEP-2:
            Common methods execute.

            STEP-3:
            Child overridden method executes.

            STEP-4:
            Workflow completes.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Parent controls algorithm structure.

            Child cannot change overall workflow order.

         */



        /*
            ======================================================
            WHY TEMPLATE METHOD IMPORTANT?
            ======================================================

            Frameworks need:
            - fixed workflow
            - customizable hooks

            Example:
            framework controls flow,
            developer customizes parts.

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            MOST IMPORTANT REAL EXAMPLE.

            JdbcTemplate

            Spring defines workflow:
            - open connection
            - execute query
            - handle exceptions
            - close connection

            Developer customizes:
            query/result mapping logic.

         */



        /*
            ======================================================
            SERVLET API EXAMPLE
            ======================================================

            HttpServlet class.

            Parent defines request flow.

            Child overrides:
            - doGet()
            - doPost()

         */



        /*
            ======================================================
            HIBERNATE EXAMPLE
            ======================================================

            Transaction workflows internally
            follow template-like structure.

         */



        /*
            ======================================================
            HOLLYWOOD PRINCIPLE
            ======================================================

            VERY IMPORTANT concept.

            "Don't call us,
             we'll call you."

            Framework controls workflow.

            Child methods invoked automatically.

         */



        /*
            ======================================================
            KEY PLAYERS
            ======================================================

            1. Abstract Parent
                defines template method

            2. Concrete Child
                customizes steps

         */



        /*
            ======================================================
            TEMPLATE METHOD vs STRATEGY
            ======================================================

            Strategy:
            composition-based behavior change

            Template Method:
            inheritance-based workflow customization

         */



        /*
            ======================================================
            TEMPLATE METHOD vs FACTORY METHOD
            ======================================================

            Factory Method:
            object creation customization

            Template Method:
            workflow customization

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - removes duplicate code
            - fixed workflow control
            - reusable common logic
            - framework extensibility

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - inheritance coupling
            - limited runtime flexibility

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Template Method?

            Answer:
            Define workflow skeleton
            with customizable steps.



            Q2:
            Real-world analogy?

            Answer:
            Tea vs coffee preparation.



            Q3:
            Who controls workflow?

            Answer:
            Parent class.



            Q4:
            Child classes customize what?

            Answer:
            Specific steps only.



            Q5:
            Most important Spring example?

            Answer:
            JdbcTemplate.



            Q6:
            Which principle related?

            Answer:
            Hollywood Principle.



            Q7:
            Strategy vs Template Method?

            Answer:

            Strategy:
            composition-based

            Template:
            inheritance-based



            Q8:
            Biggest advantage?

            Answer:
            Removes duplicate workflow code.

         */



        /*
            ======================================================
            IS TEMPLATE METHOD COMPLETE?
            ======================================================

            YES BASIC TO INTERMEDIATE COMPLETE.

            MOST IMPORTANT BEHAVIORAL PATTERNS DONE:

            - Strategy
            - Observer
            - Command
            - Chain Of Responsibility
            - Template Method

            ------------------------------------------------------

            CORE DESIGN PATTERN MASTERY COMPLETE.

            You now understand:
            - Creational Patterns
            - Structural Patterns
            - Behavioral Patterns

         */

    }

}



/*
    ==========================================================
    ABSTRACT PARENT
    ==========================================================

    Defines fixed workflow.
 */
abstract class Beverage {

    /*
        TEMPLATE METHOD

        Final prevents workflow modification.
     */
    public final void prepareRecipe() {

        boilWater();

        addIngredient();

        pourInCup();
    }



    /*
        Common method.
     */
    private void boilWater() {

        System.out.println(
                "Boiling Water"
        );
    }



    /*
        Child customization step.
     */
    abstract void addIngredient();



    /*
        Common method.
     */
    private void pourInCup() {

        System.out.println(
                "Pouring Into Cup"
        );
    }

}



/*
    ==========================================================
    CHILD-1
    ==========================================================
 */
class Tea extends Beverage {

    @Override
    void addIngredient() {

        System.out.println(
                "Adding Tea"
        );
    }

}



/*
    ==========================================================
    CHILD-2
    ==========================================================
 */
class Coffee extends Beverage {

    @Override
    void addIngredient() {

        System.out.println(
                "Adding Coffee"
        );
    }

}