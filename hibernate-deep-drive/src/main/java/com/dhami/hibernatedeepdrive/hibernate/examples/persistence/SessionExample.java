package com.dhami.hibernatedeepdrive.hibernate.examples.persistence;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SessionExample {

    @PersistenceContext
    private EntityManager entityManager;

    public void printSessionImplementation() {

        Session session = entityManager.unwrap(Session.class);

        System.out.println(session.getClass().getName());
    }
}

/*
Notice something important:

Session session = entityManager.unwrap(Session.class);

We did not create the Session.

Hibernate already created it. We simply asked the EntityManager to expose the underlying Hibernate-specific implementation.
 */

// Why does Spring recommend EntityManager?

/*
 * =============================================================================
 * Why Prefer EntityManager?
 * =============================================================================
 *
 * Spring Boot recommends programming against the JPA API instead of the
 * Hibernate API.
 *
 * Advantages:
 *
 * +--------------------------------------------------------------+
 * | Application depends on a standard API                        |
 * | Easier to switch JPA providers if required                   |
 * | Better portability                                            |
 * | Less vendor-specific code                                     |
 * +--------------------------------------------------------------+
 *
 * Use EntityManager unless you need a feature that exists only in
 * Hibernate's Session API.
 *
 * Throughout this course, we will primarily use EntityManager.
 *
 * Whenever a Hibernate-specific feature is required, we will unwrap
 * the underlying Session.
 */