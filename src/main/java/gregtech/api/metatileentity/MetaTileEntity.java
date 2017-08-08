package gregtech.api.metatileentity;

import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.material.Dyes;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

public abstract class MetaTileEntity implements IMetaTileEntity {

    public final String name;
    private long energyStored;

    protected final ItemStack[] itemInventory;
    protected final FluidStack[] fluidInventory;
    protected EnumFacing frontFacing = EnumFacing.NORTH;

    private IGregTechTileEntity holder;

    public MetaTileEntity(String name) {
        this.name = name;
        this.itemInventory = new ItemStack[getSlotsCount()];
        this.fluidInventory = new FluidStack[getTanksCount()];
        this.energyStored = 0L;
    }

    @Override
    public String getMetaName() {
        return this.name;
    }

    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation("gt.machine." + name);
    }

    @Override
    public IGregTechTileEntity getHolder() {
        return holder;
    }

    @Override
    public void setHolder(IGregTechTileEntity holder) {
        holder.setMetaTileEntity(this);
        this.holder = holder;
    }

    /**
     * @return the amount of slots extender tile entity use
     * Total amount of slots also adds decharger and charger slots
     */
    public abstract int getOrdinarySlotsCount();

    @Override
    public int getSlotsCount() {
        return getOrdinarySlotsCount() + getDechargerSlotCount() + getRechargerSlotCount();
    }

    @Override
    public int[] getSlotsForFace(EnumFacing face) {
        int[] slots = new int[getSlotsCount()];
        int lastIndex = 0;
        for(int i = 0; i < getSlotsCount(); i++) {
            if(allowPullStack(i, face, null) || allowPutStack(i, face, null)) {
                slots[lastIndex++] = i;
            }
        }
        return Arrays.copyOf(slots, lastIndex);
    }

    @Override
    public boolean allowPullStack(int index, EnumFacing side, ItemStack stack) {
        return isValidSlot(index);
    }

    @Override
    public boolean allowPutStack(int index, EnumFacing side, ItemStack stack) {
        return isValidSlot(index);
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.itemInventory[index];
    }

    @Override
    public void setStackInSlot(int index, ItemStack stack) {
        this.itemInventory[index] = stack == null || stack.stackSize <= 0 ? null : stack;
        holder.markDirty();
    }

    @Override
    public boolean isValidSlot(int index) {
        return true;
    }

    @Override
    public int[] getTanksForFace(EnumFacing face) {
        int[] slots = new int[getTanksCount()];
        int lastIndex = 0;
        for(int i = 0; i < getTanksCount(); i++) {
            if(allowPullFluid(i, face, null) || allowPutFluid(i, face, null)) {
                slots[lastIndex++] = i;
            }
        }
        return Arrays.copyOf(slots, lastIndex);
    }

    @Override
    public boolean allowPullFluid(int tankIndex, EnumFacing side, FluidStack fluidStack) {
        return isValidFluidTank(tankIndex);
    }

    @Override
    public boolean allowPutFluid(int tankIndex, EnumFacing side, FluidStack fluidStack) {
        return isValidFluidTank(tankIndex);
    }

    @Override
    public FluidStack getFluidInTank(int tankIndex) {
        return this.fluidInventory[tankIndex];
    }

    @Override
    public void setFluidInTank(int index, FluidStack fluidStack) {
        this.fluidInventory[index] = fluidStack == null || fluidStack.amount <= 0 ? null : fluidStack;
        holder.markDirty();
    }

    @Override
    public boolean onRightClick(EntityPlayer player, EnumFacing side, float clickX, float clickY, float clickZ) {
        return false;
    }

    @Override
    public void onLeftClick(EntityPlayer player) {

    }

    @Override
    public void onScrewdriverRightClick(EnumFacing side, EntityPlayer player, float clickX, float clickY, float clickZ) {

    }

    @Override
    public boolean onWrenchRightClick(EnumFacing side, EnumFacing wrenchingSide, EntityPlayer player, float clickX, float clickY, float clickZ) {
        if(isFacingValid(wrenchingSide)) {
            setFrontFacing(wrenchingSide);
            return true;
        }
        return false;
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
    public Container getServerGUI(int ID, InventoryPlayer playerInventory) {
        return null;
    }

    @Override
    public GuiContainer getClientGUI(int ID, InventoryPlayer playerInventory) {
        return null;
    }

    @Override
    public int getTanksCount() {
        return 0;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer player) {
        return true; //default - to be overridden
    }

    @Override
    public void onMachineBlockUpdate() {
    }

    @Override
    public void onUpdateDataReceived(byte value) {

    }

    @Override
    public byte getUpdateData() {
        return 0;
    }

    @Override
    public void receiveClientEvent(byte eventID, byte value) {
    }

    @Override
    public byte getComparatorValue(EnumFacing side) {
        return 0;
    }

    @Override
    public float getExplosionResistance(EnumFacing side) {
        return Math.max(getInputTier(), getOutputTier()) * 0.8f;
    }

    @Override
    public String[] getInfoData() {
        return new String[0];
    }

    @Override
    public void onColorChangeServer(Dyes color) {
    }

    @Override
    public void onColorChangeClient(Dyes color) {
    }

    @Override
    public int getLightOpacity() {
        return 0;
    }

    @Override
    public void onEntityCollidedWithBlock(Entity collider) {
    }

    @Override
    public boolean isValidFluidTank(int tankIndex) {
        return true;
    }

    @Override
    public boolean isFacingValid(EnumFacing facing) {
        return true;
    }

    @Override
    public boolean allowCoverOnSide(EnumFacing aSide, int coverId) {
        return true;
    }

    @Override
    public void initFromItemStackData(NBTTagCompound data) {
    }

    @Override
    public void writeItemStackData(NBTTagCompound data) {
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        if(this.energyStored > 0) {
            data.setLong("EnergyStored", this.energyStored);
        }
        data.setInteger("Facing", frontFacing.getIndex());
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        if(data.hasKey("EnergyStored", Constants.NBT.TAG_LONG)) {
            this.energyStored = data.getLong("EnergyStored");
        }
        if(data.hasKey("Facing", Constants.NBT.TAG_INT)) {
            this.frontFacing = EnumFacing.VALUES[data.getInteger("Facing")];
        }
    }

    public boolean isElectric() {
        return false;
    }

    /**
     * @return true if this device emits energy
     */
    public boolean isEnetOutput() {
        return false;
    }

    /**
     * @return true if this device consumes energy
     */
    public boolean isEnetInput() {
        return false;
    }

    /**
     * @return amount of EU that can be stored in device
     */
    public long getMaxEUStore() {
        return 0L;
    }

    /**
     * @return maximum amount of EU/t this machine can input per tick
     * Passing more EU/t will explode this machine
     */
    public long getMaxEUInput() {
        return 0L;
    }

    /**
     * @return maximum amount of EU/t this machine can output per tick
     */
    public long getMaxEUOutput() {
        return 0;
    }

    /**
     * @return amount of amperes this machine can output at once.
     */
    public long getMaxAmperesOut() {
        return 1;
    }

    /**
     * @return amount of amperes this machine can receive at once
     * Passing more amperes than this machine can receive won't blow it up.
     */
    public long getMaxAmperesIn() {
        return 1;
    }

    public long getEUStored() {
        return energyStored;
    }

    public void setEUStored(long energy) {
        this.energyStored = energy;
    }

    /**
     * @return the amount of EU, which this Device stores before starting to emit Energy.
     * useful if you don't want to emit stored Energy until a certain Level is reached.
     */
    public long getMinimumStoredEU() {
        return GT_Values.V[getInputTier()] * 8;
    }

    /**
     * Determines the Tier of the Machine, used for de-charging Tools.
     */
    public int getInputTier() {
        return GT_Utility.getTier(getMaxEUInput());
    }

    /**
     * Determines the Tier of the Machine, used for charging Tools.
     */
    public long getOutputTier() {
        return GT_Utility.getTier(getMaxEUOutput());
    }

    /**
     * gets the amount of recharger slots
     */
    public int getRechargerSlotCount() {
        return 0;
    }

    /**
     * gets the amount of decharger slots
     */
    public int getDechargerSlotCount() {
        return 0;
    }

    /**
     * @return true if you use getProgressTime and getMaxProgressTime + increaseProgress
     * This is used mainly for cover behaviors
     */
    public boolean hasProgressTime() {
        return false;
    }

    /**
     * Progress this machine has already made
     */
    public int getProgressTime() {
        return 0;
    }

    /**
     * Progress this machine has to do to produce something
     */
    public int getMaxProgressTime() {
        return 0;
    }

    /**
     * Increases the progress, returns the overflown progress.
     */
    public int increaseProgress(int aProgress) {
        return 0;
    }

    /**
     * If this TileEntity makes use of Sided Redstone behaviors.
     * Determines only, if the Output Redstone Array is getting filled with 0 for true, or 15 for false.
     */
    public boolean hasSidedRedstoneOutputBehavior() {
        return false;
    }

    /**
     * Called when the facing gets changed
     */
    public void onFacingChange() {
    }

    @Override
    public void onExplosion() {
        doExplosion(0L);
    }

    @Override
    public void doExplosion(long explosionPower) {
        float strength = Math.max(Math.max(getInputTier(), getOutputTier()) * 1.0f, explosionPower);
        BlockPos pos = holder.getWorldPos();
        World world = holder.getWorldObj();
        GT_Utility.playSound(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, GregTech_API.sSoundList.get(209), SoundCategory.BLOCKS, 1.0f, 1.0f);
        if (GregTech_API.sMachineExplosions) {
            world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, strength, true);
        }
    }

}