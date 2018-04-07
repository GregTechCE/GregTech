package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

public class DynamicLabelWidget extends Widget {

    protected int xPosition;
    protected int yPosition;

    protected Supplier<String> textSupplier;
    private int color;

    public DynamicLabelWidget(int xPosition, int yPosition, Supplier<String> text) {
        this(xPosition, yPosition, text, 0x404040);
    }

    public DynamicLabelWidget(int xPosition, int yPosition, Supplier<String> text, int color) {
        super(SLOT_DRAW_PRIORITY + 500);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.textSupplier = text;
        this.color = color;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        Minecraft.getMinecraft().fontRenderer.drawString(textSupplier.get(), this.xPosition, this.yPosition, color);
    }

}
