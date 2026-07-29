package com.dhami.hibernatedeepdrive.hibernate.examples.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

@Service
public class EntityManagerExample {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}

/*
 * Spring does NOT create a new EntityManager during application startup.
 *
 * Instead,
 *
 * Spring injects a proxy object.
 *
 * Whenever a transaction starts,
 * that proxy obtains the correct EntityManager for the current thread.
 *
 * We will study this proxy mechanism in detail later when discussing
 * transactions.
 */

/*
 * =============================================================================
 * Internal Creation Flow
 * =============================================================================
 *
 * Spring Boot Starts
 *          |
 *          v
 * Create DataSource
 *          |
 *          v
 * Create EntityManagerFactory
 *          |
 *          v
 * Transaction Starts
 *          |
 *          v
 * EntityManagerFactory creates EntityManager
 *          |
 *          v
 * EntityManager attached to current transaction
 *          |
 *          v
 * Repository uses EntityManager
 *          |
 *          v
 * Transaction Ends
 *          |
 *          v
 * EntityManager Closed
 *
 */