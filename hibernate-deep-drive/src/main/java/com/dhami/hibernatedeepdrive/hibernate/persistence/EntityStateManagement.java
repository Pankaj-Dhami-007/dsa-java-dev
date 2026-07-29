package com.dhami.hibernatedeepdrive.hibernate.persistence;

/*
 * =============================================================================
 *                          EntityStates.java
 * =============================================================================
 *
 * Goal
 * ----
 * Understand the different states through which an entity passes during
 * its lifetime.
 *
 * After completing this chapter, you should be able to answer:
 *
 * • What is a Transient entity?
 * • What is a Managed entity?
 * • What is a Detached entity?
 * • What is a Removed entity?
 * • How does Hibernate behave differently for each state?
 *
 * Everything related to Dirty Checking, Flush, Merge and Remove depends
 * on understanding these states.
 *
 */
public class EntityStateManagement {
}

/*
 * =============================================================================
 * Why Entity States?
 * =============================================================================
 *
 * Consider the following code.
 *
 *      Employee employee = new Employee();
 *
 * Is this employee stored in the database?
 *
 * Hibernate doesn't know.
 *
 * Now consider:
 *
 *      entityManager.persist(employee);
 *
 * The same Java object still exists.
 *
 * But Hibernate now treats it differently.
 *
 * Why?
 *
 * Because its state has changed.
 *
 * Hibernate behaves based on an entity's state,
 * not simply because it is a Java object.
 *
 */

/*
 * =============================================================================
 * The Four Entity States
 * =============================================================================
 *
 *                    new Employee()
 *                          │
 *                          ▼
 *        ┌──────────────────────────────────────┐
 *        │        TRANSIENT                      │
 *        │                                      │
 *        │ • Only exists in Java memory         │
 *        │ • Not tracked by Hibernate           │
 *        │ • No database identity               │
 *        └──────────────────────────────────────┘
 *                          │
 *              entityManager.persist()
 *                          │
 *                          ▼
 *        ┌──────────────────────────────────────┐
 *        │         MANAGED                      │
 *        │                                      │
 *        │ • Stored in Persistence Context      │
 *        │ • Hibernate tracks every change      │
 *        │ • Dirty Checking enabled             │
 *        └──────────────────────────────────────┘
 *                          │
 *             EntityManager closes
 *             clear()
 *             detach()
 *                          │
 *                          ▼
 *        ┌──────────────────────────────────────┐
 *        │         DETACHED                     │
 *        │                                      │
 *        │ • Normal Java object                 │
 *        │ • Hibernate no longer tracks it      │
 *        │ • Changes are ignored                │
 *        └──────────────────────────────────────┘
 *                          │
 *                 entityManager.remove()
 *                          │
 *                          ▼
 *        ┌──────────────────────────────────────┐
 *        │          REMOVED                     │
 *        │                                      │
 *        │ • Scheduled for deletion             │
 *        │ • DELETE SQL generated on Flush      │
 *        └──────────────────────────────────────┘
 *
 */

/*
 * =============================================================================
 * TRANSIENT State
 * =============================================================================
 *
 * A Transient entity is a normal Java object.
 *
 * Hibernate has absolutely no knowledge of it.
 *
 * Example:
 *
 *      Employee employee = new Employee();
 *
 * At this point:
 *
 * ✓ Object exists in JVM memory
 *
 * ✓ Persistence Context does not contain it
 *
 * ✓ Database does not contain it
 *
 * ✓ Hibernate does not track changes
 *
 * Think of it as:
 *
 *      "A newly born object."
 *
 */

/*
 * =============================================================================
 * TRANSIENT
 * =============================================================================
 *
 * JVM Memory
 *
 *      Employee
 *      +----------------+
 *      | id = null      |
 *      | name=Pankaj    |
 *      +----------------+
 *
 *
 * Persistence Context
 *
 *      Empty
 *
 *
 * Database
 *
 *      No Row
 *
 */


/*
 * =============================================================================
 * Characteristics of TRANSIENT
 * =============================================================================
 *
 * ✓ Created using new
 *
 * ✓ Not stored inside Persistence Context
 *
 * ✓ No SQL generated
 *
 * ✓ Hibernate ignores it completely
 *
 * ✓ Can be garbage collected like any normal object
 *
 */





