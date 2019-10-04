package gregtech.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.impl.FluidHandlerDelegate;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.covers.CoverConveyor.ConveyorMode;
import gregtech.common.covers.filter.FluidFilterContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class CoverPump extends CoverBehavior implements CoverWithUI, ITickable, IControllable {

    public final int tier;
    public final int maxFluidTransferRate;
    protected int transferRate;
    protected PumpMode pumpMode;
    protected boolean allowManualImportExport = false;
    protected int fluidLeftToTransferLastSecond;
    private CoverableFluidHandlerWrapper fluidHandlerWrapper;
    protected boolean isWorkingAllowed = true;
    protected final FluidFilterContainer fluidFilter;

    public CoverPump(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide);
        this.tier = tier;
        this.maxFluidTransferRate = mbPerTick;
        this.transferRate = mbPerTick;
        this.fluidLeftToTransferLastSecond = transferRate;
        this.pumpMode = PumpMode.EXPORT;
        this.fluidFilter = new FluidFilterContainer(this);
    }

    protected void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
        coverHolder.markDirty();
    }

    protected void adjustTransferRate(int amount) {
        setTransferRate(MathHelper.clamp(transferRate + amount, 1, maxFluidTransferRate));
    }

    public void setPumpMode(PumpMode pumpMode) {
        this.pumpMode = pumpMode;
        coverHolder.markDirty();
    }

    @Override
    public void update() {
        long timer = coverHolder.getTimer();
        if (isWorkingAllowed && fluidLeftToTransferLastSecond > 0) {
            this.fluidLeftToTransferLastSecond -= doTransferFluids(fluidLeftToTransferLastSecond);
        }
        if (timer % 20 == 0) {
            this.fluidLeftToTransferLastSecond = transferRate;
        }
    }

    protected int doTransferFluids(int transferLimit) {
        PooledMutableBlockPos blockPos = PooledMutableBlockPos.retain();
        blockPos.setPos(coverHolder.getPos()).move(attachedSide);
        TileEntity tileEntity = coverHolder.getWorld().getTileEntity(blockPos);
        blockPos.release();
        IFluidHandler fluidHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide.getOpposite());
        IFluidHandler myFluidHandler = coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide);
        if (fluidHandler == null || myFluidHandler == null) {
            return 0;
        }
        return doTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
    }

    protected int doTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit) {
        if (pumpMode == PumpMode.IMPORT) {
            return moveHandlerFluids(fluidHandler, myFluidHandler, transferLimit, fluidFilter::testFluidStack);
        } else if (pumpMode == PumpMode.EXPORT) {
            return moveHandlerFluids(myFluidHandler, fluidHandler, transferLimit, fluidFilter::testFluidStack);
        }
        return 0;
    }

    public static int moveHandlerFluids(IFluidHandler sourceHandler, IFluidHandler destHandler, int transferLimit, Predicate<FluidStack> fluidFilter) {
        int fluidLeftToTransfer = transferLimit;
        for (IFluidTankProperties tankProperties : sourceHandler.getTankProperties()) {
            FluidStack currentFluid = tankProperties.getContents();
            if (currentFluid == null || currentFluid.amount == 0 || !fluidFilter.test(currentFluid)) continue;
            currentFluid.amount = fluidLeftToTransfer;
            FluidStack fluidStack = sourceHandler.drain(currentFluid, false);
            if (fluidStack == null || fluidStack.amount == 0) continue;
            int canInsertAmount = destHandler.fill(fluidStack, false);
            if (canInsertAmount > 0) {
                fluidStack.amount = canInsertAmount;
                fluidStack = sourceHandler.drain(fluidStack, true);
                if (fluidStack != null && fluidStack.amount > 0) {
                    destHandler.fill(fluidStack, true);

                    fluidLeftToTransfer -= fluidStack.amount;
                    if (fluidLeftToTransfer == 0) break;
                }
            }
        }
        return transferLimit - fluidLeftToTransfer;
    }

    protected boolean checkInputFluid(FluidStack fluidStack) {
        return fluidFilter.testFluidStack(fluidStack);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, "cover.pump.title", GTValues.VN[tier]));

        primaryGroup.addWidget(new ClickButtonWidget(10, 20, 20, 20, "-10", data -> adjustTransferRate(data.isShiftClick ? -100 : -10)));
        primaryGroup.addWidget(new ClickButtonWidget(146, 20, 20, 20, "+10", data -> adjustTransferRate(data.isShiftClick ? +100 : +10)));
        primaryGroup.addWidget(new ClickButtonWidget(30, 20, 20, 20, "-1", data -> adjustTransferRate(data.isShiftClick ? -5 : -1)));
        primaryGroup.addWidget(new ClickButtonWidget(126, 20, 20, 20, "+1", data -> adjustTransferRate(data.isShiftClick ? +5 : +1)));
        primaryGroup.addWidget(new ImageWidget(50, 20, 76, 20, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new SimpleTextWidget(88, 30, "cover.pump.transfer_rate", 0xFFFFFF, () -> Integer.toString(transferRate)));

        primaryGroup.addWidget(new CycleButtonWidget(10, 45, 75, 20,
            GTUtility.mapToString(ConveyorMode.values(), it -> it.localeName),
            () -> pumpMode.ordinal(), newMode -> setPumpMode(PumpMode.values()[newMode])));

        primaryGroup.addWidget(new ToggleButtonWidget(151, 45, 20, 20,
            this::isAllowManualImportExport, this::setAllowManualImportExport)
            .setTooltipText("cover.pump.manual_io")
            .setButtonTexture(GuiTextures.BUTTON_ALLOW_IMPORT_EXPORT));

        this.fluidFilter.initUI(70, primaryGroup::addWidget);

        return ModularUI.builder(GuiTextures.BACKGROUND_EXTENDED, 176, 170 + 82)
            .widget(primaryGroup)
            .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 8, 170)
            .build(this, player);
    }

    public boolean isAllowManualImportExport() {
        return allowManualImportExport;
    }

    public void setAllowManualImportExport(boolean allowManualImportExport) {
        this.allowManualImportExport = allowManualImportExport;
        markAsDirty();
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (!coverHolder.getWorld().isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide) != null;
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
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        Textures.PUMP_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            IFluidHandler delegate = (IFluidHandler) defaultValue;
            if (fluidHandlerWrapper == null || fluidHandlerWrapper.delegate != delegate) {
                this.fluidHandlerWrapper = new CoverableFluidHandlerWrapper(delegate);
            }
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandlerWrapper);
        }
        if(capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return defaultValue;
    }

    @Override
    public boolean isWorkingEnabled() {
        return isWorkingAllowed;
    }

    @Override
    public void setWorkingEnabled(boolean isActivationAllowed) {
        this.isWorkingAllowed = isActivationAllowed;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferRate", transferRate);
        tagCompound.setInteger("PumpMode", pumpMode.ordinal());
        tagCompound.setBoolean("WorkingAllowed", isWorkingAllowed);
        tagCompound.setBoolean("AllowManualIO", allowManualImportExport);
        tagCompound.setTag("Filter", fluidFilter.serializeNBT());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferRate = tagCompound.getInteger("TransferRate");
        this.pumpMode = PumpMode.values()[tagCompound.getInteger("PumpMode")];
        //LEGACY SAVE FORMAT SUPPORT
        if(tagCompound.hasKey("FluidFilter")) {
            this.fluidFilter.deserializeNBT(tagCompound);
        } else {
            this.fluidFilter.deserializeNBT(tagCompound.getCompoundTag("Filter"));
        }
        if(tagCompound.hasKey("WorkingAllowed")) {
            this.isWorkingAllowed = tagCompound.getBoolean("WorkingAllowed");
        }
        if (tagCompound.hasKey("AllowManualIO")) {
            this.allowManualImportExport = tagCompound.getBoolean("AllowManualIO");
        }
    }

    public enum PumpMode {
        IMPORT("cover.pump.mode.import"),
        EXPORT("cover.pump.mode.export");

        public final String localeName;

        PumpMode(String localeName) {
            this.localeName = localeName;
        }
    }

    private class CoverableFluidHandlerWrapper extends FluidHandlerDelegate {

        public CoverableFluidHandlerWrapper(IFluidHandler delegate) {
            super(delegate);
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (pumpMode == PumpMode.EXPORT && !allowManualImportExport) {
                return 0;
            }
            if (!checkInputFluid(resource)) {
                return 0;
            }
            return super.fill(resource, doFill);
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (pumpMode == PumpMode.IMPORT && !allowManualImportExport) {
                return null;
            }
            if (!checkInputFluid(resource)) {
                return null;
            }
            return super.drain(resource, doDrain);
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (pumpMode == PumpMode.IMPORT && !allowManualImportExport) {
                return null;
            }
            FluidStack result = super.drain(maxDrain, false);
            if (!checkInputFluid(result)) {
                return null;
            }
            if (doDrain) {
                super.drain(maxDrain, true);
            }
            return result;
        }
    }
}
