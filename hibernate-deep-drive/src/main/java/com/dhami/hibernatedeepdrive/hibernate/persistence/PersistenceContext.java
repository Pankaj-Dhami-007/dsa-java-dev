package com.dhami.hibernatedeepdrive.hibernate.persistence;


/*
 * =============================================================================
 *                    PersistenceContext.java
 * =============================================================================
 *
 * Goal
 * ----
 * Understand why Hibernate introduced the Persistence Context,
 * what problem it solves, and why it is the heart of Hibernate.
 *
 * After this chapter, you should be able to answer:
 *
 * • What happens after entityManager.persist()?
 * • Where does Hibernate keep entities?
 * • Why doesn't Hibernate hit the database immediately?
 * • Why doesn't Hibernate create duplicate Java objects?
 *
 * Everything else in Hibernate depends on understanding this concept.
 *
 */
public class PersistenceContext {
}

/*
 * =============================================================================
 * The Problem
 * =============================================================================
 *
 * Imagine the following code.
 *
 *      Employee employee = new Employee();
 *      employee.setName("Pankaj");
 *
 *      entityManager.persist(employee);
 *
 * A beginner usually thinks:
 *
 *      "Hibernate immediately inserts the record into the database."
 *
 * But that is NOT what happens.
 *
 * This raises several important questions.
 *
 * • Where is the employee object now?
 * • Has SQL already been executed?
 * • Is the object inside Java memory?
 * • Is Hibernate tracking this object?
 *
 * To answer these questions,
 * Hibernate introduced the Persistence Context.
 *
 */

/*
 * =============================================================================
 * Life Without Persistence Context
 * =============================================================================
 *
 * Suppose Hibernate did not maintain any internal memory.
 *
 * Every operation would directly communicate with the database.
 *
 *
 *                 Employee Object
 *                        │
 *                        ▼
 *                   JDBC Driver
 *                        │
 *                        ▼
 *                     Database
 *
 *
 * Problems:
 *
 * • Too many database calls
 * • Duplicate objects
 * • No automatic update detection
 * • Poor performance
 * • Difficult transaction management
 *
 * Hibernate needed a better approach.
 *
 */

/*
 * =============================================================================
 * Hibernate's Solution
 * =============================================================================
 *
 * Instead of sending every entity directly to the database,
 * Hibernate first keeps entities inside an internal workspace.
 *
 * That internal workspace is called:
 *
 *                  Persistence Context
 *
 * Think of it as Hibernate's working memory.
 *
 * Every entity loaded, saved or managed by Hibernate
 * first enters this workspace.
 *
 */

/*
 * =============================================================================
 * High-Level Architecture
 * =============================================================================
 *
 *                   Application Code
 *                          │
 *                          ▼
 *                 EntityManager (JPA)
 *                          │
 *                          ▼
 *        ┌──────────────────────────────────────┐
 *        │         Persistence Context          │
 *        │                                      │
 *        │   • Managed Employee                 │
 *        │   • Managed Department               │
 *        │   • Managed Leave                    │
 *        │                                      │
 *        └──────────────────────────────────────┘
 *                          │
 *                SQL generated later
 *                          │
 *                          ▼
 *                     JDBC Driver
 *                          │
 *                          ▼
 *                     MySQL Database
 *
 */

/*
 * =============================================================================
 * What Is a Persistence Context?
 * =============================================================================
 *
 * A Persistence Context is an in-memory container managed by Hibernate.
 *
 * It stores and manages entity objects during the lifetime of an
 * EntityManager.
 *
 * Every entity inside the Persistence Context is called a
 * Managed Entity.
 *
 * Hibernate continuously monitors these managed entities
 * for changes.
 *
 */

/*
 * =============================================================================
 * How Hibernate Thinks
 * =============================================================================
 *
 * Suppose we execute:
 *
 *      entityManager.persist(employee);
 *
 * Hibernate does NOT think:
 *
 *      "Insert into database immediately."
 *
 * Instead, Hibernate thinks:
 *
 *      Step 1:
 *          Store employee inside Persistence Context.
 *
 *      Step 2:
 *          Mark employee as MANAGED.
 *
 *      Step 3:
 *          Track every future modification.
 *
 *      Step 4:
 *          Synchronize with the database later.
 *
 * This delayed synchronization is one of Hibernate's biggest
 * performance optimizations.
 *
 */

/*
 * =============================================================================
 * Internal View of Persistence Context
 * =============================================================================
 *
 *                    EntityManager
 *                          │
 *                          ▼
 *        ┌───────────────────────────────────────────────┐
 *        │             Persistence Context               │
 *        │                                               │
 *        │  Entity Type      Primary Key     Java Object │
 *        │  -----------      -----------     ----------- │
 *        │  Employee             101         0x8AF231    │
 *        │  Department            10         0x91BC44    │
 *        │  Leave                 25         0xAB8821    │
 *        │                                               │
 *        └───────────────────────────────────────────────┘
 *                          │
 *                          ▼
 *                     Database
 *
 * Hibernate always knows which Java object represents
 * which database row.
 *
 */

/*
 * =============================================================================
 * Characteristics
 * =============================================================================
 *
 * ✓ Exists only while EntityManager is alive
 *
 * ✓ Lives in application memory
 *
 * ✓ Stores managed entities
 *
 * ✓ Prevents duplicate entity objects
 *
 * ✓ Enables Dirty Checking
 *
 * ✓ Enables First-Level Cache
 *
 * ✓ Coordinates Flush operations
 *
 */

