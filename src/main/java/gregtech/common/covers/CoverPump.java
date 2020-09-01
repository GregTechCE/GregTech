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

public class CoverPump extends CoverBehavior implements CoverWithUI, ITickable, IControllable {

    public final int tier;
    public final int maxFluidTransferRate;
    protected final FluidFilterContainer fluidFilter;
    protected int percentageInTank;
    protected int transferRate;
    protected PumpMode pumpMode;
    protected boolean allowManualImportExport = false;
    protected int fluidLeftToTransferLastSecond;
    protected boolean isWorkingAllowed = true;
    protected BucketMode bucketMode;
    private CoverableFluidHandlerWrapper fluidHandlerWrapper;

    public CoverPump(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide);
        this.tier = tier;
        this.maxFluidTransferRate = mbPerTick;
        this.percentageInTank = 0;
        this.transferRate = mbPerTick;
        this.fluidLeftToTransferLastSecond = transferRate;
        this.pumpMode = PumpMode.EXPORT;
        this.bucketMode = BucketMode.MILLI_BUCKET;
        this.fluidFilter = new FluidFilterContainer(this);
    }

    protected void setPortionInTank(int percentage) {
        this.percentageInTank = percentage;
        coverHolder.markDirty();
    }

    protected void adjustPortionInTank(int percentage) {
        this.setPortionInTank(MathHelper.clamp(this.percentageInTank + percentage, 0, 100));
    }


    protected void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
        coverHolder.markDirty();
    }

    protected void adjustTransferRate(int amount) {
        amount *= this.bucketMode == BucketMode.BUCKET ? 1000 : 1;
        setTransferRate(MathHelper.clamp(transferRate + amount, 1, maxFluidTransferRate));
    }

    public void setPumpMode(PumpMode pumpMode) {
        this.pumpMode = pumpMode;
        coverHolder.markDirty();
    }

    public void setBucketMode(BucketMode bucketMode) {
        this.bucketMode = bucketMode;
        if (this.bucketMode == BucketMode.BUCKET)
            setTransferRate(transferRate / 1000 * 1000);
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

    protected int doTransferFluidsInternal(IFluidHandler coveredBlockFluidHandler, IFluidHandler connectionFluidHandler, int transferLimit) {
        switch (this.pumpMode) {
            case EXPORT: return doExportFluid(coveredBlockFluidHandler , connectionFluidHandler, transferLimit);
            case IMPORT: return doImportFluid(coveredBlockFluidHandler , connectionFluidHandler, transferLimit);
            default:
                return 0;
        }
    }

    protected int doImportFluid(IFluidHandler me, IFluidHandler source, int transferLimit) {
        int fluidLeftToTransfer = transferLimit;
        for (IFluidTankProperties sourceTankProperties : source.getTankProperties()) {
            if (fluidLeftToTransfer == 0) break;
            FluidStack currentFluid = sourceTankProperties.getContents();
            if (currentFluid == null || currentFluid.amount == 0 || !fluidFilter.testFluidStack(currentFluid)) continue;

            FluidStack canExtractFluid = source.drain(currentFluid, false);
            if (canExtractFluid == null || canExtractFluid.amount == 0) continue;

            int canInsertAmount = source.fill(canExtractFluid, false);



            int keptAmount = 0;
            int minCapacity = Integer.MAX_VALUE;
            for (IFluidTankProperties myTankProperties : me.getTankProperties()) {

                if (myTankProperties.getCapacity() < minCapacity){
                    minCapacity = myTankProperties.getCapacity();
                }
                if (myTankProperties.getContents() != null &&
                myTankProperties.getContents().getFluid() == canExtractFluid.getFluid()){
                    keptAmount += myTankProperties.getContents().amount;
                }

            }
            int  liquidDeltaLimitByPortion = Math.round((minCapacity * ((float) this.percentageInTank / 100)))
                - keptAmount ;
            int   finalTransferAmount  = Math.min(liquidDeltaLimitByPortion, Math.min(canInsertAmount , transferLimit));
            if (finalTransferAmount <= 0) continue;
            canExtractFluid.amount = finalTransferAmount;
            FluidStack fluidExtracted = source.drain(canExtractFluid, true);
            int actualFilled =   me.fill(fluidExtracted, true);
            fluidLeftToTransfer -= actualFilled;


        }
        return transferLimit - fluidLeftToTransfer;


    }

    protected int doExportFluid(IFluidHandler me, IFluidHandler dest, int transferLimit) {
        int fluidLeftToTransfer = transferLimit;

        for (IFluidTankProperties sourceTankProperties : me.getTankProperties()) {
            if (fluidLeftToTransfer == 0) break;

            FluidStack currentFluid = sourceTankProperties.getContents();
            if (currentFluid == null || currentFluid.amount == 0 || !fluidFilter.testFluidStack(currentFluid)) continue;

            currentFluid.amount = fluidLeftToTransfer;
            FluidStack canExtractFluid = me.drain(currentFluid, false);
            if (canExtractFluid == null || canExtractFluid.amount == 0) continue;

            int canInsertAmount = dest.fill(canExtractFluid, false);

            int liquidDeltaLimitFromPortion = sourceTankProperties.getContents().amount - Math.round((sourceTankProperties.getCapacity() * ((float) this.percentageInTank / 100)));

            int finalTransferAmount = Math.min(liquidDeltaLimitFromPortion, canInsertAmount);
            if (finalTransferAmount <= 0) continue;
            currentFluid.amount = finalTransferAmount;
            FluidStack fluidExtracted = me.drain(currentFluid, true);
            int actualFilled = dest.fill(fluidExtracted, true);
            fluidLeftToTransfer -= actualFilled;
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

        primaryGroup.addWidget(new ClickButtonWidget(10, 20, 34, 18, "-100", data -> adjustTransferRate(data.isShiftClick ? -500 : -100)));
        primaryGroup.addWidget(new ClickButtonWidget(128, 20, 34, 18, "+100", data -> adjustTransferRate(data.isShiftClick ? +500 : +100)));
        primaryGroup.addWidget(new ClickButtonWidget(45, 20, 23, 18, "-10", data -> adjustTransferRate(data.isShiftClick ? -50 : -10)));
        primaryGroup.addWidget(new ClickButtonWidget(105, 20, 23, 18, "+10", data -> adjustTransferRate(data.isShiftClick ? +50 : +10)));
        primaryGroup.addWidget(new ClickButtonWidget(68, 20, 18, 18, "-1", data -> adjustTransferRate(data.isShiftClick ? -5 : -1)));
        primaryGroup.addWidget(new ClickButtonWidget(86, 20, 18, 18, "+1", data -> adjustTransferRate(data.isShiftClick ? +5 : +1)));
        primaryGroup.addWidget(new ImageWidget(10, 40, 120, 18, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new SimpleTextWidget(65, 50, "cover.pump.transfer_rate", 0xFFFFFF, () -> bucketMode == BucketMode.BUCKET ? Integer.toString(transferRate / 1000) : Integer.toString(transferRate)));
        primaryGroup.addWidget(new CycleButtonWidget(132, 40, 30, 18,
            GTUtility.mapToString(BucketMode.values(), it -> it.localeName),
            () -> bucketMode.ordinal(), newMode -> setBucketMode(BucketMode.values()[newMode])));


        primaryGroup.addWidget(new ClickButtonWidget(85, 63, 20, 18, "-10", data -> adjustPortionInTank(-10)));
        primaryGroup.addWidget(new ImageWidget(105, 63, 20, 18, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new ClickButtonWidget(125, 63, 20, 18, "+10", data -> adjustPortionInTank(10)));
        primaryGroup.addWidget(new SimpleTextWidget(115, 71, "", 0xFFFFFF, () -> this.percentageInTank + "%"));


        primaryGroup.addWidget(new CycleButtonWidget(10, 63, 75, 18,
            GTUtility.mapToString(ConveyorMode.values(), it -> it.localeName),
            () -> pumpMode.ordinal(), newMode -> setPumpMode(PumpMode.values()[newMode])));

        primaryGroup.addWidget(new ToggleButtonWidget(146, 63, 18, 18,
            this::isAllowManualImportExport, this::setAllowManualImportExport)
            .setTooltipText("cover.pump.manual_io")
            .setButtonTexture(GuiTextures.BUTTON_ALLOW_IMPORT_EXPORT));

        this.fluidFilter.initUI(88, primaryGroup::addWidget);

        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 170 + 82)
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
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
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
        tagCompound.setInteger("PercentageInTank", this.percentageInTank);
        tagCompound.setInteger("PumpMode", pumpMode.ordinal());
        tagCompound.setBoolean("WorkingAllowed", isWorkingAllowed);
        tagCompound.setBoolean("AllowManualIO", allowManualImportExport);
        tagCompound.setTag("Filter", fluidFilter.serializeNBT());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferRate = tagCompound.getInteger("TransferRate");
        this.percentageInTank = tagCompound.getInteger("PercentageInTank");
        this.pumpMode = PumpMode.values()[tagCompound.getInteger("PumpMode")];
        //LEGACY SAVE FORMAT SUPPORT
        if (tagCompound.hasKey("FluidFilter")) {
            this.fluidFilter.deserializeNBT(tagCompound);
        } else {
            this.fluidFilter.deserializeNBT(tagCompound.getCompoundTag("Filter"));
        }
        if (tagCompound.hasKey("WorkingAllowed")) {
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

    public enum BucketMode {
        BUCKET("cover.bucket.mode.bucket"),
        MILLI_BUCKET("cover.bucket.mode.milli_bucket");

        public final String localeName;

        BucketMode(String localeName) {
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
