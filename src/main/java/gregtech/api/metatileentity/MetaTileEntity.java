package gregtech.api.metatileentity;

import com.google.common.base.Preconditions;
import gregtech.api.GregTech_API;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.unification.Dyes;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Arrays;

public abstract class MetaTileEntity implements IMetaTileEntity {

    public final IMetaTileEntityFactory factory;

    protected final ItemStack[] itemInventory;
    protected final FluidStack[] fluidInventory;
    protected EnumFacing frontFacing = EnumFacing.NORTH;

    protected int[] sidedRedstoneOutput = new int[6];

    IGregTechTileEntity holder;

    public MetaTileEntity(IMetaTileEntityFactory factory) {
        this.factory = factory;
        this.itemInventory = new ItemStack[getSlotsCount()];
        this.fluidInventory = new FluidStack[getTanksCount()];
    }

    @Override
    public String getMetaName() {
        return GregTech_API.METATILEENTITY_REGISTRY.getNameForObject(factory);
    }

    @Override
    public String getUnlocalizedName() {
        return "gt.machine." + getMetaName();
    }

    @Override
    public ITextComponent getLocalizedName() {
        return new TextComponentTranslation(getUnlocalizedName());
    }

    @Override
    public boolean canConnectRedstone(@Nullable EnumFacing side) {
        return true;
    }

    @Override
    public int getInputRedstoneSignal(EnumFacing side) {
        return getWorldObj().getStrongPower(getWorldPos().offset(side));
    }

    @Override
    public World getWorldObj() {
        return holder.getWorldObj();
    }

    @Override
    public BlockPos getWorldPos() {
        return holder.getWorldPos();
    }

    @Override
    public long getTimer() {
        return holder.getTimer();
    }

    @Override
    public void markDirty() {
        holder.markDirty();
    }

    @Override
    public int getOutputRedstoneSignal(@Nullable EnumFacing side) {
        return side == null ? Arrays.stream(sidedRedstoneOutput).sorted().findFirst().orElse(0) : sidedRedstoneOutput[side.getIndex()];
    }

    @Override
    public void setOutputRedstoneSignal(EnumFacing side, int strength) {
        Preconditions.checkNotNull(side);
        this.sidedRedstoneOutput[side.getIndex()] = strength;
        BlockPos relative = getWorldPos().offset(side);
        getWorldObj().markBlockRangeForRenderUpdate(relative, relative);
        markDirty();
    }

    @Override
    public boolean isValidFacing(EnumFacing side) {
        return true;
    }

    @Override
    public ResourceLocation getModelLocation() {
        return null;
    }

    @Override
    public IBlockState getModelState() {
        return null;
    }

    @Override
    public boolean allowCoverOnSide(EnumFacing side, int coverId) {
        return false;
    }

    @Override
    public int getSlotsCount() {
        return 0;
    }

    @Override
    public IGregTechTileEntity getHolder() {
        return holder;
    }

    @Override
    public EnumFacing getFrontFacing() {
        return frontFacing;
    }

    @Override
    public void setFrontFacing(EnumFacing facing) {
        this.frontFacing = facing;
        onFacingChange();
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
    public boolean allowPullFluid(int tankIndex, @Nullable EnumFacing side, @Nullable Fluid fluid) {
        return isValidFluidTank(tankIndex);
    }

    @Override
    public boolean allowPutFluid(int tankIndex, @Nullable EnumFacing side, @Nullable Fluid fluid) {
        return isValidFluidTank(tankIndex);
    }

    @Override
    public @Nullable FluidStack getFluidInTank(int tankIndex) {
        return this.fluidInventory[tankIndex];
    }

    @Override
    public void setFluidInTank(int index, @Nullable FluidStack fluidStack) {
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
    public int getComparatorValue(EnumFacing side) {
        return 0;
    }

    @Override
    public float getExplosionResistance(EnumFacing side) {
        return 2.0f;
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
    public void initFromItemStackData(NBTTagCompound data) {
    }

    @Override
    public void writeItemStackData(NBTTagCompound data) {
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        data.setInteger("Facing", frontFacing.getIndex());
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        if(data.hasKey("Facing", Constants.NBT.TAG_INT)) {
            this.frontFacing = EnumFacing.VALUES[data.getInteger("Facing")];
        }
    }

    /**
     * Called when the facing gets changed
     */
    protected void onFacingChange() {
    }

    @Override
    public void onExplosion() {
        doExplosion(1L);
    }

    @Override
    public void doExplosion(long strength) {
        BlockPos pos = holder.getWorldPos();
        World world = holder.getWorldObj();
        GT_Utility.playSound(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, GregTech_API.sSoundList.get(209), SoundCategory.BLOCKS, 1.0f, 1.0f);
        if (GregTech_API.sMachineExplosions) {
            world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, strength, true);
        }
    }

}