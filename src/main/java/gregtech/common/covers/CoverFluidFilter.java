package gregtech.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.FluidHandlerDelegate;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.PhantomFluidWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class CoverFluidFilter extends CoverBehavior implements CoverWithUI {

    protected FluidStack[] fluidFilterSlots;
    protected FluidFilterMode filterMode;
    protected CoverFluidFilter.FilteredFluidHandler fluidHandler;

    public CoverFluidFilter(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
        this.filterMode = FluidFilterMode.FILTER_FILL;
        this.fluidFilterSlots = new FluidStack[9];
    }

    protected void setFilterMode(FluidFilterMode filterMode) {
        this.filterMode = filterMode;
        this.coverHolder.markDirty();
    }

    public boolean canAttach() {
        return this.coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this.attachedSide) != null;
    }

    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (!playerIn.world.isRemote) {
            this.openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup fluidFilterGroup = new WidgetGroup();
        fluidFilterGroup.addWidget(new LabelWidget(10, 5, "cover.fluid_filter.title"));
        fluidFilterGroup.addWidget(new CycleButtonWidget(10, 20, 110, 20,
            GTUtility.mapToString(FluidFilterMode.values(), (it) -> it.localeName), () -> this.filterMode.ordinal(),
            (newMode) -> this.setFilterMode(FluidFilterMode.values()[newMode])));
        for (int i = 0; i < 9; ++i) {
            int index = i;
            fluidFilterGroup.addWidget((new PhantomFluidWidget(10 + 18 * (i % 3), 46 + 18 * (i / 3), 18, 18,
                () -> this.fluidFilterSlots[index],
                (newFluid) -> this.fluidFilterSlots[index] = newFluid))
                .setBackgroundTexture(GuiTextures.SLOT));
        }
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 130)
            .widget(fluidFilterGroup)
            .bindPlayerHotbar(player.inventory, GuiTextures.SLOT, 8, 107)
            .build(this, player);
    }

    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.FLUID_FILTER_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            IFluidHandler delegate = (IFluidHandler) defaultValue;
            if (fluidHandler == null || fluidHandler.delegate != delegate) {
                this.fluidHandler = new CoverFluidFilter.FilteredFluidHandlerImpl(delegate);
            }
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
        }
        return defaultValue;
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("FilterMode", this.filterMode.ordinal());
        NBTTagList filterSlots = new NBTTagList();
        for (int i = 0; i < this.fluidFilterSlots.length; ++i) {
            FluidStack fluidStack = this.fluidFilterSlots[i];
            if (fluidStack != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                fluidStack.writeToNBT(stackTag);
                stackTag.setInteger("Slot", i);
                filterSlots.appendTag(stackTag);
            }
        }
        tagCompound.setTag("FluidFilter", filterSlots);
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.filterMode = FluidFilterMode.values()[tagCompound.getInteger("FilterMode")];
        NBTTagList filterSlots = tagCompound.getTagList("FluidFilter", 10);
        for (NBTBase nbtBase : filterSlots) {
            NBTTagCompound stackTag = (NBTTagCompound) nbtBase;
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(stackTag);
            this.fluidFilterSlots[stackTag.getInteger("Slot")] = fluidStack;
        }
    }

    public static boolean checkInputFluid(FluidStack[] fluidFilterSlots, FluidStack fluidStack) {
        for (FluidStack filterStack : fluidFilterSlots) {
            if (filterStack != null && filterStack.isFluidEqual(fluidStack)) {
                return true;
            }
        }
        return false;
    }

    public abstract static class FilteredFluidHandler extends FluidHandlerDelegate {

        public FilteredFluidHandler(IFluidHandler delegate) {
            super(delegate);
        }

        public int fill(FluidStack resource, boolean doFill) {
            FluidFilterMode filterMode = getFilterMode();
            if (filterMode == FluidFilterMode.FILTER_DRAIN) {
                return 0;
            }
            if(!checkInputFluid(getFilterSlots(), resource)) {
                return 0;
            }
            return super.fill(resource, doFill);
        }

        @Nullable
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            FluidFilterMode filterMode = getFilterMode();
            if (filterMode == FluidFilterMode.FILTER_DRAIN) {
                return null;
            }
            if(!checkInputFluid(getFilterSlots(), resource)) {
                return null;
            }
            return super.drain(resource, doDrain);
        }

        @Nullable
        public FluidStack drain(int maxDrain, boolean doDrain) {
            FluidFilterMode filterMode = getFilterMode();
            if (filterMode == FluidFilterMode.FILTER_FILL) {
                return null;
            }
            FluidStack result = super.drain(maxDrain, false);
            if (!checkInputFluid(getFilterSlots(), result)) {
                return null;
            }
            if (doDrain) {
                super.drain(maxDrain, true);
            }
            return result;
        }

        public abstract FluidFilterMode getFilterMode();

        public abstract FluidStack[] getFilterSlots();

    }

    private class FilteredFluidHandlerImpl extends CoverFluidFilter.FilteredFluidHandler {

        public FilteredFluidHandlerImpl(IFluidHandler delegate) {
            super(delegate);
        }

        public FluidFilterMode getFilterMode() {
            return CoverFluidFilter.this.filterMode;
        }

        public FluidStack[] getFilterSlots() {
            return CoverFluidFilter.this.fluidFilterSlots;
        }
    }

}
