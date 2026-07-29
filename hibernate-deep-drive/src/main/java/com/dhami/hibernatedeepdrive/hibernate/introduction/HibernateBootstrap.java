package com.dhami.hibernatedeepdrive.hibernate.introduction;

/*
 * =============================================================================
 * Topic      : Hibernate Bootstrap Process
 * Package    : com.dhami.hibernatedeepdrive.hibernate.introduction
 * =============================================================================
 *
 * Objective
 * ---------
 *
 * We have already learned:
 *
 * • Why Hibernate was created.
 * • What ORM is.
 * • Hibernate Architecture.
 *
 * But one important question still remains.
 *
 *      "When are all these Hibernate objects created?"
 *
 * We never write:
 *
 *      new EntityManagerFactory()
 *      new EntityManager()
 *      new SessionFactory()
 *
 * Yet all of them somehow exist when our application starts.
 *
 * So who creates them?
 *
 * The answer is:
 *
 * Spring Boot Auto Configuration.
 *
 * In this chapter, we will follow the application from the moment we execute
 * the application until Hibernate becomes ready to execute SQL.
 *
 * This process is called Hibernate Bootstrap.
 */
public class HibernateBootstrap {
}

/*
 * =============================================================================
 * 1. What is Bootstrap?
 * =============================================================================
 *
 * Bootstrap simply means:
 *
 *      Initializing and preparing a framework before the application
 *      starts serving requests.
 *
 * Every framework has a bootstrap process.
 *
 * +----------------------+---------------------------------------------+
 * | Framework            | Bootstrap Responsibility                    |
 * +----------------------+---------------------------------------------+
 * | Spring Boot          | Creates Spring Beans                        |
 * | Hibernate            | Initializes ORM Engine                      |
 * | Tomcat               | Starts Web Server                           |
 * | MySQL                | Opens Database Connections                  |
 * +----------------------+---------------------------------------------+
 *
 * In our case,
 * Hibernate Bootstrap means creating every object required for ORM.
 *
 * After bootstrap completes,
 * Hibernate is ready to perform CRUD operations.
 */

/*
 * =============================================================================
 * 2. Starting the Application
 * =============================================================================
 *
 * Every Spring Boot application starts from the main method.
 */

/*
 * The application looks very simple.
 *
 * But internally Spring Boot performs hundreds of operations before the
 * application becomes ready.
 *
 * High Level Startup Flow
 *
 * +------------------------------------------------------+
 * | JVM Starts                                           |
 * +------------------------------------------------------+
 *                    |
 *                    v
 * +------------------------------------------------------+
 * | main() Method Executes                               |
 * +------------------------------------------------------+
 *                    |
 *                    v
 * +------------------------------------------------------+
 * | SpringApplication.run()                              |
 * +------------------------------------------------------+
 *                    |
 *                    v
 * +------------------------------------------------------+
 * | Spring Container Created                             |
 * +------------------------------------------------------+
 *                    |
 *                    v
 * +------------------------------------------------------+
 * | Auto Configuration Starts                            |
 * +------------------------------------------------------+
 *                    |
 *                    v
 * +------------------------------------------------------+
 * | Hibernate Configuration Begins                       |
 * +------------------------------------------------------+
 *                    |
 *                    v
 * +------------------------------------------------------+
 * | Application Ready                                    |
 * +------------------------------------------------------+
 *
 * Notice something important.
 *
 * Hibernate does not start itself.
 *
 * Spring Boot starts Hibernate during application startup.
 */

/*
 * =============================================================================
 * 3. What Does @SpringBootApplication Actually Do?
 * =============================================================================
 *
 * The most important annotation in a Spring Boot application is:
 *
 *      @SpringBootApplication
 *
 * Although it appears to be a single annotation,
 * internally it combines three annotations.
 */

/*
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
 */


