package com.dhami.hibernatedeepdrive.hibernate.introduction;

/*
 * =============================================================================
 * Topic      : Why Hibernate
 * Package    : com.dhami.hibernatedeepdrive.hibernate.introduction
 * =============================================================================
 *
 * Objective
 * ---------
 * Before learning Hibernate, we must first understand why it was created.
 * Hibernate is not just another library that simplifies database operations;
 * it solves a fundamental problem that exists between Object-Oriented Programming
 * and Relational Databases.
 *
 * Most developers start learning Hibernate by memorizing annotations such as
 * @Entity, @Table or @Id. Although this helps in writing code, it does not
 * explain why Hibernate exists or what happens internally when an entity is
 * saved.
 *
 * In this chapter we will answer the following questions:
 *
 * 1. Why isn't JDBC enough?
 * 2. Why wasn't Spring JDBC the final solution?
 * 3. What problems does Hibernate solve?
 * 4. Where does Hibernate fit inside Spring Boot?
 * 5. Does Hibernate replace JDBC?
 * 6. What happens internally when save() is called?
 *
 * After completing this chapter, the entire Hibernate ecosystem will make much
 * more sense because every future topic will build upon the concepts introduced
 * here.
 *
 * =============================================================================
 * Quick Revision
 * =============================================================================
 *
 * Before Hibernate, Java applications communicated with databases using JDBC.
 *
 * JDBC is a low-level API provided by Java that allows applications to execute
 * SQL statements against relational databases.
 *
 * Typical JDBC Flow
 *
 * +-------------+     +------------------+     +--------------+
 * | Java Object | --> |  SQL Statement   | --> |   Database   |
 * +-------------+     +------------------+     +--------------+
 *
 * Every database operation requires the developer to:
 *
 * - Open a database connection.
 * - Create a PreparedStatement.
 * - Write SQL manually.
 * - Execute the SQL.
 * - Convert ResultSet into Java objects.
 * - Close database resources.
 *
 * Example
 *
 * Employee employee = new Employee("Rahul", "rahul@gmail.com");
 *
 * INSERT INTO employee(name, email)
 * VALUES (?, ?);
 *
 * Although JDBC gives complete control over SQL execution, large enterprise
 * applications quickly become difficult to maintain because the same type of
 * database code is repeated throughout the project.
 *
 * Spring introduced JdbcTemplate to reduce much of this boilerplate.
 *
 * JdbcTemplate automatically manages:
 *
 * - Connection handling
 * - Statement management
 * - Exception translation
 * - Resource cleanup
 *
 * However, one important thing still remains...
 *
 * We continue writing SQL manually.
 *
 * Whether the project contains 5 tables or 500 tables, developers are still
 * responsible for writing INSERT, UPDATE, DELETE and SELECT queries.
 *
 * This is where Hibernate enters the picture.
 */
public class WhyHibernate {

}


