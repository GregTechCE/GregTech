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

// this implementation requires maxProgress to be sent to client every time its value is updated
public class ProgressWidget<T extends IWorkable & IUIHolder> extends Widget<T> {

    protected final int xPosition;
    protected final int yPosition;

    protected final boolean vertical;
    protected final boolean direction; //false = down to up , true = up to down, not applied to horizontal

    protected float progress;

    private ResourceLocation filledImageLocation;
    private ResourceLocation imageLocation;

    private int width = 20;
    private int height = 16;

    private int u = 0;
    private int v = 0;

    public ProgressWidget(int xPosition, int yPosition) {

        this(xPosition, yPosition, false, false);
    }
    public ProgressWidget(int xPosition, int yPosition, boolean vertical) {
        this(xPosition, yPosition, vertical, false);
    }

    public ProgressWidget(int xPosition, int yPosition, boolean vertical, boolean direction) {
        super(SLOT_DRAW_PRIORITY - 200);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vertical = vertical;
        this.direction = direction;
    }

    public ProgressWidget<T> setFilledImageLocation(ResourceLocation onImageLocation) {//Filled progress bar texture location
        this.filledImageLocation = onImageLocation;
        return this;
    }

    public ProgressWidget<T> setImageLocation(ResourceLocation offImageLocation) {//Empty progress bar texture location
        this.imageLocation = offImageLocation;
        return this;
    }

    public ProgressWidget<T> setImageWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ProgressWidget<T> setImageUV(int u, int v) {
        this.u = u;
        this.v = v;
        return this;
    }

    @Override
    public void initWidget() {
    }

    //Find the amount of pixels to increment the filled texture by
    protected int getProgressScaled(int pixels) {
        int maxProgress = this.gui.holder.getMaxProgress();
        return maxProgress != 0 && progress != 0 ? ((int) progress) * pixels / maxProgress : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int guiLeft, int guiTop, float partialTicks, int mouseX, int mouseY) {
        drawInBackgroundInternal(guiLeft, guiTop, () -> {
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            if (this.vertical){ //if it is vertical then reverse textures
                if (!direction) {
                    textureManager.bindTexture(filledImageLocation);
                }
                else {
                    textureManager.bindTexture(imageLocation);
                }
            } else {
                textureManager.bindTexture(imageLocation);
            }
            Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, u, v, width, height, width, height);

            if (this.vertical){//if it is vertical then reverse textures
                if (!direction) {
                    textureManager.bindTexture(imageLocation);
                }
                else {
                    textureManager.bindTexture(filledImageLocation);
                }
            } else {
                textureManager.bindTexture(filledImageLocation);
            }

            if (progress >= this.gui.holder.getMaxProgress()) {
                progress = 0;
            } else {
                progress += partialTicks;
            }

            if (!this.vertical) {
                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, u, v, this.getProgressScaled(width), height, width, height);
            } else if (!direction) {
                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, u, v, width, height - this.getProgressScaled(height), width, height);
            } else {
                Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, u, v, width, this.getProgressScaled(height), width, height);
            }
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