/*
 * Their responsibilities are:
 *
 * +---------------------------+-------------------------------------------+
 * | Annotation                | Responsibility                            |
 * +---------------------------+-------------------------------------------+
 * | @SpringBootConfiguration  | Declares configuration class              |
 * | @EnableAutoConfiguration  | Automatically configures Spring Boot      |
 * | @ComponentScan            | Finds Spring Beans                        |
 * +---------------------------+-------------------------------------------+
 *
 * For Hibernate,
 * the most important annotation is:
 *
 *      @EnableAutoConfiguration
 *
 * This annotation is responsible for detecting Hibernate on the classpath
 * and configuring it automatically.
 */

/*
 * =============================================================================
 * 4. Auto Configuration Detection
 * =============================================================================
 *
 * Spring Boot now starts inspecting the application's dependencies.
 *
 * It checks:
 *
 * "Which libraries are present?"
 *
 * Suppose our pom.xml contains:
 */

/*

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
 */

/*
 * Spring Boot detects:
 *
 *      spring-boot-starter-data-jpa
 *
 * This starter brings several libraries transitively:
 *
 * +--------------------------------------------+
 * | Spring Data JPA                            |
 * | Hibernate ORM                              |
 * | Jakarta Persistence API                    |
 * | JDBC                                       |
 * | HikariCP                                   |
 * +--------------------------------------------+
 *
 * Because Hibernate is present,
 * Spring Boot decides:
 *
 *      "This application requires JPA and Hibernate configuration."
 *
 * No XML configuration.
 * No manual object creation.
 * No custom bootstrap code.
 *
 * Everything is configured automatically.
 */

/*
 * =============================================================================
 * 5. Creating the DataSource
 * =============================================================================
 *
 * Before Hibernate can execute a single SQL statement, it needs a way to
 * communicate with the database.
 *
 * Hibernate does not create database connections itself.
 *
 * Instead, it asks Spring Boot for a DataSource.
 *
 * The DataSource is one of the very first infrastructure beans created during
 * application startup.
 *
 * Spring Boot creates it automatically using the properties defined in
 * application.properties.
 */

/*

spring.datasource.url=jdbc:mysql://localhost:3306/hrms
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
 */

/*
 * Let's understand what each property means.
 *
 * +--------------------------------------+--------------------------------------+
 * | Property                             | Purpose                              |
 * +--------------------------------------+--------------------------------------+
 * | spring.datasource.url                | Database Location                    |
 * | spring.datasource.username           | Database Username                    |
 * | spring.datasource.password           | Database Password                    |
 * | spring.datasource.driver-class-name  | JDBC Driver                          |
 * | spring.jpa.hibernate.ddl-auto        | Schema Management Strategy           |
 * | spring.jpa.show-sql                  | Print Generated SQL                 |
 * +--------------------------------------+--------------------------------------+
 *
 * At this stage, Hibernate has not started yet.
 *
 * Spring Boot is only collecting the information required to create a
 * DataSource.
 */


/*
 * =============================================================================
 * How Spring Boot Creates the DataSource
 * =============================================================================
 *
 * Internally, the startup process looks like this.
 *
 * application.properties
 *            |
 *            v
 * +----------------------------+
 * | Read Configuration Values  |
 * +----------------------------+
 *            |
 *            v
 * +----------------------------+
 * | DataSourceProperties Bean  |
 * +----------------------------+
 *            |
 *            v
 * +----------------------------+
 * | HikariDataSource           |
 * +----------------------------+
 *            |
 *            v
 * +----------------------------+
 * | Registered as Spring Bean  |
 * +----------------------------+
 *
 * The DataSource is now available for every component that requires
 * database access.
 */