/*
 * =============================================================================
 * 1. Why Hibernate Was Created
 * =============================================================================
 *
 * Before understanding Hibernate, let's first understand the actual problem.
 *
 * Java applications are developed using Object-Oriented Programming (OOP),
 * whereas databases store data in the form of tables, rows and columns.
 *
 * Unfortunately, these two worlds are completely different.
 *
 * Java thinks in terms of:
 *
 * +------------------+        +------------------+
 * |     Objects      |        |     Classes      |
 * +------------------+        +------------------+
 * | Employee         |        | Employee.java    |
 * | Department       |        | Department.java  |
 * | Attendance       |        | Attendance.java  |
 * +------------------+        +------------------+
 *
 * A relational database thinks in terms of:
 *
 * +------------------+        +------------------+
 * |      Tables      |        |      Rows        |
 * +------------------+        +------------------+
 * | employee         |        | id = 101         |
 * | department       |        | id = 10          |
 * | attendance       |        | id = 5001        |
 * +------------------+        +------------------+
 *
 * Notice something interesting...
 *
 * Java never understands what a database table is.
 *
 * Likewise,
 *
 * MySQL never understands what a Java object is.
 *
 * Both technologies solve different problems.
 *
 * +----------------------------+----------------------------------+
 * | Java                       | Relational Database              |
 * +----------------------------+----------------------------------+
 * | Class                      | Table                            |
 * | Object                     | Row                              |
 * | Field                      | Column                           |
 * | Inheritance                | No Direct Equivalent             |
 * | Object Reference           | Foreign Key                      |
 * | Collection                 | Multiple Rows                    |
 * +----------------------------+----------------------------------+
 *
 * Because of these differences, somebody has to perform the conversion
 * between Java Objects and Database Tables.
 *
 * This conversion is known as Object Relational Mapping (ORM).
 *
 * Before Hibernate, this mapping was completely the developer's
 * responsibility.
 *
 * Example
 * -------
 *
 * Step 1 : Create Java Object
 *
 * Employee employee = new Employee();
 * employee.setName("Pankaj");
 * employee.setEmail("pankaj@gmail.com");
 *
 * Step 2 : Convert Object into SQL
 *
 * INSERT INTO employee(name, email)
 * VALUES (?, ?);
 *
 * Step 3 : Execute SQL
 *
 * pstmt.executeUpdate();
 *
 * Step 4 : Read ResultSet
 *
 * ResultSet rs = pstmt.executeQuery();
 *
 * Step 5 : Convert ResultSet back into Java Object
 *
 * Employee employee = new Employee();
 * employee.setId(rs.getLong("id"));
 * employee.setName(rs.getString("name"));
 * employee.setEmail(rs.getString("email"));
 *
 * Think about a real HRMS project.
 *
 * We have entities like:
 *
 * - Employee
 * - Attendance
 * - Leave
 * - Profile
 * - Department
 * - Role
 * - Salary
 * - Holiday
 * - CompanyConfiguration
 *
 * If every entity requires manual SQL creation and manual object mapping,
 * thousands of lines of repetitive database code are written across the
 * project.
 *
 * Most of this code is not business logic.
 *
 * It is only conversion logic.
 *
 * Hibernate was created to eliminate this repetitive mapping work so that
 * developers can focus on business logic instead of writing the same CRUD
 * code repeatedly.
 */


