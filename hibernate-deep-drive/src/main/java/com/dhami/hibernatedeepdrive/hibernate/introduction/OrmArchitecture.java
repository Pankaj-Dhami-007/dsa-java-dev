package com.dhami.hibernatedeepdrive.hibernate.introduction;
/*
 *
 * Objective
 * ---------
 * In the previous chapter, we learned why Hibernate was created.
 *
 * We know that Hibernate converts Java Objects into database records and
 * database records back into Java Objects.
 *
 * But an important question still remains...
 *
 *      "How does Hibernate know which object belongs to which table?"
 *
 *      "How does it generate SQL automatically?"
 *
 *      "How does it know which field belongs to which column?"
 *
 * All these questions are answered by ORM.
 *
 * ORM is the core idea on which Hibernate is built.
 *
 * After completing this chapter, you will understand the complete architecture
 * behind Object Relational Mapping before learning annotations or
 * Persistence Context.
 */
public class OrmArchitecture {
}


/*
 * =============================================================================
 * 1. What is ORM?
 * =============================================================================
 *
 * ORM stands for Object Relational Mapping.
 *
 * Let's understand every word separately.
 *
 * +----------------------+------------------------------------------------------+
 * | Word                 | Meaning                                              |
 * +----------------------+------------------------------------------------------+
 * | Object               | Java Object                                          |
 * | Relational           | Relational Database (MySQL, PostgreSQL, Oracle...)   |
 * | Mapping              | Establishing a relationship between the two          |
 * +----------------------+------------------------------------------------------+
 *
 * Therefore,
 *
 * ORM is a technique that maps Java Objects to database tables and database
 * rows back into Java Objects.
 *
 * Instead of writing SQL for every CRUD operation, developers work with
 * Java objects while the ORM framework handles the conversion.
 *
 * -----------------------------------------------------------------------------
 * Without ORM
 * -----------------------------------------------------------------------------
 *
 *                  Java World                          Database World
 *
 * +---------------------------+              +-------------------------------+
 * | Employee Object           |              | employee Table                |
 * |---------------------------|              |-------------------------------|
 * | id                        |              | id                            |
 * | name                      |              | name                          |
 * | email                     |              | email                         |
 * | salary                    |              | salary                        |
 * +---------------------------+              +-------------------------------+
 *
 * The developer manually converts between both worlds.
 *
 * -----------------------------------------------------------------------------
 * With ORM
 * -----------------------------------------------------------------------------
 *
 *                  Java World                    ORM                  Database
 *
 * +---------------------------+       +------------------+      +----------------------+
 * | Employee Object           | ----> |    Hibernate     | ---> | employee Table       |
 * +---------------------------+       +------------------+      +----------------------+
 *            ^                                                      |
 *            |______________________________________________________|
 *
 * Hibernate performs the conversion in both directions.
 *
 * This is why developers work with Java Objects instead of SQL.
 */

/*
 * =============================================================================
 * 2. Why Do We Need ORM?
 * =============================================================================
 *
 * Imagine our HRMS project contains the following entities.
 *
 * +------------------+------------------+------------------+------------------+
 * | Employee         | Attendance       | Leave            | Department       |
 * +------------------+------------------+------------------+------------------+
 * | Profile          | Salary           | Holiday          | Shift            |
 * +------------------+------------------+------------------+------------------+
 *
 * Every entity requires CRUD operations.
 *
 * Without ORM, developers must repeatedly:
 *
 * +---------------------------------------------------------------+
 * | Write INSERT statements                                       |
 * | Write UPDATE statements                                       |
 * | Write DELETE statements                                       |
 * | Write SELECT statements                                       |
 * | Map ResultSet to Objects                                      |
 * | Handle Relationships                                          |
 * +---------------------------------------------------------------+
 *
 * The majority of this work is repetitive.
 *
 * ORM automates this repetitive persistence layer so developers can
 * concentrate on implementing business logic.
 *
 * Remember...
 *
 * ORM does NOT eliminate SQL.
 *
 * ORM generates SQL automatically.
 */


/*
 * =============================================================================
 * 3. Mapping Explained
 * =============================================================================
 *
 * Mapping simply means establishing a relationship between a Java class
 * and a database table.
 *
 * Example
 *
 * +------------------------------+       +-------------------------------+
 * | EmployeeEntity               |       | employee                      |
 * +------------------------------+       +-------------------------------+
 * | id                           | ----> | id                            |
 * | name                         | ----> | name                          |
 * | email                        | ----> | email                         |
 * | salary                       | ----> | salary                        |
 * +------------------------------+       +-------------------------------+
 *
 * Hibernate stores this information internally as metadata.
 *
 * Later, whenever an EmployeeEntity object is saved,
 * Hibernate already knows:
 *
 * - Which table should be used.
 * - Which columns should be inserted.
 * - Which column is the primary key.
 * - Which strategy generates IDs.
 *
 * This metadata becomes the foundation for every database operation.
 *
 * We will study metadata generation in detail in later chapters.
 */


