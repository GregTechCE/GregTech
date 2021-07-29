package gregtech.api.gui.widgets;

import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.util.LocalisationUtils;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;

/**
 * Simple one-line text widget with text synced and displayed
 * as the raw string from the server
 */
public class SimpleTextWidget extends Widget {

    protected String formatLocale;
    protected int color;
    protected Supplier<String> textSupplier;
    protected String lastText = "";

    public SimpleTextWidget(int xPosition, int yPosition, String formatLocale, int color, Supplier<String> textSupplier) {
        super(new Position(xPosition, yPosition), Size.ZERO);
        this.color = color;
        this.formatLocale = formatLocale;
        this.textSupplier = textSupplier;
    }

    public SimpleTextWidget(int xPosition, int yPosition, String formatLocale, Supplier<String> textSupplier) {
        this(xPosition, yPosition, formatLocale, 0x404040, textSupplier);
    }

    private void updateSize() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int stringWidth = fontRenderer.getStringWidth(lastText);
        setSize(new Size(stringWidth, fontRenderer.FONT_HEIGHT));
        if (uiAccess != null) {
            uiAccess.notifySizeChange();
        }
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY, IRenderContext context) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String text = formatLocale.isEmpty() ? (LocalisationUtils.hasKey(lastText) ? LocalisationUtils.format(lastText) : lastText) : LocalisationUtils.format(formatLocale, lastText);
        Position position = getPosition();
        fontRenderer.drawString(text,
            position.x - fontRenderer.getStringWidth(text) / 2,
            position.y - fontRenderer.FONT_HEIGHT / 2, color);
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
            updateSize();
        }
    }
}