/*
 * =============================================================================
 * 2. Why Spring JDBC Was Still Not Enough
 * =============================================================================
 *
 * When Spring Framework introduced JdbcTemplate, many developers believed
 * that JDBC had finally become easy enough and there was no need for another
 * framework.
 *
 * JdbcTemplate certainly solved many problems, but it never attempted to
 * solve Object Relational Mapping (ORM).
 *
 * To understand this, let's compare the responsibilities of JDBC,
 * Spring JDBC and Hibernate.
 *
 * +----------------------+-------------------------+-----------------------------+
 * |        JDBC          |      Spring JDBC        |          Hibernate          |
 * +----------------------+-------------------------+-----------------------------+
 * | Manual SQL           | Manual SQL             | SQL Generated Automatically |
 * | Manual Mapping       | RowMapper              | Object Mapping              |
 * | Manual Connection    | Managed by Spring      | Managed by Spring           |
 * | Low Level API        | Spring Abstraction     | Full ORM Framework          |
 * +----------------------+-------------------------+-----------------------------+
 *
 * Notice something very important.
 *
 * JdbcTemplate reduced boilerplate code.
 *
 * It did NOT eliminate SQL.
 *
 * It did NOT understand Java Objects.
 *
 * It did NOT automatically map database rows into entities.
 *
 * It did NOT track object changes.
 *
 * It did NOT manage entity state.
 *
 * It did NOT provide caching.
 *
 * In other words,
 *
 * JdbcTemplate improved JDBC.
 *
 * Hibernate introduced a completely different programming model.
 *
 * -----------------------------------------------------------------------------
 * Let's understand this using an Employee module.
 * -----------------------------------------------------------------------------
 *
 * Spring JDBC
 * -----------
 *
 * Saving an employee requires the developer to write SQL manually.
 *
 * String sql =
 *     "INSERT INTO employee(name, email, salary) VALUES (?, ?, ?)";
 *
 * jdbcTemplate.update(
 *         sql,
 *         employee.getName(),
 *         employee.getEmail(),
 *         employee.getSalary()
 * );
 *
 * Updating an employee?
 *
 * Write another SQL.
 *
 * Searching an employee?
 *
 * Write another SQL.
 *
 * Deleting an employee?
 *
 * Write another SQL.
 *
 * Every CRUD operation depends on SQL written by the developer.
 *
 * -----------------------------------------------------------------------------
 * Hibernate
 * -----------------------------------------------------------------------------
 *
 * Employee employee = new Employee();
 *
 * employee.setName("Pankaj");
 * employee.setEmail("pankaj@gmail.com");
 * employee.setSalary(50000);
 *
 * entityManager.persist(employee);
 *
 * That's it.
 *
 * Hibernate reads the metadata of EmployeeEntity,
 * generates the appropriate INSERT statement,
 * binds all parameters,
 * executes SQL using JDBC,
 * and synchronizes the Persistence Context.
 *
 * The developer never writes INSERT statements manually.
 *
 * -----------------------------------------------------------------------------
 * Internal Responsibility Comparison
 * -----------------------------------------------------------------------------
 *
 * +---------------------------+---------------+---------------+
 * | Responsibility            | JdbcTemplate  | Hibernate     |
 * +---------------------------+---------------+---------------+
 * | Write SQL                 | Developer     | Hibernate     |
 * | Bind Parameters           | Developer     | Hibernate     |
 * | Map ResultSet             | Developer     | Hibernate     |
 * | Manage Entity State       | No            | Yes           |
 * | Dirty Checking            | No            | Yes           |
 * | First Level Cache         | No            | Yes           |
 * | Relationship Handling     | Manual        | Automatic     |
 * | SQL Generation            | Manual        | Automatic     |
 * +---------------------------+---------------+---------------+
 *
 * -----------------------------------------------------------------------------
 * Think Like an Enterprise Developer
 * -----------------------------------------------------------------------------
 *
 * Imagine an HRMS application having:
 *
 * + Employee
 * + Attendance
 * + Leave
 * + Payroll
 * + Department
 * + Holiday
 * + Shift
 * + Profile
 * + Company Configuration
 * + Notification
 *
 * Each module may contain dozens of database operations.
 *
 * With JdbcTemplate, every operation requires manually written SQL.
 *
 * As the project grows, maintaining hundreds or even thousands of SQL
 * statements becomes increasingly difficult.
 *
 * Hibernate removes this repetitive work by allowing developers to work
 * directly with Java objects while it handles the persistence layer.
 *
 * -----------------------------------------------------------------------------
 * Important Conclusion
 * -----------------------------------------------------------------------------
 *
 * JdbcTemplate answers the question:
 *
 *     "How can I make JDBC easier?"
 *
 * Hibernate answers a completely different question:
 *
 *     "How can I eliminate the gap between Java Objects and Relational
 *      Databases?"
 *
 * These are different problems.
 *
 * Therefore, JdbcTemplate and Hibernate are not competitors.
 *
 * JdbcTemplate is a JDBC abstraction.
 *
 * Hibernate is an ORM framework built on top of JDBC.
 */



