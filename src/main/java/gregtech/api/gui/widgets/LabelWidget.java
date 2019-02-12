package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LabelWidget extends Widget {

    protected int xPosition;
    protected int yPosition;
    protected boolean xCentered = false;

    protected String text;
    protected Object[] formatting;
    private int color;

    public LabelWidget(int xPosition, int yPosition, String text, Object... formatting) {
        this(xPosition, yPosition, text, 0x404040, formatting);
    }

    public LabelWidget(int xPosition, int yPosition, String text, int color) {
        this(xPosition, yPosition, text, color, new Object[0]);
    }

    public LabelWidget setXCentered(boolean xCentered) {
        this.xCentered = xCentered;
        return this;
    }

    public LabelWidget(int xPosition, int yPosition, String text, int color, Object[] formatting) {
        super();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.text = text;
        this.color = color;
        this.formatting = formatting;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY) {
        String resultText = I18n.format(text, formatting);
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        if(!xCentered) {
            fontRenderer.drawString(resultText, this.xPosition, this.yPosition, color);
        } else {
            fontRenderer.drawString(resultText,
                xPosition - fontRenderer.getStringWidth(resultText) / 2,
                yPosition, color);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

}
