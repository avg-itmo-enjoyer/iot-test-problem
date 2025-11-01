package com.example

import com.example.proto.IotTestProblemMessage
import com.example.proto.iotTestProblemMessage
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

data class MessageC2SPayload(
    val message: String
) : CustomPayload {
    companion object {
        val MESSAGE_PAYLOAD_ID: Identifier = Identifier.of("iot-test-problem", "message")
        val ID: CustomPayload.Id<MessageC2SPayload> = CustomPayload.Id(MESSAGE_PAYLOAD_ID)
        val CODEC = object : PacketCodec<RegistryByteBuf, MessageC2SPayload> {
            override fun decode(buf: RegistryByteBuf): MessageC2SPayload =
                buf.readByteArray()
                    .let { IotTestProblemMessage.parseFrom(it) }
                    .let { MessageC2SPayload(it.text) }

            override fun encode(buf: RegistryByteBuf, value: MessageC2SPayload): Unit =
                iotTestProblemMessage { text = value.message }
                    .toByteArray()
                    .let { buf.writeByteArray(it) }
        }
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> = ID
}