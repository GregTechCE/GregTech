package gregtech.api.gui.widgets;

import com.google.common.base.Preconditions;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.SizedTextureArea;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.function.BooleanConsumer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.BooleanSupplier;

public class ToggleButtonWidget extends Widget {

    protected int xPosition;
    protected int yPosition;
    protected int width, height;
    protected TextureArea buttonTexture;
    private BooleanSupplier isPressedCondition;
    private BooleanConsumer setPressedExecutor;
    protected boolean isPressed;

    public ToggleButtonWidget(int xPosition, int yPosition, int width, int height, BooleanSupplier isPressedCondition, BooleanConsumer setPressedExecutor) {
        this(xPosition, yPosition, width, height, GuiTextures.VANILLA_BUTTON, isPressedCondition, setPressedExecutor);
    }

    public ToggleButtonWidget(int xPosition, int yPosition, int width, int height, TextureArea buttonTexture,
                              BooleanSupplier isPressedCondition, BooleanConsumer setPressedExecutor) {
        super();
        Preconditions.checkNotNull(buttonTexture, "texture");
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.buttonTexture = buttonTexture;
        this.isPressedCondition = isPressedCondition;
        this.setPressedExecutor = setPressedExecutor;
    }

    public ToggleButtonWidget setButtonTexture(TextureArea texture) {
        Preconditions.checkNotNull(texture, "texture");
        this.buttonTexture = texture;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY) {
        if (buttonTexture instanceof SizedTextureArea) {
            ((SizedTextureArea) buttonTexture).drawHorizontalCutSubArea(xPosition, yPosition, width, height, isPressed ? 0.5 : 0.0, 0.5);
        } else {
            buttonTexture.drawSubArea(xPosition, yPosition, width, height, 0.0, isPressed ? 0.5 : 0.0, 1.0, 0.5);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (isPressedCondition.getAsBoolean() != isPressed) {
            this.isPressed = isPressedCondition.getAsBoolean();
            writeUpdateInfo(1, buf -> buf.writeBoolean(isPressed));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.isPressed = buffer.readBoolean();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (isMouseOver(xPosition, yPosition, width, height, mouseX, mouseY)) {
            this.isPressed = !this.isPressed;
            writeClientAction(1, buf -> buf.writeBoolean(isPressed));
            playButtonClickSound();
            return true;
        }
        return false;
    }


    @Override
    public void handleClientAction(int id, PacketBuffer buffer) {
        super.handleClientAction(id, buffer);
        if (id == 1) {
            this.isPressed = buffer.readBoolean();
            setPressedExecutor.apply(isPressed);
        }
    }

}