/*
 * =============================================================================
 * 3. Where Hibernate Fits in Spring Boot
 * =============================================================================
 *
 * One of the biggest misconceptions among developers is:
 *
 *      "Spring Boot uses Hibernate."
 *
 * Although this statement is correct, it is incomplete.
 *
 * Spring Boot, Spring Data JPA, JPA and Hibernate all have different
 * responsibilities.
 *
 * Let's understand the complete architecture.
 *
 * +----------------------+------------------------------------------------------+
 * | Component            | Responsibility                                       |
 * +----------------------+------------------------------------------------------+
 * | Spring Boot          | Creates and configures application components        |
 * | Spring Data JPA      | Provides Repository abstraction                      |
 * | JPA                  | Defines ORM specification (Interfaces & Contracts)   |
 * | Hibernate            | Implements the JPA specification                     |
 * | JDBC                 | Executes SQL against the database                    |
 * | MySQL                | Stores the actual data                               |
 * +----------------------+------------------------------------------------------+
 *
 * Most developers think Hibernate is directly used by Spring Boot.
 *
 * Actually the flow is:
 *
 * +-------------+     +----------------+     +-------------+     +-------------+
 * | Spring Boot | --> | Spring Data    | --> |     JPA     | --> |  Hibernate  |
 * |             |     |      JPA       |     | Specification|     |Implementation|
 * +-------------+     +----------------+     +-------------+     +-------------+
 *                                                                     |
 *                                                                     v
 *                                                             +---------------+
 *                                                             |     JDBC      |
 *                                                             +---------------+
 *                                                                     |
 *                                                                     v
 *                                                             +---------------+
 *                                                             |     MySQL     |
 *                                                             +---------------+
 *
 * Hibernate is NOT part of Spring Framework.
 *
 * Hibernate is an independent ORM framework.
 *
 * Spring Boot simply detects Hibernate on the classpath and configures it
 * automatically.
 *
 * -----------------------------------------------------------------------------
 * Let's see what happens when we execute:
 *
 * employeeRepository.save(employee);
 * -----------------------------------------------------------------------------
 *
 * Most developers think save() directly inserts data into the database.
 *
 * That is NOT what actually happens.
 *
 * Internally the request travels through multiple layers.
 *
 * +----------------------+     +----------------------+     +----------------------+
 * | EmployeeController   | --> | EmployeeService      | --> | EmployeeRepository   |
 * +----------------------+     +----------------------+     +----------------------+
 *                                                                  |
 *                                                                  v
 *                                                        JpaRepository.save()
 *                                                                  |
 *                                                                  v
 *                                                         EntityManager.persist()
 *                                                                  |
 *                                                                  v
 *                                                            Hibernate Session
 *                                                                  |
 *                                                                  v
 *                                                          Persistence Context
 *                                                                  |
 *                                                                  v
 *                                                            SQL Generation
 *                                                                  |
 *                                                                  v
 *                                                                  JDBC
 *                                                                  |
 *                                                                  v
 *                                                                 MySQL
 *
 * Notice something interesting...
 *
 * Your application never directly communicates with Hibernate.
 *
 * Most Spring Boot applications communicate with:
 *
 * Repository
 *
 * Repository internally uses:
 *
 * EntityManager
 *
 * EntityManager is implemented by:
 *
 * Hibernate
 *
 * Hibernate internally uses:
 *
 * JDBC
 *
 * JDBC communicates with:
 *
 * Database
 *
 * -----------------------------------------------------------------------------
 * Real HRMS Example
 * -----------------------------------------------------------------------------
 *
 * Consider the following code:
 *
 * employeeRepository.save(employee);
 *
 * The above line looks simple.
 *
 * But internally Spring Boot performs dozens of operations before the data
 * reaches the database.
 *
 * 1. Repository receives the request.
 *
 * 2. Repository delegates to EntityManager.
 *
 * 3. EntityManager delegates to Hibernate.
 *
 * 4. Hibernate stores the entity inside the Persistence Context.
 *
 * 5. Hibernate decides whether SQL should be generated immediately or later.
 *
 * 6. SQL is generated.
 *
 * 7. JDBC executes the SQL.
 *
 * 8. MySQL stores the row.
 *
 * This entire process happens automatically.
 *
 * -----------------------------------------------------------------------------
 * Why is this important?
 * -----------------------------------------------------------------------------
 *
 * Every advanced Hibernate topic depends on understanding this architecture.
 *
 * For example:
 *
 * - Dirty Checking
 * - First Level Cache
 * - Entity Lifecycle
 * - Flush
 * - Lazy Loading
 * - Transactions
 *
 * None of these concepts can be understood unless we know where Hibernate
 * actually sits inside the Spring Boot ecosystem.
 *
 * In the next chapter, we will study ORM Architecture in detail and understand
 * how Hibernate converts Java Objects into Relational Database records.
 */


