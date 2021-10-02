package gregtech.common.covers;

import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.util.GTFluidUtils;
import gregtech.api.util.TextFormattingUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.*;
import java.util.function.Predicate;


public class CoverFluidRegulator extends CoverPump {

    protected TransferMode transferMode;
    protected int keepAmount = 0;
    protected int supplyAmount = 0;
    private static final String supplyKey = "SupplyAmount";
    private static final String keepKey = "KeepAmount";

    public CoverFluidRegulator(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide, tier, mbPerTick);
        this.transferMode = TransferMode.TRANSFER_ANY;
    }

    @Override
    protected int doTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit) {
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
            case TRANSFER_ANY: return GTFluidUtils.transferFluids(sourceHandler, destHandler, transferLimit, fluidFilter::testFluidStack);
            case KEEP_EXACT: return doKeepExact(transferLimit, sourceHandler, destHandler, fluidFilter::testFluidStack, this.keepAmount);
            case TRANSFER_EXACT: return doTransferExact(transferLimit, sourceHandler, destHandler, fluidFilter::testFluidStack, this.supplyAmount);
        }
        return 0;
    }

    protected int doTransferExact(int transferLimit, IFluidHandler sourceHandler, IFluidHandler destHandler, Predicate<FluidStack> fluidFilter, int supplyAmount) {
        int fluidLeftToTransfer = transferLimit;
        for (IFluidTankProperties tankProperties : sourceHandler.getTankProperties()) {
            if (fluidLeftToTransfer < supplyAmount)
                break;
            FluidStack sourceFluid = tankProperties.getContents();
            if (sourceFluid == null || sourceFluid.amount == 0 || !fluidFilter.test(sourceFluid)) continue;
            sourceFluid.amount = supplyAmount;
            if (GTFluidUtils.transferExactFluidStack(sourceHandler, destHandler, sourceFluid.copy())) {
                fluidLeftToTransfer -= sourceFluid.amount;
            }
            if (fluidLeftToTransfer == 0) break;
        }
        return transferLimit - fluidLeftToTransfer;
    }

    /**
     * Performs one tick worth of Keep Exact behavior.
     * @param transferLimit the maximum amount in milliBuckets that may be transferred in one tick
     * @param sourceHandler source(s) to move fluids from
     * @param destHandler destination(s) to move fluids to
     * @param fluidFilter a predicate which determines what fluids may be moved
     * @param keepAmount the desired amount in milliBuckets of a particular fluid in the destination
     * @return the total amount in milliBuckets of all fluids transferred from source to dest by this method
     */
    protected int doKeepExact(final int transferLimit,
                              final IFluidHandler sourceHandler,
                              final IFluidHandler destHandler,
                              final Predicate<FluidStack> fluidFilter,
                              final int keepAmount) {

        if(sourceHandler == null || destHandler == null || fluidFilter == null || keepAmount <= 0)
            return 0;

        final Map<FluidStack, Integer> sourceFluids =
            collectDistinctFluids(sourceHandler, IFluidTankProperties::canDrain, fluidFilter);
        final Map<FluidStack, Integer> destFluids =
            collectDistinctFluids(destHandler, IFluidTankProperties::canFill, fluidFilter);

        int transferred = 0;
        for(FluidStack fluidStack : sourceFluids.keySet()) {
            if(transferred >= transferLimit)
                break;

            // if fluid needs to be moved to meet the Keep Exact value
            int amountInDest;
            if((amountInDest = destFluids.getOrDefault(fluidStack, 0)) < keepAmount) {

                // move the lesser of the remaining transfer limit and the difference in actual vs keep exact amount
                int amountToMove = Math.min(transferLimit - transferred,
                                            keepAmount - amountInDest);

                // Nothing to do here, try the next fluid.
                if(amountToMove <= 0)
                    continue;

                // Simulate a drain of this fluid from the source tanks
                FluidStack drainedResult = sourceHandler.drain(copyFluidStackWithAmount(fluidStack, amountToMove), false);

                // Can't drain this fluid. Try the next one.
                if(drainedResult == null || drainedResult.amount <= 0 || !fluidStack.equals(drainedResult))
                    continue;

                // account for the possibility that the drain might give us less than requested
                final int drainable = Math.min(amountToMove, drainedResult.amount);

                // Simulate a fill of the drained amount
                int fillResult = destHandler.fill(copyFluidStackWithAmount(fluidStack, drainable), false);

                // Can't fill, try the next fluid.
                if(fillResult <= 0)
                    continue;

                // This Fluid can be drained and filled, so let's move the most that will actually work.
                int fluidToMove = Math.min(drainable, fillResult);
                FluidStack drainedActual = sourceHandler.drain(copyFluidStackWithAmount(fluidStack, fluidToMove), true);

                // Account for potential error states from the drain
                if(drainedActual == null)
                    throw new RuntimeException("Misbehaving fluid container: drain produced null after simulation succeeded");

                if(!fluidStack.equals(drainedActual))
                    throw new RuntimeException("Misbehaving fluid container: drain produced a different fluid than the simulation");

                if(drainedActual.amount != fluidToMove)
                    throw new RuntimeException(new FormattedMessage(
                        "Misbehaving fluid container: drain expected: {}, actual: {}",
                        fluidToMove,
                        drainedActual.amount).getFormattedMessage());


                // Perform Fill
                int filledActual = destHandler.fill(copyFluidStackWithAmount(fluidStack, fluidToMove), true);

                // Account for potential error states from the fill
                if(filledActual != fluidToMove)
                    throw new RuntimeException(new FormattedMessage(
                        "Misbehaving fluid container: fill expected: {}, actual: {}",
                        fluidToMove,
                        filledActual).getFormattedMessage());

                // update the transferred amount
                transferred += fluidToMove;
            }
        }

        return transferred;
    }

    /**
     * Copies a FluidStack and sets its amount to the specified value.
     *
     * @param fs     the original fluid stack to copy
     * @param amount the amount to set the copied FluidStack to
     * @return the copied FluidStack with the specified amount
     */
    private static FluidStack copyFluidStackWithAmount(FluidStack fs, int amount) {
        FluidStack fs2 = fs.copy();
        fs2.amount = amount;
        return fs2;
    }

    private Map<FluidStack, Integer> collectDistinctFluids(IFluidHandler handler,
                                                     Predicate<IFluidTankProperties> tankTypeFilter,
                                                     Predicate<FluidStack> fluidTypeFilter) {

        final Map<FluidStack, Integer> summedFluids = new HashMap<>();
        Arrays.stream(handler.getTankProperties())
              .filter(tankTypeFilter)
              .map(IFluidTankProperties::getContents)
              .filter(Objects::nonNull)
              .filter(fluidTypeFilter)
              .forEach(fs -> {
                  summedFluids.putIfAbsent(fs, 0);
                  summedFluids.computeIfPresent(fs, (k,v) -> v + fs.amount);
              });

        return summedFluids;
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
        return val == -1 ? "" : TextFormattingUtil.formatLongToCompactString(val);
    }

    protected void getHoverString(List<ITextComponent> textList) {
        switch (this.transferMode) {
            case KEEP_EXACT:
                ITextComponent keepComponent = new TextComponentString(getTransferSizeString());
                TextComponentTranslation hoverKeep = new TextComponentTranslation("cover.fluid_regulator.keep_exact", this.keepAmount);
                keepComponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverKeep));
                textList.add(keepComponent);
                break;
            case TRANSFER_EXACT:
                ITextComponent supplyComponent = new TextComponentString(getTransferSizeString());
                TextComponentTranslation hoverSupply = new TextComponentTranslation("cover.fluid_regulator.supply_exact", this.supplyAmount);
                supplyComponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverSupply));
                textList.add(supplyComponent);
                break;
        }
    }

    @Override
    public void setBucketMode(BucketMode bucketMode){
        super.setBucketMode(bucketMode);
        if (this.bucketMode == BucketMode.BUCKET) {
            setKeepAmount(keepAmount / 1000 * 1000);
            setSupplyAmount(supplyAmount / 1000 * 1000);
        }
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
        stackSizeGroup.addWidget(new AdvancedTextWidget(114, 89, this::getHoverString, 0xFFFFFF));
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
