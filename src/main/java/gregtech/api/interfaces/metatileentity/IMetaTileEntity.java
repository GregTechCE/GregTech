package gregtech.api.interfaces.metatileentity;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.File;
import java.util.ArrayList;

/**
 * Warning, this Interface has just been made to be able to add multiple kinds of MetaTileEntities (Cables, Pipes, Transformers, but not the regular Blocks)
 * <p/>
 * Don't implement this yourself and expect it to work. Extend @MetaTileEntity itself.
 */
public interface IMetaTileEntity extends ISidedInventory, net.minecraftforge.fluids.IFluidHandler {

    /**
     * new getter for the BaseMetaTileEntity, which restricts usage to certain Functions.
     */
    IGregTechTileEntity getTile();

    /**
     * Sets the BaseMetaTileEntity of this
     */
    void setTile(IGregTechTileEntity baseMetaTileEntity);

    /**
     * when placing a Machine in World, to initialize default Modes. data can be null!
     */
    void initDefaultModes(NBTTagCompound data);

    /**
     * Adds the NBT-Information to the ItemStack, when being dismanteled properly
     * Used to store Machine specific Upgrade Data.
     */
    void setItemNBT(NBTTagCompound data);

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

    /**
     * @return if facing would be a valid Facing for this Device. Used for wrenching.
     */
    boolean isFacingValid(EnumFacing facing);

    /**
     * @return the Server Side Container
     */
    Object getServerGUI(int ID, InventoryPlayer playerInventory);

    /**
     * @return the Client Side GUI Container
     */
    Object getClientGUI(int ID, InventoryPlayer playerInventory);

    /**
     * From new ISidedInventory
     */
    boolean allowPullStack(int index, EnumFacing side, ItemStack stack);

    /**
     * From new ISidedInventory
     */
    boolean allowPutStack(int index, EnumFacing side, ItemStack stack);

    /**
     * @return if aIndex is a valid Slot. false for things like HoloSlots. Is used for determining if an Item is dropped upon Block destruction and for Inventory Access Management
     */
    boolean isValidSlot(int index);

    /**
     * If this Side can connect to inputting pipes
     */
    boolean isLiquidInput(EnumFacing side);

    /**
     * If this Side can connect to outputting pipes
     */
    boolean isLiquidOutput(EnumFacing side);

    /**
     * Just an Accessor for the Name variable.
     */
    String getMetaName();

    /**
     * @return true if the Machine can be accessed
     */
    boolean isAccessAllowed(EntityPlayer player);

    /**
     * When a Machine Update occurs
     */
    void onMachineBlockUpdate();

    /**
     * a Player rightclicks the Machine
     * Sneaky rightclicks are not getting passed to this!
     *
     */
    boolean onRightclick(EntityPlayer player, EnumFacing side, float clickX, float clickY, float clickZ);

    /**
     * a Player leftclicks the Machine
     * Sneaky leftclicks are getting passed to this unlike with the rightclicks.
     */
    void onLeftclick(EntityPlayer player);

    /**
     * Called Clientside with the Data got from @getUpdateData
     */
    void onUpdateDataReceived(byte value);

    /**
     * return a small bit of Data, like a secondary Facing for example with this Function, for the Client.
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
     * If there should be a Lag Warning if something laggy happens during this Tick.
     * <p/>
     * The Advanced Pump uses this to not cause the Lag Message, while it scans for all close Fluids.
     * The Item Pipes and Retrievers neither send this Message, when scanning for Pipes.
     */
    boolean doTickProfilingMessageDuringThisTick();

    /**
     * returns the DebugLog
     */
    ArrayList<String> getSpecialDebugInfo(EntityPlayer player, int logLevel, ArrayList<String> list);

    /**
     * get a small Description
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    String[] getDescription(ItemStack tileStack);

    /**
     * Gets the Output for the comparator on the given Side
     */
    byte getComparatorValue(EnumFacing side);

    float getExplosionResistance(EnumFacing side);

    String[] getInfoData();

    boolean isGivingInformation();

    ItemStack[] getRealInventory();

    boolean connectsToItemPipe(EnumFacing side);

    void onColorChangeServer(byte color);

    void onColorChangeClient(byte color);

    int getLightOpacity();

    void onEntityCollidedWithBlock(World world, BlockPos pos, Entity collider);

    /**
     * This determines the BaseMetaTileEntity belonging to this MetaTileEntity by using the Meta ID of the Block itself.
     * <p/>
     * 0 = BaseMetaTileEntity, Wrench lvl 0 to dismantle
     * 1 = BaseMetaTileEntity, Wrench lvl 1 to dismantle
     * 2 = BaseMetaTileEntity, Wrench lvl 2 to dismantle
     * 3 = BaseMetaTileEntity, Wrench lvl 3 to dismantle
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    byte getTileEntityBaseType();

    /**
     * @param tileEntity is just because the internal Variable "mBaseMetaTileEntity" is set after this Call.
     * @return a newly created and ready MetaTileEntity
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    IMetaTileEntity newMetaEntity(IGregTechTileEntity tileEntity);

    /**
     * @return an ItemStack representing this MetaTileEntity.
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    ItemStack getStackForm(long amount);

    /**
     * Called in the registered MetaTileEntity when the Server starts, to reset static variables
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    void onServerStart();

    /**
     * Called in the registered MetaTileEntity when the Server ticks a World the first time, to load things from the World Save
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    void onWorldLoad(File saveDirectory);

    /**
     * Called in the registered MetaTileEntity when the Server stops, to save the Game.
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    void onWorldSave(File saveDirectory);

    /**
     * Called to set Configuration values for this MetaTileEntity.
     * Use config.get(ConfigCategories.machineconfig, "MetaTileEntityName.Ability", DEFAULT_VALUE); to set the Values.
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    void onConfigLoad(GT_Config config);

    /**
     * The onCreated Function of the Item Class redirects here
     *
     * CALLED ON SAMPLE TILE ENTITY. BASE TILE IS NULL!
     */
    void onCreated(ItemStack stack, World world, EntityPlayer player);

}