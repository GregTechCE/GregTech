package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
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
public class AdvancedTextWidget extends Widget {
    protected int maxWidthLimit;

    protected Consumer<List<ITextComponent>> textSupplier;
    private ArrayList<ITextComponent> lastText = new ArrayList<>();
    private int color;

    public AdvancedTextWidget(int xPosition, int yPosition, Consumer<List<ITextComponent>> text, int color) {
        super(new Position(xPosition, yPosition), Size.ZERO);
        this.textSupplier = text;
        this.color = color;
    }

    public AdvancedTextWidget setMaxWidthLimit(int maxWidthLimit) {
        this.maxWidthLimit = maxWidthLimit;
        if (isClientSide()) {
            updateSize();
        }
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

    private String[] getResultText() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<ITextComponent> cutText;
        if (maxWidthLimit > 0) {
            cutText = lastText.stream()
                .flatMap(c -> GuiUtilRenderComponents.splitText(c, maxWidthLimit, fontRenderer, true, true).stream())
                .collect(Collectors.toList());
        } else {
            cutText = lastText;
        }
        return cutText.stream()
            .map(ITextComponent::getFormattedText)
            .toArray(String[]::new);
    }

    private void updateSize() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String[] resultText = getResultText();
        int maxStringWidth = 0;
        int totalHeight = 0;
        for (String string : resultText) {
            maxStringWidth = Math.max(maxStringWidth, fontRenderer.getStringWidth(string));
            totalHeight += fontRenderer.FONT_HEIGHT + 2;
        }
        totalHeight -= 2;
        setSize(new Size(maxStringWidth, totalHeight));
        if (uiAccess != null) {
            uiAccess.notifySizeChange();
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
                updateSize();
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String[] resultText = getResultText();
        Position position = getPosition();
        for (int i = 0; i < resultText.length; i++) {
            fontRenderer.drawString(resultText[i], position.x, position.y + (i * (fontRenderer.FONT_HEIGHT + 2)), color);
        }
    }
}