/*
 * =============================================================================
 * MANAGED State
 * =============================================================================
 *
 * A Managed entity is an entity that is currently being tracked by Hibernate.
 *
 * It exists inside the Persistence Context.
 *
 * Hibernate continuously monitors every managed entity for changes.
 *
 * This is the most important entity state because almost every Hibernate
 * feature works only for managed entities.
 *
 * Think of a managed entity as:
 *
 *      "An employee currently under Hibernate's supervision."
 *
 */


/*
 * =============================================================================
 * How Does an Entity Become Managed?
 * =============================================================================
 *
 * There are several ways an entity enters the Persistence Context.
 *
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Operation                         Result                                │
 * ├──────────────────────────────────────────────────────────────────────────┤
 * │ entityManager.persist(entity)    New entity becomes Managed             │
 * │ entityManager.find(id)           Loaded entity becomes Managed          │
 * │ repository.findById(id)          Managed entity returned                │
 * │ JPQL Query                       Returned entities become Managed       │
 * └──────────────────────────────────────────────────────────────────────────┘
 *
 * Once an entity becomes Managed,
 * Hibernate starts tracking it automatically.
 *
 */

/*
 * =============================================================================
 * Managed Entity Inside Persistence Context
 * =============================================================================
 *
 *                        EntityManager
 *                              │
 *                              ▼
 *        ┌──────────────────────────────────────────────────────────────┐
 *        │                  Persistence Context                         │
 *        │                                                              │
 *        │  ┌────────────────────────────────────────────────────────┐  │
 *        │  │ Employee (Managed)                                    │  │
 *        │  │--------------------------------------------------------│  │
 *        │  │ id      = 101                                          │  │
 *        │  │ name    = "Pankaj"                                     │  │
 *        │  │ salary  = 50000                                        │  │
 *        │  └────────────────────────────────────────────────────────┘  │
 *        │                                                              │
 *        │ Hibernate continuously watches this object.                  │
 *        └──────────────────────────────────────────────────────────────┘
 *                              │
 *                              ▼
 *                        MySQL Database
 *
 */

//Employee employee =
//        entityManager.find(Employee.class, 101L);

/*
 * =============================================================================
 * Internal Flow
 * =============================================================================
 *
 * entityManager.find(Employee.class, 101L)
 *
 *                     │
 *                     ▼
 *      Check Persistence Context
 *                     │
 *         ┌───────────┴───────────┐
 *         │                       │
 *         ▼                       ▼
 *   Found in Memory         Not Found
 *         │                       │
 *         ▼                       ▼
 * Return Same Object       Execute SELECT
 *                                   │
 *                                   ▼
 *                          Create Employee
 *                                   │
 *                                   ▼
 *                     Store in Persistence Context
 *                                   │
 *                                   ▼
 *                          Return Managed Entity
 *
 */


/*
 * =============================================================================
 * Why Managed State Is Powerful
 * =============================================================================
 *
 * Once an entity becomes Managed,
 * Hibernate automatically provides:
 *
 * ✓ Dirty Checking
 *
 * ✓ First Level Cache
 *
 * ✓ Automatic SQL generation
 *
 * ✓ Transaction synchronization
 *
 * ✓ Relationship management
 *
 * ✓ Lazy Loading support
 *
 * All of these features work because Hibernate owns the entity.
 *
 */

// example
// Modify the Entity
// Employee employee =
//        entityManager.find(Employee.class, 101L);
//
//employee.setSalary(65000);

//Most beginners ask: "Where is update()?"

/*
 * =============================================================================
 * What Happens After setSalary()?
 * =============================================================================
 *
 *                    Managed Employee
 *                           │
 *                           ▼
 *                employee.setSalary(65000)
 *                           │
 *                           ▼
 *         Persistence Context now contains:
 *
 *                  salary = 65000
 *
 *                           │
 *                           ▼
 *           Hibernate marks entity as modified
 *
 *                           │
 *                           ▼
 *                SQL NOT executed yet
 *
 * Hibernate waits until Flush or Transaction Commit.
 *
 */


/*
 * =============================================================================
 * Common Misconception
 * =============================================================================
 *
 * Many developers think:
 *
 *      employee.setSalary(65000);
 *
 * immediately executes:
 *
 *      UPDATE employee ...
 *
 * This is incorrect.
 *
 * Modifying a managed entity changes only the Java object.
 *
 * The database remains unchanged until Hibernate performs a Flush.
 *
 */

