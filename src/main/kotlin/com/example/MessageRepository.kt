package com.example

import com.example.persistence.Repository
import jakarta.persistence.EntityManagerFactory
import java.util.UUID

class MessageRepository(
    override val entityManagerFactory: EntityManagerFactory
): Repository(entityManagerFactory) {
    fun insertMessage(playerUuid: UUID, messageText: String): Unit = useTransaction { session ->
        MessageEntity(playerUuid = playerUuid, text = messageText).let { session.persist(it) }
    }
}