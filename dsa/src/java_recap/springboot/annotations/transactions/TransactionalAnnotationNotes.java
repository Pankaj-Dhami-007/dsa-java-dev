package java_recap.springboot.annotations.transactions;

/*

=================================================================
                @Transactional
=================================================================

Core Concepts:
---------------
1. Database Transactions
2. ACID Properties
3. Commit & Rollback
4. Transaction Propagation
5. Isolation Levels
6. Spring AOP Proxy
7. Hibernate Session
8. Transaction Manager

=================================================================
                WHY TRANSACTIONS EXIST?
=================================================================

Imagine banking operation:

-----------------------------------------------------------------

1. Deduct money from Account A
2. Add money to Account B

-----------------------------------------------------------------

=================================================================
                PROBLEM
=================================================================

Suppose:

Money deducted from A
BUT
Server crashes before adding to B.

=================================================================
                RESULT
=================================================================

Data inconsistency.

Money lost.

=================================================================
                SOLUTION
=================================================================

Database Transactions.

=================================================================
                WHAT IS TRANSACTION?
=================================================================

Transaction =
--------------
Group of operations executed
as SINGLE UNIT OF WORK.

=================================================================
                IMPORTANT RULE
=================================================================

Either:
---------

ALL operations succeed

OR

ALL operations fail

=================================================================
                REAL EXAMPLE
=================================================================

E-commerce Order:
------------------

1. Create order
2. Reduce inventory
3. Process payment
4. Save invoice

If payment fails:
------------------

Everything should rollback.

=================================================================
                ACID PROPERTIES
=================================================================

Most important interview topic.

=================================================================
                A → Atomicity
=================================================================

All or nothing.

=================================================================
                EXAMPLE
=================================================================

Bank transfer:
---------------

Deduct + Add both succeed together.

=================================================================
                C → Consistency
=================================================================

Database remains valid before/after transaction.

=================================================================
                EXAMPLE
=================================================================

Account balance rules maintained.

=================================================================
                I → Isolation
=================================================================

Concurrent transactions should not interfere.

=================================================================
                D → Durability
=================================================================

Once committed,
data permanently stored.

=================================================================
                WHAT IS @Transactional?
=================================================================

Most important Spring transaction annotation.

=================================================================
                DEFINITION
=================================================================

@Transactional tells Spring:

"Execute this method inside database transaction."

=================================================================
                EXAMPLE
=================================================================

@Service
public class PaymentService {

    @Transactional

    public void transferMoney() {

        debit();

        credit();

    }

}

=================================================================
                WHAT HAPPENS INTERNALLY?
=================================================================

Method Called
        ↓
Spring Proxy Intercepts Call
        ↓
Transaction Started
        ↓
Method Executes
        ↓
If Success → COMMIT
If Exception → ROLLBACK

=================================================================
                VERY IMPORTANT
=================================================================

@Transactional works using:
----------------------------

AOP Proxy Mechanism

=================================================================
                INTERNAL FLOW
=================================================================

Client
   ↓
Proxy Object
   ↓
Transaction Manager
   ↓
Actual Method Execution
   ↓
Commit/Rollback

=================================================================
                SPRING TRANSACTION MANAGER
=================================================================

Central component managing transactions.

=================================================================
                RESPONSIBILITIES
=================================================================

1. Begin transaction
2. Commit transaction
3. Rollback transaction
4. Manage connection lifecycle

=================================================================
                DEFAULT BEHAVIOR
=================================================================

Rollback happens ONLY for:
---------------------------

RuntimeException
and
Error

=================================================================
                IMPORTANT
=================================================================

Checked exceptions DO NOT rollback by default.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Why checked exceptions not rollback?

=================================================================
                ANSWER
=================================================================

Spring assumes checked exceptions
may represent recoverable business scenarios.

=================================================================
                CUSTOM ROLLBACK
=================================================================

@Transactional(
    rollbackFor = Exception.class
)

=================================================================
                NOW
=================================================================

Checked exceptions also rollback.

=================================================================
                COMMIT FLOW
=================================================================

Transaction starts
        ↓
SQL queries execute
        ↓
Changes stored temporarily
        ↓
Commit occurs
        ↓
Changes permanently saved

=================================================================
                ROLLBACK FLOW
=================================================================

Transaction starts
        ↓
Exception occurs
        ↓
Rollback triggered
        ↓
All DB changes reverted

=================================================================
                REAL PROJECT EXAMPLE
=================================================================

Order Service:
---------------

@Transactional
public void placeOrder() {

    saveOrder();

    reduceInventory();

    processPayment();

}

=================================================================
                IF PAYMENT FAILS
=================================================================

Entire order transaction rollback.

=================================================================
                WHY IMPORTANT?
=================================================================

Prevents partial/inconsistent data.

=================================================================
                TRANSACTION PROPAGATION
=================================================================

Most asked interview topic.

=================================================================
                WHAT IS PROPAGATION?
=================================================================

Defines transaction behavior
when one transactional method
calls another transactional method.

=================================================================
                EXAMPLE
=================================================================

OrderService
    ↓
PaymentService
    ↓
InventoryService

=================================================================
                QUESTION
=================================================================

Should child methods:
----------------------

1. Join existing transaction?
2. Create new transaction?

=================================================================
                PROPAGATION TYPES
=================================================================

1. REQUIRED
2. REQUIRES_NEW
3. SUPPORTS
4. MANDATORY
5. NEVER
6. NOT_SUPPORTED
7. NESTED

=================================================================
                1. REQUIRED
=================================================================

DEFAULT propagation.

=================================================================
                BEHAVIOR
=================================================================

If transaction exists:
-----------------------

Join existing transaction.

Otherwise:
------------

Create new transaction.

=================================================================
                MOST USED
=================================================================

@Transactional
(default REQUIRED)

=================================================================
                2. REQUIRES_NEW
=================================================================

Always creates NEW transaction.

=================================================================
                IMPORTANT
=================================================================

Existing transaction suspended temporarily.

=================================================================
                REAL PROJECT USE CASE
=================================================================

Audit logging.

=================================================================
                EXAMPLE
=================================================================

Main transaction fails,
but audit log should still save.

=================================================================
                3. SUPPORTS
=================================================================

Uses transaction if exists.

Otherwise runs without transaction.

=================================================================
                4. MANDATORY
=================================================================

Must already have transaction.

Otherwise exception thrown.

=================================================================
                5. NEVER
=================================================================

Must NOT run inside transaction.

=================================================================
                6. NOT_SUPPORTED
=================================================================

Suspends existing transaction.

Runs non-transactionally.

=================================================================
                7. NESTED
=================================================================

Creates nested transaction/savepoint.

=================================================================
                ISOLATION LEVELS
=================================================================

Very important DB interview topic.

=================================================================
                WHY NEEDED?
=================================================================

Multiple users access DB simultaneously.

=================================================================
                PROBLEMS
=================================================================

1. Dirty Read
2. Non-repeatable Read
3. Phantom Read

=================================================================
                DIRTY READ
=================================================================

Reading uncommitted data.

=================================================================
                NON-REPEATABLE READ
=================================================================

Same query returns different values
inside same transaction.

=================================================================
                PHANTOM READ
=================================================================

New rows appear during transaction.

=================================================================
                ISOLATION LEVELS
=================================================================

READ_UNCOMMITTED
READ_COMMITTED
REPEATABLE_READ
SERIALIZABLE

=================================================================
                SERIALIZABLE
=================================================================

Highest isolation.

=================================================================
                PROBLEM
=================================================================

Slow performance.

=================================================================
                MOST USED
=================================================================

READ_COMMITTED

=================================================================
                TRANSACTIONAL ON CLASS
=================================================================

@Transactional
@Service
public class UserService {

}

=================================================================
                MEANING
=================================================================

All public methods transactional.

=================================================================
                IMPORTANT LIMITATION
=================================================================

@Transactional works only on:
------------------------------

PUBLIC methods

=================================================================
                WHY?
=================================================================

Because Spring AOP proxy intercepts
public method calls.

=================================================================
                SELF INVOCATION PROBLEM
=================================================================

Very famous interview question.

=================================================================
                EXAMPLE
=================================================================

@Service
public class UserService {

    public void methodA() {

        methodB();

    }

    @Transactional
    public void methodB() {

    }

}

=================================================================
                PROBLEM
=================================================================

Transaction NOT applied.

=================================================================
                WHY?
=================================================================

Internal method call bypasses proxy.

=================================================================
                MOST IMPORTANT INTERNAL
=================================================================

@Transactional uses:
---------------------

Proxy-based AOP

=================================================================
                NO PROXY
=================================================================

No transaction management.

=================================================================
                LAZY LOADING CONNECTION
=================================================================

Hibernate Session often tied
to transaction lifecycle.

=================================================================
                WITHOUT TRANSACTION
=================================================================

LazyInitializationException possible.

=================================================================
                READ ONLY TRANSACTIONS
=================================================================

@Transactional(readOnly = true)

=================================================================
                BENEFITS
=================================================================

1. Optimization
2. Better performance
3. Prevent accidental updates

=================================================================
                TIMEOUT
=================================================================

@Transactional(timeout = 5)

=================================================================
                MEANING
=================================================================

Transaction fails after 5 seconds.

=================================================================
                REAL PROJECT SCENARIOS
=================================================================

Banking:
---------
Money transfer

E-commerce:
-------------
Order placement

Booking:
----------
Seat reservation

Payment:
----------
Wallet updates

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
How @Transactional works internally?

=================================================================
                ANSWER
=================================================================

Spring creates AOP proxy around bean.
Proxy intercepts method calls,
starts transaction before execution,
and commits/rolls back after execution.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why self-invocation breaks @Transactional?

=================================================================
                ANSWER
=================================================================

Internal method calls bypass Spring proxy,
so transaction interception never happens.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Difference between REQUIRED
and REQUIRES_NEW?

=================================================================
                ANSWER
=================================================================

REQUIRED:
-----------
Joins existing transaction.

REQUIRES_NEW:
---------------
Always creates new transaction.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Will checked exception trigger rollback?

NO by default.

=================================================================
                SOLUTION
=================================================================

rollbackFor attribute.

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Can private method be transactional?

NO.

Proxy cannot intercept private methods.

=================================================================
                COMMON MISTAKES
=================================================================

1. Using transactions everywhere

2. Long-running transactions

3. Ignoring propagation

4. Self-invocation issues

5. Mixing external API calls inside transaction

=================================================================
                BEST PRACTICE
=================================================================

1. Keep transactions short
2. Use service layer transactions
3. Prefer REQUIRED mostly
4. Avoid DB + remote calls together
5. Understand lazy loading

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain complete flow of @Transactional.

Answer:
--------
1. Client calls transactional method
2. Spring proxy intercepts method
3. Transaction manager starts transaction
4. Hibernate session participates
5. SQL operations execute
6. If success → commit
7. If exception → rollback

=================================================================
                SUMMARY
=================================================================

@Transactional
----------------
Executes method inside DB transaction.

Spring internally uses:
------------------------

1. AOP Proxy
2. Transaction Manager
3. Hibernate Session

Supports:
-----------
1. Commit/Rollback
2. Propagation
3. Isolation
4. Read-only optimization

=================================================================

*/

public class TransactionalAnnotationNotes {
}