/*
 * =============================================================================
 * 4. Hibernate Architecture
 * =============================================================================
 *
 * Hibernate is not a single class or a single library.
 *
 * It is a collection of components working together to convert Java Objects
 * into database records.
 *
 * Understanding this architecture is extremely important because every advanced
 * topic such as Persistence Context, Dirty Checking, Flush, Lazy Loading and
 * Transactions depends on these components.
 *
 * -----------------------------------------------------------------------------
 * Complete Architecture
 * -----------------------------------------------------------------------------
 *
 *                   Spring Boot Application
 *
 * +------------------+    +------------------+    +----------------------+
 * |   Controller     | -> |     Service      | -> |      Repository      |
 * +------------------+    +------------------+    +----------------------+
 *                                                          |
 *                                                          v
 *                                            +---------------------------+
 *                                            |       EntityManager       |
 *                                            +---------------------------+
 *                                                          |
 *                                                          v
 *                                            +---------------------------+
 *                                            |    Persistence Context    |
 *                                            +---------------------------+
 *                                                          |
 *                                                          v
 *                                            +---------------------------+
 *                                            |        Hibernate          |
 *                                            +---------------------------+
 *                                                          |
 *                                                          v
 *                                            +---------------------------+
 *                                            |           JDBC            |
 *                                            +---------------------------+
 *                                                          |
 *                                                          v
 *                                            +---------------------------+
 *                                            |          MySQL            |
 *                                            +---------------------------+
 *
 * Every component has a specific responsibility.
 *
 * Hibernate works correctly because every layer performs exactly one job.
 */




/*
 * -----------------------------------------------------------------------------
 * Component Responsibilities
 * -----------------------------------------------------------------------------
 *
 * +----------------------+------------------------------------------------------+
 * | Component            | Responsibility                                       |
 * +----------------------+------------------------------------------------------+
 * | Controller           | Receives HTTP Request                                |
 * | Service              | Executes Business Logic                              |
 * | Repository           | Performs Persistence Operations                      |
 * | EntityManager        | Manages Entity Lifecycle                             |
 * | Persistence Context  | Tracks Managed Entities                              |
 * | Hibernate            | Generates SQL                                        |
 * | JDBC                 | Executes SQL                                         |
 * | Database             | Stores Data                                          |
 * +----------------------+------------------------------------------------------+
 *
 * Notice something important.
 *
 * Hibernate never communicates directly with the Controller.
 *
 * Likewise,
 *
 * The Controller never communicates directly with Hibernate.
 *
 * Every request flows through well-defined layers.
 */


/*
 * From our perspective,
 * only one line actually stores the employee.
 *
 *      repository.save(employee);
 *
 * But internally, many components participate in this operation.
 *
 * +--------------------+     +---------------------+     +--------------------+
 * | repository.save()  | --> | EntityManager       | --> | Persistence Context|
 * +--------------------+     +---------------------+     +--------------------+
 *                                                               |
 *                                                               v
 *                                                     +--------------------+
 *                                                     | Hibernate Engine   |
 *                                                     +--------------------+
 *                                                               |
 *                                                               v
 *                                                     +--------------------+
 *                                                     | JDBC Driver        |
 *                                                     +--------------------+
 *                                                               |
 *                                                               v
 *                                                     +--------------------+
 *                                                     | MySQL              |
 *                                                     +--------------------+
 */



/*
 * -----------------------------------------------------------------------------
 * Why So Many Components?
 * -----------------------------------------------------------------------------
 *
 * Imagine Hibernate directly executed SQL every time save() was called.
 *
 * It would become responsible for:
 *
 * - Managing object state
 * - Tracking changes
 * - Creating SQL
 * - Executing SQL
 * - Managing transactions
 * - Caching
 * - Object mapping
 *
 * One class handling all these responsibilities would become extremely
 * difficult to maintain.
 *
 * Instead, Hibernate follows the Single Responsibility Principle (SRP).
 *
 * Every component performs exactly one responsibility.
 *
 * +---------------------------+-----------------------------------------------+
 * | Component                 | Responsibility                                |
 * +---------------------------+-----------------------------------------------+
 * | EntityManager             | Manage entities                               |
 * | Persistence Context       | Maintain managed objects                      |
 * | Hibernate Core            | Convert Objects into SQL                      |
 * | JDBC                      | Execute SQL                                   |
 * | Database                  | Store records                                 |
 * +---------------------------+-----------------------------------------------+
 *
 * This separation makes Hibernate highly extensible and maintainable.
 */


/*
 * -----------------------------------------------------------------------------
 * Key Observation
 * -----------------------------------------------------------------------------
 *
 * When learning Hibernate, beginners usually focus on annotations.
 *
 * Experienced developers focus on architecture.
 *
 * Why?
 *
 * Because annotations are only metadata.
 *
 * The real work is performed by the architecture shown above.
 *
 * Once you understand how these components communicate, concepts such as
 * Persistence Context, Dirty Checking, Flush, Entity Lifecycle and Caching
 * become much easier to understand.
 *
 * In the next section, we will study every internal component individually,
 * starting with EntityManagerFactory and EntityManager.
 */





