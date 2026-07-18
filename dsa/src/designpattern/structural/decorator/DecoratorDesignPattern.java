package designpattern.structural.decorator;

public class DecoratorDesignPattern {

    /*
        ==========================================================
        DECORATOR DESIGN PATTERN
        ==========================================================

        ONE OF THE MOST IMPORTANT
        structural design patterns.

        VERY heavily used in:
        - Java IO classes
        - Spring Framework
        - Logging systems
        - Security systems
        - Enterprise applications

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Add new behavior to object
        dynamically at runtime

        WITHOUT modifying original class.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Decorator Pattern wraps object
        to add extra functionality dynamically.

     */



    /*
        ==========================================================
        VERY SIMPLE UNDERSTANDING
        ==========================================================

        Decorator means:

            WRAPPING

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think about pizza.

        Base pizza exists.

        Then dynamically add:
        - cheese
        - mayo
        - paneer
        - olives

        Base object same,
        behavior/features increase.

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Suppose we create inheritance for features.

        Example:

            PlainCoffee
            MilkCoffee
            SugarCoffee
            MilkSugarCoffee
            MilkSugarCreamCoffee

        Classes explode rapidly.

        Huge inheritance mess.

     */



    /*
        ==========================================================
        DECORATOR SOLUTION
        ==========================================================

        Start with base object.

        Dynamically wrap decorators.

        Example:

            Coffee coffee =
                    new MilkDecorator(
                        new SugarDecorator(
                            new PlainCoffee()
                        )
                    );

     */



    public static void main(String[] args) {

        /*
            Base object.
         */
        Coffee coffee =
                new PlainCoffee();



        System.out.println(
                coffee.getDescription()
        );

        System.out.println(
                coffee.getCost()
        );



        /*
            OUTPUT:

            Plain Coffee

            100

         */



        /*
            ======================================================
            ADD MILK DECORATOR
            ======================================================
         */

        coffee =
                new MilkDecorator(coffee);



        System.out.println(
                coffee.getDescription()
        );

        System.out.println(
                coffee.getCost()
        );



        /*
            OUTPUT:

            Plain Coffee + Milk

            120

         */



        /*
            ======================================================
            ADD SUGAR DECORATOR
            ======================================================
         */

        coffee =
                new SugarDecorator(coffee);



        System.out.println(
                coffee.getDescription()
        );

        System.out.println(
                coffee.getCost()
        );



        /*
            OUTPUT:

            Plain Coffee + Milk + Sugar

            130

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Object behavior extended dynamically.

            Original class unchanged.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            PlainCoffee object created.

            STEP-2:
            MilkDecorator wraps object.

            STEP-3:
            SugarDecorator wraps milk object.

            STEP-4:
            Calls flow layer-by-layer.

         */



        /*
            ======================================================
            VERY IMPORTANT CONCEPT
            ======================================================

            Decorator and original object
            share SAME parent type.

            That allows recursive wrapping.

         */



        /*
            ======================================================
            COMPOSITION OVER INHERITANCE
            ======================================================

            Decorator Pattern heavily uses:

                HAS-A relationship

            Example:

                MilkDecorator
                    HAS-A
                Coffee

         */



        /*
            ======================================================
            WHY BETTER THAN INHERITANCE?
            ======================================================

            Inheritance creates:
            huge class hierarchy.

            Decorator:
            runtime flexible behavior.

         */



        /*
            ======================================================
            REAL WORLD JAVA EXAMPLES
            ======================================================

            MOST IMPORTANT:

            Java IO Classes

            Example:

                FileInputStream fis =
                        new FileInputStream();

                BufferedInputStream bis =
                        new BufferedInputStream(fis);

                DataInputStream dis =
                        new DataInputStream(bis);

            Stream wrapped layer-by-layer.

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            Spring Security filters

            Logging wrappers

            Transaction wrappers

            Many features internally behave
            like decorators.

         */



        /*
            ======================================================
            REAL COMPANY USAGE
            ======================================================

            E-commerce:
            add discounts dynamically

            Security:
            add authentication layer

            Logging:
            add logging wrapper

            Compression:
            add compression wrapper

         */



        /*
            ======================================================
            KEY PLAYERS
            ======================================================

            1. Component
                common interface

            2. Concrete Component
                original object

            3. Decorator
                wrapper parent

            4. Concrete Decorator
                adds behavior

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - runtime behavior addition
            - avoids class explosion
            - flexible
            - reusable
            - open closed principle

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - many small objects
            - debugging slightly difficult
            - layered complexity

         */



        /*
            ======================================================
            DECORATOR vs INHERITANCE
            ======================================================

            Inheritance:
            compile-time behavior extension

            Decorator:
            runtime behavior extension

         */



        /*
            ======================================================
            DECORATOR vs ADAPTER
            ======================================================

            Adapter:
            changes interface compatibility

            Decorator:
            adds extra behavior

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Decorator Pattern?

            Answer:
            Add behavior dynamically.



            Q2:
            Real-world analogy?

            Answer:
            Pizza toppings.



            Q3:
            Which relationship heavily used?

            Answer:
            Composition (HAS-A).



            Q4:
            Biggest advantage?

            Answer:
            Avoids inheritance explosion.



            Q5:
            Java real-world example?

            Answer:
            Java IO streams.



            Q6:
            Decorator and wrapped object
            must share what?

            Answer:
            Same parent type/interface.



            Q7:
            Difference from Adapter?

            Answer:

            Adapter:
            converts interface

            Decorator:
            adds functionality



            Q8:
            Runtime or compile-time behavior addition?

            Answer:
            Runtime.

         */



        /*
            ======================================================
            IS DECORATOR PATTERN COMPLETE?
            ======================================================

            BASIC TO INTERMEDIATE:
            YES COMPLETE.

            Next Important Pattern:

                PROXY PATTERN

         */

    }

}



/*
    ==========================================================
    COMPONENT
    ==========================================================

    Common interface.
 */
interface Coffee {

    String getDescription();

    int getCost();
}



/*
    ==========================================================
    CONCRETE COMPONENT
    ==========================================================

    Base original object.
 */
class PlainCoffee implements Coffee {

    @Override
    public String getDescription() {

        return "Plain Coffee";
    }



    @Override
    public int getCost() {

        return 100;
    }

}



/*
    ==========================================================
    DECORATOR PARENT
    ==========================================================

    Wrapper parent class.
 */
abstract class CoffeeDecorator implements Coffee {

    /*
        HAS-A relationship.
     */
    protected Coffee coffee;



    public CoffeeDecorator(Coffee coffee) {

        this.coffee = coffee;
    }

}



/*
    ==========================================================
    CONCRETE DECORATOR-1
    ==========================================================

    Adds milk feature.
 */
class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee coffee) {

        super(coffee);
    }



    @Override
    public String getDescription() {

        return coffee.getDescription()
                + " + Milk";
    }



    @Override
    public int getCost() {

        return coffee.getCost() + 20;
    }

}



/*
    ==========================================================
    CONCRETE DECORATOR-2
    ==========================================================

    Adds sugar feature.
 */
class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee coffee) {

        super(coffee);
    }



    @Override
    public String getDescription() {

        return coffee.getDescription()
                + " + Sugar";
    }



    @Override
    public int getCost() {

        return coffee.getCost() + 10;
    }

}