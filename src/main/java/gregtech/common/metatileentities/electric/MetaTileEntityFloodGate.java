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
import gregtech.api.gui.widgets.FluidContainerSlotWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
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

public class MetaTileEntityFloodGate extends TieredMetaTileEntity {

    private static final Cuboid6 PIPE_CUBOID = new Cuboid6(4 / 16.0, 0.0, 4 / 16.0, 12 / 16.0, 1.0, 12 / 16.0);
    private static final int MAX_RANGE = 32;
    private static final int SPEED_BASE = 40;

    private Deque<BlockPos> airBlocks;
    private Deque<BlockPos> blocksToCheck;
    private boolean initializedQueue;
    private int headY;

    public MetaTileEntityFloodGate(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.airBlocks = new ArrayDeque<>();
        this.blocksToCheck = new ArrayDeque<>();
        this.initializedQueue = false;
        this.headY = 0;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityFloodGate(metaTileEntityId, getTier());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        IVertexOperation[] coloredPipeline = ArrayUtils.add(pipeline, multiplier);
        for(EnumFacing renderSide : EnumFacing.HORIZONTALS) {
            if(renderSide == getFrontFacing()) {
                Textures.PIPE_OUT_OVERLAY.renderSided(renderSide, renderState, translation, pipeline);
            } else {
                Textures.ADV_PUMP_OVERLAY.renderSided(renderSide, renderState, translation, coloredPipeline);
            }
        }
        Textures.SCREEN.renderSided(EnumFacing.UP, renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(EnumFacing.DOWN, renderState, translation, pipeline);
        for(int i = 0; i < headY; i++) {
            translation.translate(0.0, -1.0, 0.0);
            Textures.SOLID_STEEL_CASING.render(renderState, translation, pipeline, PIPE_CUBOID);
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(headY);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.headY = buf.readVarInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == 200) {
            this.headY = buf.readVarInt();
        }
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        class FloodGateTank extends FluidTankList {
            public FloodGateTank() {
                super(false, new FluidTank(16000 * Math.max(1, getTier())));
            }

            @Override
            public int fill(FluidStack resource, boolean doFill) {
                // avoid fluids that do not have corresponding block
                if(resource.getFluid().getBlock() == null)
                    return 0;

                return super.fill(resource, doFill);
            }
        }

        return new FloodGateTank();
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
        return (side == null || side.getAxis() != EnumFacing.Axis.Y) ? super.getCapability(capability, side) : null;
    }

    private boolean isFluid(BlockPos pos) {
        Block block = getWorld().getBlockState(pos).getBlock();
        return block instanceof IFluidBlock || block instanceof BlockLiquid;
    }

    private boolean canPutAt(BlockPos pos) {
        if(getWorld().isAirBlock(pos)) {
            return true;
        }

        if(isFluid(pos)) {
            IFluidHandler fluidHandler = FluidUtil.getFluidHandler(getWorld(), pos, null);
            FluidStack drainStack = null;

            if (fluidHandler != null) {
                drainStack = fluidHandler.drain(Integer.MAX_VALUE, false);
            }
            if (drainStack != null) {
                return drainStack.amount < Fluid.BUCKET_VOLUME;
            }

            return true;
        }

        return false;
    }

    private void moveHeadToBottom() {
        BlockPos selfPos = getPos();
        int temp = 0;
        while(canPutAt(selfPos.down(temp + 1)))
            temp ++;

        if (headY != temp) {
            headY = temp;
            writeCustomData(200, b -> b.writeVarInt(headY));
            markDirty();
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        TankWidget tankWidget = new TankWidget(importFluids.getTankAt(0), 69, 52, 18, 18)
            .setHideTooltip(true)
            .setAlwaysShowFull(true);

        ModularUI.Builder builder = ModularUI.defaultBuilder()
            .image(7, 16, 81, 55, GuiTextures.DISPLAY)
            .widget(tankWidget)
            .label(11, 20, "gregtech.gui.fluid_amount", 0xFFFFFF)
            .dynamicLabel(11, 30, tankWidget::getFormattedFluidAmount, 0xFFFFFF)
            .dynamicLabel(11, 40, tankWidget::getFluidLocalizedName, 0xFFFFFF)
            .label(6, 6, getMetaFullName())
            .widget(new FluidContainerSlotWidget(importItems, 0, 90, 17, false)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY))
            .widget(new ImageWidget(91, 36, 14, 15, GuiTextures.TANK_ICON))
            .widget(new SlotWidget(exportItems, 0, 90, 54, true, false)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY))
            .bindPlayerInventory(entityPlayer.inventory);
        return builder.build(getHolder(), entityPlayer);
    }

    private void updateQueueState() {
        BlockPos headPos = getPos().down(headY);

        if(!blocksToCheck.isEmpty()) {
            BlockPos checkPos = this.blocksToCheck.poll();

            if(canPutAt(checkPos)) {
                for(EnumFacing facing : EnumFacing.HORIZONTALS) {
                    BlockPos offsetPos = checkPos.offset(facing);
                    if(offsetPos.distanceSq(headPos) > MAX_RANGE * MAX_RANGE)
                        continue; //do not add blocks outside bounds
                    if(canPutAt(offsetPos) && (!blocksToCheck.contains(offsetPos)))
                        this.blocksToCheck.add(offsetPos);
                }

                if(!airBlocks.contains(checkPos))
                    this.airBlocks.add(checkPos);
            }
        }

        if(airBlocks.isEmpty() && this.blocksToCheck.isEmpty()) {
            if(getTimer() % 20 == 0) {
                moveHeadToBottom();
                //schedule queue rebuild because we changed our position and no fluid is available
                this.initializedQueue = false;
            }

            if((!initializedQueue || getTimer() % 6000 == 0) && headY != 0) {
                this.initializedQueue = true;
                //just add ourselves to check list and see how this will go
                this.blocksToCheck.add(getPos().down(headY));
            }
        }
    }

    private void tryPutFirstBlock() {
        BlockPos AirBlockPos = this.airBlocks.poll();

        if(AirBlockPos == null)
            return;

        if(canPutAt(AirBlockPos)) {
            FluidStack drainStack = importFluids.drain(Fluid.BUCKET_VOLUME, false);
            if(drainStack != null && drainStack.amount >= Fluid.BUCKET_VOLUME) {
                // put fluid
                if(FluidUtil.tryPlaceFluid(null, getWorld(), AirBlockPos, importFluids, drainStack)) {
                    this.energyContainer.changeEnergy(- GTValues.V[getTier()]);
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if(getWorld().isRemote) {
            return;
        }
        //do not do anything without enough energy supplied
        if(energyContainer.getEnergyStored() < GTValues.V[getTier()] * 4) {
            return;
        }
        fillInternalTankFromFluidContainer(importItems, exportItems, 0, 0);

        updateQueueState();
        if(getTimer() % getCycleLength() == 0 && !airBlocks.isEmpty() && energyContainer.getEnergyStored() >= GTValues.V[getTier()]) {
            tryPutFirstBlock();
        }
    }

    private int getCycleLength(){
        return SPEED_BASE / Math.max(1, getTier());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.flood_gate.tooltip_range", MAX_RANGE, MAX_RANGE));
        tooltip.add(I18n.format("gregtech.machine.flood_gate.tooltip_speed", getCycleLength()));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", energyContainer.getInputVoltage(), GTValues.VN[getTier()]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", importFluids.getTankAt(0).getCapacity()));
    }
}
