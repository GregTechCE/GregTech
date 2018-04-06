package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.function.BooleanConsumer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.BooleanSupplier;

public class ButtonWidget extends Widget {

    protected int xPosition;
    protected int yPosition;
    protected int width, height;
    protected TextureArea buttonTexture;
    private BooleanSupplier isPressedCondition;
    private BooleanConsumer setPressedExecutor;
    private boolean isPressed;

    public ButtonWidget(int xPosition, int yPosition, int width, int height, TextureArea buttonTexture,
                        BooleanSupplier isPressedCondition, BooleanConsumer setPressedExecutor) {
        super(Widget.SLOT_DRAW_PRIORITY - 1);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.buttonTexture = buttonTexture;
        this.isPressedCondition = isPressedCondition;
        this.setPressedExecutor = setPressedExecutor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(float partialTicks, int mouseX, int mouseY) {
        if(!this.isPressed) {
            buttonTexture.drawSubArea(xPosition, yPosition, width, height, 0.0, 0.0, 1.0, 0.5);
        } else {
            buttonTexture.drawSubArea(xPosition, yPosition, width, height, 0.0, 0.5, 1.0, 0.5);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(isPressedCondition.getAsBoolean() != isPressed) {
            this.isPressed = isPressedCondition.getAsBoolean();
            writeUpdateInfo(1, buf -> buf.writeBoolean(isPressed));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if(id == 1) {
            this.isPressed = buffer.readBoolean();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if(mouseX >= xPosition && mouseY >= yPosition &&
            xPosition + width >= mouseX && yPosition + height >= mouseY) {
            writeClientAction(1, buf -> buf.writeBoolean(!isPressed));
        }
    }


    @Override
    public void handleClientAction(int id, PacketBuffer buffer) {
        super.handleClientAction(id, buffer);
        if(id == 1) {
            this.isPressed = buffer.readBoolean();
            writeUpdateInfo(1, buf -> buf.writeBoolean(isPressed));
            setPressedExecutor.apply(isPressed);
        }
    }

}
