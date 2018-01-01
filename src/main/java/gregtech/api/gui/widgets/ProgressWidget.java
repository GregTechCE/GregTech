package gregtech.api.gui.widgets;

import gregtech.api.capability.internal.IWorkable;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProgressWidget<T extends IWorkable & IUIHolder> extends Widget<T> {

    protected final int xPosition;
    protected final int yPosition;

    protected final boolean vertical;

    protected float progress;

    private ResourceLocation onImageLocation;
    private ResourceLocation offImageLocation;

    public ProgressWidget(int xPosition, int yPosition) {
        this(xPosition, yPosition, false);
    }

    public ProgressWidget(int xPosition, int yPosition, boolean vertical) {
        super(SLOT_DRAW_PRIORITY - 200);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vertical = vertical; //TODO vertical ProgressWidgets
    }

    public ProgressWidget<T> setOnImageLocation(ResourceLocation onImageLocation) {
        this.onImageLocation = onImageLocation;
        return this;
    }

    public ProgressWidget<T> setOffImageLocation(ResourceLocation offImageLocation) {
        this.offImageLocation = offImageLocation;
        return this;
    }

    @Override
    public void initWidget() {
    }

    protected int getProgressScaled(int pixels) {
        int maxProgress = this.gui.holder.getMaxProgress();
        return maxProgress != 0 && progress != 0 ? ((int) progress) * pixels / maxProgress : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int guiLeft, int guiTop, float partialTicks, int mouseX, int mouseY) {
        drawInBackgroundInternal(guiLeft, guiTop, () -> {
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            textureManager.bindTexture(offImageLocation);
            Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, 20, 16, 20, 16);

            textureManager.bindTexture(onImageLocation);
            if (progress >= this.gui.holder.getMaxProgress()) {
                progress = 0;
            } else {
                progress += partialTicks;
            }
            Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, this.getProgressScaled(20), 16, 20, 16);
        });
    }

    @Override
    public void writeInitialSyncInfo(PacketBuffer buffer) {
        buffer.writeInt(this.gui.holder.getProgress());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void readInitialSyncInfo(PacketBuffer buffer) {
        progress = buffer.readInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void readUpdateInfo(PacketBuffer buffer) {
    }
}