/*
 * =============================================================================
 * HikariCP
 * =============================================================================
 *
 * Spring Boot does not create a plain JDBC DataSource.
 *
 * By default, it creates a HikariDataSource.
 *
 * HikariCP is a high-performance JDBC Connection Pool.
 *
 * Instead of creating a new database connection for every request,
 * HikariCP maintains a pool of reusable connections.
 *
 * Example
 *
 *                Hikari Connection Pool
 *
 *        +------------------------------+
 *        | Connection 1                 |
 *        | Connection 2                 |
 *        | Connection 3                 |
 *        | Connection 4                 |
 *        | Connection 5                 |
 *        +------------------------------+
 *
 * Request 1 ---> Connection 1
 *
 * Request 2 ---> Connection 2
 *
 * Request 3 ---> Connection 1 (reused)
 *
 * Creating a database connection is expensive.
 *
 * Reusing existing connections makes applications much faster.
 *
 * Hibernate simply asks the DataSource:
 *
 *      "Give me a connection."
 *
 * The DataSource returns one from the connection pool.
 */


/*
 * =============================================================================
 * 6. Creating the EntityManagerFactory
 * =============================================================================
 *
 * In the previous section, Spring Boot created the DataSource.
 *
 * At this point, Hibernate has everything required to communicate with the
 * database.
 *
 * But Hibernate still cannot execute ORM operations.
 *
 * Why?
 *
 * Because Hibernate does not know:
 *
 * • Which classes are entities?
 * • Which table belongs to which entity?
 * • Which field maps to which column?
 * • Which database dialect should be used?
 *
 * To collect and manage all this information, Hibernate creates the
 * EntityManagerFactory.
 */

/*
 * =============================================================================
 * Startup Flow So Far
 * =============================================================================
 *
 * main()
 *      |
 *      v
 * SpringApplication.run()
 *      |
 *      v
 * Spring Container
 *      |
 *      v
 * Read application.properties
 *      |
 *      v
 * Create DataSource
 *      |
 *      v
 * Create EntityManagerFactory   <---- Current Step
 *      |
 *      v
 * Create EntityManager
 *      |
 *      v
 * Ready to execute SQL
 *
 */

/*
 * Think of EntityManagerFactory as the factory responsible for creating
 * EntityManager objects.
 *
 * Similar to:
 *
 *      CarFactory  ------------> creates Cars
 *
 *      EmployeeFactory --------> creates Employees
 *
 *      EntityManagerFactory ---> creates EntityManagers
 *
 * Since creating EntityManagerFactory is expensive,
 * Spring Boot creates only ONE instance during application startup.
 *
 * Every EntityManager used later in the application is created from this
 * factory.
 */

/*
 * =============================================================================
 * Responsibilities of EntityManagerFactory
 * =============================================================================
 *
 * +---------------------------------------------------------------+
 * | Read every @Entity class                                      |
 * | Build Entity Metadata                                         |
 * | Validate entity mappings                                      |
 * | Create Hibernate SessionFactory                               |
 * | Create EntityManager instances                                |
 * | Store mapping metadata                                        |
 * +---------------------------------------------------------------+
 *
 * EntityManagerFactory is created only once.
 *
 * EntityManager objects are created many times.
 */

/*
 * Internally the process looks like this.
 *
 *
 *                    EntityManagerFactory
 *
 *                +-------------------------+
 *                | Read @Entity classes    |
 *                +------------+------------+
 *                             |
 *                             v
 *                +-------------------------+
 *                | Build Metadata          |
 *                +------------+------------+
 *                             |
 *                             v
 *                +-------------------------+
 *                | Validate Mapping        |
 *                +------------+------------+
 *                             |
 *                             v
 *                +-------------------------+
 *                | Create SessionFactory   |
 *                +------------+------------+
 *                             |
 *                             v
 *                +-------------------------+
 *                | Ready                   |
 *                +-------------------------+
 *
 */


