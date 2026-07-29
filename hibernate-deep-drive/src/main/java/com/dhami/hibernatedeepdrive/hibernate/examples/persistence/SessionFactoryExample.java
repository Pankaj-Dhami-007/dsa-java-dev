package com.dhami.hibernatedeepdrive.hibernate.examples.persistence;

import jakarta.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class SessionFactoryExample {

    private final EntityManagerFactory entityManagerFactory;

    public SessionFactoryExample(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void printSessionFactoryImplementation() {

        SessionFactory sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);

        System.out.println(sessionFactory.getClass().getName());
    }
}

/*
 * =============================================================================
 * Relationship Between All Components
 * =============================================================================
 *
 *                      Spring Boot
 *                           |
 *                           v
 *                    EntityManagerFactory
 *                           |
 *                    unwrap(SessionFactory.class)
 *                           |
 *                           v
 *                     SessionFactory
 *                           |
 *                  open/create Session
 *                           |
 *                           v
 *                        Session
 *                           |
 *                           v
 *                    Persistence Context
 *                           |
 *                           v
 *                          JDBC
 *                           |
 *                           v
 *                        Database
 *
 * This is the complete runtime chain used by Hibernate.
 */

/*
 * =============================================================================
 * Interview Question
 * =============================================================================
 *
 * Q. Is SessionFactory thread-safe?
 *
 * Yes.
 *
 * SessionFactory is designed to be shared across the entire application.
 *
 * Q. Is Session thread-safe?
 *
 * No.
 *
 * A Session is intended to be used by a single thread and usually exists
 * only for the duration of a transaction or request.
 *
 * Remember:
 *
 * SessionFactory
 *      One per application
 *
 * Session
 *      Many per application
 *
 */