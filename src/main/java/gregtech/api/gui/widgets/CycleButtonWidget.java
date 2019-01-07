package gregtech.api.gui.widgets;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.SizedTextureArea;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class CycleButtonWidget extends Widget {

    protected int xPosition;
    protected int yPosition;
    protected int width, height;
    protected TextureArea buttonTexture = GuiTextures.VANILLA_BUTTON.getSubArea(0.0, 0.0, 1.0, 0.5);
    private String[] optionNames;
    private int textColor = 0xFFFFFF;
    private IntSupplier currentOptionSupplier;
    private IntConsumer setOptionExecutor;
    protected int currentOption;

    public CycleButtonWidget(int xPosition, int yPosition, int width, int height, String[] optionNames, IntSupplier currentOptionSupplier, IntConsumer setOptionExecutor) {
        super();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.optionNames = optionNames;
        this.currentOptionSupplier = currentOptionSupplier;
        this.setOptionExecutor = setOptionExecutor;
    }

    public CycleButtonWidget setButtonTexture(TextureArea texture) {
        this.buttonTexture = texture;
        return this;
    }

    public CycleButtonWidget setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY) {
        if(buttonTexture instanceof SizedTextureArea) {
            ((SizedTextureArea) buttonTexture).drawHorizontalCutSubArea(xPosition, yPosition, width, height, 0.0, 1.0);
        } else {
            buttonTexture.drawSubArea(xPosition, yPosition, width, height, 0.0, 0.0, 1.0, 1.0);
        }
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String text = I18n.format(optionNames[currentOption]);
        fontRenderer.drawString(text,
            xPosition + width / 2 - fontRenderer.getStringWidth(text) / 2,
            yPosition + height / 2 - fontRenderer.FONT_HEIGHT / 2, textColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(currentOptionSupplier.getAsInt() != currentOption) {
            this.currentOption = currentOptionSupplier.getAsInt();
            writeUpdateInfo(1, buf -> buf.writeVarInt(currentOption));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if(id == 1) {
            this.currentOption = buffer.readVarInt();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if(isMouseOver(xPosition, yPosition, width, height, mouseX, mouseY)) {
            this.currentOption = (currentOption + 1) % optionNames.length;
            writeClientAction(1, buf -> buf.writeVarInt(currentOption));
            playButtonClickSound();
        }
    }


    @Override
    public void handleClientAction(int id, PacketBuffer buffer) {
        super.handleClientAction(id, buffer);
        if(id == 1) {
            this.currentOption = MathHelper.clamp(buffer.readVarInt(), 0, optionNames.length);
            setOptionExecutor.accept(currentOption);
        }
    }

}
