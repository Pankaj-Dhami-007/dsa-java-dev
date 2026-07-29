package com.dhami.hibernatedeepdrive.hibernate.persistence;

/*
 * =============================================================================
 *                         FlushMechanism.java
 * =============================================================================
 *
 * Goal
 * ----
 * Understand what Flush is, when it happens, and why Hibernate delays SQL
 * execution until Flush instead of sending SQL immediately.
 *
 * After completing this chapter, you should be able to answer:
 *
 * • What is Flush?
 * • Why doesn't persist() immediately execute INSERT?
 * • Why doesn't remove() immediately execute DELETE?
 * • When does Hibernate Flush automatically?
 * • What happens during Flush?
 *
 */
public class FlushMechanism {
}

/*
 * =============================================================================
 * Why Flush?
 * =============================================================================
 *
 * Consider the following code.
 *
 *      Employee employee = new Employee();
 *
 *      entityManager.persist(employee);
 *
 *      employee.setSalary(70000);
 *
 * We called persist().
 *
 * We modified the entity.
 *
 * Yet no SQL has been executed.
 *
 * Why?
 *
 * Hibernate does not immediately synchronize every change with the database.
 *
 * Instead,
 *
 * it collects all pending changes inside the Persistence Context and
 * synchronizes them together.
 *
 * This synchronization process is called Flush.
 *
 */

/*
 * =============================================================================
 * What is Flush?
 * =============================================================================
 *
 * Flush is the process of synchronizing the Persistence Context with the
 * database.
 *
 * During Flush,
 * Hibernate examines every Managed entity and generates the necessary SQL.
 *
 * Depending on what has changed,
 * Hibernate may generate:
 *
 * • INSERT
 * • UPDATE
 * • DELETE
 *
 * Flush synchronizes memory with the database.
 *
 * It does NOT end the transaction.
 *
 */

/*
 * =============================================================================
 * Flush Architecture
 * =============================================================================
 *
 *                     Application
 *                          │
 *                          ▼
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │                 Persistence Context                                     │
 * │                                                                         │
 * │ Employee#101  (Modified)                                                │
 * │ Employee#102  (New)                                                     │
 * │ Employee#103  (Removed)                                                 │
 * └─────────────────────────────────────────────────────────────────────────┘
 *                          │
 *                    Flush Occurs
 *                          │
 *                          ▼
 *
 *             INSERT   UPDATE   DELETE
 *                          │
 *                          ▼
 *                    MySQL Database
 *
 */

/*
 * =============================================================================
 * Internal Steps During Flush
 * =============================================================================
 *
 * Step 1
 *
 * Hibernate scans the Persistence Context.
 *
 * ---------------------------------------------------------
 *
 * Step 2
 *
 * Every Managed entity is inspected.
 *
 * ---------------------------------------------------------
 *
 * Step 3
 *
 * Dirty Checking compares snapshots with current values.
 *
 * ---------------------------------------------------------
 *
 * Step 4
 *
 * SQL statements are generated.
 *
 * ---------------------------------------------------------
 *
 * Step 5
 *
 * SQL statements are sent to the database.
 *
 * ---------------------------------------------------------
 *
 * Step 6
 *
 * The Persistence Context remains active.
 *
 * The transaction is still running.
 *
 */

//Example
//Employee employee =
//        entityManager.find(Employee.class, 101L);
//
//employee.setSalary(90000);
//
//entityManager.flush();
//
//Generated SQL
//
//UPDATE employee
//SET salary = ?
//WHERE id = ?;
//
//Notice that the UPDATE happens only when flush() is called, not when setSalary() is executed.

/*
 * =============================================================================
 * Flush Flow
 * =============================================================================
 *
 *          Employee (Managed)
 *                    │
 *                    ▼
 *        employee.setSalary(90000)
 *                    │
 *                    ▼
 *
 *          Persistence Context
 *                    │
 *          Dirty Checking
 *                    │
 *                    ▼
 *              Flush()
 *                    │
 *                    ▼
 *              UPDATE employee
 *
 */

