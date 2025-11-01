package com.example

import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import org.slf4j.LoggerFactory

object IotTestProblem : ModInitializer {
    const val MOD_ID = "iot-test-problem"
    private const val PERSISTENCE_UNIT_ID = "iot-test-problem"

    private val logger = LoggerFactory.getLogger(MOD_ID)

    private lateinit var sessionFactory: EntityManagerFactory
    private lateinit var messageRepository: MessageRepository

    override fun onInitialize() {
        sessionFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_ID)
        messageRepository = MessageRepository(sessionFactory)

        PayloadTypeRegistry.playC2S().register(MessageC2SPayload.ID, MessageC2SPayload.CODEC)
        ServerPlayNetworking.registerGlobalReceiver(MessageC2SPayload.ID) { payload, context ->
            val player = context.player()
            logger.info("""[[Player(name = "${player.name.string}", uuid = "${player.uuid}")]] - Message(text = "${payload.message}")""")

            messageRepository.insertMessage(player.uuid, payload.message)
        }
    }
}