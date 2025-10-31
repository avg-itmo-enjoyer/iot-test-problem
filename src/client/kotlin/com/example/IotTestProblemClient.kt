package com.example

import com.example.screen.DummyScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

object IotTestProblemClient : ClientModInitializer {
    lateinit var openScreenKeybind: KeyBinding

	override fun onInitializeClient() {
        openScreenKeybind = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.iot-test-problem.open_screen",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT,
                "Iot test problem"
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (openScreenKeybind.wasPressed()) {
                client.setScreen(
                    DummyScreen(Text.literal("Iot test problem"))
                )
            }
        }
	}
}