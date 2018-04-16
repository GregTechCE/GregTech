package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.RenderUtil;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TankWidget extends Widget {

    public final IFluidTank fluidTank;

    public final int x, y, width, height;
    private int fluidRenderOffset = 1;
    private boolean hideTooltip;
    private boolean alwaysShowFull;

    private TextureArea[] backgroundTexture;
    private TextureArea overlayTexture;

    private FluidStack lastFluidInTank;
    private int lastTankCapacity;

    public TankWidget(IFluidTank fluidTank, int x, int y, int width, int height) {
        super(SLOT_DRAW_PRIORITY + 100);
        this.fluidTank = fluidTank;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public TankWidget setHideTooltip(boolean hideTooltip) {
        this.hideTooltip = hideTooltip;
        return this;
    }

    public TankWidget setAlwaysShowFull(boolean alwaysShowFull) {
        this.alwaysShowFull = alwaysShowFull;
        return this;
    }

    public TankWidget setBackgroundTexture(TextureArea... backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
        return this;
    }

    public TankWidget setOverlayTexture(TextureArea overlayTexture) {
        this.overlayTexture = overlayTexture;
        return this;
    }

    public TankWidget setFluidRenderOffset(int fluidRenderOffset) {
        this.fluidRenderOffset = fluidRenderOffset;
        return this;
    }

    public FluidStack getLastFluidInTank() {
        return lastFluidInTank;
    }

    public String getFormattedFluidAmount() {
        return String.format("%,d", lastFluidInTank == null ? 0 : lastFluidInTank.amount);
    }

    public String getFluidLocalizedName() {
        return lastFluidInTank == null ? "" : lastFluidInTank.getLocalizedName();
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY) {
        if(backgroundTexture != null) {
            for(TextureArea textureArea : backgroundTexture) {
                textureArea.draw(x, y, width, height);
            }
        }
        //do not draw fluids if they are handled by JEI - it draws them itself
        if(lastFluidInTank != null && !gui.isJEIHandled) {
            GlStateManager.disableBlend();
            RenderUtil.drawFluidForGui(lastFluidInTank, alwaysShowFull ? lastFluidInTank.amount : lastTankCapacity,
                x + fluidRenderOffset, y + fluidRenderOffset,
                width - fluidRenderOffset, height - fluidRenderOffset);
            int bucketsAmount = lastFluidInTank.amount / 1000;
            if(alwaysShowFull && bucketsAmount > 0) {
                String s = String.valueOf(bucketsAmount);
                FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
                fontRenderer.drawStringWithShadow(s, x + 1 + width - 2 - fontRenderer.getStringWidth(s), y + (height / 3) + 3, 0xFFFFFF);
            }
            GlStateManager.enableBlend();
        }
        if(overlayTexture != null) {
            overlayTexture.draw(x, y, width, height);
        }

    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        if(!hideTooltip && !gui.isJEIHandled && isMouseOver(x, y, width, height, mouseX, mouseY)) {
            List<String> tooltips = new ArrayList<>();
            if(lastFluidInTank != null) {
                Fluid fluid = lastFluidInTank.getFluid();
                tooltips.add(fluid.getLocalizedName(lastFluidInTank));
                tooltips.add(I18n.format("gregtech.fluid.amount", lastFluidInTank.amount, lastTankCapacity));
                tooltips.add(I18n.format("gregtech.fluid.temperature", fluid.getTemperature(lastFluidInTank)));
                tooltips.add(I18n.format(fluid.isGaseous(lastFluidInTank) ? "gregtech.fluid.state_gas" : "gregtech.fluid.state_liquid"));
            } else {
                tooltips.add(I18n.format("gregtech.fluid.empty"));
                tooltips.add(I18n.format("gregtech.fluid.amount", 0, lastTankCapacity));
            }
            GuiUtils.drawHoveringText(tooltips, mouseX, mouseY, gui.width, gui.height, -1, Minecraft.getMinecraft().fontRenderer);
            GlStateManager.color(1.0f, 1.0f, 1.0f);
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
