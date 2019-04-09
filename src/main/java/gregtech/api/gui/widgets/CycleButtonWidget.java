package gregtech.api.gui.widgets;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.SizedTextureArea;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.GTUtility;
import gregtech.api.util.function.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class CycleButtonWidget extends AbstractPositionedRectangleWidget {

    protected TextureArea buttonTexture = GuiTextures.VANILLA_BUTTON.getSubArea(0.0, 0.0, 1.0, 0.5);
    private String[] optionNames;
    private int textColor = 0xFFFFFF;
    private IntSupplier currentOptionSupplier;
    private IntConsumer setOptionExecutor;
    protected int currentOption;
    protected String tooltipHoverString;
    protected long hoverStartTime = -1L;
    protected boolean isMouseHovered;

    public CycleButtonWidget(int xPosition, int yPosition, int width, int height, String[] optionNames, IntSupplier currentOptionSupplier, IntConsumer setOptionExecutor) {
        super(xPosition, yPosition, width, height);
        this.optionNames = optionNames;
        this.currentOptionSupplier = currentOptionSupplier;
        this.setOptionExecutor = setOptionExecutor;
    }

    public <T extends Enum<T> & IStringSerializable> CycleButtonWidget(int xPosition, int yPosition, int width, int height, Class<T> enumClass, Supplier<T> supplier, Consumer<T> updater) {
        super(xPosition, yPosition, width, height);
        T[] enumConstantPool = enumClass.getEnumConstants();
        this.optionNames = GTUtility.mapToString(enumConstantPool, it -> ((IStringSerializable) it).getName());
        this.currentOptionSupplier = () -> supplier.get().ordinal();
        this.setOptionExecutor = (newIndex) -> updater.accept(enumConstantPool[newIndex]);
    }

    public CycleButtonWidget(int xPosition, int yPosition, int width, int height, BooleanSupplier supplier, BooleanConsumer updater, String... optionNames) {
        super(xPosition, yPosition, width, height);
        this.optionNames = optionNames;
        this.currentOptionSupplier = () -> supplier.getAsBoolean() ? 1 : 0;
        this.setOptionExecutor = (value) -> updater.apply(value >= 1);
    }

    public CycleButtonWidget setTooltipHoverString(String hoverString) {
        this.tooltipHoverString = hoverString;
        return this;
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
        if (buttonTexture instanceof SizedTextureArea) {
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
    public void drawInForeground(int mouseX, int mouseY) {
        boolean isHovered = isMouseOver(xPosition, yPosition, width, height, mouseX, mouseY);
        boolean wasHovered = isMouseHovered;
        if (isHovered && !wasHovered) {
            this.isMouseHovered = true;
            this.hoverStartTime = System.currentTimeMillis();
        } else if (!isHovered && wasHovered) {
            this.isMouseHovered = false;
            this.hoverStartTime = 0L;
        } else if (isHovered) {
            long timeSinceHover = System.currentTimeMillis() - hoverStartTime;
            if (timeSinceHover > 1000L && tooltipHoverString != null) {
                List<String> hoverList = Arrays.asList(I18n.format(tooltipHoverString).split("/n"));
                drawHoveringText(ItemStack.EMPTY, hoverList, 300, mouseX, mouseY);
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (currentOptionSupplier.getAsInt() != currentOption) {
            this.currentOption = currentOptionSupplier.getAsInt();
            writeUpdateInfo(1, buf -> buf.writeVarInt(currentOption));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.currentOption = buffer.readVarInt();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (isMouseOver(xPosition, yPosition, width, height, mouseX, mouseY)) {
            this.currentOption = (currentOption + 1) % optionNames.length;
            writeClientAction(1, buf -> buf.writeVarInt(currentOption));
            playButtonClickSound();
            return true;
        }
        return false;
    }


    @Override
    public void handleClientAction(int id, PacketBuffer buffer) {
        super.handleClientAction(id, buffer);
        if (id == 1) {
            this.currentOption = MathHelper.clamp(buffer.readVarInt(), 0, optionNames.length);
            setOptionExecutor.accept(currentOption);
        }
    }

}