/*
 * =============================================================================
 * Characteristics of MANAGED State
 * =============================================================================
 *
 * ✓ Stored inside Persistence Context
 *
 * ✓ Hibernate tracks every field
 *
 * ✓ Dirty Checking enabled
 *
 * ✓ First Level Cache enabled
 *
 * ✓ Can participate in Flush
 *
 * ✓ Automatically synchronized with the database
 *
 */

/*
 * =============================================================================
 * Interview Question
 * =============================================================================
 *
 * Q. Why can Hibernate update the database without calling update()?
 *
 * Answer:
 *
 * Because the entity is in the MANAGED state.
 *
 * Hibernate continuously tracks managed entities inside the Persistence
 * Context.
 *
 * During Flush, it compares the current state with the original state and
 * generates the required SQL automatically.
 *
 */






/*
 * =============================================================================
 * DETACHED State
 * =============================================================================
 *
 * A Detached entity is an entity that was once Managed,
 * but Hibernate is no longer tracking it.
 *
 * The Java object still exists in memory.
 *
 * The data inside the object is still accessible.
 *
 * However,
 *
 * Hibernate has completely stopped monitoring it.
 *
 * Any modifications made to a Detached entity are ignored
 * unless the entity is attached again.
 *
 */

/*
 * =============================================================================
 * Real Life Analogy
 * =============================================================================
 *
 * Imagine an employee working in a company.
 *
 *                     Employee
 *                        │
 *                        ▼
 *             Company HR Department
 *
 * As long as the employee works in the company,
 * HR keeps track of:
 *
 * • Salary
 * • Department
 * • Designation
 * • Attendance
 *
 * Now suppose the employee resigns.
 *
 * The employee still exists.
 *
 * But HR no longer tracks future changes.
 *
 * The Detached state is exactly the same.
 *
 * The entity still exists.
 *
 * Hibernate simply stops tracking it.
 *
 */

/*
 * =============================================================================
 * Detached Entity
 * =============================================================================
 *
 *                    EntityManager Closed
 *                            │
 *                            ▼
 *
 *      ┌───────────────────────────────────────────────────────────────┐
 *      │                Persistence Context                            │
 *      │                                                               │
 *      │      (Employee Removed From Context)                          │
 *      │                                                               │
 *      └───────────────────────────────────────────────────────────────┘
 *
 *                            │
 *                            ▼
 *
 *                 Java Heap (Detached Object)
 *
 *      ┌───────────────────────────────────────────────┐
 *      │ Employee                                      │
 *      │-----------------------------------------------│
 *      │ id      = 101                                 │
 *      │ name    = "Pankaj"                            │
 *      │ salary  = 50000                               │
 *      │                                               │
 *      │ State : DETACHED                              │
 *      └───────────────────────────────────────────────┘
 *
 *
 * Hibernate no longer watches this object.
 *
 */

/*
 * =============================================================================
 * How Does an Entity Become Detached?
 * =============================================================================
 *
 * The most common reasons are:
 *
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Operation                      Result                                   │
 * ├──────────────────────────────────────────────────────────────────────────┤
 * │ EntityManager closes           All entities become Detached             │
 * │ entityManager.clear()          Entire Persistence Context cleared       │
 * │ entityManager.detach(entity)   One entity becomes Detached             │
 * │ Transaction ends               (Typical Spring Boot request)           │
 * └──────────────────────────────────────────────────────────────────────────┘
 *
 * Once detached,
 * Hibernate immediately stops tracking the entity.
 *
 */

/*
 * =============================================================================
 * Managed → Detached
 * =============================================================================
 *
 *                  Employee (Managed)
 *                          │
 *                          ▼
 *                 EntityManager.close()
 *                          │
 *                          ▼
 *              Remove From Persistence Context
 *                          │
 *                          ▼
 *                 Employee (Detached)
 *
 *
 * Same Java Object
 *
 * Different Hibernate State
 *
 */

/*
 * =============================================================================
 * Internal View
 * =============================================================================
 *
 * Before close()
 *
 *        Persistence Context
 *
 *              Employee#101
 *                    │
 *                    ▼
 *          Hibernate tracks changes
 *
 *
 * After close()
 *
 *        Persistence Context
 *
 *             Empty
 *
 *
 * Employee object still exists,
 * but Hibernate has lost the reference.
 *
 */

