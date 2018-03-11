package gregtech.api.metatileentity;

import com.google.common.base.Preconditions;
import gregtech.api.GTValues;
import gregtech.api.capability.ICustomDataTile;
import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.capability.impl.ItemHandlerProxy;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.ModularUI;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MetaTileEntity extends TickableTileEntityBase implements ICustomDataTile, IUIHolder {

    protected IItemHandlerModifiable importItems;
    protected IItemHandlerModifiable exportItems;

    protected IItemHandler itemInventory;

    protected FluidTankHandler importFluids;
    protected FluidTankHandler exportFluids;

    protected IFluidHandler fluidInventory;

    protected List<MTETrait> mteTraits = new ArrayList<>();

    protected EnumFacing frontFacing = EnumFacing.NORTH;
    protected int paintingColor = 0xCCCCCC;

    protected int[] sidedRedstoneOutput = new int[6];

    public MetaTileEntity() {
        this.importItems = createImportItemHandler();
        this.exportItems = createExportItemHandler();
        this.itemInventory = new ItemHandlerProxy(importItems, exportItems);

        this.importFluids = createImportFluidHandler();
        this.exportFluids = createExportFluidHandler();
        this.fluidInventory = new FluidHandlerProxy(importFluids, exportFluids);
    }

    public final String getMetaName() {
        return "gregtech.machine." + TileEntity.getKey(getClass()).getResourcePath() + ".name";
    }

    /**
     * Adds a trait to this meta tile entity
     * traits are objects linked with meta tile entity and performing certian
     * actions. usually traits implement capabilities
     * @param trait trait object to add
     * @return trait object passed in arguments
     */
    protected <T extends MTETrait> T addTrait(T trait) {
        trait.setMetaTileEntityAndIndex(this, mteTraits.size());
        this.mteTraits.add(trait);
        return trait;
    }

    /**
     * Setups this MetaTileEntity as being used for rendering as item
     * This usually sets default facing, texture-dependent parameters (like isActive)
     * item-dependent parameters (like color & covers) are set in initFromItemStackData individually
     * @return a location of item model json (to allow custom rotation, scaling and translation)
     */
    @SideOnly(Side.CLIENT)
    public ResourceLocation setupForItemRendering() {
        return new ResourceLocation(GTValues.MODID, "metatileentity/default");
    }

    /**
     * Applies special rendering (such as miscellaneous overlays) to this meta tile entity
     * You can use helper methods provided by instance passed here
     * Note that this method can be called both on meta tile entity in world and on
     * meta tile entity used to render item in inventory
     */
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(MetaTileEntityRenderer renderer) {

    }

    /**
     * Called from ItemBlock to initialize this MTE with data contained in ItemStack
     * also called by GTHooksClient to initialize this MTE with rendered itemstack data
     *
     * @param itemStack itemstack of itemblock
     */
    public void initFromItemStackData(NBTTagCompound itemStack) {
    }

    /**
     * Called to write MTE specific data when it is destroyed to save it's state
     * into itemblock, which can be placed later to get {@link #initFromItemStackData} called
     * @param itemStack itemstack from which this MTE is being placed
     */
    public void writeItemStackData(NBTTagCompound itemStack) {
    }

    protected abstract IItemHandlerModifiable createImportItemHandler();

    protected abstract IItemHandlerModifiable createExportItemHandler();

    protected abstract FluidTankHandler createImportFluidHandler();

    protected abstract FluidTankHandler createExportFluidHandler();

    /**
     * Creates a UI instance for player opening inventory of this meta tile entity
     * @param entityPlayer player opening inventory
     * @return freshly created UI instance
     */
    protected abstract ModularUI<MetaTileEntity> createUI(EntityPlayer entityPlayer);

    /**
     * Called when player clicks on specific side of this meta tile entity
     * @return true if something happened, so animation will be played
     */
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }

    /**
     * Called when player clicks wrench on specific side of this meta tile entity
     * @return true if something happened, so wrench will get damaged and animation will be played
     */
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.isSneaking()) {
            if(facing == getFrontFacing() || !isValidFrontFacing(facing))
                return false;
            //todo wrench sound here
            setFrontFacing(facing);
            return true;
        }
        return false;
    }

    /**
     * Called when player clicks screwdriver on specific side of this meta tile entity
     * @return true if something happened, so screwdriver will get damaged and animation will be played
     */
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }

    public void onLeftClick(EntityPlayer player) {
    }

    public boolean canConnectRedstone(@Nullable EnumFacing side) {
        return false;
    }

    public int getComparatorValue() {
        return 0;
    }

    @Override
    public void update() {
        super.update();
        for(MTETrait mteTrait : this.mteTraits) {
            mteTrait.update();
        }
    }

    /**
     * Add special drops which this meta tile entity contains here
     * It includes inventory contents, upgrades & so
     * Meta tile entity item is ALREADY added into this list
     *
     * @param dropsList list of meta tile entity drops
     * @param harvester harvester of this meta tile entity, or null
     */
    public void getDrops(NonNullList<ItemStack> dropsList, @Nullable EntityPlayer harvester) {
        for(int i = 0; i < itemInventory.getSlots(); i++) {
            ItemStack stackInSlot = itemInventory.getStackInSlot(i);
            if(!stackInSlot.isEmpty()) {
                dropsList.add(stackInSlot);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("FrontFacing", frontFacing.getIndex());
        data.setInteger("PaintingColor", paintingColor);

        GTUtility.writeItems(importItems, "ImportInventory", data);
        GTUtility.writeItems(exportItems, "ExportInventory", data);

        data.setTag("ImportFluidInventory", importFluids.serializeNBT());
        data.setTag("ExportFluidInventory", exportFluids.serializeNBT());

        for(MTETrait mteTrait : this.mteTraits) {
            data.setTag(mteTrait.getName(), mteTrait.serializeNBT());
        }

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.frontFacing = EnumFacing.VALUES[data.getInteger("FrontFacing")];
        this.paintingColor = data.getInteger("PaintingColor");

        GTUtility.readItems(importItems, "ImportInventory", data);
        GTUtility.readItems(exportItems, "ExportInventory", data);

        importFluids.deserializeNBT(data.getCompoundTag("ImportFluidInventory"));
        exportFluids.deserializeNBT(data.getCompoundTag("ExportFluidInventory"));

        for(MTETrait mteTrait : this.mteTraits) {
            NBTTagCompound traitCompound = data.getCompoundTag(mteTrait.getName());
            mteTrait.deserializeNBT(traitCompound);
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        buf.writeByte(this.frontFacing.getIndex());
        buf.writeInt(this.paintingColor);
        for (EnumFacing side : EnumFacing.VALUES) {
            buf.writeInt(sidedRedstoneOutput[side.getIndex()]);
        }
        for(MTETrait mteTrait : this.mteTraits) {
            mteTrait.writeInitialData(buf);
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        this.frontFacing = EnumFacing.VALUES[buf.readByte()];
        this.paintingColor = buf.readInt();
        for (EnumFacing side : EnumFacing.VALUES) {
            this.sidedRedstoneOutput[side.getIndex()] = buf.readInt();
        }
        for(MTETrait mteTrait : this.mteTraits) {
            mteTrait.receiveInitialData(buf);
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if(dataId == -1) {
            this.frontFacing = EnumFacing.VALUES[buf.readByte()];
        } else if(dataId == -2) {
            this.paintingColor = buf.readInt();
        } else if(dataId == -1) {
            this.sidedRedstoneOutput[buf.readByte()] = buf.readInt();
        } else {
            for(MTETrait mteTrait : this.mteTraits) {
                mteTrait.readSyncData(dataId, buf);
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing side) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ||
            capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        for(MTETrait mteTrait : this.mteTraits) {
            if(mteTrait.getImplementingCapability() == capability)
                return true;
        }
        return super.hasCapability(capability, side);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidInventory());
        } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemInventory());
        }
        for(MTETrait mteTrait : this.mteTraits) {
            if(mteTrait.getImplementingCapability() == capability)
                //noinspection unchecked
                return (T) mteTrait;
        }
        return super.getCapability(capability, side);
    }

    public static boolean addItemsToItemHandler(IItemHandler handler, boolean simulate, NonNullList<ItemStack> items) {
        boolean notAllInserted = false;
        List<ItemStack> stacks = new ArrayList<>(items); //copy collection
        for (ItemStack stack : stacks) {
            notAllInserted |= !ItemHandlerHelper.insertItemStacked(handler, stack, simulate).isEmpty();
            if (notAllInserted && simulate) return false;
        }
        return !notAllInserted;
    }

    public static boolean addFluidsToFluidHandler(IFluidHandler handler, boolean simulate, List<FluidStack> items) {
        boolean notAllInserted = false;
        items = new ArrayList<>(items); //copy collection
        for (FluidStack stack : items) {
            int filled = handler.fill(stack, !simulate);
            notAllInserted |= !(filled == stack.amount);
            if (notAllInserted && simulate) return false;
        }
        return !notAllInserted;
    }

    public void doExplosion(float strength) {
        World world = getWorld();
        BlockPos pos = getPos();
        for(int i = 0; i < itemInventory.getSlots(); i++) {
            ItemStack stackInSlot = itemInventory.getStackInSlot(i);
            if(!stackInSlot.isEmpty() && world.rand.nextFloat() <= 0.65) { //todo explosion drop chance in config
                Block.spawnAsEntity(world, pos, importItems.getStackInSlot(i));
            }
        }
        //TODO custom sound of explosion
        world.setBlockToAir(pos);
        world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, strength, true);
    }

    public final int getOutputRedstoneSignal(@Nullable EnumFacing side) {
        return side == null ? Arrays.stream(sidedRedstoneOutput)
            .max().orElse(0) : sidedRedstoneOutput[side.getIndex()];
    }

    public final void setOutputRedstoneSignal(EnumFacing side, int strength) {
        Preconditions.checkNotNull(side, "side");
        this.sidedRedstoneOutput[side.getIndex()] = strength;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(-3, buf -> {
                buf.writeByte(side.getIndex());
                buf.writeInt(strength);
            });
        }
    }

    public void setFrontFacing(EnumFacing frontFacing) {
        Preconditions.checkNotNull(frontFacing, "frontFacing");
        this.frontFacing = frontFacing;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(-1, buf -> buf.writeByte(frontFacing.getIndex()));
        }
    }

    public void setPaintingColor(int paintingColor) {
        this.paintingColor = paintingColor;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(-2, buf -> buf.writeInt(paintingColor));
        }
    }

    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != EnumFacing.UP && facing != EnumFacing.DOWN;
    }

    public final int getInputRedstoneSignal(EnumFacing side) {
        return getWorld().getStrongPower(getPos().offset(side));
    }

    public EnumFacing getFrontFacing() {
        return frontFacing;
    }

    public int getPaintingColor() {
        return paintingColor;
    }

    public IItemHandler getItemInventory() {
        return itemInventory;
    }

    public IFluidHandler getFluidInventory() {
        return fluidInventory;
    }

    public IItemHandlerModifiable getImportItems() {
        return importItems;
    }

    public IItemHandlerModifiable getExportItems() {
        return exportItems;
    }

    public FluidTankHandler getImportFluids() {
        return importFluids;
    }

    public FluidTankHandler getExportFluids() {
        return exportFluids;
    }

}