/*
 * =============================================================================
 * What is Entity Metadata?
 * =============================================================================
 *
 * Metadata means:
 *
 *      "Information about the entity."
 *
 * Consider the following entity.
 *
 * See:
 *
 * examples.entity.Employee
 *
 * Hibernate reads this class only once during startup.
 *
 * From that class, Hibernate learns:
 *
 * +-----------------------------+------------------------------+
 * | Information                 | Value                        |
 * +-----------------------------+------------------------------+
 * | Java Class                  | Employee                     |
 * | Database Table              | employees                    |
 * | Primary Key                 | id                           |
 * | ID Strategy                 | IDENTITY                     |
 * | Columns                     | id,name,email,salary         |
 * +-----------------------------+------------------------------+
 *
 * Hibernate stores all this information in memory.
 *
 * Later, when we call:
 *
 *      employeeRepository.save(employee);
 *
 * Hibernate already knows exactly how to generate the SQL.
 *
 */

/*
 * Hibernate keeps this metadata inside the EntityManagerFactory.
 *
 * It does NOT scan entity classes for every request.
 *
 * Imagine an application with:
 *
 *      350 Entity classes
 *
 * If Hibernate scanned every entity for every API request,
 * the application would become extremely slow.
 *
 * Instead,
 *
 * Startup Time
 *
 *      Scan Entity Classes
 *               ↓
 *      Build Metadata
 *               ↓
 *      Cache Metadata
 *
 * Runtime
 *
 *      Reuse Cached Metadata
 *
 * This is one of the reasons Hibernate performs well.
 */

/*
 * =============================================================================
 * Example
 * =============================================================================
 *
 * The following reusable examples will be created under:
 *
 * examples.entity.Employee
 *
 * examples.repository.EmployeeRepository
 *
 * examples.service.EmployeeService
 *
 * In the next chapter (FirstHibernateProject.java),
 * we will build these classes step by step and use them throughout the
 * remainder of this course.
 *
 * From that point onward, every Hibernate concept will reuse the same
 * application instead of creating new demo projects.
 */

/*
 * =============================================================================
 * 7. Creating the EntityManager
 * =============================================================================
 *
 * In the previous section, Hibernate created the EntityManagerFactory.
 *
 * However, the EntityManagerFactory itself is never used to perform database
 * operations.
 *
 * Instead, its primary responsibility is to create EntityManager instances.
 *
 * Think of the relationship like this.
 *
 *                +---------------------------+
 *                | EntityManagerFactory      |
 *                +------------+--------------+
 *                             |
 *                 creates many EntityManagers
 *                             |
 *          +------------------+------------------+
 *          |                  |                  |
 *          v                  v                  v
 *  +----------------+  +----------------+  +----------------+
 *  | EntityManager1 |  | EntityManager2 |  | EntityManager3 |
 *  +----------------+  +----------------+  +----------------+
 *
 * One factory.
 *
 * Many EntityManagers.
 *
 * This keeps object creation efficient while allowing multiple requests
 * to work independently.
 */

/*
 * =============================================================================
 * Why Multiple EntityManagers?
 * =============================================================================
 *
 * Imagine a web application receiving requests from multiple users.
 *
 * User A
 *      POST /employees
 *
 * User B
 *      GET /employees/10
 *
 * User C
 *      PUT /employees/20
 *
 * If every request shared the same EntityManager,
 * all requests would also share:
 *
 * • Managed entities
 * • Transactions
 * • Persistence Context
 *
 * This would cause:
 *
 * - Data corruption
 * - Thread safety issues
 * - Incorrect transactions
 * - Unpredictable application behavior
 *
 * Therefore,
 *
 * each request works with its own EntityManager.
 */

/*
 * =============================================================================
 * EntityManager Lifecycle
 * =============================================================================
 *
 * Every request typically follows this lifecycle.
 *
 * HTTP Request
 *      |
 *      v
 * +---------------------------+
 * | Create EntityManager      |
 * +-------------+-------------+
 *               |
 *               v
 * +---------------------------+
 * | Execute Business Logic    |
 * +-------------+-------------+
 *               |
 *               v
 * +---------------------------+
 * | Commit / Rollback         |
 * +-------------+-------------+
 *               |
 *               v
 * +---------------------------+
 * | Close EntityManager       |
 * +---------------------------+
 *
 * Once closed,
 * the EntityManager cannot be reused.
 */