/*
 * =============================================================================
 * How Does an Entity Enter the Persistence Context?
 * =============================================================================
 *
 * We now know that Hibernate keeps entities inside the Persistence Context.
 *
 * The next question is:
 *
 *      "How does an entity get inside it?"
 *
 * The answer is simple.
 *
 * Hibernate places an entity into the Persistence Context whenever it
 * becomes responsible for managing that entity.
 *
 * The most common ways are:
 *
 * 1. entityManager.persist()
 * 2. entityManager.find()
 * 3. Repository methods (save(), findById(), findAll(), ...)
 *
 * Once an entity enters the Persistence Context,
 * Hibernate begins tracking it automatically.
 *
 */

/*
 * =============================================================================
 * Ways an Entity Becomes Managed
 * =============================================================================
 *
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Operation                         Result                                │
 * ├──────────────────────────────────────────────────────────────────────────┤
 * │ entityManager.persist(entity)    Entity becomes Managed                 │
 * │ entityManager.find(...)          Loaded entity becomes Managed          │
 * │ repository.save(entity)          Delegates to EntityManager             │
 * │ repository.findById(id)          Delegates to EntityManager             │
 * │ JPQL Query                       Returned entities become Managed       │
 * └──────────────────────────────────────────────────────────────────────────┘
 *
 * Regardless of which API you use,
 * Hibernate eventually stores the entity in the same Persistence Context.
 *
 */

/*
 * =============================================================================
 * Example : persist()
 * =============================================================================
 *
 * Application Code
 *
 *      Employee employee = new Employee();
 *
 *      entityManager.persist(employee);
 *
 *
 * Hibernate's Internal Flow
 *
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ New Employee Object                                                      │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ entityManager.persist(employee)                                          │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Employee added to Persistence Context                                    │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ State becomes MANAGED                                                    │
 * └──────────────────────────────────────────────────────────────────────────┘
 *
 * Notice:
 *
 * Hibernate has NOT executed SQL yet.
 *
 */

/*
 * =============================================================================
 * Internal Memory After persist()
 * =============================================================================
 *
 * Java Heap
 *
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Persistence Context                                                       │
 * │                                                                            │
 * │ +-----------------------------------------------------------------------+  │
 * │ | Employee                                                              |  │
 * │ |-----------------------------------------------------------------------|  │
 * │ | id      = null                                                        |  │
 * │ | name    = "Pankaj"                                                    |  │
 * │ | salary  = 50000                                                       |  │
 * │ | State   = MANAGED                                                     |  │
 * │ +-----------------------------------------------------------------------+  │
 * │                                                                            │
 * └────────────────────────────────────────────────────────────────────────────┘
 *
 * Database
 *
 *      (No INSERT executed yet)
 *
 */

/*
 * =============================================================================
 * Example : find()
 * =============================================================================
 *
 *      Employee employee =
 *              entityManager.find(Employee.class, 101L);
 *
 *
 * Hibernate executes the following steps:
 *
 *      Step 1
 *          Check Persistence Context.
 *
 *      Step 2
 *          If entity already exists,
 *          return the existing Java object.
 *
 *      Step 3
 *          Otherwise execute SQL.
 *
 *      Step 4
 *          Create Employee object.
 *
 *      Step 5
 *          Store it inside Persistence Context.
 *
 *      Step 6
 *          Return the managed entity.
 *
 */

/*
 * =============================================================================
 * Loading an Entity
 * =============================================================================
 *
 *                     entityManager.find()
 *                              │
 *                              ▼
 *               ┌───────────────────────────────┐
 *               │ Persistence Context           │
 *               └──────────────┬────────────────┘
 *                              │
 *                 Already Exists?
 *                    │       │
 *                 Yes│       │No
 *                    │       ▼
 *                    │   Execute SQL
 *                    │       │
 *                    │       ▼
 *                    │  Create Java Object
 *                    │       │
 *                    └──────►│
 *                            ▼
 *               Store Inside Persistence Context
 *                            │
 *                            ▼
 *                    Return Entity
 *
 */

/*
 * =============================================================================
 * Important Observation
 * =============================================================================
 *
 * Hibernate never works directly with the database.
 *
 * Every operation first passes through the Persistence Context.
 *
 * Persistence Context acts like the central coordinator.
 *
 *                     EntityManager
 *                           │
 *                           ▼
 *               Persistence Context
 *                    │         │
 *                    │         │
 *                    ▼         ▼
 *              Java Objects   SQL Generation
 *                    │
 *                    ▼
 *                 Database
 *
 */

/*
 * =============================================================================
 * When Does an Entity Leave the Persistence Context?
 * =============================================================================
 *
 * An entity remains managed only while the Persistence Context exists.
 *
 * It leaves the Persistence Context when:
 *
 * • EntityManager is closed
 * • EntityManager is cleared
 * • Entity is detached
 * • Transaction ends (typical Spring application)
 *
 * Once an entity leaves,
 * Hibernate no longer tracks its changes.
 *
 * We will study these transitions in detail in the next chapter:
 *
 *      EntityStateManagement.java
 *
 */



