package com.example

import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import org.slf4j.LoggerFactory

object IotTestProblem : ModInitializer {
    private val logger = LoggerFactory.getLogger("iot-test-problem")

    private const val PERSISTENCE_UNIT_ID = "iot-test-problem"
    private lateinit var sessionFactory: EntityManagerFactory
    private lateinit var messageRepository: MessageRepository

    override fun onInitialize() {
//        Thread.currentThread().contextClassLoader = this::class.java.classLoader
        sessionFactory = Persistence.createEntityManagerFactory(
            PERSISTENCE_UNIT_ID,
            mapOf("hibernate.configuration.xml.enabled" to "false")
        )
        messageRepository = MessageRepository(sessionFactory)

        PayloadTypeRegistry.playC2S().register(MessageC2SPayload.ID, MessageC2SPayload.CODEC)
        ServerPlayNetworking.registerGlobalReceiver(MessageC2SPayload.ID) { payload, context ->
            val player = context.player()
            logger.info("[[${player.uuid}]] - ${payload.message}")

            messageRepository.insertMessage(player.uuid, payload.message)
        }
    }
}