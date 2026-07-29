package com.dhami.hibernatedeepdrive.hibernate.introduction;

public class SessionFactoryandEntityManagerFactory {
}

/*
 * =============================================================================
 * EntityManagerFactory vs SessionFactory
 * =============================================================================
 *
 * Exactly the same relationship exists here.
 *
 *        EntityManagerFactory
 *                  |
 *                  |
 *                  v
 *             SessionFactory
 *
 * Spring Boot injects EntityManagerFactory.
 *
 * Hibernate internally creates a SessionFactory.
 *
 * Both refer to the same underlying infrastructure.
 *
 */

/*
 * =============================================================================
 * 9. SessionFactory Creation
 * =============================================================================
 *
 * In the previous section, we learned:
 *
 *      EntityManagerFactory (JPA)
 *                      ↓
 *              SessionFactory (Hibernate)
 *
 * But an important question still remains.
 *
 *      "Who actually creates the SessionFactory?"
 *
 * The answer is:
 *
 * Hibernate creates the SessionFactory during application startup,
 * while Spring Boot coordinates the entire process.
 *
 * Spring Boot does not implement SessionFactory.
 *
 * Hibernate does.
 */

/*
 * =============================================================================
 * Complete Startup Flow
 * =============================================================================
 *
 * Let's follow the complete startup sequence.
 *
 * main()
 *      |
 *      v
 * SpringApplication.run()
 *      |
 *      v
 * Spring Container Created
 *      |
 *      v
 * Read application.properties
 *      |
 *      v
 * Create DataSource
 *      |
 *      v
 * Scan @Entity Classes
 *      |
 *      v
 * Build Entity Metadata
 *      |
 *      v
 * Hibernate Creates SessionFactory
 *      |
 *      v
 * Spring Exposes EntityManagerFactory
 *      |
 *      v
 * Application Ready
 *
 * Notice carefully.
 *
 * SessionFactory is created BEFORE any EntityManager exists.
 */


// Why does Hibernate need SessionFactory?

/*
 * =============================================================================
 * Why SessionFactory?
 * =============================================================================
 *
 * SessionFactory is the central object inside Hibernate.
 *
 * It contains everything Hibernate needs to perform ORM operations.
 *
 * Responsibilities
 *
 * +--------------------------------------------------------------+
 * | Store entity metadata                                        |
 * | Store mapping information                                    |
 * | Build SQL generators                                         |
 * | Create Session objects                                       |
 * | Maintain second-level cache (if enabled)                     |
 * | Store Hibernate configuration                                |
 * +--------------------------------------------------------------+
 *
 * SessionFactory is expensive to build.
 *
 * Therefore,
 *
 * it is created only once during application startup.
 */


/*
 * =============================================================================
 * Internal SessionFactory Creation
 * =============================================================================
 *
 * Hibernate internally performs something similar to the following.
 *
 *                Read Configuration
 *                         |
 *                         v
 *                Read Entity Metadata
 *                         |
 *                         v
 *                Validate Mappings
 *                         |
 *                         v
 *                Build SQL Metadata
 *                         |
 *                         v
 *                Create SessionFactory
 *                         |
 *                         v
 *                Ready for ORM Operations
 *
 * This entire process happens only once.
 *
 */

/*
 * =============================================================================
 * SessionFactory in Action
 * =============================================================================
 *
 * Once SessionFactory has been created,
 * every database request follows this pattern.
 *
 * HTTP Request
 *      |
 *      v
 * Repository
 *      |
 *      v
 * EntityManager
 *      |
 *      v
 * Session
 *      |
 *      v
 * SessionFactory
 *      |
 *      v
 * SQL Generation
 *      |
 *      v
 * JDBC
 *      |
 *      v
 * Database
 *
 * SessionFactory itself does not execute SQL.
 *
 * Instead,
 *
 * it creates Session objects.
 *
 * Those Session objects perform the actual work.
 */

/*
Can we use Session?

Yes.

Hibernate allows us to access its native API.

We'll create a reusable example.
 */

