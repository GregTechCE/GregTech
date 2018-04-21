package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a text-component based widget, which obtains
 * text from server and automatically synchronizes it with clients
 */
public class AdvancedTextWidget extends Widget {

    protected int xPosition;
    protected int yPosition;

    protected Consumer<List<ITextComponent>> textSupplier;
    private ArrayList<ITextComponent> lastText = new ArrayList<>();
    private ArrayList<ITextComponent> textBuffer = new ArrayList<>();
    private int color;

    public AdvancedTextWidget(int xPosition, int yPosition, Consumer<List<ITextComponent>> text, int color) {
        super(SLOT_DRAW_PRIORITY + 500);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.textSupplier = text;
        this.color = color;
    }

    @Override
    public void detectAndSendChanges() {
        textBuffer.clear();
        textSupplier.accept(textBuffer);
        if(!lastText.equals(textBuffer)) {
            this.lastText.clear();
            this.lastText.addAll(textBuffer);
            writeClientAction(1, buffer -> {
                buffer.writeInt(lastText.size());
                for(ITextComponent textComponent : lastText) {
                    buffer.writeString(ITextComponent.Serializer.componentToJson(textComponent));
                }
            });
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        if(id == 1) {
            this.lastText.clear();
            int count = buffer.readInt();
            for(int i = 0; i < count; i++) {
                String jsonText = buffer.readString(32767);
                this.lastText.add(ITextComponent.Serializer.jsonToComponent(jsonText));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        for(int i = 0; i < lastText.size(); i++) {
            fontRenderer.drawString(lastText.get(i).getFormattedText(), this.xPosition, this.yPosition + (i * (fontRenderer.FONT_HEIGHT + 2)), color);
        }
    }
}
