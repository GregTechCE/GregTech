package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LabelWidget extends Widget {

    protected int xPosition;
    protected int yPosition;

    protected String text;
    private int color;

    public LabelWidget(int xPosition, int yPosition, String text) {
        this(xPosition, yPosition, text, 0x404040);
    }

    public LabelWidget(int xPosition, int yPosition, String text, int color) {
        super(SLOT_DRAW_PRIORITY + 500);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.text = text;
        this.color = color;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY) {
        Minecraft.getMinecraft().fontRenderer.drawString(I18n.format(text), this.xPosition, this.yPosition, color);
    }

}
