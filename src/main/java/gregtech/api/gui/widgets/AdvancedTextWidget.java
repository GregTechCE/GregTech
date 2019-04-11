package gregtech.api.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents a text-component based widget, which obtains
 * text from server and automatically synchronizes it with clients
 */
public class AdvancedTextWidget extends AbstractPositionedWidget {
    protected int maxWidthLimit;

    protected Consumer<List<ITextComponent>> textSupplier;
    private ArrayList<ITextComponent> lastText = new ArrayList<>();
    private int color;

    public AdvancedTextWidget(int xPosition, int yPosition, Consumer<List<ITextComponent>> text, int color) {
        super(xPosition, yPosition);
        this.textSupplier = text;
        this.color = color;
    }

    public AdvancedTextWidget setMaxWidthLimit(int maxWidthLimit) {
        this.maxWidthLimit = maxWidthLimit;
        return this;
    }

    @Override
    public void detectAndSendChanges() {
        ArrayList<ITextComponent> textBuffer = new ArrayList<>();
        textSupplier.accept(textBuffer);
        if (!lastText.equals(textBuffer)) {
            this.lastText = textBuffer;
            writeUpdateInfo(1, buffer -> {
                buffer.writeVarInt(lastText.size());
                for (ITextComponent textComponent : lastText) {
                    buffer.writeString(ITextComponent.Serializer.componentToJson(textComponent));
                }
            });
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        if (id == 1) {
            this.lastText.clear();
            int count = buffer.readVarInt();
            for (int i = 0; i < count; i++) {
                String jsonText = buffer.readString(32767);
                this.lastText.add(ITextComponent.Serializer.jsonToComponent(jsonText));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<ITextComponent> cutText;
        if (maxWidthLimit > 0) {
            cutText = lastText.stream()
                .flatMap(c -> GuiUtilRenderComponents.splitText(c, maxWidthLimit, fontRenderer, true, true).stream())
                .collect(Collectors.toList());
        } else {
            cutText = lastText;
        }
        for (int i = 0; i < cutText.size(); i++) {
            fontRenderer.drawString(cutText.get(i).getFormattedText(), this.xPosition, this.yPosition + (i * (fontRenderer.FONT_HEIGHT + 2)), color);
        }
    }
}
