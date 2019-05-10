package gregtech.api.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;

/**
 * Simple one-line text widget with text synced and displayed
 * as the raw string from the server
 */
public class SimpleTextWidget extends AbstractPositionedWidget {

    protected String formatLocale;
    protected int color;
    protected Supplier<String> textSupplier;
    protected String lastText = "";

    public SimpleTextWidget(int xPosition, int yPosition, String formatLocale, int color, Supplier<String> textSupplier) {
        super(xPosition, yPosition);
        this.color = color;
        this.formatLocale = formatLocale;
        this.textSupplier = textSupplier;
    }

    public SimpleTextWidget(int xPosition, int yPosition, String formatLocale, Supplier<String> textSupplier) {
        this(xPosition, yPosition, formatLocale, 0x404040, textSupplier);
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String text = formatLocale.isEmpty() ? (I18n.hasKey(lastText) ? I18n.format(lastText) : lastText) : I18n.format(formatLocale, lastText);
        fontRenderer.drawString(text,
            xPosition - fontRenderer.getStringWidth(text) / 2,
            yPosition - fontRenderer.FONT_HEIGHT / 2, color);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    @Override
    public void detectAndSendChanges() {
        if (!textSupplier.get().equals(lastText)) {
            this.lastText = textSupplier.get();
            writeUpdateInfo(1, buffer -> buffer.writeString(lastText));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        if (id == 1) {
            this.lastText = buffer.readString(Short.MAX_VALUE);
        }
    }
}
