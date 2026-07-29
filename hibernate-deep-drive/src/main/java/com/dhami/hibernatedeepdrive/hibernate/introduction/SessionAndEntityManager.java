package com.dhami.hibernatedeepdrive.hibernate.introduction;

public class SessionAndEntityManager {
}

/*
 * =============================================================================
 * EntityManager vs Session
 * =============================================================================
 *
 * EntityManager is the standard JPA interface.
 *
 * Session is Hibernate's implementation.
 *
 * Relationship
 *
 *          EntityManager
 *                 |
 *                 |
 *                 v
 *             Session
 *
 * Every Session is an EntityManager.
 *
 * Hibernate internally uses Session,
 * while Spring Boot exposes EntityManager to our application.
 *
 */