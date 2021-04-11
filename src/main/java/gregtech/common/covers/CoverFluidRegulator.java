package gregtech.common.covers;

import gregtech.api.GTValues;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.function.Predicate;

public class CoverFluidRegulator extends CoverPump {

    protected TransferMode transferMode;
    protected int keepAmount = 0;
    protected int supplyAmount = 0;

    public CoverFluidRegulator(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide, tier, mbPerTick);
        this.transferMode = TransferMode.TRANSFER_ANY;
    }

    @Override
    protected int doTransferFluids(int transferLimit) {
        BlockPos.PooledMutableBlockPos blockPos = BlockPos.PooledMutableBlockPos.retain();
        blockPos.setPos(coverHolder.getPos()).move(attachedSide);
        TileEntity tileEntity = coverHolder.getWorld().getTileEntity(blockPos);
        blockPos.release();
        IFluidHandler fluidHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide.getOpposite());
        IFluidHandler myFluidHandler = coverHolder.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, attachedSide);
        if (fluidHandler == null || myFluidHandler == null) {
            return 0;
        }
        IFluidHandler sourceHandler;
        IFluidHandler destHandler;

        if (pumpMode == PumpMode.IMPORT) {
            sourceHandler = fluidHandler;
            destHandler = myFluidHandler;
        } else if (pumpMode == PumpMode.EXPORT) {
            sourceHandler = myFluidHandler;
            destHandler = fluidHandler;
        } else {
            return 0;
        }
        switch (transferMode) {
            case TRANSFER_ANY: return doTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
            case KEEP_EXACT: return doKeepExact(transferLimit, sourceHandler, destHandler, fluidFilter::testFluidStack, this.keepAmount);
            case TRANSFER_EXACT: return doTransferExact(transferLimit, sourceHandler, destHandler, fluidFilter::testFluidStack, this.supplyAmount);
            default: return 0;
        }
    }

    protected int doTransferExact(int transferLimit, IFluidHandler sourceHandler, IFluidHandler destHandler, Predicate<FluidStack> fluidFilter, int supplyAmount) {
        int fluidLeftToTransfer = transferLimit;
        for (IFluidTankProperties tankProperties : sourceHandler.getTankProperties()) {
            if (fluidLeftToTransfer < supplyAmount)
                break;
            FluidStack sourceFluid = tankProperties.getContents();
            if (sourceFluid == null || sourceFluid.amount == 0 || !fluidFilter.test(sourceFluid)) continue;
            sourceFluid.amount = supplyAmount;
            sourceFluid = sourceHandler.drain(sourceFluid, false);
            if (sourceFluid == null || sourceFluid.amount != supplyAmount) continue;
            int canInsertAmount = destHandler.fill(sourceFluid, false);
            if (canInsertAmount == supplyAmount) {
                sourceFluid = sourceHandler.drain(sourceFluid, true);
                if (sourceFluid != null && sourceFluid.amount > 0) {
                    destHandler.fill(sourceFluid, true);

                    fluidLeftToTransfer -= sourceFluid.amount;
                    if (fluidLeftToTransfer == 0) break;
                }
            }
        }
        return transferLimit - fluidLeftToTransfer;
    }

    protected int doKeepExact(int transferLimit, IFluidHandler sourceHandler, IFluidHandler destHandler, Predicate<FluidStack> fluidFilter, int keepAmount) {
        int fluidLeftToTransfer = transferLimit;
        for (IFluidTankProperties tankProperties : sourceHandler.getTankProperties()) {
            FluidStack sourceFluid = tankProperties.getContents();
            if (sourceFluid == null || sourceFluid.amount == 0 || !fluidFilter.test(sourceFluid)) continue;
            sourceFluid.amount = keepAmount;
            FluidStack destFluid = destHandler.drain(sourceFluid, false);
            int amountToDrainAndFill;
            //no fluid in destination
            if (destFluid == null) {
                amountToDrainAndFill = Math.min(keepAmount, fluidLeftToTransfer);
            //if the amount of fluid in the destination is sufficient or the destinations fluid isnt equal to the sources
            //how to check if destHandler is full?
            } else if (destFluid.amount >= keepAmount || !destFluid.isFluidEqual(sourceFluid)) {
                continue;
            } else {
            //if keepAmount is larger than the transferLimit we will have to stock it over several ticks (seconds?)
                amountToDrainAndFill = Math.min(keepAmount - destFluid.amount, fluidLeftToTransfer);
            }
            sourceFluid.amount = amountToDrainAndFill;
            sourceFluid = sourceHandler.drain(sourceFluid, false);
            if (sourceFluid == null || sourceFluid.amount != amountToDrainAndFill) continue;
            int canInsertAmount = destHandler.fill(sourceFluid, false);
            if (canInsertAmount == amountToDrainAndFill) {
                sourceFluid = sourceHandler.drain(sourceFluid, true);
                if (sourceFluid != null && sourceFluid.amount > 0) {
                    destHandler.fill(sourceFluid, true);

                    fluidLeftToTransfer -= sourceFluid.amount;
                    if (fluidLeftToTransfer == 0) break;
                }
            }
        }
        return transferLimit - fluidLeftToTransfer;
    }

    public void setTransferMode(TransferMode transferMode) {
        this.transferMode = transferMode;
        this.coverHolder.markDirty();
    }

    public TransferMode getTransferMode() {
        return transferMode;
    }

    private boolean checkTransferMode() {
        return this.transferMode == TransferMode.TRANSFER_EXACT || this.transferMode == TransferMode.KEEP_EXACT;
    }

    private String getTransferSizeString() {
        int val;
        switch (transferMode) {
            case KEEP_EXACT:
                val = keepAmount;
                break;
            case TRANSFER_EXACT:
                val = supplyAmount;
                break;
            default: val = -1;
        }
        if (this.bucketMode == BucketMode.BUCKET) {
            val /= 1000;
        }
        return val == -1 ? "" : Integer.toString(val);
    }


    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup filterGroup = new WidgetGroup();
        filterGroup.addWidget(new CycleButtonWidget(88, 63, 75, 18,
                TransferMode.class, this::getTransferMode, this::setTransferMode)
                .setTooltipHoverString("cover.fluid_regulator.transfer_mode.description"));

        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, "cover.fluid_regulator.title", GTValues.VN[tier]));

        ServerWidgetGroup stackSizeGroup = new ServerWidgetGroup(this::checkTransferMode);
        stackSizeGroup.addWidget(new ClickButtonWidget(88, 84, 18, 18, "-1", data -> adjustTransferSize(data.isCtrlClick ? -100 : data.isShiftClick ? -10 : -1)));
        stackSizeGroup.addWidget(new ClickButtonWidget(144, 84, 18, 18, "+1", data -> adjustTransferSize(data.isCtrlClick ? 100 : data.isShiftClick ? +10 : +1)));
        stackSizeGroup.addWidget(new ImageWidget(108, 84, 34, 18, GuiTextures.DISPLAY));
        stackSizeGroup.addWidget(new SimpleTextWidget(125, 93, "", 0xFFFFFF,
                this::getTransferSizeString));

        primaryGroup.addWidget(new ClickButtonWidget(10, 20, 34, 18, "-100", data -> adjustTransferRate(data.isShiftClick ? -500 : -100)));
        primaryGroup.addWidget(new ClickButtonWidget(128, 20, 34, 18, "+100", data -> adjustTransferRate(data.isShiftClick ? +500 : +100)));
        primaryGroup.addWidget(new ClickButtonWidget(45, 20, 23, 18, "-10", data -> adjustTransferRate(data.isShiftClick ? -50 : -10)));
        primaryGroup.addWidget(new ClickButtonWidget(105, 20, 23, 18, "+10", data -> adjustTransferRate(data.isShiftClick ? +50 : +10)));
        primaryGroup.addWidget(new ClickButtonWidget(68, 20, 18, 18, "-1", data -> adjustTransferRate(data.isShiftClick ? -5 : -1)));
        primaryGroup.addWidget(new ClickButtonWidget(86, 20, 18, 18, "+1", data -> adjustTransferRate(data.isShiftClick ? +5 : +1)));
        primaryGroup.addWidget(new ImageWidget(10, 40, 120, 18, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new SimpleTextWidget(65, 50, "cover.pump.transfer_rate", 0xFFFFFF, () -> bucketMode == BucketMode.BUCKET ? Integer.toString(transferRate / 1000) : Integer.toString(transferRate)));
        primaryGroup.addWidget(new CycleButtonWidget(132, 40, 30, 18,
                BucketMode.class, this::getBucketMode, this::setBucketMode));

        primaryGroup.addWidget(new CycleButtonWidget(10, 63, 75, 18,
                PumpMode.class, this::getPumpMode, this::setPumpMode));

        primaryGroup.addWidget(new CycleButtonWidget(10, 160, 113, 18,
                ManualImportExportMode.class, this::getManualImportExportMode, this::setManualImportExportMode)
                .setTooltipHoverString("cover.universal.manual_import_export.mode.description"));

        this.fluidFilter.initUI(88, primaryGroup::addWidget);

        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 184 + 82)
                .widget(primaryGroup)
                .widget(filterGroup)
                .widget(stackSizeGroup)
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 8, 184)
                .build(this, player);
    }

    private void adjustTransferSize(int amount) {
        amount *= this.bucketMode == BucketMode.BUCKET ? 1000 : 1;
        switch(this.transferMode) {
            case TRANSFER_EXACT:
                this.supplyAmount = MathHelper.clamp(this.supplyAmount + amount, 0, this.transferRate);
            case KEEP_EXACT:
                this.keepAmount = MathHelper.clamp(this.keepAmount + amount, 0, Integer.MAX_VALUE);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferMode", transferMode.ordinal());
        tagCompound.setInteger("KeepAmount", keepAmount);
        tagCompound.setInteger("SupplyAmount", supplyAmount);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferMode = TransferMode.values()[tagCompound.getInteger("TransferMode")];
        this.keepAmount = tagCompound.getInteger("KeepAmount");
        this.supplyAmount = tagCompound.getInteger("SupplyAmouny");
    }

}