/*
 * =============================================================================
 * Why Doesn't Hibernate Execute UPDATE?
 * =============================================================================
 *
 * Hibernate performs Dirty Checking only on Managed entities.
 *
 * Detached entities are completely ignored.
 *
 * Therefore:
 *
 *      employee.setSalary(70000);
 *
 * changes only the Java object.
 *
 * Since Hibernate is no longer tracking the entity,
 * no UPDATE SQL is generated.
 *
 */

/*
 * =============================================================================
 * Characteristics of DETACHED State
 * =============================================================================
 *
 * ✓ Java object still exists
 *
 * ✓ Persistence Context no longer contains it
 *
 * ✓ Hibernate ignores modifications
 *
 * ✓ Dirty Checking disabled
 *
 * ✓ First Level Cache unavailable
 *
 * ✓ Flush has no effect
 *
 */

/*
 * =============================================================================
 * Managed vs Detached
 * =============================================================================
 *
 * ┌─────────────────────────────┬────────────────────────────────────────────┐
 * │           MANAGED           │                DETACHED                    │
 * ├─────────────────────────────┼────────────────────────────────────────────┤
 * │ Inside Persistence Context  │ Outside Persistence Context               │
 * │ Hibernate tracks changes    │ Hibernate ignores changes                 │
 * │ Dirty Checking enabled      │ Dirty Checking disabled                   │
 * │ Flush generates SQL         │ Flush generates nothing                   │
 * │ First Level Cache available │ First Level Cache unavailable             │
 * └─────────────────────────────┴────────────────────────────────────────────┘
 *
 */

/*
 * =============================================================================
 * Interview Question
 * =============================================================================
 *
 * Q. Why doesn't Hibernate update a Detached entity?
 *
 * Answer:
 *
 * Because Hibernate only performs Dirty Checking on entities that are
 * present inside the Persistence Context.
 *
 * Once an entity becomes Detached,
 * Hibernate no longer tracks its changes.
 *
 */

/*

Transient
    │ persist()
    ▼
Managed ─────detach()/close()────► Detached
    │                                 │
    │ merge()                         │
    └─────────────────────────────────┘
    │
 remove()
    ▼
Removed
    │
 flush()/commit()
    ▼
Database DELETE
 */



/*
 * =============================================================================
 * REMOVED State
 * =============================================================================
 *
 * A Removed entity is an entity that has been marked for deletion.
 *
 * It still exists inside the Persistence Context.
 *
 * However,
 *
 * Hibernate has scheduled it to be deleted from the database.
 *
 * The actual DELETE statement is usually executed during:
 *
 * • Flush
 * • Transaction Commit
 *
 * Until then,
 * the entity remains inside the Persistence Context.
 *
 */


/*
 * =============================================================================
 * Real Life Analogy
 * =============================================================================
 *
 * Imagine an employee resigns from a company.
 *
 * HR approves the resignation today.
 *
 * But the employee officially leaves on the last working day.
 *
 *
 * Current Status
 *
 *      ✓ Employee still exists
 *      ✓ Salary still exists
 *      ✓ Employee ID still exists
 *
 * Future Action
 *
 *      Employee record will be removed.
 *
 *
 * Hibernate behaves the same way.
 *
 * Calling remove() means:
 *
 *      "Schedule this entity for deletion."
 *
 * It does NOT immediately delete the database row.
 *
 */

/*
 * =============================================================================
 * REMOVED State
 * =============================================================================
 *
 *                      entityManager.remove(employee)
 *                                   │
 *                                   ▼
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │                    Persistence Context                                 │
 * │                                                                         │
 * │  Employee#101                                                          │
 * │                                                                         │
 * │  State : REMOVED                                                       │
 * │                                                                         │
 * │  Hibernate has scheduled this entity for deletion.                     │
 * └─────────────────────────────────────────────────────────────────────────┘
 *                                   │
 *                                   │
 *                            Flush / Commit
 *                                   │
 *                                   ▼
 *                           DELETE FROM employee
 *                                   │
 *                                   ▼
 *                              Database Row Deleted
 *
 */
//Notice:
//
//The entity is still inside the Persistence Context.
//
//Its state has changed.
// Employee employee =
//        entityManager.find(Employee.class, 101L);
//
//entityManager.remove(employee);

/*
 * =============================================================================
 * Internal Flow
 * =============================================================================
 *
 *                 Employee (Managed)
 *                         │
 *                         ▼
 *               entityManager.remove()
 *                         │
 *                         ▼
 *                State becomes REMOVED
 *                         │
 *                         ▼
 *              SQL NOT executed immediately
 *                         │
 *                         ▼
 *                 Flush / Commit
 *                         │
 *                         ▼
 *                DELETE SQL generated
 *
 */

