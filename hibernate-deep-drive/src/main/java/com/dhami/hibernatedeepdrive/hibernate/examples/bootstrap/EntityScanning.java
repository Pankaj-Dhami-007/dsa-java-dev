package com.dhami.hibernatedeepdrive.hibernate.examples.bootstrap;

public class EntityScanning {
}

/*
 * =============================================================================
 * 10. Entity Scanning
 * =============================================================================
 *
 * At this stage, Hibernate has:
 *
 * ✓ Database Connection (DataSource)
 * ✓ SessionFactory
 * ✓ EntityManagerFactory
 *
 * However, Hibernate still cannot generate SQL.
 *
 * Why?
 *
 * Because it does not yet know which Java classes should be treated as
 * database entities.
 *
 * Before the application starts, Hibernate scans the project and discovers
 * every class annotated with @Entity.
 */

/*
 * =============================================================================
 * Entity Discovery Process
 * =============================================================================
 *
 * Consider the following project.
 *
 * src/main/java
 * └── com.dhami.hibernatedeepdrive
 *     ├── entity
 *     │     ├── Employee.java      (@Entity)
 *     │     ├── Department.java    (@Entity)
 *     │     └── Leave.java         (@Entity)
 *     │
 *     ├── service
 *     ├── repository
 *     └── controller
 *
 * During startup, Spring Boot scans the application's packages.
 *
 * Whenever it finds a class annotated with @Entity,
 * it registers that class with Hibernate.
 */

/*
 * =============================================================================
 * Startup Sequence
 * =============================================================================
 *
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Spring Boot Starts                                                      │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Scan Application Packages                                                │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Find Classes Annotated With @Entity                                      │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Read Mapping Information (@Table, @Id, @Column, ...)                     │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Build Entity Metadata                                                    │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                    │
 *                    ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Store Metadata Inside SessionFactory                                     │
 * └──────────────────────────────────────────────────────────────────────────┘
 */

/*
 * =============================================================================
 * Information Collected From an Entity
 * =============================================================================
 *
 * Assume we have:
 *
 *      examples.entity.Employee
 *
 * Hibernate extracts information such as:
 *
 * +---------------------------+----------------------------------------------+
 * | Property                  | Example                                      |
 * +---------------------------+----------------------------------------------+
 * | Entity Name               | Employee                                     |
 * | Table Name                | employees                                    |
 * | Primary Key               | id                                           |
 * | ID Generation Strategy    | IDENTITY                                     |
 * | Columns                   | name, email, salary                          |
 * | Relationships             | @OneToMany, @ManyToOne, etc.                 |
 * | Constraints               | nullable, unique, length                     |
 * +---------------------------+----------------------------------------------+
 *
 * Hibernate converts all this information into internal metadata.
 *
 * Later, every SQL statement is generated using this metadata.
 */

/*
 * =============================================================================
 * Why Doesn't Hibernate Scan Entities For Every Request?
 * =============================================================================
 *
 * Imagine a project containing:
 *
 *      500 Entity classes
 *
 * If Hibernate scanned all 500 classes before every API request,
 * application performance would become unacceptable.
 *
 * Instead, Hibernate follows this strategy.
 *
 * Startup Phase
 *
 *      Scan All Entities
 *              │
 *              ▼
 *      Build Metadata
 *              │
 *              ▼
 *      Cache Metadata
 *
 * Runtime Phase
 *
 *      Reuse Cached Metadata
 *
 * This significantly reduces runtime overhead.
 */

/*
 * =============================================================================
 * 11. Repository Creation & Application Ready
 * =============================================================================
 *
 * At this stage, Hibernate has completed all of its initialization.
 *
 * ✓ Database Connection Created
 * ✓ DataSource Ready
 * ✓ SessionFactory Created
 * ✓ EntityManagerFactory Created
 * ✓ Entity Metadata Loaded
 * ✓ Mapping Validated
 *
 * Now Spring Boot creates the Repository beans.
 *
 * These repositories become the bridge between our business logic and
 * Hibernate.
 *
 */

