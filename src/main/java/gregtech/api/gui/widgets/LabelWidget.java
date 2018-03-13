package gregtech.api.gui.widgets;

import com.sun.org.apache.xml.internal.security.utils.I18n;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LabelWidget<T extends IUIHolder> extends Widget<T> {

    protected int xPosition;
    protected int yPosition;

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
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        Minecraft.getMinecraft().fontRenderer.drawString(I18n.translate(text), this.xPosition, this.yPosition, color);
    }

}
