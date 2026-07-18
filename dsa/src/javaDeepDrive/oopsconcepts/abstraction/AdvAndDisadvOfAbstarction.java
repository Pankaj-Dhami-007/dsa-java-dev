package javaDeepDrive.oopsconcepts.abstraction;

/*

=========================================================
    ADVANTAGES AND DISADVANTAGES OF ABSTRACTION
=========================================================

Definition:
Abstraction hides implementation details and
shows only essential functionality to the user.

Example:
--------
ATM Machine

User only sees:
- withdraw()
- deposit()

User does NOT see:
- database logic
- server communication
- transaction processing

=========================================================
*/


public class AdvAndDisadvOfAbstarction {

    public static void main(String[] args) {

        System.out.println("======== ABSTRACTION EXAMPLE ========");

        Account account = new SavingAccount();

        account.deposit(5000);

        account.withdraw(2000);
    }
}



/*
=========================================================
                ABSTRACT CLASS
=========================================================
*/

abstract class Account {

    abstract void deposit(int amount);

    abstract void withdraw(int amount);
}



/*
=========================================================
            IMPLEMENTATION CLASS
=========================================================
*/

class SavingAccount extends Account {

    private int balance;



    @Override
    void deposit(int amount) {

        balance += amount;

        System.out.println(amount + " deposited");
    }



    @Override
    void withdraw(int amount) {

        if (amount <= balance) {

            balance -= amount;

            System.out.println(amount + " withdrawn");

        } else {

            System.out.println("Insufficient Balance");
        }
    }
}



/*

=========================================================
        ADVANTAGES OF ABSTRACTION
=========================================================

1. Simplifies Complexity
-------------------------
User focuses only on important features.

Example:
---------
Driving car without knowing engine internals.

---------------------------------------------------------

2. Improves Code Reusability
-----------------------------
Common logic can be reused using:
- abstract classes
- interfaces

---------------------------------------------------------

3. Enhances Maintainability
----------------------------
Internal implementation can change
without affecting users.

---------------------------------------------------------

4. Improves Security
--------------------
Sensitive implementation details remain hidden.

---------------------------------------------------------

5. Loose Coupling
-----------------
Programs depend on abstraction,
not concrete implementation.

=========================================================
        DISADVANTAGES OF ABSTRACTION
=========================================================

1. Can Lead to Over-Engineering
--------------------------------
Too many abstractions make system complex.

---------------------------------------------------------

2. Learning Curve
------------------
Difficult for beginners to understand.

---------------------------------------------------------

3. Performance Overhead
------------------------
Extra abstraction layers may slightly
reduce performance.

---------------------------------------------------------

4. Important Details May Be Hidden
-----------------------------------
Debugging becomes difficult sometimes.

=========================================================
                REAL LIFE EXAMPLES
=========================================================

1. ATM Machine
---------------
User sees operations only.

2. Banking Application
-----------------------
User uses account object
without knowing backend logic.

3. Car
-------
Driver uses steering and brake
without knowing engine mechanism.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What are advantages of abstraction?
Q2. What are disadvantages of abstraction?
Q3. Why abstraction important?
Q4. How abstraction reduces complexity?
Q5. Difference between abstraction and encapsulation?
Q6. Can abstraction affect performance?

=========================================================
                INTERVIEW ANSWER
=========================================================

Advantages of Abstraction:
---------------------------
1. Simplifies complexity
2. Improves code reusability
3. Enhances maintainability
4. Improves security
5. Provides loose coupling

Disadvantages of Abstraction:
------------------------------
1. Can lead to over-engineering
2. Learning curve for beginners
3. Slight performance overhead
4. Important details may be hidden

=========================================================

*/