/*
 * =============================================================================
 * Memory After remove()
 * =============================================================================
 *
 * Java Heap
 *
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Persistence Context                                                     │
 * │                                                                          │
 * │ Employee#101                                                            │
 * │ +--------------------------------------------------------------------+  │
 * │ | id      = 101                                                      |  │
 * │ | name    = "Pankaj"                                                 |  │
 * │ | salary  = 50000                                                    |  │
 * │ | State   = REMOVED                                                  |  │
 * │ +--------------------------------------------------------------------+  │
 * │                                                                          │
 * └──────────────────────────────────────────────────────────────────────────┘
 *
 *
 * Database
 *
 * Employee Row Still Exists
 *
 * DELETE has NOT been executed yet.
 *
 */

/*
 * =============================================================================
 * When Does Hibernate Execute DELETE?
 * =============================================================================
 *
 * Hibernate delays the DELETE operation until the Persistence Context is
 * synchronized with the database.
 *
 * Typical synchronization points are:
 *
 * • entityManager.flush()
 *
 * • Transaction Commit
 *
 * During synchronization,
 * Hibernate generates:
 *
 *      DELETE FROM employee
 *      WHERE id = ?;
 *
 */

/*
 * =============================================================================
 * Characteristics of REMOVED State
 * =============================================================================
 *
 * ✓ Entity is still managed by Hibernate
 *
 * ✓ Entity exists inside the Persistence Context
 *
 * ✓ Scheduled for deletion
 *
 * ✓ DELETE SQL is delayed until Flush or Commit
 *
 * ✓ Changes made after remove() are generally meaningless because the entity
 *   is already marked for deletion.
 *
 */

/*
 * =============================================================================
 * Common Misconception
 * =============================================================================
 *
 * Many developers believe:
 *
 *      entityManager.remove(employee);
 *
 * immediately executes:
 *
 *      DELETE FROM employee ...
 *
 * This is incorrect.
 *
 * Hibernate follows a Unit of Work pattern.
 *
 * It groups database operations together and executes them during Flush or
 * Transaction Commit.
 *
 */



/*
 * =============================================================================
 * Complete Entity State Transition
 * =============================================================================
 *
 *                         new Employee()
 *                               │
 *                               ▼
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │                            TRANSIENT                                   │
 * └─────────────────────────────────────────────────────────────────────────┘
 *                               │
 *                      persist() / save()
 *                               │
 *                               ▼
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │                             MANAGED                                    │
 * │                                                                         │
 * │ ✓ Tracked by Hibernate                                                  │
 * │ ✓ Dirty Checking                                                       │
 * │ ✓ First Level Cache                                                    │
 * │ ✓ Automatic SQL Generation                                             │
 * └─────────────────────────────────────────────────────────────────────────┘
 *            │                          │
 *            │ detach()                 │ remove()
 *            ▼                          ▼
 * ┌──────────────────────────┐   ┌─────────────────────────────────────────┐
 * │         DETACHED         │   │                REMOVED                  │
 * │                          │   │                                         │
 * │ Hibernate ignores        │   │ Scheduled for deletion                  │
 * │ all future changes       │   │                                         │
 * └──────────────────────────┘   └─────────────────────────────────────────┘
 *            ▲                          │
 *            │                          │ Flush / Commit
 *            │ merge()                  ▼
 *            │                  Database Row Deleted
 *            └──────────────────────────────────────────────────────────────
 *
 */


/*
 * =============================================================================
 * Entity State Comparison
 * =============================================================================
 *
 * ┌─────────────┬──────────┬──────────────┬──────────────┬───────────────────┐
 * │ State       │ In PC    │ Tracked      │ SQL Generated│ Typical Operation │
 * ├─────────────┼──────────┼──────────────┼──────────────┼───────────────────┤
 * │ Transient   │ No       │ No           │ No           │ new               │
 * │ Managed     │ Yes      │ Yes          │ Yes          │ persist(), find() │
 * │ Detached    │ No       │ No           │ No           │ detach(), close() │
 * │ Removed     │ Yes      │ Yes          │ DELETE later │ remove()          │
 * └─────────────┴──────────┴──────────────┴──────────────┴───────────────────┘
 *
 */
