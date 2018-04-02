package gregtech.api.gui.widgets;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.RenderUtil;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.io.IOException;

public class TankWidget<T extends IUIHolder> extends Widget<T> {

    public final IFluidTank fluidTank;

    private final int x, y, width, height;

    private TextureArea[] backgroundTexture;
    private TextureArea overlayTexture;

    private FluidStack lastFluidInTank;
    private int lastTankCapacity;

    public TankWidget(IFluidTank fluidTank, int x, int y, int width, int height) {
        super(SLOT_DRAW_PRIORITY + 200);
        this.fluidTank = fluidTank;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public TankWidget<T> setBackgroundTexture(TextureArea... backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
        return this;
    }

    public TankWidget<T> setOverlayTexture(TextureArea overlayTexture) {
        this.overlayTexture = overlayTexture;
        return this;
    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        if(backgroundTexture != null) {
            for(TextureArea textureArea : backgroundTexture) {
                textureArea.draw(x, y, width, height);
            }
        }
        if(lastFluidInTank != null) {
            GlStateManager.disableBlend();
            RenderUtil.drawFluidForGui(lastFluidInTank, fluidTank.getCapacity(), x + 1, y + 1, width - 1, height - 1);
            GlStateManager.enableBlend();
        }
        if(overlayTexture != null) {
            overlayTexture.draw(x, y, width, height);
        }
    }

    @Override
    public void detectAndSendChanges() {
        FluidStack fluidStack = fluidTank.getFluid();
        if(fluidTank.getCapacity() != lastTankCapacity) {
            this.lastTankCapacity = fluidTank.getCapacity();
            writeUpdateInfo(0, buffer -> buffer.writeInt(lastTankCapacity));
        }
        if(fluidStack == null && lastFluidInTank != null) {
            this.lastFluidInTank = null;
            writeUpdateInfo(1, buffer -> {});
        } else if(fluidStack != null) {
            if(!fluidStack.isFluidEqual(lastFluidInTank)) {
                this.lastFluidInTank = fluidStack.copy();
                NBTTagCompound fluidStackTag = fluidStack.writeToNBT(new NBTTagCompound());
                writeUpdateInfo(2, buffer -> buffer.writeCompoundTag(fluidStackTag));
            } else if(fluidStack.amount != lastFluidInTank.amount) {
                this.lastFluidInTank.amount = fluidStack.amount;
                writeUpdateInfo(3, buffer -> buffer.writeInt(lastFluidInTank.amount));
            }
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        if(id == 0) {
            this.lastTankCapacity = buffer.readInt();
        } else if(id == 1) {
            this.lastFluidInTank = null;
        } else if(id == 2) {
            NBTTagCompound fluidStackTag;
            try {
                fluidStackTag = buffer.readCompoundTag();
            } catch (IOException ignored) {
                return;
            }
            this.lastFluidInTank = FluidStack.loadFluidStackFromNBT(fluidStackTag);
        } else if(id == 3 && lastFluidInTank != null) {
            this.lastFluidInTank.amount = buffer.readInt();
        }
    }
}