/*
 * =============================================================================
 * Automatic Flush
 * =============================================================================
 *
 * Hibernate automatically performs Flush in several situations.
 *
 * Common triggers include:
 *
 * 1. Transaction Commit
 *
 *      transaction.commit();
 *
 * ---------------------------------------------------------
 *
 * 2. Explicit Flush
 *
 *      entityManager.flush();
 *
 * ---------------------------------------------------------
 *
 * 3. Before executing certain queries
 *
 * Hibernate may Flush pending changes before executing a query
 * so that the query sees the latest database state.
 *
 */



/*
 * =============================================================================
 * Flush vs Commit
 * =============================================================================
 *
 * Many developers think Flush and Commit are the same.
 *
 * They are different.
 *
 * Flush
 * -----
 *
 * • Synchronizes the Persistence Context with the database.
 *
 * • Executes SQL.
 *
 * • Transaction remains active.
 *
 * ---------------------------------------------------------
 *
 * Commit
 * ------
 *
 * • Flushes pending changes (if required).
 *
 * • Permanently commits the transaction.
 *
 * • Ends the transaction.
 *
 */

/*
 * =============================================================================
 * Flush vs Commit
 * =============================================================================
 *
 *                     Java Objects
 *                           │
 *                           ▼
 *                 Persistence Context
 *                           │
 *                   Flush()
 *                           │
 *             SQL Executed Against Database
 *                           │
 *                  Transaction Still Active
 *                           │
 *                    commit()
 *                           │
 *                           ▼
 *               Transaction Permanently Saved
 *
 */


/*
 * =============================================================================
 * Common Misconceptions
 * =============================================================================
 *
 * Misconception 1
 *
 * Flush commits the transaction.
 *
 * False.
 *
 * Flush only synchronizes changes.
 *
 * ---------------------------------------------------------
 *
 * Misconception 2
 *
 * persist() immediately executes INSERT.
 *
 * False.
 *
 * INSERT is usually delayed until Flush.
 *
 * ---------------------------------------------------------
 *
 * Misconception 3
 *
 * remove() immediately executes DELETE.
 *
 * False.
 *
 * DELETE is normally generated during Flush.
 *
 */

/*
 * =============================================================================
 * Characteristics of Flush
 * =============================================================================
 *
 * ✓ Synchronizes memory with the database
 *
 * ✓ Executes INSERT, UPDATE and DELETE statements
 *
 * ✓ Uses Dirty Checking
 *
 * ✓ Does not end the transaction
 *
 * ✓ Can happen automatically or manually
 *
 * ✓ Keeps the Persistence Context and database consistent
 *
 */

/*
 * =============================================================================
 * Complete Persistence Flow
 * =============================================================================
 *
 *          new Employee()
 *                │
 *                ▼
 *            TRANSIENT
 *                │
 *          persist()
 *                │
 *                ▼
 *             MANAGED
 *                │
 *      Stored in Persistence Context
 *                │
 *                ▼
 *      First Level Cache
 *                │
 *                ▼
 *      Application modifies entity
 *                │
 *                ▼
 *         Dirty Checking
 *                │
 *                ▼
 *             Flush
 *                │
 *                ▼
 *      INSERT / UPDATE / DELETE
 *                │
 *                ▼
 *           Database
 *
 */


/*
 * =============================================================================
 * Interview Questions
 * =============================================================================
 *
 * Q1. What is Flush?
 *
 * Answer:
 * Flush synchronizes the Persistence Context with the database by executing
 * pending INSERT, UPDATE, and DELETE statements.
 *
 * ------------------------------------------------------------
 *
 * Q2. Does Flush commit the transaction?
 *
 * Answer:
 * No.
 *
 * Flush executes SQL, but the transaction remains active.
 *
 * ------------------------------------------------------------
 *
 * Q3. Why doesn't persist() immediately execute INSERT?
 *
 * Answer:
 * Hibernate delays SQL execution until Flush so that multiple operations can
 * be synchronized efficiently.
 *
 * ------------------------------------------------------------
 *
 * Q4. Can Flush happen automatically?
 *
 * Answer:
 * Yes.
 *
 * Hibernate automatically flushes before transaction commit and, in some
 * cases, before executing queries.
 *
 */

