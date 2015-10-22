package gregtech.api.interfaces.metatileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGearEnergyTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.GT_Config;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Warning, this Interface has just been made to be able to add multiple kinds of MetaTileEntities (Cables, Pipes, Transformers, but not the regular Blocks)
 * <p/>
 * Don't implement this yourself and expect it to work. Extend @MetaTileEntity itself.
 */
public interface IMetaTileEntity extends ISidedInventory, IFluidTank, IFluidHandler, IGearEnergyTileEntity {
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
    public byte getTileEntityBaseType();

    /**
     * @param aTileEntity is just because the internal Variable "mBaseMetaTileEntity" is set after this Call.
     * @return a newly created and ready MetaTileEntity
     */
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);

    /**
     * @return an ItemStack representing this MetaTileEntity.
     */
    public ItemStack getStackForm(long aAmount);

    /**
     * new getter for the BaseMetaTileEntity, which restricts usage to certain Functions.
     */
    public IGregTechTileEntity getBaseMetaTileEntity();

    /**
     * Sets the BaseMetaTileEntity of this
     */
    public void setBaseMetaTileEntity(IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * when placing a Machine in World, to initialize default Modes. aNBT can be null!
     */
    public void initDefaultModes(NBTTagCompound aNBT);

    /**
     * ^= writeToNBT
     */
    public void saveNBTData(NBTTagCompound aNBT);

    /**
     * ^= readFromNBT
     */
    public void loadNBTData(NBTTagCompound aNBT);

    /**
     * Adds the NBT-Information to the ItemStack, when being dismanteled properly
     * Used to store Machine specific Upgrade Data.
     */
    public void setItemNBT(NBTTagCompound aNBT);

    /**
     * Called in the registered MetaTileEntity when the Server starts, to reset static variables
     */
    public void onServerStart();

    /**
     * Called in the registered MetaTileEntity when the Server ticks a World the first time, to load things from the World Save
     */
    public void onWorldLoad(File aSaveDirectory);

    /**
     * Called in the registered MetaTileEntity when the Server stops, to save the Game.
     */
    public void onWorldSave(File aSaveDirectory);

    /**
     * Called to set Configuration values for this MetaTileEntity.
     * Use aConfig.get(ConfigCategories.machineconfig, "MetaTileEntityName.Ability", DEFAULT_VALUE); to set the Values.
     */
    public void onConfigLoad(GT_Config aConfig);

    /**
     * If a Cover of that Type can be placed on this Side.
     * Also Called when the Facing of the Block Changes and a Cover is on said Side.
     */
    public boolean allowCoverOnSide(byte aSide, GT_ItemStack aStack);

    /**
     * When a Player rightclicks the Facing with a Screwdriver.
     */
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ);

    /**
     * When a Player rightclicks the Facing with a Wrench.
     */
    public boolean onWrenchRightClick(byte aSide, byte aWrenchingSide, EntityPlayer aPlayer, float aX, float aY, float aZ);

    /**
     * Called right before this Machine explodes
     */
    public void onExplosion();

    /**
     * The First processed Tick which was passed to this MetaTileEntity
     */
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * The Tick before all the generic handling happens, what gives a slightly faster reaction speed.
     * Don't use this if you really don't need to. @onPostTick is better suited for ticks.
     * This happens still after the Cover handling.
     */
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick);

    /**
     * The Tick after all the generic handling happened.
     * Recommended to use this like updateEntity.
     */
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick);

    /**
     * Called when this MetaTileEntity gets (intentionally) disconnected from the BaseMetaTileEntity.
     * Doesn't get called when this thing is moved by Frames or similar hacks.
     */
    public void inValidate();

    /**
     * Called when the BaseMetaTileEntity gets invalidated, what happens right before the @inValidate above gets called
     */
    public void onRemoval();

    /**
     * @param aFacing
     * @return if aFacing would be a valid Facing for this Device. Used for wrenching.
     */
    public boolean isFacingValid(byte aFacing);

    /**
     * @return the Server Side Container
     */
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * @return the Client Side GUI Container
     */
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity);

    /**
     * From new ISidedInventory
     */
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack);

    /**
     * From new ISidedInventory
     */
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack);

    /**
     * @return if aIndex is a valid Slot. false for things like HoloSlots. Is used for determining if an Item is dropped upon Block destruction and for Inventory Access Management
     */
    public boolean isValidSlot(int aIndex);

    /**
     * @return if aIndex can be set to Zero stackSize, when being removed.
     */
    public boolean setStackToZeroInsteadOfNull(int aIndex);

    /**
     * If this Side can connect to inputting pipes
     */
    public boolean isLiquidInput(byte aSide);

    /**
     * If this Side can connect to outputting pipes
     */
    public boolean isLiquidOutput(byte aSide);

    /**
     * Just an Accessor for the Name variable.
     */
    public String getMetaName();

    /**
     * @return true if the Machine can be accessed
     */
    public boolean isAccessAllowed(EntityPlayer aPlayer);

    /**
     * When a Machine Update occurs
     */
    public void onMachineBlockUpdate();

    /**
     * a Player rightclicks the Machine
     * Sneaky rightclicks are not getting passed to this!
     *
     * @return
     */
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ);

    /**
     * a Player leftclicks the Machine
     * Sneaky leftclicks are getting passed to this unlike with the rightclicks.
     */
    public void onLeftclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer);

    /**
     * Called Clientside with the Data got from @getUpdateData
     */
    public void onValueUpdate(byte aValue);

    /**
     * return a small bit of Data, like a secondary Facing for example with this Function, for the Client.
     * The BaseMetaTileEntity detects changes to this Value and will then send an Update.
     * This is only for Information, which is visible as Texture to the outside.
     * <p/>
     * If you just want to have an Active/Redstone State then set the Active State inside the BaseMetaTileEntity instead.
     */
    public byte getUpdateData();

    /**
     * For the rare case you need this Function
     */
    public void receiveClientEvent(byte aEventID, byte aValue);

    /**
     * Called to actually play the Sound.
     * Do not insert Client/Server checks. That is already done for you.
     * Do not use @playSoundEffect, Minecraft doesn't like that at all. Use @playSound instead.
     */
    public void doSound(byte aIndex, double aX, double aY, double aZ);

    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ);

    public void stopSoundLoop(byte aValue, double aX, double aY, double aZ);

    /**
     * Sends the Event for the Sound Triggers, only usable Server Side!
     */
    public void sendSound(byte aIndex);

    /**
     * Sends the Event for the Sound Triggers, only usable Server Side!
     */
    public void sendLoopStart(byte aIndex);

    /**
     * Sends the Event for the Sound Triggers, only usable Server Side!
     */
    public void sendLoopEnd(byte aIndex);

    /**
     * Called when the Machine explodes, override Explosion Code here.
     *
     * @param aExplosionPower
     */
    public void doExplosion(long aExplosionPower);

    /**
     * If this is just a simple Machine, which can be wrenched at 100%
     */
    public boolean isSimpleMachine();

    /**
     * If there should be a Lag Warning if something laggy happens during this Tick.
     * <p/>
     * The Advanced Pump uses this to not cause the Lag Message, while it scans for all close Fluids.
     * The Item Pipes and Retrievers neither send this Message, when scanning for Pipes.
     */
    public boolean doTickProfilingMessageDuringThisTick();

    /**
     * returns the DebugLog
     */
    public ArrayList<String> getSpecialDebugInfo(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, int aLogLevel, ArrayList<String> aList);

    /**
     * get a small Description
     */
    public String[] getDescription();

    /**
     * In case the Output Voltage varies.
     */
    public String getSpecialVoltageToolTip();

    /**
     * Icon of the Texture. If this returns null then it falls back to getTextureIndex.
     *
     * @param aSide       is the Side of the Block
     * @param aFacing     is the direction the Block is facing (or a Bitmask of all Connections in case of Pipes)
     * @param aColorIndex The Minecraft Color the Block is having
     * @param aActive     if the Machine is currently active (use this instead of calling mBaseMetaTileEntity.mActive!!!). Note: In case of Pipes this means if this Side is connected to something or not.
     * @param aRedstone   if the Machine is currently outputting a RedstoneSignal (use this instead of calling mBaseMetaTileEntity.mRedstone!!!)
     */
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone);

    /**
     * The Textures used for the Item rendering. Return null if you want the regular 3D Block Rendering.
     */
    //public ITexture[] getItemTexture(ItemStack aStack);

    /**
     * Register Icons here. This gets called when the Icons get initialized by the Base Block
     * Best is you put your Icons in a static Array for quick and easy access without relying on the MetaTileList.
     *
     * @param aBlockIconRegister The Block Icon Register
     */
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister);

    /**
     * @return true if you override the Rendering.
     */
    @SideOnly(Side.CLIENT)
    public boolean renderInInventory(Block aBlock, int aMeta, RenderBlocks aRenderer);

    /**
     * @return true if you override the Rendering.
     */
    @SideOnly(Side.CLIENT)
    public boolean renderInWorld(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, RenderBlocks aRenderer);

    /**
     * Gets the Output for the comparator on the given Side
     */
    public byte getComparatorValue(byte aSide);

    public float getExplosionResistance(byte aSide);

    public String[] getInfoData();

    public boolean isGivingInformation();

    public ItemStack[] getRealInventory();

    public boolean connectsToItemPipe(byte aSide);

    public void onColorChangeServer(byte aColor);

    public void onColorChangeClient(byte aColor);

    public int getLightOpacity();

    public void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB, List<AxisAlignedBB> outputAABB, Entity collider);

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ);

    public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity collider);

    /**
     * The onCreated Function of the Item Class redirects here
     */
    public void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer);
}