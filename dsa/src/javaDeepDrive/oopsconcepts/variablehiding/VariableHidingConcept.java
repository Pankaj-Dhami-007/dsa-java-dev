package javaDeepDrive.oopsconcepts.variablehiding;

/*

=========================================================
                    VARIABLE HIDING
=========================================================

Definition:
Variable hiding occurs when a local variable,
method parameter, or child class variable has
the SAME name as another variable.

The nearest scope variable hides outer variable.

=========================================================
                TYPES OF VARIABLE HIDING
=========================================================

1. Local Variable hides Instance Variable
2. Parameter hides Instance Variable
3. Child Variable hides Parent Variable

=========================================================
*/


public class VariableHidingConcept {

    public static void main(String[] args) {

        System.out.println("======== LOCAL VARIABLE HIDING ========");

        Employee employee = new Employee();

        employee.printLocalVariable();

        employee.printInstanceVariable();



        System.out.println("\n======== PARAMETER HIDING ========");

        employee.setName("Pankaj");



        System.out.println("\n======== CHILD VARIABLE HIDING ========");

        Parent parent = new Child();

        parent.display();



        System.out.println(parent.message);



        Child child = new Child();

        System.out.println(child.message);
    }
}



/*
=========================================================
        1. LOCAL VARIABLE HIDING
=========================================================
*/

class Employee {

    // INSTANCE VARIABLE
    String name = "Instance Variable";



    void printLocalVariable() {

        // LOCAL VARIABLE
        String name = "Local Variable";

        System.out.println(name);
    }



    void printInstanceVariable() {

        String name = "Local Variable";

        // this refers to current object variable

        System.out.println(this.name);
    }



/*
=========================================================
        2. PARAMETER HIDING
=========================================================
*/

    void setName(String name) {

        /*
        Parameter name hides instance variable name
        */

        this.name = name;

        System.out.println(this.name);
    }
}



/*
=========================================================
        3. CHILD VARIABLE HIDING
=========================================================
*/

class Parent {

    String message = "Parent Variable";



    void display() {

        System.out.println(message);
    }
}



class Child extends Parent {

    String message = "Child Variable";
}



/*

=========================================================
                    OUTPUT
=========================================================

======== LOCAL VARIABLE HIDING ========

Local Variable
Instance Variable


======== PARAMETER HIDING ========

Pankaj


======== CHILD VARIABLE HIDING ========

Parent Variable

Parent Variable

Child Variable

=========================================================
            IMPORTANT UNDERSTANDING
=========================================================

Parent parent = new Child();

parent.message

OUTPUT:
--------
Parent Variable

WHY?
-----
Variables do NOT support runtime polymorphism.

Variables are resolved using REFERENCE TYPE.

Reference Type:
----------------
Parent

So parent.message executes Parent variable.

=========================================================
            METHOD VS VARIABLE
=========================================================

METHODS:
---------
- Runtime polymorphism supported
- Object type decides execution

VARIABLES:
-----------
- No runtime polymorphism
- Reference type decides access

=========================================================
                THIS KEYWORD
=========================================================

this.variableName

Used to access current object instance variable.

Especially useful when:
- local variable hides instance variable
- parameter hides instance variable

=========================================================
                MEMORY UNDERSTANDING
=========================================================

Employee Object:
-------------------------
| Instance Variable      |
| Methods                |
-------------------------

Local variables:
----------------
Stored inside method stack memory.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is variable hiding?
Q2. Difference between variable hiding and method overriding?
Q3. Why use this keyword?
Q4. Do variables support polymorphism?
Q5. Can child class hide parent variable?
Q6. Difference between local and instance variables?

=========================================================
                IMPORTANT RULES
=========================================================

1. Local variable gets highest priority
2. Then instance variable
3. this accesses current object variable
4. Variables use compile-time binding
5. Variables do NOT override

=========================================================
                INTERVIEW ANSWER
=========================================================

Variable hiding occurs when a local variable,
parameter, or child class variable has the same
name as another variable.

The nearest scope variable hides outer variable.

Variables are resolved using reference type,
not object type.

=========================================================

*/