package gregtech.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IFluidVoiding;
import gregtech.api.capability.impl.FluidHandlerDelegate;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.common.covers.filter.FluidFilterContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.function.Predicate;

public class CoverFluidVoiding extends CoverBehavior implements CoverWithUI, IFluidVoiding {

    private FluidFilterContainer fluidFilter;
    protected FluidHandlerVoiding fluidHandler;
    private final int voidingAmount = Integer.MAX_VALUE;

    public CoverFluidVoiding(ICoverable coverable, EnumFacing attachedSide) {
        super(coverable, attachedSide);
        fluidFilter = new FluidFilterContainer(this);
    }

    @Override
    public int voidingAmount() {
        return voidingAmount;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide) != null;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        Textures.PUMP_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public void onRemoved() {
        NonNullList<ItemStack> drops = NonNullList.create();
        MetaTileEntity.clearInventory(drops, fluidFilter.getFilterInventory());
        for (ItemStack itemStack : drops) {
            Block.spawnAsEntity(coverHolder.getWorld(), coverHolder.getPos(), itemStack);
        }
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (!coverHolder.getWorld().isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean shouldCoverInteractWithOutputside() {
        return true;
    }

    @Override
    public Predicate<FluidStack> checkInputFluid() {
        return fluidStack -> fluidFilter.testFluidStack(fluidStack);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();

        primaryGroup.addWidget(new LabelWidget(10, 5, "cover.fluid_voiding.title"));
        fluidFilter.initUI(88, primaryGroup::addWidget);

        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 184 + 82)
            .widget(primaryGroup)
            .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 8, 184)
            .build(this, player);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            IFluidHandler delegate = (IFluidHandler) defaultValue;
            if (fluidHandler == null || fluidHandler.delegate != delegate) {
                this.fluidHandler = new FluidHandlerVoiding(delegate);
            }
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
        }
        else if (capability == GregtechCapabilities.CAPABILITY_FLUID_VOIDING) {
            return GregtechCapabilities.CAPABILITY_FLUID_VOIDING.cast(this);
        }
        return defaultValue;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("Filter", fluidFilter.serializeNBT());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        if (tagCompound.hasKey("FluidFilter")) {
            fluidFilter.deserializeNBT(tagCompound);
        } else {
            fluidFilter.deserializeNBT(tagCompound.getCompoundTag("Filter"));
        }

    }

    public class FluidHandlerVoiding extends FluidHandlerDelegate {

        public FluidHandlerVoiding(IFluidHandler delegate) {
            super(delegate);
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if(!checkInputFluid().test(resource)) {
                return 0;
            }

            return voidingAmount;
        }
    }

}