/*
 * =============================================================================
 * 4. How Hibernate Works (High-Level Flow)
 * =============================================================================
 *
 * At this point we know:
 *
 * - Hibernate is an ORM framework.
 * - Hibernate sits between our application and JDBC.
 * - Hibernate is responsible for converting Java Objects into database records.
 *
 * The next question is:
 *
 *      "How does Hibernate actually perform this conversion?"
 *
 * Let's understand the complete journey of a single Employee object.
 *
 * -----------------------------------------------------------------------------
 * Step 1 : Create a Java Object
 * -----------------------------------------------------------------------------
 *
 * Employee employee = new Employee();
 * employee.setName("Pankaj");
 * employee.setEmail("pankaj@gmail.com");
 * employee.setSalary(BigDecimal.valueOf(50000));
 *
 * At this moment...
 *
 * +----------------------+----------------------------------------------+
 * | Object State         | Description                                  |
 * +----------------------+----------------------------------------------+
 * | Exists in JVM        | Yes                                          |
 * | Stored in Database   | No                                           |
 * | SQL Generated        | No                                           |
 * | Hibernate Aware      | No                                           |
 * +----------------------+----------------------------------------------+
 *
 * This is simply a normal Java object.
 *
 * -----------------------------------------------------------------------------
 * Step 2 : Ask Hibernate to Persist the Object
 * -----------------------------------------------------------------------------
 *
 * employeeRepository.save(employee);
 *
 * or
 *
 * entityManager.persist(employee);
 *
 * Now the object enters the Hibernate world.
 *
 * Hibernate starts managing this object and prepares it for persistence.
 *
 * -----------------------------------------------------------------------------
 * Step 3 : Hibernate Reads Entity Metadata
 * -----------------------------------------------------------------------------
 *
 * Hibernate first analyzes the entity class.
 *
 * Example:
 *
 * @Entity
 * @Table(name = "employee")
 * public class Employee {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Long id;
 *
 *     private String name;
 *
 *     private String email;
 * }
 *
 * Hibernate now knows:
 *
 * +----------------------+--------------------------------------+
 * | Java Class           | Employee                             |
 * | Database Table       | employee                             |
 * | Primary Key          | id                                   |
 * | Columns              | id, name, email                      |
 * +----------------------+--------------------------------------+
 *
 * This information is called Entity Metadata.
 *
 * -----------------------------------------------------------------------------
 * Step 4 : SQL Generation
 * -----------------------------------------------------------------------------
 *
 * Using the metadata, Hibernate automatically generates SQL.
 *
 * Generated SQL
 *
 * INSERT INTO employee
 * (
 *     name,
 *     email,
 *     salary
 * )
 * VALUES
 * (
 *     ?,
 *     ?,
 *     ?
 * );
 *
 * Notice...
 *
 * We never wrote this SQL.
 *
 * Hibernate generated it automatically.
 *
 * -----------------------------------------------------------------------------
 * Step 5 : Execute SQL using JDBC
 * -----------------------------------------------------------------------------
 *
 * Hibernate does NOT communicate directly with MySQL.
 *
 * It delegates SQL execution to JDBC.
 *
 * +--------------------+      +----------------+      +----------------+
 * |    Hibernate       | ---> |      JDBC      | ---> |     MySQL      |
 * +--------------------+      +----------------+      +----------------+
 *
 * Hibernate prepares the SQL.
 *
 * JDBC executes the SQL.
 *
 * MySQL stores the row.
 *
 * -----------------------------------------------------------------------------
 * Complete Request Flow
 * -----------------------------------------------------------------------------
 *
 * +------------------+    +------------------+    +----------------------+
 * | Employee Object  | -> | Repository.save()| -> | EntityManager.persist |
 * +------------------+    +------------------+    +----------------------+
 *                                                          |
 *                                                          v
 *                                               +----------------------+
 *                                               |     Hibernate        |
 *                                               +----------------------+
 *                                                          |
 *                                      Read Entity Metadata|
 *                                                          |
 *                                                          v
 *                                               Generate SQL Statement
 *                                                          |
 *                                                          v
 *                                               +----------------------+
 *                                               |        JDBC          |
 *                                               +----------------------+
 *                                                          |
 *                                                          v
 *                                               +----------------------+
 *                                               |        MySQL         |
 *                                               +----------------------+
 *
 * -----------------------------------------------------------------------------
 * Important Observation
 * -----------------------------------------------------------------------------
 *
 * Hibernate never stores Java objects directly into the database.
 *
 * Every operation eventually becomes SQL.
 *
 * Hibernate simply automates the generation of SQL and the mapping between
 * Java objects and relational tables.
 *
 * Therefore:
 *
 * Java Object
 *        |
 *        v
 * Hibernate
 *        |
 *        v
 * Generated SQL
 *        |
 *        v
 * JDBC
 *        |
 *        v
 * Database
 *
 * SQL never disappears.
 *
 * Hibernate writes it on your behalf.
 */