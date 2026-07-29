package com.dhami.hibernatedeepdrive.hibernate.persistence;


/*
 * =============================================================================
 *                         EntityLifecycle.java
 * =============================================================================
 *
 * Goal
 * ----
 * Understand how an entity moves through different states during its lifetime.
 *
 * This chapter answers:
 *
 * • How does a Transient entity become Managed?
 * • How does a Managed entity become Detached?
 * • How does a Detached entity become Managed again?
 * • How does an entity become Removed?
 *
 * This chapter connects all the entity states together.
 *
 */

/*
 * =============================================================================
 * Why Study Entity Lifecycle?
 * =============================================================================
 *
 * In the previous chapter, we learned about four entity states.
 *
 * But Hibernate applications are dynamic.
 *
 * An entity never remains in the same state forever.
 *
 * During its lifetime, it continuously moves between different states.
 *
 * Understanding these transitions is essential because Hibernate behaves
 * differently at each stage.
 *
 */
public class EntityLifecycle {
}

/*
 * =============================================================================
 * Complete Entity Lifecycle
 * =============================================================================
 *
 *                          new Employee()
 *                                 │
 *                                 ▼
 *
 *        ┌──────────────────────────────────────────────┐
 *        │                TRANSIENT                      │
 *        └──────────────────────────────────────────────┘
 *                      │
 *                      │ persist()
 *                      ▼
 *
 *        ┌──────────────────────────────────────────────┐
 *        │                 MANAGED                       │
 *        └──────────────────────────────────────────────┘
 *            │              │                │
 *            │              │                │
 * detach()   │              │ remove()       │ close()
 *            │              │                │
 *            ▼              ▼                ▼
 *
 *     ┌──────────────┐   ┌──────────────┐  ┌──────────────┐
 *     │  DETACHED    │   │   REMOVED    │  │  DETACHED    │
 *     └──────────────┘   └──────────────┘  └──────────────┘
 *            ▲                  │
 *            │                  │ Flush / Commit
 *            │ merge()          ▼
 *            └──────────────► Database DELETE
 *
 */

/*
 * =============================================================================
 * Transition 1
 * TRANSIENT → MANAGED
 * =============================================================================
 *
 * Operation:
 *
 *      entityManager.persist(employee);
 *
 * What happens internally?
 *
 * Step 1
 *      Employee object already exists in JVM memory.
 *
 * Step 2
 *      Hibernate receives persist().
 *
 * Step 3
 *      Employee is placed inside Persistence Context.
 *
 * Step 4
 *      State changes to MANAGED.
 *
 * Step 5
 *      Hibernate starts tracking every modification.
 *
 */
/*
 * =============================================================================
 * TRANSIENT → MANAGED
 * =============================================================================
 *
 *         new Employee()
 *               │
 *               ▼
 *
 *      Employee Object
 *               │
 *               ▼
 *      entityManager.persist()
 *               │
 *               ▼
 *
 * ┌──────────────────────────────────────┐
 * │      Persistence Context             │
 * │                                      │
 * │   Employee (Managed)                 │
 * │                                      │
 * └──────────────────────────────────────┘
 *
 */


/*
 * =============================================================================
 * Transition 2
 * MANAGED → DETACHED
 * =============================================================================
 *
 * Operations:
 *
 *      entityManager.detach(entity);
 *
 *      entityManager.clear();
 *
 *      entityManager.close();
 *
 * What happens?
 *
 * Hibernate removes the entity from the Persistence Context.
 *
 * The Java object still exists.
 *
 * However,
 *
 * Hibernate immediately stops monitoring it.
 *
 */
/*
 * =============================================================================
 * MANAGED → DETACHED
 * =============================================================================
 *
 * ┌──────────────────────────────────────┐
 * │ Persistence Context                  │
 * │                                      │
 * │ Employee (Managed)                   │
 * └──────────────────────────────────────┘
 *               │
 *        detach()/close()
 *               │
 *               ▼
 *
 *      Employee Object
 *
 *      State : DETACHED
 *
 * Hibernate no longer tracks it.
 *
 */


/*
 * =============================================================================
 * Transition 3
 * DETACHED → MANAGED
 * =============================================================================
 *
 * Operation:
 *
 *      entityManager.merge(employee);
 *
 * Important:
 *
 * merge() does NOT simply continue using the detached object.
 *
 * Instead,
 *
 * Hibernate creates or retrieves a Managed entity,
 * copies the state from the Detached entity,
 * and returns the Managed instance.
 *
 * Therefore,
 *
 * always use the object returned by merge().
 *
 */
/*
 * =============================================================================
 * DETACHED → MANAGED
 * =============================================================================
 *
 * Detached Employee
 *        │
 *        ▼
 * entityManager.merge()
 *        │
 *        ▼
 *
 * ┌──────────────────────────────────────┐
 * │ Persistence Context                  │
 * │                                      │
 * │ Employee (Managed)                   │
 * └──────────────────────────────────────┘
 *
 * Returned Object
 *        │
 *        ▼
 * Managed Employee
 *
 */


/*
 * =============================================================================
 * Common Mistake
 * =============================================================================
 *
 * Incorrect:
 *
 *      entityManager.merge(employee);
 *
 *      employee.setSalary(90000);
 *
 * The variable 'employee' still refers to the Detached object.
 *
 * Correct:
 *
 *      Employee managed =
 *              entityManager.merge(employee);
 *
 *      managed.setSalary(90000);
 *
 * Always continue using the object returned by merge().
 *
 */



/*
 * =============================================================================
 * Transition 4
 * MANAGED → REMOVED
 * =============================================================================
 *
 * Operation:
 *
 *      entityManager.remove(employee);
 *
 * Hibernate does NOT immediately execute DELETE.
 *
 * Instead,
 *
 * Step 1
 *      Mark entity as REMOVED.
 *
 * Step 2
 *      Keep it inside Persistence Context.
 *
 * Step 3
 *      Execute DELETE during Flush or Commit.
 *
 */

/*
 * =============================================================================
 * Lifecycle Summary
 * =============================================================================
 *
 * ┌─────────────┬──────────────────────────────┬─────────────────────┐
 * │ From State  │ Operation                    │ To State            │
 * ├─────────────┼──────────────────────────────┼─────────────────────┤
 * │ Transient   │ persist()                    │ Managed             │
 * │ Managed     │ detach(), close(), clear()   │ Detached            │
 * │ Detached    │ merge()                      │ Managed             │
 * │ Managed     │ remove()                     │ Removed             │
 * │ Removed     │ Flush / Commit               │ Database Row Deleted│
 * └─────────────┴──────────────────────────────┴─────────────────────┘
 *
 */



/*
 * =============================================================================
 * Interview Questions
 * =============================================================================
 *
 * Q1. Which operation converts a Transient entity into a Managed entity?
 *
 * Answer:
 *      persist()
 *
 * ------------------------------------------------------------
 *
 * Q2. Which operation converts a Detached entity into a Managed entity?
 *
 * Answer:
 *      merge()
 *
 * ------------------------------------------------------------
 *
 * Q3. Does merge() reattach the same object?
 *
 * Answer:
 *
 * No.
 *
 * merge() returns a Managed entity.
 *
 * The original Detached object remains Detached.
 *
 */
