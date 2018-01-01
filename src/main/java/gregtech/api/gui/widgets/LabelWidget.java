package gregtech.api.gui.widgets;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LabelWidget<T extends IUIHolder> extends Widget<T> {

    protected int xPosition;
    protected int yPosition;

    protected boolean xCentered; // todo
    protected boolean yCentered;

    protected String text;
    private int color;

    public LabelWidget(int xPosition, int yPosition, String text) {
        this(xPosition, yPosition, text, 0x404040);
    }

    public LabelWidget(int xPosition, int yPosition, String text, int color) {
        super(SLOT_DRAW_PRIORITY + 200);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.text = text;
        this.color = color;
    }

    @Override
    public void initWidget() {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        Minecraft.getMinecraft().fontRenderer.drawString(text, this.xPosition, this.yPosition, color);
    }

    @Override
    public void writeInitialSyncInfo(PacketBuffer buffer) {
    }

    @Override
    public void readInitialSyncInfo(PacketBuffer buffer) {
    }

    @Override
    public void readUpdateInfo(PacketBuffer buffer) {
    }
}
