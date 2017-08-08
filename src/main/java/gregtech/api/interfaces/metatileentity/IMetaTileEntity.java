package gregtech.api.interfaces.metatileentity;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.material.Dyes;
import gregtech.api.util.GT_Config;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.ArrayList;

public interface IMetaTileEntity {

    String getMetaName();

    String getLocalizedName();

    /**
     * getter for the BaseMetaTileEntity, which restricts usage to certain Functions.
     */
    IGregTechTileEntity getHolder();

    /**
     * Sets the BaseMetaTileEntity of this
     */
    void setHolder(IGregTechTileEntity baseMetaTileEntity);

    /**
     * when placing a Machine in World, to initialize default Modes. data can be null!
     */
    void initFromItemStackData(NBTTagCompound data);

    /**
     * Adds the NBT-Information to the ItemStack, when being dismanteled properly
     * Used to store Machine specific Upgrade Data.
     */
    void writeItemStackData(NBTTagCompound data);

    /**
     * writeToNBT
     */
    void saveNBTData(NBTTagCompound data);

    /**
     * readFromNBT
     */
    void loadNBTData(NBTTagCompound data);

    /**
     * If a Cover of that Type can be placed on this Side.
     * Also Called when the Facing of the Block Changes and a Cover is on said Side.
     */
    boolean allowCoverOnSide(EnumFacing side, int coverId);

    /**
     * When a Player rightclicks the Facing with a Screwdriver.
     */
    void onScrewdriverRightClick(EnumFacing side, EntityPlayer player, float clickX, float clickY, float clickZ);

    /**
     * When a Player rightclicks the Facing with a Wrench.
     */
    boolean onWrenchRightClick(EnumFacing side, EnumFacing wrenchingSide, EntityPlayer player, float clickX, float clickY, float clickZ);

    /**
     * Called right before this Machine explodes
     */
    void onExplosion();

    /**
     * The First processed Tick which was passed to this MetaTileEntity
     */
    void onFirstTick();

    /**
     * The Tick before all the generic handling happens, what gives a slightly faster reaction speed.
     * Don't use this if you really don't need to. @onPostTick is better suited for ticks.
     * This happens still after the Cover handling.
     */
    void onPreTick(long tickTimer);

    /**
     * The Tick after all the generic handling happened.
     * Recommended to use this like updateEntity.
     */
    void onPostTick(long tickTimer);

    /**
     * Called when this MetaTileEntity gets (intentionally) disconnected from the BaseMetaTileEntity.
     * Doesn't get called when this thing is moved by Frames or similar hacks.
     */
    void inValidate();

    /**
     * Called when the BaseMetaTileEntity gets invalidated, what happens right before the @inValidate above gets called
     */
    void onRemoval();

    EnumFacing getFrontFacing();

    void setFrontFacing(EnumFacing facing);

    /**
     * @return if facing would be a valid Facing for this Device. Used for wrenching.
     */
    boolean isFacingValid(EnumFacing facing);

    /**
     * @return the Server Side Container
     */
    Container getServerGUI(int ID, InventoryPlayer playerInventory);

    /**
     * @return the Client Side GUI Container
     */
    @SideOnly(Side.CLIENT)
    GuiContainer getClientGUI(int ID, InventoryPlayer playerInventory);

    int getSlotsCount();
    int[] getSlotsForFace(EnumFacing face);
    //side == null - internal inventory change
    boolean allowPullStack(int index, EnumFacing side, ItemStack stack);
    boolean allowPutStack(int index, EnumFacing side, ItemStack stack);

    /**
     * @return a COPY of stack in slot. Actual stack won't change.
     */
    ItemStack getStackInSlot(int index);
    void setStackInSlot(int index, ItemStack stack);
    boolean isValidSlot(int index);

    int getTanksCount();
    int[] getTanksForFace(EnumFacing face);
    //side == null - internal tank change
    boolean allowPullFluid(int tankIndex, EnumFacing side, FluidStack fluidStack);
    boolean allowPutFluid(int tankIndex, EnumFacing side, FluidStack fluidStack);

    /**
     * @return a COPY of stack in slot. Actual stack won't change.
     */
    FluidStack getFluidInTank(int tankIndex);
    void setFluidInTank(int index, FluidStack fluidStack);
    boolean isValidFluidTank(int tankIndex);

    /**
     * @return true if the Machine can be accessed
     */
    boolean isAccessAllowed(EntityPlayer player);

    /**
     * Called when machine update occur
     */
    void onMachineBlockUpdate();

    /**
     * Called when a player rightclicks the machine
     * Sneaky rightclicks are not getting passed to this!
     */
    boolean onRightClick(EntityPlayer player, EnumFacing side, float clickX, float clickY, float clickZ);

    /**
     * Called when a player leftclicks the machine
     * Sneaky leftclicks are getting passed to this unlike with the rightclicks.
     */
    void onLeftClick(EntityPlayer player);

    /**
     * Called Clientside with the Data got from @getUpdateData
     */
    void onUpdateDataReceived(byte value);

    /**
     * Return a small bit of data, like a secondary facing for example with this Function, for the Client.
     * The BaseMetaTileEntity detects changes to this Value and will then send an Update.
     * This is only for Information, which is visible as Texture to the outside.
     * <p/>
     * If you just want to have an Active/Redstone State then set the Active State inside the BaseMetaTileEntity instead.
     */
    byte getUpdateData();

    /**
     * For the rare case you need this Function
     */
    void receiveClientEvent(byte eventID, byte value);

    /**
     * Called when the Machine explodes, override Explosion Code here.
     */
    void doExplosion(long explosionPower);

    /**
     * Gets the Output for the comparator on the given Side
     */
    byte getComparatorValue(EnumFacing side);

    float getExplosionResistance(EnumFacing side);

    String[] getInfoData();

    void onColorChangeServer(Dyes color);

    void onColorChangeClient(Dyes color);

    int getLightOpacity();

    void onEntityCollidedWithBlock(Entity collider);

}