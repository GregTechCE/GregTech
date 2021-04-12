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
    private static String supplyKey = "SupplyAmount";
    private static String keepKey = "KeepAmount";

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
    public void setBucketMode(BucketMode bucketMode){
        super.setBucketMode(bucketMode);
        if (this.bucketMode == BucketMode.BUCKET)
            setKeepAmount(keepAmount / 1000 * 1000);
            setSupplyAmount(supplyAmount / 1000 * 1000);
    }

    private void adjustTransferSize(int amount) {
        amount *= this.bucketMode == BucketMode.BUCKET ? 1000 : 1;
        switch(this.transferMode) {
            case TRANSFER_EXACT:
                setSupplyAmount(MathHelper.clamp(this.supplyAmount + amount, 0, this.transferRate));
            case KEEP_EXACT:
                setKeepAmount(MathHelper.clamp(this.keepAmount + amount, 0, Integer.MAX_VALUE));
        }
    }

    private void setKeepAmount(int keepAmount) {
        this.keepAmount = keepAmount;
        coverHolder.markDirty();
    }

    private void setSupplyAmount(int supplyAmount) {
        this.supplyAmount = supplyAmount;
        coverHolder.markDirty();
    }

    @Override
    protected String getUITitle() {
        return "cover.fluid_regulator.title";
    }

    @Override
    protected ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player) {
        WidgetGroup filterGroup = new WidgetGroup();
        filterGroup.addWidget(new CycleButtonWidget(88, 63, 75, 18,
                TransferMode.class, this::getTransferMode, this::setTransferMode)
                .setTooltipHoverString("cover.fluid_regulator.transfer_mode.description"));

        ServerWidgetGroup stackSizeGroup = new ServerWidgetGroup(this::checkTransferMode);
        stackSizeGroup.addWidget(new ClickButtonWidget(88, 84, 18, 18, "-1", data -> adjustTransferSize(data.isCtrlClick ? -100 : data.isShiftClick ? -10 : -1)));
        stackSizeGroup.addWidget(new ClickButtonWidget(144, 84, 18, 18, "+1", data -> adjustTransferSize(data.isCtrlClick ? 100 : data.isShiftClick ? +10 : +1)));
        stackSizeGroup.addWidget(new ImageWidget(108, 84, 34, 18, GuiTextures.DISPLAY));
        stackSizeGroup.addWidget(new SimpleTextWidget(125, 93, "", 0xFFFFFF,
                this::getTransferSizeString));
        return super.buildUI(builder.widget(filterGroup).widget(stackSizeGroup), player);
    }


    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferMode", transferMode.ordinal());
        tagCompound.setInteger(keepKey, keepAmount);
        tagCompound.setInteger(supplyKey, supplyAmount);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferMode = TransferMode.values()[tagCompound.getInteger("TransferMode")];
        this.keepAmount = tagCompound.getInteger(keepKey);
        this.supplyAmount = tagCompound.getInteger(supplyKey);
    }

}
