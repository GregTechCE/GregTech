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
    protected final boolean direction; //false = down to up , true = up to down

    protected float progress;

    private ResourceLocation onImageLocation;
    private ResourceLocation offImageLocation;

    private int width;
    private int height;

    private int u = 0;
    private int v = 0;

    @Deprecated
    //For people who want to quickly setup ProcessWidgets, don't use this for actual build
    public ProgressWidget(int xPosition, int yPosition) {
        this(xPosition, yPosition, false, false);
    }
    //This is okay but the next constructor is preferred
    public ProgressWidget(int xPosition, int yPosition, boolean vertical) {
        this(xPosition, yPosition, vertical, false);
    }
    // This constructor allows the user to have full control over the ProcessWidget
    public ProgressWidget(int xPosition, int yPosition, boolean vertical, boolean direction) {
        super(SLOT_DRAW_PRIORITY - 200);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vertical = vertical;
        this.direction = direction;
    }

    public ProgressWidget<T> setFilledImageLocation(ResourceLocation onImageLocation) {//Filled progress bar texture location
        this.onImageLocation = onImageLocation;
        return this;
    }

    public ProgressWidget<T> setImageLocation(ResourceLocation offImageLocation) {//Empty progress bar texture location
        this.offImageLocation = offImageLocation;
        return this;
    }
    //This is required and minecraft will crash without it
    public ProgressWidget<T> setImageWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
    //This is optional as u and v are initialized to 0
    public ProgressWidget<T> setImageUV(int u, int v) {
        this.u = u;
        this.v = v;
        return this;
    }

    @Override
    public void initWidget() {
    }

    protected int getProgressScaled(int pixels) { //Find the amount of pixels to increment the filled texture by
        int maxProgress = this.gui.holder.getMaxProgress();
        return maxProgress != 0 && progress != 0 ? ((int) progress) * pixels / maxProgress : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int guiLeft, int guiTop, float partialTicks, int mouseX, int mouseY) {
        drawInBackgroundInternal(guiLeft, guiTop, () -> {
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            if (this.vertical){ //if it is vertical then reverse textures
                textureManager.bindTexture(onImageLocation);
            } else {
                textureManager.bindTexture(offImageLocation);
            }
            Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, u, v, width, height, width, height);

            if (this.vertical){//if it is vertical then reverse textures
                textureManager.bindTexture(offImageLocation);
            } else {
                textureManager.bindTexture(onImageLocation);
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


