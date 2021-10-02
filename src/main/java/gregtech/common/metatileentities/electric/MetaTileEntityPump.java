package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.FluidContainerSlotWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.api.util.IDirtyNotifiable;
import gregtech.common.covers.filter.FluidFilterContainer;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MetaTileEntityPump extends TieredMetaTileEntity implements IDirtyNotifiable {

    private static final Cuboid6 PIPE_CUBOID = new Cuboid6(6 / 16.0, 0.0, 6 / 16.0, 10 / 16.0, 1.0, 10 / 16.0);
    private static final int BASE_PUMP_RANGE = 32;
    private static final int EXTRA_PUMP_RANGE = 8;
    private static final int PUMP_SPEED_BASE = 40;

    private final Deque<BlockPos> fluidSourceBlocks = new ArrayDeque<>();
    private final Deque<BlockPos> blocksToCheck = new ArrayDeque<>();
    private final Deque<BlockPos> blocksToReCheck = new ArrayDeque<>();
    private boolean initializedQueue = false;
    private int pumpHeadY;

    protected final FluidFilterContainer fluidFilter;

    public MetaTileEntityPump(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.fluidFilter = new FluidFilterContainer(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityPump(metaTileEntityId, getTier());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        IVertexOperation[] coloredPipeline = ArrayUtils.add(pipeline, multiplier);
        for (EnumFacing renderSide : EnumFacing.HORIZONTALS) {
            if (renderSide == getFrontFacing()) {
                Textures.PIPE_OUT_OVERLAY.renderSided(renderSide, renderState, translation, pipeline);
            } else {
                Textures.ADV_PUMP_OVERLAY.renderSided(renderSide, renderState, translation, coloredPipeline);
            }
        }
        Textures.SCREEN.renderSided(EnumFacing.UP, renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(EnumFacing.DOWN, renderState, translation, pipeline);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(pumpHeadY);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.pumpHeadY = buf.readVarInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 200) {
            this.pumpHeadY = buf.readVarInt();
            scheduleRenderUpdate();
        }
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, new FluidTank(16000 * Math.max(1, getTier())));
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return (side == null || side.getAxis() != Axis.Y) ? super.getCapability(capability, side) : null;
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        super.clearMachineInventory(itemBuffer);
        clearInventory(itemBuffer, this.fluidFilter.getFilterInventory());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 147 + 82);
        builder.image(7, 16, 81, 55, GuiTextures.DISPLAY);
        TankWidget tankWidget = new TankWidget(exportFluids.getTankAt(0), 69, 52, 18, 18)
                .setHideTooltip(true).setAlwaysShowFull(true);

        builder.widget(tankWidget);
        builder.label(11, 20, "gregtech.gui.fluid_amount", 0xFFFFFF);
        builder.dynamicLabel(11, 30, tankWidget::getFormattedFluidAmount, 0xFFFFFF);
        builder.dynamicLabel(11, 40, tankWidget::getFluidLocalizedName, 0xFFFFFF);

        this.fluidFilter.initUI(75, builder::widget);

        return builder.label(6, 6, getMetaFullName())
                .widget(new FluidContainerSlotWidget(importItems, 0, 90, 17, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY))
                .widget(new ImageWidget(91, 36, 14, 15, GuiTextures.TANK_ICON))
                .widget(new SlotWidget(exportItems, 0, 90, 54, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY))
                .bindPlayerInventory(entityPlayer.inventory, 147)
                .build(getHolder(), entityPlayer);
    }

    private int getMaxPumpRange() {
        return BASE_PUMP_RANGE + EXTRA_PUMP_RANGE * getTier();
    }

    private boolean isStraightInPumpRange(BlockPos checkPos) {
        BlockPos pos = getPos();
        return checkPos.getX() == pos.getX() &&
                checkPos.getZ() == pos.getZ() &&
                pos.getY() < checkPos.getY() &&
                pos.getY() + pumpHeadY >= checkPos.getY();
    }

    private BlockPos getValidPos(Deque<BlockPos> posDeque) {
        BlockPos checkPos = null;
        int amountIterated = 0;
        do {
            if (checkPos != null) {
                blocksToCheck.push(checkPos);
                amountIterated++;
            }
            checkPos = blocksToCheck.poll();

        } while (checkPos != null &&
                !getWorld().isBlockLoaded(checkPos) &&
                amountIterated < blocksToCheck.size());
        return checkPos;
    }

    private void updateQueueState(int blocksToCheckAmount) {
        BlockPos selfPos = getPos().down(pumpHeadY);

        for (int i = 0; i < blocksToCheckAmount; i++) {
            BlockPos checkPos = getValidPos(blocksToCheck);
            if (checkPos != null) {
                checkFluidBlockAt(selfPos, checkPos);
            } else {
                checkPos = getValidPos(blocksToReCheck);
                if (checkPos != null) {
                    reCheckFluidBlockAt(selfPos, checkPos);
                } else break;
            }
        }

        if (fluidSourceBlocks.isEmpty()) {
            if (getOffsetTimer() % 20 == 0) {
                BlockPos downPos = selfPos.down(1);
                if (downPos != null && downPos.getY() >= 0) {
                    IBlockState downBlock = getWorld().getBlockState(downPos);
                    if (downBlock.getBlock() instanceof BlockLiquid ||
                            downBlock.getBlock() instanceof IFluidBlock ||
                            !downBlock.isTopSolid()) {
                        this.pumpHeadY++;
                    }
                }

                // Always recheck next time
                writeCustomData(200, b -> b.writeVarInt(pumpHeadY));
                markDirty();
                //schedule queue rebuild because we changed our position and no fluid is available
                this.initializedQueue = false;
            }

            if (!initializedQueue || getOffsetTimer() % 6000 == 0 || getTimer() == 0) {
                this.initializedQueue = true;
                //just add ourselves to check list and see how this will go
                this.blocksToCheck.add(selfPos);
            }
        }
    }

    private void checkFluidBlockAt(BlockPos pumpHeadPos, BlockPos checkPos) {
        IBlockState blockHere = getWorld().getBlockState(checkPos);
        boolean shouldCheckNeighbours = isStraightInPumpRange(checkPos);

        if (blockHere.getBlock() instanceof BlockLiquid ||
                blockHere.getBlock() instanceof IFluidBlock) {
            IFluidHandler fluidHandler = FluidUtil.getFluidHandler(getWorld(), checkPos, null);
            FluidStack drainStack = fluidHandler.drain(Integer.MAX_VALUE, false);
            if (drainStack != null && drainStack.amount > 0 && this.fluidFilter.testFluidStack(drainStack)) {
                this.fluidSourceBlocks.add(checkPos);
            }
            shouldCheckNeighbours = true;
        }

        if (shouldCheckNeighbours) {
            checkNeighbours(pumpHeadPos, checkPos);
        }
    }

    private void reCheckFluidBlockAt(BlockPos pumpHeadPos, BlockPos checkPos) {
        IBlockState blockHere = getWorld().getBlockState(checkPos);
        boolean shouldCheckNeighbours = isStraightInPumpRange(checkPos);

        if (blockHere.getBlock() instanceof BlockLiquid ||
                blockHere.getBlock() instanceof IFluidBlock) {
            IFluidHandler fluidHandler = FluidUtil.getFluidHandler(getWorld(), checkPos, null);
            FluidStack drainStack = fluidHandler.drain(Integer.MAX_VALUE, false);
            if (drainStack != null && drainStack.amount > 0 && this.fluidFilter.testFluidStack(drainStack)) {
                this.fluidSourceBlocks.add(checkPos);
                shouldCheckNeighbours = true;
            }
        }

        if (shouldCheckNeighbours) {
            checkNeighbours(pumpHeadPos, checkPos);
        }
    }

    private void checkNeighbours(BlockPos pumpHeadPos, BlockPos checkPos) {
        int maxPumpRange = getMaxPumpRange();
        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offsetPos = checkPos.offset(facing);
            if (offsetPos.distanceSq(pumpHeadPos) > maxPumpRange * maxPumpRange)
                continue; //do not add blocks outside bounds
            if (!fluidSourceBlocks.contains(offsetPos) && !blocksToCheck.contains(offsetPos) && !blocksToReCheck.contains(offsetPos)) {

                if (offsetPos.distanceSq(pumpHeadPos) > checkPos.distanceSq(pumpHeadPos)) {
                    this.blocksToCheck.add(offsetPos);
                } else {
                    this.blocksToReCheck.add(offsetPos);
                }

            }
        }
    }

    private void tryPumpFirstBlock() {
        BlockPos fluidBlockPos = fluidSourceBlocks.poll();
        if (fluidBlockPos == null) return;
        IBlockState blockHere = getWorld().getBlockState(fluidBlockPos);
        if (blockHere.getBlock() instanceof BlockLiquid ||
                blockHere.getBlock() instanceof IFluidBlock) {
            IFluidHandler fluidHandler = FluidUtil.getFluidHandler(getWorld(), fluidBlockPos, null);
            FluidStack drainStack = fluidHandler.drain(Integer.MAX_VALUE, false);
            if (drainStack != null && exportFluids.fill(drainStack, false) == drainStack.amount && this.fluidFilter.testFluidStack(drainStack)) {
                exportFluids.fill(drainStack, true);
                fluidHandler.drain(drainStack.amount, true);
                this.fluidSourceBlocks.remove(fluidBlockPos);
                energyContainer.changeEnergy(-GTValues.V[getTier()]);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (getWorld().isRemote) {
            return;
        }
        //do not do anything without enough energy supplied
        if (energyContainer.getEnergyStored() < GTValues.V[getTier()] * 4) {
            return;
        }
        pushFluidsIntoNearbyHandlers(getFrontFacing());
        fillContainerFromInternalTank(importItems, exportItems, 0, 0);
        updateQueueState(getTier());
      
        if (getOffsetTimer() % getPumpingCycleLength() == 0 && !fluidSourceBlocks.isEmpty() &&
            energyContainer.getEnergyStored() >= GTValues.V[getTier()]) {

            tryPumpFirstBlock();
        }
    }

    private int getPumpingCycleLength() {
        return PUMP_SPEED_BASE / Math.max(1, getTier());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("PumpHeadDepth", pumpHeadY);
        data.setTag("Filter", fluidFilter.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.pumpHeadY = data.getInteger("PumpHeadDepth");
        if (data.hasKey("Filter")) {
            this.fluidFilter.deserializeNBT(data.getCompoundTag("Filter"));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        int maxPumpRange = getMaxPumpRange();
        tooltip.add(I18n.format("gregtech.machine.pump.tooltip_range", maxPumpRange, maxPumpRange));
        tooltip.add(I18n.format("gregtech.machine.pump.tooltip_speed", getPumpingCycleLength()));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", energyContainer.getInputVoltage(), GTValues.VN[getTier()]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", exportFluids.getTankAt(0).getCapacity()));
    }

    @Override
    public void markAsDirty() {
        // Empty the different queues when a filter is added, removed or switched from whitelist to blacklist
        while (fluidSourceBlocks.poll() != null) ;
        while (blocksToReCheck.poll() != null) ;
        while (blocksToCheck.poll() != null) ;
        this.markDirty();
    }
}
