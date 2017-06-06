package gregtech.api.interfaces.metatileentity;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.GT_Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Warning, this Interface has just been made to be able to add multiple kinds of MetaTileEntities (Cables, Pipes, Transformers, but not the regular Blocks)
 * <p/>
 * Don't implement this yourself and expect it to work. Extend @MetaTileEntity itself.
 */
public interface IMetaTileEntity extends ISidedInventory, net.minecraftforge.fluids.IFluidHandler {

    /**
     * This determines the BaseMetaTileEntity belonging to this MetaTileEntity by using the Meta ID of the Block itself.
     * <p/>
     * 0 = BaseMetaTileEntity, Wrench lvl 0 to dismantle
     * 1 = BaseMetaTileEntity, Wrench lvl 1 to dismantle
     * 2 = BaseMetaTileEntity, Wrench lvl 2 to dismantle
     * 3 = BaseMetaTileEntity, Wrench lvl 3 to dismantle
     * 4 = BaseMetaPipeEntity, Wrench lvl 0 to dismantle
     * 5 = BaseMetaPipeEntity, Wrench lvl 1 to dismantle
     * 6 = BaseMetaPipeEntity, Wrench lvl 2 to dismantle
     * 7 = BaseMetaPipeEntity, Wrench lvl 3 to dismantle
     * 8 = BaseMetaPipeEntity, Cutter lvl 0 to dismantle
     * 9 = BaseMetaPipeEntity, Cutter lvl 1 to dismantle
     * 10 = BaseMetaPipeEntity, Cutter lvl 2 to dismantle
     * 11 = BaseMetaPipeEntity, Cutter lvl 3 to dismantle
     * 12 = BaseMetaPipeEntity, Axe lvl 0 to dismantle
     * 13 = BaseMetaPipeEntity, Axe lvl 1 to dismantle
     * 14 = BaseMetaPipeEntity, Axe lvl 2 to dismantle
     * 15 = BaseMetaPipeEntity, Axe lvl 3 to dismantle
     */
    byte getTileEntityBaseType();

