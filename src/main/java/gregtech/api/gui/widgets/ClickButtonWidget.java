package gregtech.api.gui.widgets;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.SizedTextureArea;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import org.lwjgl.input.Keyboard;

import java.util.function.Consumer;

public class ClickButtonWidget extends Widget {

    protected int xPosition;
    protected int yPosition;
    protected int width, height;
    protected TextureArea buttonTexture = GuiTextures.VANILLA_BUTTON.getSubArea(0.0, 0.0, 1.0, 0.5);
    protected String displayText;
    protected int textColor = 0xFFFFFF;
    protected Consumer<ClickData> onPressCallback;

    public ClickButtonWidget(int xPosition, int yPosition, int width, int height, String displayText, Consumer<ClickData> onPressed) {
        super();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.displayText = displayText;
        this.onPressCallback = onPressed;
    }

    public ClickButtonWidget setButtonTexture(TextureArea buttonTexture) {
        this.buttonTexture = buttonTexture;
        return this;
    }

    public ClickButtonWidget setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY) {
        super.drawInBackground(mouseX, mouseY);
        if(buttonTexture instanceof SizedTextureArea) {
            ((SizedTextureArea) buttonTexture).drawHorizontalCutSubArea(xPosition, yPosition, width, height, 0.0, 1.0);
        } else {
            buttonTexture.drawSubArea(xPosition, yPosition, width, height, 0.0, 0.0, 1.0, 1.0);
        }
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String text = I18n.format(displayText);
        fontRenderer.drawString(text,
            xPosition + width / 2 - fontRenderer.getStringWidth(text) / 2,
            yPosition + height / 2 - fontRenderer.FONT_HEIGHT / 2, textColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if(isMouseOver(xPosition, yPosition, width, height, mouseX, mouseY)) {
            triggerButton();
            return true;
        }
        return false;
    }

    protected void triggerButton() {
        boolean isShiftClick = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
        boolean isCtrlClick = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
        writeClientAction(1, buffer -> {
            buffer.writeBoolean(isShiftClick);
            buffer.writeBoolean(isCtrlClick);
        });
        playButtonClickSound();
    }

    @Override
    public void handleClientAction(int id, PacketBuffer buffer) {
        super.handleClientAction(id, buffer);
        if(id == 1) {
            boolean isShiftClick = buffer.readBoolean();
            boolean isCtrlClick = buffer.readBoolean();
            onPressCallback.accept(new ClickData(isShiftClick, isCtrlClick));
        }
    }

    public final class ClickData {
        public final boolean isShiftClick;
        public final boolean isCtrlClick;

        public ClickData(boolean isShiftClick, boolean isCtrlClick) {
            this.isShiftClick = isShiftClick;
            this.isCtrlClick = isCtrlClick;
        }
    }
}
