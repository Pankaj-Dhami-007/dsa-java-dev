package javaDeepDrive.oopsconcepts.abstraction;

/*

=========================================================
    IN WHICH SITUATION WE USE ABSTRACT CLASS'S
                CONCRETE METHOD?
=========================================================

IMPORTANT INTERVIEW QUESTION
=========================================================

We use concrete methods in abstract classes when:

1. Multiple subclasses need COMMON LOGIC
2. We want CODE REUSABILITY
3. We want DEFAULT BEHAVIOR
4. We want HELPER METHODS
5. We want partial abstraction

=========================================================
                IMPORTANT UNDERSTANDING
=========================================================

Abstract class can contain:
--------------------------------
1. Abstract methods
2. Concrete methods

ABSTRACT METHOD:
----------------
Only declaration

CONCRETE METHOD:
----------------
Has implementation

=========================================================
*/


public class AbstractConcreteMethod {

    public static void main(String[] args) {

        Car car = new Car();

        car.start();

        car.stop();



        Bike bike = new Bike();

        bike.start();

        bike.stop();
    }
}



/*
=========================================================
                ABSTRACT CLASS
=========================================================
*/

abstract class Vehicle {



    /*
    ABSTRACT METHOD
    ----------------
    Different behavior for subclasses
    */

    abstract void start();



    /*
    CONCRETE METHOD
    ----------------
    SAME behavior for all subclasses
    Common reusable logic
    */

    void stop() {

        System.out.println("Vehicle Stopped");
    }



    /*
    HELPER METHOD
    ----------------
    Common utility/helper functionality
    */

    void fuelCheck() {

        System.out.println("Fuel Checked");
    }
}



/*
=========================================================
                CHILD CLASS
=========================================================
*/

class Car extends Vehicle {

    @Override
    void start() {

        fuelCheck();

        System.out.println("Car Started");
    }
}



/*
=========================================================
                ANOTHER CHILD
=========================================================
*/

class Bike extends Vehicle {

    @Override
    void start() {

        fuelCheck();

        System.out.println("Bike Started");
    }
}



/*

=========================================================
                    OUTPUT
=========================================================

Fuel Checked
Car Started
Vehicle Stopped

Fuel Checked
Bike Started
Vehicle Stopped

=========================================================
            WHY USE CONCRETE METHOD?
=========================================================

Suppose 100 subclasses need same stop() logic.

Without concrete method:
-------------------------
Duplicate code in all subclasses.

With concrete method:
----------------------
Write once in abstract class.

=========================================================
                REAL LIFE EXAMPLE
=========================================================

Abstract Class:
----------------
Vehicle

Common Behavior:
----------------
- stop()
- fuelCheck()

Specific Behavior:
-------------------
- start()

Because:
---------
Every vehicle starts differently,
but stopping behavior may be same.

=========================================================
            DEFAULT BEHAVIOR
=========================================================

Concrete methods provide default behavior.

Subclasses:
-------------
- can use directly
OR
- override if needed

=========================================================
            TEMPLATE METHOD DESIGN
=========================================================

Very famous design pattern.

Abstract class controls flow using:
- concrete methods
- abstract methods

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Why use concrete methods in abstract class?
Q2. Difference between abstract and concrete methods?
Q3. Can abstract class contain implemented methods?
Q4. What is partial abstraction?
Q5. Can child override concrete method?
Q6. Why not use interface instead?

=========================================================
                IMPORTANT RULES
=========================================================

1. Abstract class may contain:
   - abstract methods
   - concrete methods

2. Concrete methods support code reuse

3. Child classes inherit concrete methods

4. Child may override concrete methods

=========================================================
                INTERVIEW ANSWER
=========================================================

We use concrete methods in abstract classes
when multiple subclasses require common
functionality or default behavior.

Concrete methods help achieve:
- code reusability
- maintainability
- partial abstraction

while abstract methods allow subclass-specific
implementation.

=========================================================

*/