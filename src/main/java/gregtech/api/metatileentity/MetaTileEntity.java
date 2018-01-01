package gregtech.api.metatileentity;

import com.google.common.base.Preconditions;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.capability.impl.ItemHandlerProxy;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MetaTileEntity implements IMetaTileEntity {

    public final IMetaTileEntityFactory factory;

    GregtechTileEntity holder;

    protected IItemHandlerModifiable importItems;
    protected IItemHandlerModifiable exportItems;

    protected IItemHandler itemInventory;

    protected FluidTankHandler importFluids;
    protected FluidTankHandler exportFluids;

    protected IFluidHandler fluidInventory;

    protected EnumFacing frontFacing = EnumFacing.NORTH;

    protected int[] sidedRedstoneOutput = new int[6];

    public MetaTileEntity(IMetaTileEntityFactory factory) {
        this.factory = factory;

        this.importItems = createImportItemHandler();
        this.exportItems = createExportItemHandler();

        this.itemInventory = createItemHandler();

        this.importFluids = createImportFluidHandler();
        this.exportFluids = createExportFluidHandler();

        this.fluidInventory = createFluidHandler();
    }

    @Override
    public IMetaTileEntityFactory getFactory() {
        return factory;
    }

    @Override
    public <T> boolean hasCapability(Capability<T> capability, EnumFacing side) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        } else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidInventory);
        } else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemInventory);
        }
        return null;
    }

    @Override
    public IItemHandler createItemHandler() {
        return new ItemHandlerProxy(importItems, exportItems);
    }

    @Override
    public IFluidHandler createFluidHandler() {
        return new FluidHandlerProxy(importFluids, exportFluids);
    }

    @Override
    public String getMetaName() {
        return GregTechAPI.METATILEENTITY_REGISTRY.getNameForObject(factory);
    }

    @Override
    public boolean canConnectRedstone(@Nullable EnumFacing side) {
        return true;
    }

    @Override
    public int getInputRedstoneSignal(EnumFacing side) {
        return getWorld().getStrongPower(getPos().offset(side));
    }

    @Override
    public World getWorld() {
        return holder.getWorld();
    }

    @Override
    public BlockPos getPos() {
        return holder.getPos();
    }

    @Override
    public long getTimer() {
        return holder.getTimer();
    }

    @Override
    public void markDirty() {
        holder.markDirty();
    }

    public void markBlockForRenderUpdate() {
        BlockPos pos = getPos();
        getWorld().markBlockRangeForRenderUpdate(
                pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1,
                pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        switch (dataId) {
            case 1:
                this.frontFacing = EnumFacing.VALUES[buf.readByte()];
                markBlockForRenderUpdate();
                break;
            case 2:
                this.sidedRedstoneOutput[buf.readByte()] = buf.readInt();
                markBlockForRenderUpdate();
                break;
        }
    }

    @Override
    public void writeInitialData(PacketBuffer buf) {
        buf.writeByte(frontFacing.getIndex());
        for(EnumFacing side : EnumFacing.VALUES) {
            buf.writeInt(sidedRedstoneOutput[side.getIndex()]);
        }
    }

    @Override
    public void receiveInitialData(PacketBuffer buf) {
        this.frontFacing = EnumFacing.VALUES[buf.readByte()];
        for(EnumFacing side : EnumFacing.VALUES) {
            this.sidedRedstoneOutput[side.getIndex()] = buf.readInt();
        }
    }

    @Override
    public int getOutputRedstoneSignal(@Nullable EnumFacing side) {
        return side == null ? Arrays.stream(sidedRedstoneOutput).sorted().findFirst().orElse(0) : sidedRedstoneOutput[side.getIndex()];
    }

    @Override
    public void setOutputRedstoneSignal(EnumFacing side, int strength) {
        Preconditions.checkNotNull(side);
        this.sidedRedstoneOutput[side.getIndex()] = strength;
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(2, buf -> {
                buf.writeByte(side.getIndex());
                buf.writeInt(strength);
            });
        }
    }

    @Override
    public GregtechTileEntity getHolder() {
        return holder;
    }

    //////////////////////////////////////////////////////////////////
    //                 ITurnable Implementation                     //
    //////////////////////////////////////////////////////////////////

    @Override
    public boolean isValidFacing(EnumFacing side) {
        return true;
    }

    @Override
    public EnumFacing getFrontFacing() {
        return frontFacing;
    }

    @Override
    public void setFrontFacing(EnumFacing facing) {
        this.frontFacing = facing;
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(1, buf -> buf.writeByte(facing.getIndex()));
        }
    }

    //////////////////////////////////////////////////////////////////

    @Override
    public void onLeftClick(EntityPlayer player) {

    }

    @Override
    public void onFirstTick() {
    }

    @Override
    public void onPreTick(long tickTimer) {
    }

    @Override
    public void onPostTick(long tickTimer) {
    }

    @Override
    public void inValidate() {
    }

    @Override
    public void onRemoval() {
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer player) {
        return true; //default - to be overridden
    }

    @Override
    public void onEntityCollidedWithBlock(Entity collider) {
    }

    @Override
    public void initFromItemStackData(NBTTagCompound data) {
    }

    @Override
    public void writeItemStackData(NBTTagCompound data) {
    }

    public boolean addItemsToItemHandler(IItemHandler handler, boolean simulate, NonNullList<ItemStack> items) {
        boolean notAllInserted = false;
        List<ItemStack> stacks = new ArrayList<>(items); //copy collection
        for (ItemStack stack : stacks) {
            notAllInserted |= !ItemHandlerHelper.insertItemStacked(handler, stack, simulate).isEmpty();
            if (notAllInserted && simulate) return false;
        }
        return !notAllInserted;
    }

    public boolean addFluidsToFluidHandler(IFluidHandler handler, boolean simulate, List<FluidStack> items) {
        boolean notAllInserted = false;
        items = new ArrayList<>(items); //copy collection
        for (FluidStack stack : items) {
            int filled = handler.fill(stack, !simulate);
            notAllInserted |= !(filled == stack.amount);
            if (notAllInserted && simulate) return false;
        }
        return !notAllInserted;
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        data.setInteger("Facing", frontFacing.getIndex());
        GTUtility.writeItems(importItems, "ImportInventory", data);
        GTUtility.writeItems(exportItems, "ExportInventory", data);

        data.setTag("ImportFluidInventory", importFluids.serializeNBT());
        data.setTag("ExportFluidInventory", exportFluids.serializeNBT());
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        if(data.hasKey("Facing", Constants.NBT.TAG_INT)) {
            this.frontFacing = EnumFacing.VALUES[data.getInteger("Facing")];
        }

        GTUtility.readItems(importItems, "ImportInventory", data);
        GTUtility.readItems(exportItems, "ExportInventory", data);

        importFluids.deserializeNBT(data.getCompoundTag("ImportFluidInventory"));
        exportFluids.deserializeNBT(data.getCompoundTag("ExportFluidInventory"));
    }

    @Override
    public void doExplosion(long strength) {
        // This is only for Electric Machines
//      if (GregTechAPI.sMachineWireFire && metaTileEntity instanceof EnergyMetaTileEntity) {
//          IEnergyConnected.Util.emitEnergyToNetwork(GTValues.V[5], Math.max(1, ((EnergyMetaTileEntity) metaTileEntity).getEnergyStored() / GTValues.V[5]), this);
//      }

        World world = getWorld();
        BlockPos pos = getPos();

        // Normal Explosion Code
        if (true) { //TODO CONFIG GregTechMod.gregtechproxy.explosionItemDrop
            for (int i = 0; i < importItems.getSlots(); i++) {
                Block.spawnAsEntity(world, pos, importItems.getStackInSlot(i));
                importItems.setStackInSlot(i, ItemStack.EMPTY);
            }
            for (int i = 0; i < exportItems.getSlots(); i++) {
                Block.spawnAsEntity(world, pos, exportItems.getStackInSlot(i));
                exportItems.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

//      GT_Pollution.addPollution(this.pos, 100000);

        GTUtility.playSound(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, GregTechAPI.soundList.get(209), SoundCategory.BLOCKS, 1.0f, 1.0f);
//        if (GregTechAPI.machineExplosions) { // TODO CONFIG
        world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, strength, true);
//        }
    }
}