package com.dhami.hibernatedeepdrive.hibernate.persistence;

/*
 * =============================================================================
 *                         FirstLevelCache.java
 * =============================================================================
 *
 * Goal
 * ----
 * Understand what the First Level Cache is, why Hibernate uses it,
 * and how it improves application performance.
 *
 * After completing this chapter, you should be able to answer:
 *
 * • What is the First Level Cache?
 * • Where is it stored?
 * • Why doesn't Hibernate execute SELECT every time?
 * • How long does the cache live?
 * • Can it become stale?
 *
 */

/*
 * =============================================================================
 * Why Do We Need a Cache?
 * =============================================================================
 *
 * Accessing the database is one of the slowest operations in an application.
 *
 * Every SELECT requires:
 *
 * • Sending a request over the network
 * • Database parsing the SQL
 * • Reading data from storage or memory
 * • Returning the result
 * • Converting rows into Java objects
 *
 * If Hibernate executed a SELECT every time an entity was requested,
 * application performance would degrade significantly.
 *
 * To avoid unnecessary database calls,
 * Hibernate keeps Managed entities in memory.
 *
 * This in-memory storage is called the First Level Cache.
 *
 */
public class FirstLevelCache {
}


/*
 * =============================================================================
 * What is the First Level Cache?
 * =============================================================================
 *
 * The First Level Cache is an in-memory cache associated with a single
 * Persistence Context.
 *
 * Every Managed entity automatically becomes part of this cache.
 *
 * Hibernate always checks this cache before querying the database.
 *
 * Because the cache belongs to the Persistence Context,
 * it is automatically enabled.
 *
 * No configuration is required.
 *
 */



/*
 * =============================================================================
 * First Level Cache Architecture
 * =============================================================================
 *
 *                      EntityManager
 *                            │
 *                            ▼
 *
 * ┌────────────────────────────────────────────────────────────────────────┐
 * │                 Persistence Context (First Level Cache)                │
 * │                                                                        │
 * │   Employee#101      Department#10      Leave#205                      │
 * │                                                                        │
 * │   Managed Entity    Managed Entity     Managed Entity                 │
 * └────────────────────────────────────────────────────────────────────────┘
 *                            │
 *                            ▼
 *                        MySQL Database
 *
 * Every Managed entity is automatically stored in this cache.
 *
 */

/**
 * =============================================================================
 * Persistence Context vs First Level Cache
 * =============================================================================
 *
 * Beginners often think these are different things.
 *
 * They are not.
 *
 * The First Level Cache is the caching behavior of the Persistence Context.
 *
 * Think of it this way:
 *
 *      Persistence Context
 *             │
 *             ├── Tracks entities
 *             ├── Performs Dirty Checking
 *             ├── Manages lifecycle
 *             └── Acts as the First Level Cache
 *
 */

/*

Example
Employee employee1 =
        entityManager.find(Employee.class, 101L);

Employee employee2 =
        entityManager.find(Employee.class, 101L);

Most beginners expect:

SELECT * FROM employee WHERE id = 101;

SELECT * FROM employee WHERE id = 101;

That is not what happens.
 */


/*
 * =============================================================================
 * Internal Flow
 * =============================================================================
 *
 * First find()
 *
 *      find(Employee,101)
 *              │
 *              ▼
 *      Check First Level Cache
 *              │
 *              ▼
 *         Not Found
 *              │
 *              ▼
 *        Execute SELECT
 *              │
 *              ▼
 *      Create Employee Object
 *              │
 *              ▼
 *      Store in First Level Cache
 *
 *
 * Second find()
 *
 *      find(Employee,101)
 *              │
 *              ▼
 *      Check First Level Cache
 *              │
 *              ▼
 *            Found
 *              │
 *              ▼
 *      Return Same Java Object
 *
 * No SQL is executed the second time.
 *
 */


/*
 * =============================================================================
 * Memory Representation
 * =============================================================================
 *
 * Persistence Context
 *
 * ┌─────────────────────────────────────────────────────────────┐
 * │ Employee#101                                                │
 * │-------------------------------------------------------------│
 * │ id = 101                                                    │
 * │ name = "Pankaj"                                             │
 * │ salary = 50000                                              │
 * └─────────────────────────────────────────────────────────────┘
 *
 *
 * First find()
 *      Database → Cache
 *
 * Second find()
 *      Cache → Application
 *
 */



//Identity Guarantee
//
//This is one of Hibernate's most important guarantees.
//
//Employee employee1 =
//        entityManager.find(Employee.class, 101L);
//
//Employee employee2 =
//        entityManager.find(Employee.class, 101L);
//
//System.out.println(employee1 == employee2);
//
//Output:
//
//true


/*
 * =============================================================================
 * Object Identity Guarantee
 * =============================================================================
 *
 * Within a single Persistence Context,
 * Hibernate guarantees that there is only one Managed object for a given
 * entity type and primary key.
 *
 * Therefore,
 *
 *      employee1 == employee2
 *
 * evaluates to true.
 *
 * Hibernate returns the same Java object instead of creating a new one.
 *
 */



/*
 * =============================================================================
 * Lifetime of the First Level Cache
 * =============================================================================
 *
 * The First Level Cache exists only as long as its Persistence Context exists.
 *
 * When the Persistence Context is closed:
 *
 * • All Managed entities are removed.
 * • The cache is cleared.
 * • Future queries access the database again.
 *
 * Therefore,
 * the cache is short-lived and scoped to a single Persistence Context.
 *
 */


/*
 * =============================================================================
 * Common Misconception
 * =============================================================================
 *
 * Many developers think:
 *
 *      Hibernate Cache
 *
 * means data is shared across the entire application.
 *
 * This is incorrect for the First Level Cache.
 *
 * The First Level Cache is private to a single Persistence Context.
 *
 * Two different EntityManagers do not share the same First Level Cache.
 *
 */


/*
 * =============================================================================
 * Characteristics of First Level Cache
 * =============================================================================
 *
 * ✓ Enabled by default
 *
 * ✓ Cannot be disabled
 *
 * ✓ Exists per Persistence Context
 *
 * ✓ Stores Managed entities only
 *
 * ✓ Improves performance by reducing database queries
 *
 * ✓ Guarantees object identity within the Persistence Context
 *
 */

/*
 * =============================================================================
 * Interview Questions
 * =============================================================================
 *
 * Q1. Is the First Level Cache enabled by default?
 *
 * Answer:
 * Yes.
 *
 * ------------------------------------------------------------
 *
 * Q2. Where is the First Level Cache stored?
 *
 * Answer:
 * Inside the Persistence Context associated with an EntityManager.
 *
 * ------------------------------------------------------------
 *
 * Q3. Why doesn't Hibernate execute SELECT every time find() is called?
 *
 * Answer:
 * Hibernate first checks the First Level Cache. If the entity is already
 * present, it returns the cached Managed entity instead of querying the
 * database again.
 *
 * ------------------------------------------------------------
 *
 * Q4. Why does employee1 == employee2 evaluate to true?
 *
 * Answer:
 * Hibernate maintains a single Managed instance for a given entity type and
 * primary key within the same Persistence Context.
 *
 */