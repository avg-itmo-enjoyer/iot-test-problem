package com.example

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "messages")
data class MessageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "messages_id_seq", allocationSize = 1)
    val id: Int = 0,

    @Column(name = "player_uuid", nullable = false)
    val playerUuid: UUID?,

    @Column(name = "text", nullable = false)
    val text: String?,
) {
    constructor(): this(0, null, null)
}
