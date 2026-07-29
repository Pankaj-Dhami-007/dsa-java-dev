package com.dhami.hibernatedeepdrive.hibernate.persistence;

/*
 * =============================================================================
 *                          DirtyChecking.java
 * =============================================================================
 *
 * Goal
 * ----
 * Understand how Hibernate automatically detects changes made to Managed
 * entities and generates UPDATE statements without requiring explicit
 * update() calls.
 *
 * After completing this chapter, you should be able to answer:
 *
 * • What is Dirty Checking?
 * • Why doesn't Hibernate need update()?
 * • When does Dirty Checking happen?
 * • Which entities participate in Dirty Checking?
 * • Why are Detached entities ignored?
 *
 */

/*
 * =============================================================================
 * Why Dirty Checking?
 * =============================================================================
 *
 * Consider the following code.
 *
 *      Employee employee =
 *              entityManager.find(Employee.class, 101L);
 *
 *      employee.setSalary(70000);
 *
 * We never called:
 *
 *      update(employee);
 *
 * Yet Hibernate still updates the database.
 *
 * How?
 *
 * Hibernate continuously monitors Managed entities.
 *
 * When it detects that an entity's state has changed,
 * it automatically generates the required UPDATE statement.
 *
 * This automatic change detection is called Dirty Checking.
 *
 */
public class DirtyChecking {
}

/*
 * =============================================================================
 * Meaning of "Dirty"
 * =============================================================================
 *
 * In Hibernate,
 *
 * "Dirty" does NOT mean invalid or corrupted.
 *
 * It simply means:
 *
 *      "The current state of an entity is different from its original state."
 *
 * Example
 *
 * Original salary
 *      50000
 *
 * Current salary
 *      70000
 *
 * Hibernate considers this entity "Dirty"
 * because one of its fields has changed.
 *
 */

/*
 * =============================================================================
 * Dirty Checking Definition
 * =============================================================================
 *
 * Dirty Checking is Hibernate's mechanism for automatically detecting
 * modifications made to Managed entities.
 *
 * During Flush or Transaction Commit,
 * Hibernate compares the entity's current state with its original state.
 *
 * If differences are found,
 * Hibernate generates an UPDATE statement automatically.
 *
 */

/*
 * =============================================================================
 * Dirty Checking Architecture
 * =============================================================================
 *
 *                    Employee (Managed)
 *                           │
 *                           ▼
 *
 *                  employee.setSalary(70000)
 *                           │
 *                           ▼
 *
 * ┌──────────────────────────────────────────────────────────────┐
 * │                 Persistence Context                          │
 * │                                                              │
 * │ Original Snapshot      Current Entity                        │
 * │                                                              │
 * │ salary = 50000        salary = 70000                         │
 * │                                                              │
 * │ Hibernate compares both values during Flush.                 │
 * └──────────────────────────────────────────────────────────────┘
 *                           │
 *                           ▼
 *                  UPDATE employee ...
 *
 */

/*
 * =============================================================================
 * Snapshot
 * =============================================================================
 *
 * When Hibernate loads a Managed entity,
 * it stores an internal copy of its original state.
 *
 * Example:
 *
 * Database
 *
 *      salary = 50000
 *
 *
 * Managed Entity
 *
 *      salary = 50000
 *
 *
 * Internal Snapshot
 *
 *      salary = 50000
 *
 *
 * As long as both remain identical,
 * the entity is considered clean.
 *
 */



//What Happens After Modification?
//Employee employee =
//        entityManager.find(Employee.class, 101L);
//
//employee.setSalary(70000);

/*
 * =============================================================================
 * Internal State After Modification
 * =============================================================================
 *
 * Snapshot
 *
 *      salary = 50000
 *
 *
 * Current Entity
 *
 *      salary = 70000
 *
 *
 * Hibernate has NOT executed UPDATE yet.
 *
 * It simply knows that the entity has changed.
 *
 */

//Notice:
//
//The database is still unchanged.
//
//Only memory has changed.

/*
 * =============================================================================
 * Dirty Checking Flow
 * =============================================================================
 *
 *          Managed Entity Loaded
 *                    │
 *                    ▼
 *        Hibernate stores Snapshot
 *                    │
 *                    ▼
 *          Application modifies entity
 *                    │
 *                    ▼
 *          Flush / Commit occurs
 *                    │
 *                    ▼
 *      Compare Snapshot vs Current State
 *                    │
 *          ┌─────────┴─────────┐
 *          │                   │
 *          ▼                   ▼
 *      No Difference      Difference Found
 *          │                   │
 *          ▼                   ▼
 *      No UPDATE         Generate UPDATE SQL
 *
 */

/*
 * =============================================================================
 * Which Entities Participate?
 * =============================================================================
 *
 * Dirty Checking works ONLY for Managed entities.
 *
 * Entity State            Dirty Checking
 * ------------------------------------------
 * Transient               No
 * Managed                 Yes
 * Detached                No
 * Removed                 Not applicable
 *
 * If Hibernate is not tracking an entity,
 * it cannot detect changes.
 *
 */

//Example
//Employee employee =
//        entityManager.find(Employee.class, 101L);
//
//employee.setDepartment("Engineering");
//
//employee.setSalary(85000);
//
//No SQL is executed here.
//
//During Flush:
//
//UPDATE employee
//SET department = ?,
//    salary = ?
//WHERE id = ?;
//
//Hibernate generates the SQL automatically.


/*
 * =============================================================================
 * Common Misconception
 * =============================================================================
 *
 * Many developers think:
 *
 *      setSalary()
 *
 * immediately executes UPDATE.
 *
 * This is incorrect.
 *
 * setSalary() only modifies the Java object.
 *
 * Dirty Checking runs later,
 * typically during Flush or Transaction Commit.
 *
 */

/*
 * =============================================================================
 * Why Detached Entities Are Ignored
 * =============================================================================
 *
 * Dirty Checking requires two things:
 *
 * 1. A Managed entity
 *
 * 2. The original Snapshot
 *
 * Detached entities are no longer inside the Persistence Context.
 *
 * Hibernate no longer has the snapshot needed for comparison.
 *
 * Therefore,
 * Detached entities do not participate in Dirty Checking.
 *
 */

/*
 * =============================================================================
 * Characteristics of Dirty Checking
 * =============================================================================
 *
 * ✓ Automatic
 *
 * ✓ Works only for Managed entities
 *
 * ✓ Uses an internal Snapshot
 *
 * ✓ Executes during Flush or Commit
 *
 * ✓ Eliminates the need for explicit update() calls
 *
 * ✓ Keeps Java objects and the database synchronized
 *
 */


/*
 * =============================================================================
 * Interview Questions
 * =============================================================================
 *
 * Q1. What is Dirty Checking?
 *
 * Answer:
 * Hibernate's mechanism for automatically detecting changes made to
 * Managed entities and generating UPDATE statements.
 *
 * ------------------------------------------------------------
 *
 * Q2. Why doesn't Hibernate require update()?
 *
 * Answer:
 * Because Dirty Checking automatically detects changes and generates
 * UPDATE statements during Flush.
 *
 * ------------------------------------------------------------
 *
 * Q3. Does Dirty Checking work for Detached entities?
 *
 * Answer:
 * No.
 *
 * Hibernate only tracks Managed entities that exist inside the
 * Persistence Context.
 *
 * ------------------------------------------------------------
 *
 * Q4. When does Dirty Checking execute?
 *
 * Answer:
 * During Flush or Transaction Commit, when Hibernate synchronizes the
 * Persistence Context with the database.
 *
 */
