package com.example.widget.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class PlaceholderTextFieldWidget extends TextFieldWidget {
    private final TextRenderer textRenderer;
    private final Text placeholderText;

    public PlaceholderTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text placeholder) {
        super(textRenderer, x, y, width, height, Text.literal(""));
        this.textRenderer = textRenderer;
        this.placeholderText = placeholder;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.renderWidget(context, mouseX, mouseY, deltaTicks);

        if (this.getText().isEmpty() && !this.isFocused()) {
            context.drawTextWithShadow(
                    this.textRenderer,
                    this.placeholderText,
                    this.getX() + 4,
                    this.getY() + (this.height - 8) / 2,
                    0xFF808080
            );
        }
    }
}
