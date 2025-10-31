package com.example.screen

import MessageC2SPayload
import com.example.widget.client.PlaceholderTextFieldWidget
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class DummyScreen(
    titleText: Text,
): Screen(titleText) {
    lateinit var sendBtn:   ButtonWidget
    lateinit var msgBox:    TextFieldWidget
    lateinit var backBtn:   ButtonWidget

    override fun init() {
        sendBtn = ButtonWidget
            .builder(Text.literal("Send")) { sendMsg() }
            .tooltip(Tooltip.of(Text.literal("Send encoded message to server")))
            .dimensions(width / 2 - 100, height / 2 + 15, 200, 20)
            .build()

        msgBox = PlaceholderTextFieldWidget(
            this.textRenderer,
            width / 2 - 100, height / 2 - 15, 200, 20,
            Text.translatable("iot-test-problem.msgBoxPlaceholder")
        )

        backBtn = ButtonWidget.builder(Text.translatable("iot-test-problem.backBtn")) { close() }
            .tooltip(Tooltip.of(Text.translatable("iot-test-problem.backBtnTooltip")))
            .dimensions(width - 105, height - 25, 100, 20)
            .build()

        addDrawableChild(msgBox)
        addDrawableChild(sendBtn)
        addDrawableChild(backBtn)
    }

    private fun sendMsg() = ClientPlayNetworking.send(
        MessageC2SPayload(msgBox.text)
    )

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, deltaTicks: Float) {
        super.render(context, mouseX, mouseY, deltaTicks)

        //Это просто невероятно... 0xFFFFFFFF нормально переполняется в Java, но в Kotlin переполнение вызывает мисматч типов...
        // А главное - на ноутбуке не сразу заметно количество знаков в числе...
        context!!.drawCenteredTextWithShadow(
            textRenderer,
            title,
            width / 2, 10,
            0xFFFFFFFF.toInt()
        )
    }

//    override fun close() = if (parent != null) this.client!!.setScreen(parent) else Unit

    override fun shouldPause(): Boolean = false
}