    /**
     * @param aTileEntity is just because the internal Variable "mBaseMetaTileEntity" is set after this Call.
     * @return a newly created and ready MetaTileEntity
     */
    IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);

    /**
     * @return an ItemStack representing this MetaTileEntity.
     */
    ItemStack getStackForm(long aAmount);

    /**
     * new getter for the BaseMetaTileEntity, which restricts usage to certain Functions.
     */
    IGregTechTileEntity getBaseMetaTileEntity();

    /**
     * Sets the BaseMetaTileEntity of this
     */
    void setBaseMetaTileEntity(IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * when placing a Machine in World, to initialize default Modes. aNBT can be null!
     */
    void initDefaultModes(NBTTagCompound aNBT);

    /**
     * ^= writeToNBT
     */
    void saveNBTData(NBTTagCompound aNBT);

    /**
     * ^= readFromNBT
     */
    void loadNBTData(NBTTagCompound aNBT);

    /**
     * Adds the NBT-Information to the ItemStack, when being dismanteled properly
     * Used to store Machine specific Upgrade Data.
     */
    void setItemNBT(NBTTagCompound aNBT);

    /**
     * Called in the registered MetaTileEntity when the Server starts, to reset static variables
     */
    void onServerStart();

    /**
     * Called in the registered MetaTileEntity when the Server ticks a World the first time, to load things from the World Save
     */
    void onWorldLoad(File aSaveDirectory);

    /**
     * Called in the registered MetaTileEntity when the Server stops, to save the Game.
     */
    void onWorldSave(File aSaveDirectory);

    /**
     * Called to set Configuration values for this MetaTileEntity.
     * Use aConfig.get(ConfigCategories.machineconfig, "MetaTileEntityName.Ability", DEFAULT_VALUE); to set the Values.
     */
    void onConfigLoad(GT_Config aConfig);

    /**
     * If a Cover of that Type can be placed on this Side.
     * Also Called when the Facing of the Block Changes and a Cover is on said Side.
     */
    boolean allowCoverOnSide(EnumFacing aSide, GT_ItemStack aStack);

    /**
     * When a Player rightclicks the Facing with a Screwdriver.
     */
    void onScrewdriverRightClick(EnumFacing aSide, EntityPlayer aPlayer, float aX, float aY, float aZ);

    /**
     * When a Player rightclicks the Facing with a Wrench.
     */
    boolean onWrenchRightClick(EnumFacing aSide, EnumFacing aWrenchingSide, EntityPlayer aPlayer, float aX, float aY, float aZ);

    /**
     * Called right before this Machine explodes
     */
    void onExplosion();

    /**
     * The First processed Tick which was passed to this MetaTileEntity
     */
    void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * The Tick before all the generic handling happens, what gives a slightly faster reaction speed.
     * Don't use this if you really don't need to. @onPostTick is better suited for ticks.
     * This happens still after the Cover handling.
     */
    void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick);

    /**
     * The Tick after all the generic handling happened.
     * Recommended to use this like updateEntity.
     */
    void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick);

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
     * @param aFacing
     * @return if aFacing would be a valid Facing for this Device. Used for wrenching.
     */
    boolean isFacingValid(EnumFacing aFacing);

    /**
     * @return the Server Side Container
     */
    Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * @return the Client Side GUI Container
     */
    Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * From new ISidedInventory
     */
    boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, EnumFacing aSide, ItemStack aStack);

    /**
     * From new ISidedInventory
     */
    boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, EnumFacing aSide, ItemStack aStack);

    /**
     * @return if aIndex is a valid Slot. false for things like HoloSlots. Is used for determining if an Item is dropped upon Block destruction and for Inventory Access Management
     */
    boolean isValidSlot(EnumFacing aIndex);

    /**
     * @return if aIndex can be set to Zero stackSize, when being removed.
     */
    boolean setStackToZeroInsteadOfNull(EnumFacing aIndex);

    /**
     * If this Side can connect to inputting pipes
     */
    boolean isLiquidInput(EnumFacing aSide);

    /**
     * If this Side can connect to outputting pipes
     */
    boolean isLiquidOutput(EnumFacing aSide);

    /**
     * Just an Accessor for the Name variable.
     */
    String getMetaName();

    /**
     * @return true if the Machine can be accessed
     */
    boolean isAccessAllowed(EntityPlayer aPlayer);

    /**
     * When a Machine Update occurs
     */
    void onMachineBlockUpdate();

    /**
     * a Player rightclicks the Machine
     * Sneaky rightclicks are not getting passed to this!
     *
     * @return
     */
    boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, EnumFacing aSide, float aX, float aY, float aZ, EnumHand hand);

    /**
     * a Player leftclicks the Machine
     * Sneaky leftclicks are getting passed to this unlike with the rightclicks.
     */
    void onLeftclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, EnumHand hand);

    /**
     * Called Clientside with the Data got from @getUpdateData
     */
    void onValueUpdate(byte aValue);

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
    void receiveClientEvent(byte aEventID, byte aValue);

    /**
     * Called when the Machine explodes, override Explosion Code here.
     *
     * @param aExplosionPower
     */
    void doExplosion(long aExplosionPower);

    /**
     * If this is just a simple Machine, which can be wrenched at 100%
     */
    boolean isSimpleMachine();

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
    ArrayList<String> getSpecialDebugInfo(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, int aLogLevel, ArrayList<String> aList);

    /**
     * get a small Description
     */
    String[] getDescription();


    /**
     * Gets the Output for the comparator on the given Side
     */
    byte getComparatorValue(EnumFacing aSide);

    float getExplosionResistance(EnumFacing aSide);

    String[] getInfoData();

    boolean isGivingInformation();

    ItemStack[] getRealInventory();

    boolean connectsToItemPipe(EnumFacing aSide);

    void onColorChangeServer(byte aColor);

    void onColorChangeClient(byte aColor);

    int getLightOpacity();
    void setLightOpacity(int lightOpacity);

    void onEntityCollidedWithBlock(World aWorld, BlockPos aPos, Entity collider);

    /**
     * The onCreated Function of the Item Class redirects here
     */
    void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer);

}