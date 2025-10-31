package com.example

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import org.slf4j.LoggerFactory

object IotTestProblem : ModInitializer {
    private val logger = LoggerFactory.getLogger("iot-test-problem")

	override fun onInitialize() {
		logger.info("Hello Fabric world!")
        PayloadTypeRegistry.playC2S().register(MessageC2SPayload.ID, MessageC2SPayload.CODEC)
        ServerPlayNetworking.registerGlobalReceiver(MessageC2SPayload.ID) { payload, context ->
            val player = context.player()
            logger.info("[[${player.uuid}]] - ${payload.message}")
        }
	}
}