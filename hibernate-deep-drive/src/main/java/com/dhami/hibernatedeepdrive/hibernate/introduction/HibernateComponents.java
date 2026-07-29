package com.dhami.hibernatedeepdrive.hibernate.introduction;

public class HibernateComponents {
}

/**
 * =============================================================================
 * 5. Core Components of Hibernate
 * =============================================================================
 *
 * During application startup, Spring Boot and Hibernate work together to create
 * several important objects.
 *
 * Every database operation performed by Hibernate depends on these objects.
 *
 * Their relationship is shown below.
 *
 * +---------------------+
 * |  Spring Boot Starts |
 * +----------+----------+
 *            |
 *            v
 * +---------------------+
 * |     DataSource      |
 * +----------+----------+
 *            |
 *            | Used By
 *            v
 * +---------------------------+
 * |   EntityManagerFactory     |
 * +-------------+-------------+
 *               |
 *      Creates EntityManager
 *               |
 *               v
 * +---------------------------+
 * |      EntityManager         |
 * +-------------+-------------+
 *               |
 *       Manages Entities
 *               |
 *               v
 * +---------------------------+
 * |   Persistence Context      |
 * +-------------+-------------+
 *               |
 *      Delegates Operations
 *               |
 *               v
 * +---------------------------+
 * |        Hibernate          |
 * +-------------+-------------+
 *               |
 *        Generates SQL
 *               |
 *               v
 * +---------------------------+
 * |           JDBC            |
 * +-------------+-------------+
 *               |
 *        Executes SQL
 *               |
 *               v
 * +---------------------------+
 * |          MySQL            |
 * +---------------------------+
 *
 * Let's understand the responsibility of each component.
 */

/*
 * -----------------------------------------------------------------------------
 * DataSource
 * -----------------------------------------------------------------------------
 *
 * DataSource represents the database connection configuration.
 *
 * It contains information such as:
 *
 * +------------------------------------------------------+
 * | Database URL                                         |
 * | Username                                             |
 * | Password                                             |
 * | Driver Class                                         |
 * | Connection Pool                                      |
 * +------------------------------------------------------+
 *
 * Hibernate never creates database connections directly.
 *
 * Instead, it asks the DataSource for a connection whenever one is needed.
 */

/*
 * -----------------------------------------------------------------------------
 * EntityManagerFactory
 * -----------------------------------------------------------------------------
 *
 * EntityManagerFactory is a heavyweight object.
 *
 * It is created only once during application startup.
 *
 * Responsibilities
 *
 * +------------------------------------------------------+
 * | Read entity metadata                                 |
 * | Build Hibernate configuration                        |
 * | Create EntityManager objects                         |
 * | Cache mapping information                            |
 * +------------------------------------------------------+
 *
 * Since creating this object is expensive,
 * Spring Boot creates only one instance and keeps it alive
 * until the application shuts down.
 */


/*
 * -----------------------------------------------------------------------------
 * EntityManager
 * -----------------------------------------------------------------------------
 *
 * EntityManager is the primary interface through which an application
 * communicates with Hibernate.
 *
 * Whenever we perform operations like:
 *
 * - persist()
 * - find()
 * - merge()
 * - remove()
 *
 * we are actually interacting with EntityManager.
 *
 * Example
 *
 *      entityManager.persist(employee);
 *
 * EntityManager does not directly execute SQL.
 *
 * Instead, it delegates work to the Persistence Context and Hibernate.
 */

/*
 * -----------------------------------------------------------------------------
 * Persistence Context
 * -----------------------------------------------------------------------------
 *
 * Persistence Context is an in-memory workspace maintained by Hibernate.
 *
 * Every entity loaded or persisted during a transaction is stored here.
 *
 * Example
 *
 * +-------------------------------------------------------------+
 * |                  Persistence Context                        |
 * +-------------------------------------------------------------+
 * | Employee(id=101)                                            |
 * | Employee(id=102)                                            |
 * | Department(id=10)                                           |
 * | Attendance(id=5001)                                         |
 * +-------------------------------------------------------------+
 *
 * Hibernate continuously monitors these objects.
 *
 * If any managed object changes,
 * Hibernate automatically detects the modification and later generates
 * the required SQL.
 *
 * This feature is called Dirty Checking.
 *
 * We will study Persistence Context in great detail later.
 */

/*
 * -----------------------------------------------------------------------------
 * Hibernate Engine
 * -----------------------------------------------------------------------------
 *
 * Hibernate receives requests from EntityManager.
 *
 * Responsibilities
 *
 * +------------------------------------------------------+
 * | Generate SQL                                         |
 * | Manage Entity Mapping                                |
 * | Handle Relationships                                 |
 * | Perform Dirty Checking                               |
 * | Maintain Cache                                       |
 * | Coordinate Transactions                              |
 * +------------------------------------------------------+
 *
 * Hibernate itself never communicates directly with MySQL.
 *
 * Instead, it delegates SQL execution to JDBC.
 */



/*
 * -----------------------------------------------------------------------------
 * JDBC Driver
 * -----------------------------------------------------------------------------
 *
 * JDBC acts as the communication layer between Hibernate and the database.
 *
 * Hibernate generates SQL.
 *
 * JDBC executes SQL.
 *
 * Database stores data.
 *
 * Their responsibilities should never be confused.
 *
 * +----------------------+------------------------------------------+
 * | Hibernate            | Creates SQL                              |
 * | JDBC                 | Executes SQL                             |
 * | Database             | Stores Data                              |
 * +----------------------+------------------------------------------+
 */


/*
 * -----------------------------------------------------------------------------
 * Putting Everything Together
 * -----------------------------------------------------------------------------
 *
 * Consider the following Spring Boot code.
 *
 Employee employee = new Employee();
employee.setName("Pankaj");
employeeRepository.save(employee);

 */

/*
 * The above code looks very simple.
 *
 * Internally the execution flow is much larger.
 *
 * +--------------------+     +----------------------+     +----------------------+
 * | Repository.save()  | --> | EntityManager        | --> | Persistence Context  |
 * +--------------------+     +----------------------+     +----------------------+
 *                                                                  |
 *                                                                  v
 *                                                       +----------------------+
 *                                                       | Hibernate Engine     |
 *                                                       +----------------------+
 *                                                                  |
 *                                                                  v
 *                                                       +----------------------+
 *                                                       | Generated SQL        |
 *                                                       +----------------------+
 *                                                                  |
 *                                                                  v
 *                                                       +----------------------+
 *                                                       | JDBC Driver          |
 *                                                       +----------------------+
 *                                                                  |
 *                                                                  v
 *                                                       +----------------------+
 *                                                       | MySQL Database       |
 *                                                       +----------------------+
 *
 * Every future Hibernate topic is simply a deeper explanation of one box from
 * the above architecture.
 *
 * For example:
 *
 * Persistence Package
 * -------------------
 * EntityManager
 * Persistence Context
 * Entity Lifecycle
 * Dirty Checking
 * Flush
 *
 * Transaction Package
 * -------------------
 * Transaction Management
 * Locking
 * Propagation
 *
 * Query Package
 * -------------
 * JPQL
 * Criteria API
 * Native SQL
 *
 * Performance Package
 * -------------------
 * Fetch Strategies
 * Caching
 * Batch Processing
 *
 * Once you understand this architecture, the remaining Hibernate topics become
 * much easier because you always know where each concept fits.
 */

