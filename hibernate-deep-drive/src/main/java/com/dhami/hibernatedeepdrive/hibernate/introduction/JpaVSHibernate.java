package com.dhami.hibernatedeepdrive.hibernate.introduction;

public class JpaVSHibernate {
}

/*
 * =============================================================================
 * 8. JPA vs Hibernate
 * =============================================================================
 *
 * Before understanding SessionFactory and Session,
 * we must understand the relationship between JPA and Hibernate.
 *
 * Many beginners think:
 *
 *      JPA == Hibernate
 *
 * This is NOT correct.
 *
 * JPA and Hibernate have different responsibilities.
 *
 * +----------------------+------------------------------------------------------+
 * | JPA                  | Defines the specification (Interfaces & Contracts)  |
 * | Hibernate            | Implements the JPA specification                    |
 * +----------------------+------------------------------------------------------+
 *
 * Think about JDBC.
 *
 * JDBC provides interfaces like:
 *
 *      Connection
 *      Statement
 *      ResultSet
 *
 * MySQL provides their implementation.
 *
 * Similarly,
 *
 * JPA provides interfaces.
 *
 * Hibernate provides their implementation.
 *
 */

/*
 * =============================================================================
 * JPA and Hibernate Relationship
 * =============================================================================
 *
 *                    JPA
 *
 *          +-----------------------+
 *          | EntityManager         |
 *          | EntityManagerFactory  |
 *          | EntityTransaction     |
 *          +-----------+-----------+
 *                      |
 *              Implemented By
 *                      |
 *                      v
 *          +-----------------------+
 *          | Hibernate             |
 *          | Session               |
 *          | SessionFactory        |
 *          | Transaction           |
 *          +-----------------------+
 *
 * Spring Boot applications generally program against JPA interfaces.
 *
 * Hibernate works behind the scenes.
 */

/*
 * =============================================================================
 * Interface vs Implementation
 * =============================================================================
 *
 * Consider the following analogy.
 *
 * +--------------------------------------------------------------+
 * | Interface            | Implementation                        |
 * +--------------------------------------------------------------+
 * | List                 | ArrayList                            |
 * | Map                  | HashMap                              |
 * | Set                  | HashSet                              |
 * | EntityManager        | Hibernate Session                    |
 * | EntityManagerFactory | Hibernate SessionFactory             |
 * +--------------------------------------------------------------+
 *
 * Developers usually write:
 *
 *      List<String> names = new ArrayList<>();
 *
 * not
 *
 *      ArrayList<String> names = new ArrayList<>();
 *
 * because we program against interfaces.
 *
 * Spring Boot follows the same principle.
 */


