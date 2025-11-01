package com.example.persistence

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory

abstract class Repository(
    protected open val entityManagerFactory: EntityManagerFactory
) {
    protected inline fun <T> useSession(block: (EntityManager) -> T): T =
        entityManagerFactory.createEntityManager().use { block(it) }

    protected inline fun <T> useTransaction(block: (EntityManager) -> T): T =
        useSession { session ->
            session.transaction.begin()
            try {
                block(session).also { session.transaction.commit() }
            } catch (e: Exception) {
                session.transaction.rollback()
                throw e
            }
        }
}