/*
 * =============================================================================
 * Complete Spring Boot + Hibernate Startup Lifecycle
 * =============================================================================
 *
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │                            Application Starts                             │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ main()                                                                    │
 * │ SpringApplication.run()                                                   │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Spring IoC Container Created                                              │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Read application.properties                                               │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Create DataSource (HikariCP)                                               │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Scan @Entity Classes                                                      │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Build Entity Metadata                                                     │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Hibernate Builds SessionFactory                                           │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Spring Exposes EntityManagerFactory                                       │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Create Repository Beans                                                   │
 * └────────────────────────────────────────────────────────────────────────────┘
 *                                      │
 *                                      ▼
 * ┌────────────────────────────────────────────────────────────────────────────┐
 * │ Application Ready                                                         │
 * └────────────────────────────────────────────────────────────────────────────┘
 *
 */

/*
 * =============================================================================
 * Repository Bean Creation
 * =============================================================================
 *
 * Consider the following repository.
 *
 *      EmployeeRepository
 *
 * Spring Boot detects that it extends JpaRepository.
 *
 * Instead of waiting for us to write an implementation,
 * Spring Data JPA automatically creates one.
 *
 *
 *                    EmployeeRepository
 *                            │
 *                            ▼
 *                 Spring Data JPA detects it
 *                            │
 *                            ▼
 *              Generates SimpleJpaRepository
 *                            │
 *                            ▼
 *               Injects EntityManager
 *                            │
 *                            ▼
 *                 Repository Bean Ready
 *
 *
 * This entire process happens during application startup.
 *
 */

/*
 * =============================================================================
 * What Actually Exists At Runtime?
 * =============================================================================
 *
 * You write only:
 *
 *      EmployeeRepository
 *
 *
 * Runtime Object Graph
 *
 *
 *                  EmployeeService
 *                         │
 *                         ▼
 *              EmployeeRepository (Proxy)
 *                         │
 *                         ▼
 *               SimpleJpaRepository
 *                         │
 *                         ▼
 *                  EntityManager
 *                         │
 *                         ▼
 *                      Hibernate
 *                         │
 *                         ▼
 *                        JDBC
 *                         │
 *                         ▼
 *                      Database
 *
 *
 * Notice carefully.
 *
 * EmployeeRepository is an interface.
 *
 * The actual implementation is automatically generated by
 * Spring Data JPA.
 *
 */

/*
 * =============================================================================
 * Why Don't We Implement JpaRepository?
 * =============================================================================
 *
 * Before Spring Data JPA,
 * developers manually wrote DAO implementations.
 *
 * Example:
 *
 *      EmployeeDaoImpl
 *          save()
 *          update()
 *          delete()
 *          findById()
 *          findAll()
 *
 * Every project repeated the same CRUD code.
 *
 * Spring Data JPA eliminates this duplication by generating
 * the implementation automatically.
 *
 * As developers, we define only the repository interface.
 *
 * Spring Boot generates the implementation during startup.
 *
 */

/*
 * =============================================================================
 * Complete Spring Boot + Hibernate Runtime Architecture
 * =============================================================================
 *
 *                                  Client
 *                                     │
 *                                     ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Controller                                                               │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                                     │
 *                                     ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Service                                                                  │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                                     │
 *                                     ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Repository (Generated by Spring Data JPA)                                │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                                     │
 *                                     ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ EntityManager (JPA)                                                      │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                                     │
 *                                     ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ Session (Hibernate)                                                      │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                                     │
 *                                     ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ JDBC Driver                                                              │
 * └──────────────────────────────────────────────────────────────────────────┘
 *                                     │
 *                                     ▼
 * ┌──────────────────────────────────────────────────────────────────────────┐
 * │ MySQL                                                                    │
 * └──────────────────────────────────────────────────────────────────────────┘
 *
 */