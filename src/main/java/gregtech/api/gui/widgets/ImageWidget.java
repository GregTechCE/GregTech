package gregtech.api.gui.widgets;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ImageWidget<T extends IUIHolder> extends Widget<T> {

    protected final int xPosition;
    protected final int yPosition;

    private ResourceLocation imageLocation;

    private int width = 18;
    private int height = 18;

    private int u = 0;
    private int v = 0;

    public ImageWidget(int xPosition, int yPosition) {
        super(SLOT_DRAW_PRIORITY + 200);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }


    public ImageWidget<T> setImageLocation(ResourceLocation imageLocation) {
        this.imageLocation = imageLocation;
        return this;
    }

    public ImageWidget<T> setImageWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ImageWidget<T> setImageUV(int u, int v) {
        this.u = u;
        this.v = v;
        return this;
    }

    @Override
    public void initWidget() {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(imageLocation);
            Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, u, v, width, height, width, height);
    }

    @Override
    public void writeInitialSyncInfo(PacketBuffer buffer) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void readInitialSyncInfo(PacketBuffer buffer) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void readUpdateInfo(PacketBuffer buffer) {
    }
}

