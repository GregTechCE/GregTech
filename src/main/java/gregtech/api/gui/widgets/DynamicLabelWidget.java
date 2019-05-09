package gregtech.api.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

/**
 * Represents a label with text, dynamically obtained
 * from supplied getter in constructor
 * Note that this DOESN'T DO SYNC and calls getter on client side only
 * if you're looking for server-side controlled text field, see {@link gregtech.api.gui.widgets.AdvancedTextWidget}
 */
public class DynamicLabelWidget extends AbstractPositionedWidget {

    protected Supplier<String> textSupplier;
    private int color;

    public DynamicLabelWidget(int xPosition, int yPosition, Supplier<String> text) {
        this(xPosition, yPosition, text, 0x404040);
    }

    public DynamicLabelWidget(int xPosition, int yPosition, Supplier<String> text, int color) {
        super(xPosition, yPosition);
        this.textSupplier = text;
        this.color = color;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        String[] split = textSupplier.get().split("\n");
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        for (int i = 0; i < split.length; i++) {
            fontRenderer.drawString(split[i], this.xPosition, this.yPosition + (i * (fontRenderer.FONT_HEIGHT + 2)), color);
        }
    }

}
