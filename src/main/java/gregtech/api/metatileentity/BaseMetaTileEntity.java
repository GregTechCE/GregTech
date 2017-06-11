package gregtech.api.metatileentity;

import com.google.common.collect.Lists;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.net.GT_Packet_TileEntity;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.*;
import gregtech.common.GT_Pollution;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.Info;
import ic2.api.tile.IEnergyStorage;
import net.minecraft.block.BlockFire;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.wrappers.FluidHandlerWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static gregtech.api.enums.GT_Values.NW;
import static gregtech.api.enums.GT_Values.V;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main TileEntity for EVERYTHING.
 */
public class BaseMetaTileEntity extends BaseTileEntity implements IGregTechTileEntity, IEnergySink, IEnergySource, IEnergyStorage {

    /**
     * Id of MetaTileEntity this machine holds
     * @see #setMetaTileEntity(IMetaTileEntity)
     */
    private short mID = 0;

    /**
     * Tick timer of these tile entity
     * increased by 1 every tick
     */
    private long mTickTimer = 0;

    /**
     * MetaTileEntity that this TileEntity holds reference
     */
    protected MetaTileEntity mMetaTileEntity;

    /**
     * Internal energy stored in EU
     */
    protected long mStoredEnergy = 0;

    /**
     * When true, machine emits energy in all directions and doesn't accept it
     * used to cause network overflow during explosion in {@link #doExplosion(long)}
     **/
    protected boolean mReleaseEnergy = false;

    /**
     * Current redstone outputs from sides (in D-U-N-S-W-E order)
     */
    private byte[] mSidedRedstone = new byte[] {15, 15, 15, 15, 15, 15};

    /**
     * Cover ids at machine sides (in D-U-N-S-W-E order)
     *
     * @see GT_CoverBehavior#doCoverThings(net.minecraft.util.EnumFacing, byte, int, int, gregtech.api.interfaces.tileentity.ICoverable, long)
     */
    private int[] mCoverSides = new int[] {0, 0, 0, 0, 0, 0};

    /**
     * Data returned from last call of GT_CoverBehavior.doCoverThings on each side (in D-U-N-S-W-E order)
     *
     * @see GT_CoverBehavior#doCoverThings(net.minecraft.util.EnumFacing, byte, int, int, gregtech.api.interfaces.tileentity.ICoverable, long)
     */
    private int[] mCoverData = new int[] {0, 0, 0, 0, 0, 0};

    /**
     * Time that tile entity spent for last {@link gregtech.api.GregTech_API#TICKS_FOR_LAG_AVERAGING}
     */
    private int[] mTimeStatistics = new int[GregTech_API.TICKS_FOR_LAG_AVERAGING];

    /**
     * True if tile entity has enough energy to work, false otherwise
     */
    private boolean mHasEnoughEnergy = true;

    /**
     * True if auto-input (fluids, items) disabled
     */
    private boolean mInputDisabled = false;

    /**
     * True if auto-output (fluids, items) disabled
     */
    private boolean mOutputDisabled = false;

    /**
     * True if muffler update installed on this tile entity
     */
    private boolean mMuffler = false;

    /**
     * True if lock upgrade installed on this tile entity
     */
    private boolean mLockUpgrade = false;

    /**
     * True if tile entity currently active and do some work
     */
    private boolean mActive = false;

    /**
     * True if redstone output is enabled (in general)
     */
    private boolean mRedstone = false;

    /**
     * True if work was updated since last tick
     * Will be set to false in current tick
     */
    private boolean mWorkUpdate = false;

    /**
     * True if inventory was updated since last tick
     * Will be set to false in current tick
     */
    private boolean mInventoryChanged = false;

    /**
     * True if machine currently working (doing something)
     */
    private boolean mWorks = true;

    /**
     * True if machine received a {@link #issueTextureUpdate()} request since last tick
     * Will be set to false in current tick and machine rendering will be updated
     */
    private boolean mNeedsUpdate = true;

    /**
     * True if machine received a {@link #issueBlockUpdate()} request since last tick
     * Will be set to false in current tick and machine will send onNeighbourChanged to relative blocks
     */
    private boolean mNeedsBlockUpdate = true;

    /**
     * True if machine received a {@link #issueClientUpdate()} request since last tick
     * Will be set to false in current tick and machine will send update packet to clients
     */
    private boolean mSendClientData = false;

    /**
     * Total amount of amperes machine accepted since last tick
     * Will be set to zero in current tick
     */
    private long mAcceptedAmperes = Long.MAX_VALUE;

    /**
     * Work data for redstone control of machine
     * @see #setWorkDataValue(byte)
     */
    private byte mWorkData = 0;

    /**
     * Color of machine
     * @see gregtech.api.enums.Dyes
     */
    private byte mColor = 0;

    /**
     * Bit-masked value of strong redstone output of each side
     * @see #setStrongOutputRedstoneSignal(EnumFacing, byte)
     */
    private byte mStrongRedstone = 0;

    /**
     * Light value of machine
     * @see #setLightValue(byte)
     */
    private byte mLightValue = 0;

    /**
     * Front facing of machine
     * @see #getFrontFacing()
     */
    private EnumFacing mFacing = EnumFacing.DOWN;

    /**
     * Facing of output of machine (fluids, items)
     */
    private EnumFacing oFacing = EnumFacing.DOWN;

    /**
     * Error code that machine displays in gui to indicate what exactly wrong with it
     * and why it can't work
     */
    private int mDisplayErrorCode = 0;

    /**
     * Index of current tick in time statistics array
     */
    private int mTimeStatisticsIndex = 0;

    /**
     * Count of warning that this machine caused since last load
     */
    private int mLagWarningCount = 0;

    /**
     * UUID of player who placed this machine
     * can be null
     */
    private UUID mOwnerId = null;

    /**
     * Contains data about itemstacks of components (ingredients) of these machine
     * that were used in crafting
     */
    private NBTTagCompound mRecipeStuff = new NBTTagCompound();

    /**
     * Indexes of average EU input and output
     */
    protected int mAverageEUInputIndex = 0, mAverageEUOutputIndex = 0;

    /**
     * Arrays of average EU inputs and outputs by sides (in D-U-N-S-W-E order)
     */
    protected int[] mAverageEUInput = new int[]{0, 0, 0, 0, 0}, mAverageEUOutput = new int[]{0, 0, 0, 0, 0};


    //fields for update detection

    private byte oColor = 0, //last value of mColor
            oRedstoneData = 63, //last sided redstone data
            oTextureData = 0, //last texture data
            oUpdateData = 0, //last update data
            oLightValueClient = -1, //last oLightValue on client side (for rendering)
            oLightValue = -1; //last oLightValue on server side (for packets)


    /**
     * General constructor
     */
    public BaseMetaTileEntity() {
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound aNBT) {
        try {
            super.writeToNBT(aNBT);
            aNBT.setInteger("mID", mID);
            aNBT.setLong("mStoredEnergy", mStoredEnergy);
            aNBT.setIntArray("mCoverData", mCoverData);
            aNBT.setIntArray("mCoverSides", mCoverSides);
            aNBT.setByteArray("mRedstoneSided", mSidedRedstone);
            aNBT.setByte("mColor", mColor);
            aNBT.setByte("mLightValue", mLightValue);
            aNBT.setByte("mWorkData", mWorkData);
            aNBT.setByte("mStrongRedstone", mStrongRedstone);
            aNBT.setShort("mFacing", (short) mFacing.getIndex());
            aNBT.setUniqueId("mOwnerId", mOwnerId);
            aNBT.setBoolean("mLockUpgrade", mLockUpgrade);
            aNBT.setBoolean("mMuffler", mMuffler);
            aNBT.setBoolean("mActive", mActive);
            aNBT.setBoolean("mRedstone", mRedstone);
            aNBT.setBoolean("mWorks", !mWorks);
            aNBT.setBoolean("mInputDisabled", mInputDisabled);
            aNBT.setBoolean("mOutputDisabled", mOutputDisabled);
            aNBT.setTag("GT.CraftingComponents", mRecipeStuff);
            if (hasValidMetaTileEntity()) {
                NBTTagList tItemList = new NBTTagList();
                for (int i = 0; i < mMetaTileEntity.getRealInventory().length; i++) {
                    ItemStack tStack = mMetaTileEntity.getRealInventory()[i];
                    if (tStack != null) {
                        NBTTagCompound tTag = new NBTTagCompound();
                        tTag.setInteger("IntSlot", i);
                        tStack.writeToNBT(tTag);
                        tItemList.appendTag(tTag);
                    }
                }
                aNBT.setTag("Inventory", tItemList);
                mMetaTileEntity.saveNBTData(aNBT);
            }
        } catch (Throwable e) {
            GT_Log.err.println("Encountered CRITICAL ERROR while saving MetaTileEntity, the Chunk whould've been corrupted by now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
            e.printStackTrace(GT_Log.err);
        }
        return aNBT;
    }

    @Override
    public void readFromNBT(NBTTagCompound aNBT) {
        super.readFromNBT(aNBT);
        setInitialValuesAsNBT(aNBT, (short) 0);
        issueClientUpdate();
    }

    @Override
    public void setInitialValuesAsNBT(NBTTagCompound aNBT, short aID) {
        if (aNBT == null) {
            if (aID > 0) mID = aID;
            else mID = mID > 0 ? mID : 0;
            if (mID != 0) createNewMetatileEntity(mID);
            mSidedRedstone = (hasValidMetaTileEntity() && mMetaTileEntity.hasSidedRedstoneOutputBehavior() ?
                    new byte[]{0, 0, 0, 0, 0, 0} :
                    new byte[]{15, 15, 15, 15, 15, 15});
        } else {
            if (aID <= 0) mID = (short) aNBT.getInteger("mID");
            else mID = aID;
            mStoredEnergy = aNBT.getInteger("mStoredEnergy");
            mColor = aNBT.getByte("mColor");
            mLightValue = aNBT.getByte("mLightValue");
            mWorkData = aNBT.getByte("mWorkData");
            mStrongRedstone = aNBT.getByte("mStrongRedstone");
            mFacing = oFacing = EnumFacing.VALUES[aNBT.getShort("mFacing")];
            mOwnerId = aNBT.getUniqueId("mOwnerId");
            mLockUpgrade = aNBT.getBoolean("mLockUpgrade");
            mMuffler = aNBT.getBoolean("mMuffler");
            mActive = aNBT.getBoolean("mActive");
            mRedstone = aNBT.getBoolean("mRedstone");
            mWorks = !aNBT.getBoolean("mWorks");
            mInputDisabled = aNBT.getBoolean("mInputDisabled");
            mOutputDisabled = aNBT.getBoolean("mOutputDisabled");
            mCoverSides = aNBT.getIntArray("mCoverSides");
            mCoverData = aNBT.getIntArray("mCoverData");
            mSidedRedstone = aNBT.getByteArray("mRedstoneSided");
            mRecipeStuff = aNBT.getCompoundTag("GT.CraftingComponents");

            if (mCoverData.length != 6) mCoverData = new int[]{0, 0, 0, 0, 0, 0};
            if (mCoverSides.length != 6) mCoverSides = new int[]{0, 0, 0, 0, 0, 0};
            if (mSidedRedstone.length != 6)
                if (hasValidMetaTileEntity() && mMetaTileEntity.hasSidedRedstoneOutputBehavior())
                    mSidedRedstone = new byte[]{0, 0, 0, 0, 0, 0};
                else mSidedRedstone = new byte[]{15, 15, 15, 15, 15, 15};

            if (mID != 0 && createNewMetatileEntity(mID)) {
                NBTTagList tItemList = aNBT.getTagList("Inventory", 10);
                for (int i = 0; i < tItemList.tagCount(); i++) {
                    NBTTagCompound tTag = tItemList.getCompoundTagAt(i);
                    int tSlot = tTag.getInteger("IntSlot");
                    if (tSlot >= 0 && tSlot < mMetaTileEntity.getRealInventory().length) {
                        mMetaTileEntity.getRealInventory()[tSlot] = GT_Utility.loadItem(tTag);
                    }
                }
                mMetaTileEntity.loadNBTData(aNBT);
            }
        }
    }

    private boolean createNewMetatileEntity(short aID) {
        if (aID <= 0 || aID >= GregTech_API.METATILEENTITIES.length || GregTech_API.METATILEENTITIES[aID] == null) {
            GT_Log.err.println("MetaID " + aID + " not loadable => locking TileEntity!");
        } else {
            if (hasValidMetaTileEntity()) mMetaTileEntity.setBaseMetaTileEntity(null);
            GregTech_API.METATILEENTITIES[aID].newMetaEntity(this).setTile(this);
            mTickTimer = 0;
            mID = aID;
            return true;
        }
        return false;
    }

    /**
     * Called when trying to charge Items
     */
    public void chargeItem(ItemStack stack) {
        decreaseStoredEU(GT_ModHandler.chargeElectricItem(stack, (int) Math.min(Integer.MAX_VALUE, getStoredEU()), (int) Math.min(Integer.MAX_VALUE, mMetaTileEntity.getOutputTier()), false, false), true);
    }

    /**
     * Called when trying to discharge Items
     */
    public void dischargeItem(ItemStack stack) {
        increaseStoredEnergyUnits(GT_ModHandler.dischargeElectricItem(stack, (int) Math.min(Integer.MAX_VALUE, getEUCapacity() - getStoredEU()), (int) Math.min(Integer.MAX_VALUE, mMetaTileEntity.getInputTier()), false, false, false), true);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return super.hasCapability(capability, facing) || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) new FluidHandlerWrapper(this, facing);
        }
        return super.getCapability(capability, facing);
    }


    private void updateClient() {
        if (mColor != oColor) {
            mMetaTileEntity.onColorChangeClient(oColor = mColor);
            issueTextureUpdate();
        }

        if (mLightValue != oLightValueClient) {
            worldObj.setLightFor(EnumSkyBlock.BLOCK, getPos(), oLightValueClient = mLightValue);
            worldObj.notifyLightSet(getPos());
            worldObj.notifyLightSet(getPos().add(1, 0, 0));
            worldObj.notifyLightSet(getPos().add(-1, 0, 0));
            worldObj.notifyLightSet(getPos().add(0, 1, 0));
            worldObj.notifyLightSet(getPos().add(0, -1, 0));
            worldObj.notifyLightSet(getPos().add(0, 0, 1));
            worldObj.notifyLightSet(getPos().add(0, 0, -1));
            issueTextureUpdate();
        }

        if (mNeedsUpdate) {
            causeChunkUpdate();
            mNeedsUpdate = false;
        }
    }

    @Override
    public void issueCoverUpdate(EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        if(coverIdAtSide != 0) {
            if(mMetaTileEntity.allowCoverOnSide(side, GregTech_API.getCoverItem(coverIdAtSide))) {
                dropCover(side, side, true);
            }
        }
    }

    public void validateCovers() {
        for(EnumFacing side : EnumFacing.VALUES) {
            issueCoverUpdate(side);
        }
    }

    private void onFirstTick() {
        validateCovers();
        mMetaTileEntity.onFirstTick();
        worldObj.markChunkDirty(getPos(), this);
    }

    private void updateServer() {
        if(mTickTimer > 10) {
            for (EnumFacing side : EnumFacing.VALUES) {
                int coverIdAtSide = getCoverIDAtSide(side);
                if (coverIdAtSide != 0) {
                    GT_CoverBehavior behavior = GregTech_API.getCoverBehavior(coverIdAtSide);
                    int coverTickRate = behavior.getTickRate(side, coverIdAtSide, mCoverData[side.getIndex()], this);
                    if (coverTickRate > 0 && mTickTimer % coverTickRate == 0) {
                        mCoverData[side.getIndex()] = behavior.doCoverThings(side, getInputRedstoneSignal(side),
                                coverIdAtSide, mCoverData[side.getIndex()], this, mTickTimer);
                    }
                }
            }
            byte tData = (byte) ((mFacing.getIndex() & 7) | (mActive ? 8 : 0) | (mRedstone ? 16 : 0) | (mLockUpgrade ? 32 : 0));
            if (tData != oTextureData) {
                sendBlockEvent((byte) 0, oTextureData = tData);
                issueBlockUpdate();
            }
            if (mColor != oColor) {
                sendBlockEvent((byte) 2, oColor = mColor);
                issueBlockUpdate();
            }
            tData = mMetaTileEntity.getUpdateData();
            if (tData != oUpdateData) {
                sendBlockEvent((byte) 1, oUpdateData = tData);
                issueBlockUpdate();
            }
            tData = (byte) (((mSidedRedstone[0] > 0) ? 1 : 0) | ((mSidedRedstone[1] > 0) ? 2 : 0) | ((mSidedRedstone[2] > 0) ? 4 : 0) | ((mSidedRedstone[3] > 0) ? 8 : 0) | ((mSidedRedstone[4] > 0) ? 16 : 0) | ((mSidedRedstone[5] > 0) ? 32 : 0));
            if (tData != oRedstoneData) {
                sendBlockEvent((byte) 3, oRedstoneData = tData);
                issueBlockUpdate();
            }

            if (mLightValue != oLightValue) {
                worldObj.setLightFor(EnumSkyBlock.BLOCK, getPos(), mLightValue);
                worldObj.notifyLightSet(getPos());
                worldObj.notifyLightSet(getPos().up());
                worldObj.notifyLightSet(getPos().down());
                worldObj.notifyLightSet(getPos().east());
                worldObj.notifyLightSet(getPos().west());
                worldObj.notifyLightSet(getPos().north());
                worldObj.notifyLightSet(getPos().south());
                sendBlockEvent((byte) 7, oLightValue = mLightValue);
                issueBlockUpdate();
            }

            if (mNeedsBlockUpdate) {
                worldObj.notifyNeighborsOfStateChange(getPos(), getBlockType());
                mNeedsBlockUpdate = false;
            }

            if(mSendClientData) {
                NW.sendToAllAround(worldObj, new GT_Packet_TileEntity(pos, mID,
                                mCoverSides[0], mCoverSides[1], mCoverSides[2],
                                mCoverSides[3], mCoverSides[4], mCoverSides[5],
                                oTextureData = (byte) (((oFacing = mFacing).getIndex() & 7) | (mActive ? 8 : 0) |
                                        (mRedstone ? 16 : 0) | (mLockUpgrade ? 32 : 0)),
                                oUpdateData = hasValidMetaTileEntity() ? mMetaTileEntity.getUpdateData() : 0,
                                oRedstoneData = (byte) (((mSidedRedstone[0] > 0) ? 1 : 0) | ((mSidedRedstone[1] > 0) ? 2 : 0) |
                                        ((mSidedRedstone[2] > 0) ? 4 : 0) | ((mSidedRedstone[3] > 0) ? 8 : 0) |
                                        ((mSidedRedstone[4] > 0) ? 16 : 0) | ((mSidedRedstone[5] > 0) ? 32 : 0)), oColor = mColor),
                        pos.getX(), pos.getY(), pos.getZ());
                mSendClientData = false;
            }
            
        }
        
        if (mTickTimer > 20 && mMetaTileEntity.isElectric()) {
            updateElectric();
        }

    }

    private void updateElectric() {
        mAcceptedAmperes = 0;

        if (++mAverageEUInputIndex >= mAverageEUInput.length) mAverageEUInputIndex = 0;
        if (++mAverageEUOutputIndex >= mAverageEUOutput.length) mAverageEUOutputIndex = 0;

        mAverageEUInput[mAverageEUInputIndex] = 0;
        mAverageEUOutput[mAverageEUOutputIndex] = 0;

        long outputVoltage = getOutputVoltage();
        if (mMetaTileEntity.isEnetOutput() && outputVoltage > 0) {
            long actualOutputVoltage = outputVoltage + (1 << GT_Utility.getTier(outputVoltage));
            long usableAmperage = Math.min(getOutputAmperage(), (getStoredEU() - mMetaTileEntity.getMinimumStoredEU()) / actualOutputVoltage);
            if (usableAmperage > 0) {
                long energyDispatched = actualOutputVoltage * IEnergyConnected.Util.emitEnergyToNetwork(outputVoltage, usableAmperage, this);
                decreaseStoredEU(energyDispatched, true);
            }
        }
        
        checkEnvironment();
        updateElectricItemSlots();
    }

    private void checkEnvironment() {
        if (GregTech_API.sMachineFireExplosions && getRandomNumber(1000) == 0) {
            BlockPos randomPos = pos.offset(EnumFacing.VALUES[getRandomNumber(6)]);
            if (worldObj.getBlockState(randomPos).getBlock() instanceof BlockFire) {
                doEnergyExplosion();
            }
        }
        
        Biome biome = worldObj.getBiome(pos);
        int explosionChance = 1000 - (int) (500 * MathHelper.clamp_double(biome.getRainfall(), 0, 1));
        if ((biome.canRain() || biome.getEnableSnow()) && getRandomNumber(explosionChance) == 0) {
            boolean hasUncoveredSide = false;
            for (EnumFacing side : EnumFacing.VALUES) {
                if (worldObj.getPrecipitationHeight(pos.offset(side)).getY() - 1 < pos.getY()) {
                    hasUncoveredSide = true;
                    break;
                }
            }
            if (hasUncoveredSide) {
                if (GregTech_API.sMachineRainExplosions && worldObj.isRaining()) {
                    if (getRandomNumber(10) == 0) {
                        if(mOwnerId != null) {
                            GT_Mod.achievements.issueAchievement(this.getWorld().getPlayerEntityByUUID(mOwnerId), "badweather");
                        }
                        doEnergyExplosion();
                    } else setOnFire();

                } else if (GregTech_API.sMachineThunderExplosions && worldObj.isThundering()) {
                    if (getRandomNumber(3) == 0) {
                        if(mOwnerId != null) {
                            GT_Mod.achievements.issueAchievement(this.getWorld().getPlayerEntityByUUID(mOwnerId), "badweather");
                        }
                        doEnergyExplosion();
                    } else setOnFire();
                }
            }
        }
        
    }
    
    private void updateElectricItemSlots() {
        if (mMetaTileEntity.dechargerSlotCount() > 0 && getStoredEU() < getEUCapacity()) {
            for (int i = mMetaTileEntity.dechargerSlotStartIndex(), k = mMetaTileEntity.dechargerSlotCount() + i; i < k; i++) {
                if (mMetaTileEntity.mInventory[i] != null && getStoredEU() < getEUCapacity()) {
                    dischargeItem(mMetaTileEntity.mInventory[i]);
                    if (Info.itemInfo.getEnergyValue(mMetaTileEntity.mInventory[i]) > 0) {
                        if ((getStoredEU() + Info.itemInfo.getEnergyValue(mMetaTileEntity.mInventory[i])) < getEUCapacity()) {
                            increaseStoredEnergyUnits((long) Info.itemInfo.getEnergyValue(mMetaTileEntity.mInventory[i]), false);
                            mMetaTileEntity.mInventory[i].stackSize--;
                        }
                    }
                    if (mMetaTileEntity.mInventory[i].stackSize <= 0)
                        mMetaTileEntity.mInventory[i] = null;
                    mInventoryChanged = true;
                }
            }
        }
        
        if (mMetaTileEntity.rechargerSlotCount() > 0 && getStoredEU() > 0) {
            for (int i = mMetaTileEntity.rechargerSlotStartIndex(), k = mMetaTileEntity.rechargerSlotCount() + i; i < k; i++) {
                if (getStoredEU() > 0 && mMetaTileEntity.mInventory[i] != null) {
                    chargeItem(mMetaTileEntity.mInventory[i]);
                    if (mMetaTileEntity.mInventory[i].stackSize <= 0)
                        mMetaTileEntity.mInventory[i] = null;
                    mInventoryChanged = true;
                }
            }
        }
        
    }

    @Override
    public void update() {
        super.update();

        if (!hasValidMetaTileEntity()) {
            if (mMetaTileEntity == null) return;
            mMetaTileEntity.setBaseMetaTileEntity(this);
        }

        if (mTickTimer++ == 0) {
            onFirstTick();
        }

        if (isServerSide()) {
            long tTime = System.currentTimeMillis();
            updateServer();
            tTime = System.currentTimeMillis() - tTime;
            if (mTimeStatistics.length > 0)
                mTimeStatistics[mTimeStatisticsIndex = (mTimeStatisticsIndex + 1) % mTimeStatistics.length] = (int) tTime;
            if (tTime > 0 && tTime > GregTech_API.MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING && mTickTimer > 1000 && getMetaTileEntity().doTickProfilingMessageDuringThisTick() && mLagWarningCount++ < 10) {
                System.out.println("WARNING: Possible Lag Source at [" + pos + "] in Dimension " + worldObj.provider.getDimension() + " with " + tTime + "ms caused by an instance of " + getMetaTileEntity().getClass());
            }
        } else {
            updateClient();
        }
        mWorkUpdate = mInventoryChanged = false;
    }


    @Override
    public boolean isInvalidTileEntity() {
        return isInvalid();
    }

    @Override
    public boolean isServerSide() {
        return !worldObj.isRemote;
    }

    @Override
    public boolean isClientSide() {
        return worldObj.isRemote;
    }

    @Override
    public int getRandomNumber(int range) {
        return worldObj.rand.nextInt(range);
    }

    @Override
    public String getName() {
        return mMetaTileEntity.getName();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public boolean addStackToSlot(int index, ItemStack stack) {
        if (index < 0 || index >= getSizeInventory() || !GT_Utility.isStackValid(stack)) return false;
        stack = GT_OreDictUnificator.get(stack);
        ItemStack tStack = getStackInSlot(index);
        if (!GT_Utility.isStackValid(tStack)) {
            setInventorySlotContents(index, stack);
            return true;
        }
        if (GT_Utility.areStacksEqual(tStack, stack) && tStack.stackSize + stack.stackSize <= Math.min(stack.getMaxStackSize(), getInventoryStackLimit())) {
            tStack.stackSize += stack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public boolean addStackToSlot(int index, ItemStack stack, int amount) {
        return addStackToSlot(index, GT_Utility.copyAmount(amount, stack));
    }

    @Override
    public boolean isUniversalEnergyStored(long aEnergyAmount) {
        if (getUniversalEnergyStored() >= aEnergyAmount) return true;
        mHasEnoughEnergy = false;
        return false;
    }

    @Override
    public String[] getInfoData() {
        if (hasValidMetaTileEntity()) return getMetaTileEntity().getInfoData();
        return new String[]{};
    }

    @Override
    public void markDirty() {
        super.markDirty();
        mInventoryChanged = true;
    }

    @Override
    public int getLightOpacity() {
        return mMetaTileEntity == null ? getLightValue() > 0 ? 0 : 255 : mMetaTileEntity.getLightOpacity();
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity collider) {
        mMetaTileEntity.onEntityCollidedWithBlock(world, pos, collider);
    }

    @Override
    public byte getColorization() {
        return (byte) (mColor - 1);
    }

    @Override
    public byte setColorization(byte aColor) {
        if (aColor > 15 || aColor < -1) aColor = -1;
        if (hasValidMetaTileEntity()) mMetaTileEntity.onColorChangeServer(aColor);
        return mColor = (byte) (aColor + 1);
    }

    @Override
    public float getBlastResistance(EnumFacing side) {
        return hasValidMetaTileEntity() ? Math.max(0, getMetaTileEntity().getExplosionResistance(side)) : 10.0F;
    }

    @SideOnly(Side.CLIENT)
    private void causeChunkUpdate() {
        int minX = pos.getX() - 5;
        int minY = pos.getY() - 5;
        int minZ = pos.getZ() - 5;
        int maxX = pos.getX() + 5;
        int maxY = pos.getY() + 5;
        int maxZ = pos.getZ() + 5;
        Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public final void receiveMetaTileEntityData(short aID, int aCover0, int aCover1, int aCover2, int aCover3, int aCover4, int aCover5, byte aTextureData, byte aUpdateData, byte aRedstoneData, byte aColorData) {
        boolean tNeedsRedraw = false;
        if (mID != aID && aID > 0) {
            mID = aID;
            createNewMetatileEntity(mID);
            tNeedsRedraw = true;
        }


        if (mCoverSides[0] != aCover0) {
            mCoverSides[0] = aCover0;
            tNeedsRedraw = true;
        }
        if (mCoverSides[1] != aCover1) {
            mCoverSides[1] = aCover1;
            tNeedsRedraw = true;
        }
        if (mCoverSides[2] != aCover2) {
            mCoverSides[2] = aCover2;
            tNeedsRedraw = true;
        }
        if (mCoverSides[3] != aCover3) {
            mCoverSides[3] = aCover3;
            tNeedsRedraw = true;
        }
        if (mCoverSides[4] != aCover4) {
            mCoverSides[4] = aCover4;
            tNeedsRedraw = true;
        }
        if (mCoverSides[5] != aCover5) {
            mCoverSides[5] = aCover5;
            tNeedsRedraw = true;
        }
        
        receiveClientEvent(0, aTextureData);
        receiveClientEvent(1, aUpdateData);
        receiveClientEvent(2, aColorData);
        receiveClientEvent(3, aRedstoneData);

        if (tNeedsRedraw) {
            issueTextureUpdate();
        }
        
    }

    @Override
    public boolean receiveClientEvent(int aEventID, int aValue) {
        boolean tNeedsRedraw = false;
        super.receiveClientEvent(aEventID, aValue);

        if (hasValidMetaTileEntity()) {
            try {
                mMetaTileEntity.receiveClientEvent((byte) aEventID, (byte) aValue);
            } catch (Throwable e) {
                GT_Log.err.println("Encountered Exception while receiving Data from the Server, the Client should've been crashed by now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
                e.printStackTrace(GT_Log.err);
            }
        }

        if (isClientSide()) {

            switch (aEventID) {
                case 0:
                    EnumFacing newFacing = EnumFacing.VALUES[aValue & 0x07];
                    if (mFacing != newFacing) {
                        mFacing = newFacing;
                        tNeedsRedraw = true;
                    }
                    if (mActive != ((aValue & 0x08) != 0)) {
                        mActive = ((aValue & 0x08) != 0);
                        tNeedsRedraw = true;
                    }
                    if (mRedstone != ((aValue & 0x10) != 0)) {
                        mRedstone = ((aValue & 0x10) != 0);
                        tNeedsRedraw = true;
                    }
                    if (mLockUpgrade != ((aValue & 0x20) != 0)) {
                        mLockUpgrade = ((aValue & 0x20) != 0);
                        tNeedsRedraw = true;
                    }
                    break;
                case 1:
                    if (hasValidMetaTileEntity()) mMetaTileEntity.onValueUpdate((byte) aValue);
                    break;
                case 2:
                    if (aValue > 16 || aValue < 0) aValue = 0;
                    mColor = (byte) aValue;
                    break;
                case 3:
                    mSidedRedstone[0] = (byte) ((aValue & 1) > 0 ? 15 : 0);
                    mSidedRedstone[1] = (byte) ((aValue & 2) > 0 ? 15 : 0);
                    mSidedRedstone[2] = (byte) ((aValue & 4) > 0 ? 15 : 0);
                    mSidedRedstone[3] = (byte) ((aValue & 8) > 0 ? 15 : 0);
                    mSidedRedstone[4] = (byte) ((aValue & 16) > 0 ? 15 : 0);
                    mSidedRedstone[5] = (byte) ((aValue & 32) > 0 ? 15 : 0);
                    break;
                case 4:
                    if (hasValidMetaTileEntity() && mTickTimer > 20)
                        mMetaTileEntity.doSound((byte) aValue);
                    break;
                case 5:
                    if (hasValidMetaTileEntity() && mTickTimer > 20)
                        mMetaTileEntity.startSoundLoop((byte) aValue);
                    break;
                case 6:
                    if (hasValidMetaTileEntity() && mTickTimer > 20)
                        mMetaTileEntity.stopSoundLoop((byte) aValue);
                    break;
                case 7:
                    mLightValue = (byte) aValue;
                    break;
            }
            if (tNeedsRedraw) {
                issueTextureUpdate();
            }
        }
        return true;
    }

    public ArrayList<String> getDebugInfo(EntityPlayer aPlayer, int aLogLevel) {
        ArrayList<String> tList = new ArrayList<>();
        if (aLogLevel > 2) {
            tList.add("Meta-ID: " + mID + (mMetaTileEntity == null ? " MetaTileEntity == null!" : ""));
        }
        if (aLogLevel > 1) {
            if (mTimeStatistics.length > 0) {
                double tAverageTime = 0;
                for (int tTime : mTimeStatistics) tAverageTime += tTime;
                tList.add("This particular TileEntity has caused an average CPU-load of ~" + (tAverageTime / mTimeStatistics.length) + "ms over the last " + mTimeStatistics.length + " ticks.");
            }
            if (mLagWarningCount > 0) {
                tList.add("This TileEntity has also caused " + (mLagWarningCount >= 10 ? "more than 10" : mLagWarningCount) + " Lag Spike Warnings (anything taking longer than " + GregTech_API.MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING + "ms) on the Server.");
            }
            tList.add("Is" + (mMetaTileEntity.isAccessAllowed(aPlayer) ? " " : " not ") + "accessible for you");
        }
        if (aLogLevel > 0) {
            if (getSteamCapacity() > 0)
                tList.add(getStoredSteam() + " of " + getSteamCapacity() + " Steam");
            tList.add("Machine is " + (mActive ? "active" : "inactive"));
            if (!mHasEnoughEnergy)
                tList.add("ATTENTION: This Device consumes Energy at a higher Rate than you input. You could insert more to speed up the process.");
        }
        return mMetaTileEntity.getSpecialDebugInfo(this, aPlayer, aLogLevel, tList);
    }

    @Override
    public void issueTextureUpdate() {
        mNeedsUpdate = true;
    }

    @Override
    public void issueBlockUpdate() {
        mNeedsBlockUpdate = true;
    }

    @Override
    public void issueClientUpdate() {
        mSendClientData = true;
    }

    @Override
    public byte getStrongestRedstone() {
        byte strongest = 0;
        for(EnumFacing side : EnumFacing.VALUES) {
            byte signal = getInternalInputRedstoneSignal(side);
            if(signal > strongest) strongest = signal;
        }
        return strongest;
    }

    @Override
    public boolean getRedstone() {
        for(EnumFacing side : EnumFacing.VALUES) {
            if(getRedstone(side)) return true;
        }
        return false;
    }

    @Override
    public boolean getRedstone(EnumFacing side) {
        return getInternalInputRedstoneSignal(side) > 0;
    }

    @Override
    public boolean canOutputRedstone(EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior behavior = GregTech_API.getCoverBehavior(coverIdAtSide);
        return behavior.manipulatesSidedRedstoneOutput(side, coverIdAtSide, coverDataAtSide, this) ||
                behavior.letsRedstoneGoOut(side, coverIdAtSide, coverDataAtSide, this);
    }

    @Override
    public boolean isGivingInformation() {
        return hasValidMetaTileEntity() && mMetaTileEntity.isGivingInformation();
    }

    @Override
    public boolean isValidFacing(EnumFacing side) {
        return hasValidMetaTileEntity() && mMetaTileEntity.isFacingValid(side);
    }

    @Override
    public EnumFacing getBackFacing() {
        return mFacing.getOpposite();
    }

    @Override
    public EnumFacing getFrontFacing() {
        return mFacing;
    }

    @Override
    public void setFrontFacing(EnumFacing aFacing) {
        if (isValidFacing(aFacing)) {
            mFacing = aFacing;
            mMetaTileEntity.onFacingChange();
            onMachineBlockUpdate();
        }
    }

    @Override
    public int getSizeInventory() {
        if(!hasValidMetaTileEntity()) return 0;
        return mMetaTileEntity.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int aIndex) {
        if(!hasValidMetaTileEntity()) return null;
        return mMetaTileEntity.getStackInSlot(aIndex);
    }

    @Override
    public void setInventorySlotContents(int aIndex, ItemStack aStack) {
        mInventoryChanged = true;
        if(hasValidMetaTileEntity()) {
            mMetaTileEntity.setInventorySlotContents(aIndex, worldObj.isRemote ? aStack :
                    GT_OreDictUnificator.setStack(true, aStack));
        }
    }

    @Override
    public int getInventoryStackLimit() {
        if (hasValidMetaTileEntity()) return mMetaTileEntity.getInventoryStackLimit();
        return 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        if(hasValidMetaTileEntity()) {
            mMetaTileEntity.onOpenGUI();
        }
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        if(hasValidMetaTileEntity()) {
            mMetaTileEntity.onCloseGUI();
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return hasValidMetaTileEntity() && playerOwnsThis(aPlayer) && mTickTimer > 40 &&
                aPlayer.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64 &&
                mMetaTileEntity.isAccessAllowed(aPlayer);
    }

    @Override
    public void validate() {
        super.validate();
        mTickTimer = 0;
    }

    @Override
    public void invalidate() {
        tileEntityInvalid = false;
        if (mMetaTileEntity != null) {
            mMetaTileEntity.onRemoval();
        }
        super.invalidate();
    }

    @Override
    public void onMachineBlockUpdate() {
        if (hasValidMetaTileEntity()) mMetaTileEntity.onMachineBlockUpdate();
    }

    @Override
    public int getProgress() {
        return hasValidMetaTileEntity() ? mMetaTileEntity.getProgresstime() : 0;
    }

    @Override
    public int getMaxProgress() {
        return hasValidMetaTileEntity() ? mMetaTileEntity.maxProgresstime() : 0;
    }

    @Override
    public void increaseProgress(int aProgressAmountInTicks) {
        if(hasValidMetaTileEntity()) {
            mMetaTileEntity.increaseProgress(aProgressAmountInTicks);
        }
    }

    @Override
    public boolean hasThingsToDo() {
        return getMaxProgress() > 0;
    }

    @Override
    public void enableWorking() {
        if (!mWorks) mWorkUpdate = true;
        mWorks = true;
    }

    @Override
    public void disableWorking() {
        mWorks = false;
    }

    @Override
    public boolean isAllowedToWork() {
        return mWorks;
    }

    @Override
    public boolean hasWorkJustBeenEnabled() {
        return mWorkUpdate;
    }

    @Override
    public byte getWorkDataValue() {
        return mWorkData;
    }

    @Override
    public void setWorkDataValue(byte aValue) {
        mWorkData = aValue;
    }

    @Override
    public int getMetaTileID() {
        return mID;
    }

    @Override
    public int setMetaTileID(short aID) {
        return mID = aID;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void setActive(boolean aActive) {
        worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());
        mActive = aActive;
    }

    @Override
    public long getTimer() {
        return mTickTimer;
    }

    @Override
    public boolean decreaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooLessEnergy) {
        return hasValidMetaTileEntity() && (mHasEnoughEnergy = decreaseStoredEU(aEnergy, aIgnoreTooLessEnergy) || decreaseStoredSteam(aEnergy, false) || (aIgnoreTooLessEnergy && (decreaseStoredSteam(aEnergy, true))));
    }

    @Override
    public boolean increaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        if(!hasValidMetaTileEntity()) return false;
        if (getStoredEU() < getEUCapacity() || aIgnoreTooMuchEnergy) {
            setStoredEU(mMetaTileEntity.getEUVar() + aEnergy);
            return true;
        }
        return false;
    }

    @Override
    public boolean inputEnergyFrom(EnumFacing side) {
        return side == null || isEnergyInputSide(side) && !mReleaseEnergy;
    }

    @Override
    public boolean outputsEnergyTo(EnumFacing side) {
        return side == null || isEnergyOutputSide(side) || mReleaseEnergy;
    }

    @Override
    public long getOutputAmperage() {
        if (hasValidMetaTileEntity() && mMetaTileEntity.isElectric()) return mMetaTileEntity.maxAmperesOut();
        return 0;
    }

    @Override
    public long getOutputVoltage() {
        if (hasValidMetaTileEntity() && mMetaTileEntity.isElectric() && mMetaTileEntity.isEnetOutput())
            return mMetaTileEntity.maxEUOutput();
        return 0;
    }

    @Override
    public long getInputAmperage() {
        if (hasValidMetaTileEntity() && mMetaTileEntity.isElectric()) return mMetaTileEntity.maxAmperesIn();
        return 0;
    }

    @Override
    public long getInputVoltage() {
        if (hasValidMetaTileEntity() && mMetaTileEntity.isElectric()) return mMetaTileEntity.maxEUInput();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean increaseStoredSteam(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        if (!hasValidMetaTileEntity()) return false;
        if (mMetaTileEntity.getSteamVar() < getSteamCapacity() || aIgnoreTooMuchEnergy) {
            setStoredSteam(mMetaTileEntity.getSteamVar() + aEnergy);
            return true;
        }
        return false;
    }

    @Override
    public String[] getDescription() {
        if (hasValidMetaTileEntity()) return mMetaTileEntity.getDescription();
        return new String[0];
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return hasValidMetaTileEntity() && mMetaTileEntity.isValidSlot(aIndex);
    }

    @Override
    public long getUniversalEnergyStored() {
        return Math.max(getStoredEU(), getStoredSteam());
    }

    @Override
    public long getUniversalEnergyCapacity() {
        return Math.max(getEUCapacity(), getSteamCapacity());
    }

    @Override
    public long getStoredEU() {
        if (hasValidMetaTileEntity()) return Math.min(mMetaTileEntity.getEUVar(), getEUCapacity());
        return 0;
    }

    @Override
    public long getEUCapacity() {
        if (hasValidMetaTileEntity()) return mMetaTileEntity.maxEUStore();
        return 0;
    }

    @Override
    public long getStoredSteam() {
        if (hasValidMetaTileEntity()) return Math.min(mMetaTileEntity.getSteamVar(), getSteamCapacity());
        return 0;
    }

    @Override
    public long getSteamCapacity() {
        if (hasValidMetaTileEntity()) return mMetaTileEntity.maxSteamStore();
        return 0;
    }

    private boolean isEnergyInputSide(EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        return GregTech_API.getCoverBehavior(coverIdAtSide).letsEnergyIn(side, coverIdAtSide, getCoverDataAtSide(side), this) &&
                !(isInvalid() || mReleaseEnergy) && hasValidMetaTileEntity() &&
                mMetaTileEntity.isElectric() && mMetaTileEntity.isEnetInput() && mMetaTileEntity.isInputFacing(side);
    }

    private boolean isEnergyOutputSide(EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        if (!GregTech_API.getCoverBehavior(coverIdAtSide).letsEnergyOut(side, coverIdAtSide, getCoverDataAtSide(side), this))
            return false;
        if (isInvalid() || mReleaseEnergy) return mReleaseEnergy;
        return hasValidMetaTileEntity() && mMetaTileEntity.isElectric() &&
                mMetaTileEntity.isEnetOutput() && mMetaTileEntity.isOutputFacing(side);
    }

    protected boolean hasValidMetaTileEntity() {
        return mMetaTileEntity != null && mMetaTileEntity.getBaseMetaTileEntity() == this;
    }

    public boolean setStoredEU(long energy) {
        if (!hasValidMetaTileEntity()) return false;
        if (energy < 0) energy = 0;
        mMetaTileEntity.setEUVar(energy);
        return true;
    }

    public boolean setStoredSteam(long energy) {
        if (!hasValidMetaTileEntity()) return false;
        if (energy < 0) energy = 0;
        mMetaTileEntity.setSteamVar(energy);
        return true;
    }

    public boolean decreaseStoredEU(long energy, boolean ignoreTooLessEnergy) {
        if (!hasValidMetaTileEntity()) {
            return false;
        }
        if (mMetaTileEntity.getEUVar() - energy >= 0 || ignoreTooLessEnergy) {
            mAverageEUOutput[mAverageEUOutputIndex] = (int) Math.min(mMetaTileEntity.getEUVar(), energy);
            setStoredEU(mMetaTileEntity.getEUVar() - energy);
            if (mMetaTileEntity.getEUVar() < 0) {
                setStoredEU(0);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean decreaseStoredSteam(long aEnergy, boolean aIgnoreTooLessEnergy) {
        if (!hasValidMetaTileEntity()) return false;
        if (mMetaTileEntity.getSteamVar() - aEnergy >= 0 || aIgnoreTooLessEnergy) {
            setStoredSteam(mMetaTileEntity.getSteamVar() - aEnergy);
            if (mMetaTileEntity.getSteamVar() < 0) {
                setStoredSteam(0);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean playerOwnsThis(EntityPlayer aPlayer) {
        if (!hasValidMetaTileEntity()) return false;
        if (privateAccess() || mOwnerId == null)
            if (mOwnerId == null && isServerSide()) setOwnerId(aPlayer.getPersistentID());
            else if (privateAccess() && mOwnerId != null && mOwnerId.equals(aPlayer.getPersistentID()))
                return false;
        return true;
    }

    public boolean privateAccess() {
        if (!hasValidMetaTileEntity()) return mLockUpgrade;
        return mLockUpgrade || mMetaTileEntity.ownerControl();
    }

    public void doEnergyExplosion() {
        if (getUniversalEnergyCapacity() > 0 && getUniversalEnergyStored() >= getUniversalEnergyCapacity() / 5) {
            doExplosion(getOutputVoltage() * (getUniversalEnergyStored() >= getUniversalEnergyCapacity() ? 4 : getUniversalEnergyStored() >= getUniversalEnergyCapacity() / 2 ? 2 : 1));
            if(mOwnerId != null) {
                GT_Mod.achievements.issueAchievement(this.getWorld().getPlayerEntityByUUID(mOwnerId), "electricproblems");
            }
        }
    }

    @Override
    public void doExplosion(long aAmount) {
        if (hasValidMetaTileEntity()) {
            // This is only for Electric Machines
            if (GregTech_API.sMachineWireFire && mMetaTileEntity.isElectric()) {
                try {
                    mReleaseEnergy = true;
                    IEnergyConnected.Util.emitEnergyToNetwork(V[5], Math.max(1, getStoredEU() / V[5]), this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mReleaseEnergy = false;
            // Normal Explosion Code
            mMetaTileEntity.onExplosion();
            if(GT_Mod.gregtechproxy.mExplosionItemDrop){
                for (int i = 0; i < this.getSizeInventory(); i++) {
                    ItemStack tItem = this.getStackInSlot(i);
                    if ((tItem != null) && (tItem.stackSize > 0) && (this.isValidSlot(i))) {
                        dropItems(tItem);
                        this.setInventorySlotContents(i, null); }
                }
            }
            if (mRecipeStuff != null) {
                for (int i = 0; i < 9; i++) {
                    if (this.getRandomNumber(100) < 50) {
                        dropItems(GT_Utility.loadItem(mRecipeStuff, "Ingredient." + i));
                    }
                }
            }
            GT_Pollution.addPollution(getPos(), 100000);
            mMetaTileEntity.doExplosion(aAmount);
        }
    }

    public void dropItems(ItemStack tItem){
        if(tItem==null)return;
        Random tRandom = new Random();
        EntityItem tItemEntity = new EntityItem(this.worldObj,
                pos.getX() + tRandom.nextFloat() * 0.8F + 0.1F,
                pos.getY() + tRandom.nextFloat() * 0.8F + 0.1F,
                pos.getZ() + tRandom.nextFloat() * 0.8F + 0.1F,
                new ItemStack(tItem.getItem(), tItem.stackSize, tItem.getItemDamage()));
        if (tItem.hasTagCompound()) {
            tItemEntity.getEntityItem().setTagCompound(tItem.getTagCompound().copy());
        }
        tItemEntity.motionX = (tRandom.nextGaussian() * 0.0500000007450581D);
        tItemEntity.motionY = (tRandom.nextGaussian() * 0.0500000007450581D + 0.2000000029802322D);
        tItemEntity.motionZ = (tRandom.nextGaussian() * 0.0500000007450581D);
        tItemEntity.hurtResistantTime = 999999;
        tItemEntity.lifespan = 60000;
        try {
            Field tField = tItemEntity.getClass().getDeclaredFields()[4];
            tField.setAccessible(true);
            tField.setInt(tItemEntity, 99999999);
        } catch (Exception e) {e.printStackTrace();}
        this.worldObj.spawnEntityInWorld(tItemEntity);
        tItem.stackSize = 0;
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        ItemStack stack = new ItemStack(GregTech_API.sBlockMachines, 1, mID);
        NBTTagCompound tag = new NBTTagCompound();
        if (mRecipeStuff != null && !mRecipeStuff.hasNoTags()) tag.setTag("GT.CraftingComponents", mRecipeStuff);
        if (mMuffler) tag.setBoolean("mMuffler", mMuffler);
        if (mLockUpgrade) tag.setBoolean("mLockUpgrade", mLockUpgrade);
        if (mColor > 0) tag.setByte("mColor", mColor);
        if (mStrongRedstone > 0) tag.setByte("mStrongRedstone", mStrongRedstone);
        for (int mCoverSide : mCoverSides) {
            if (mCoverSide != 0) {
                tag.setIntArray("mCoverData", mCoverData);
                tag.setIntArray("mCoverSides", mCoverSides);
                break;
            }
        }
        if (hasValidMetaTileEntity()) mMetaTileEntity.setItemNBT(tag);
        if (!tag.hasNoTags()) stack.setTagCompound(tag);
        return Lists.newArrayList(stack);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onRightclick(EntityPlayer player, EnumFacing side, float clickX, float clickY, float clickZ, EnumHand hand) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);
        if (isClientSide()) {
            return coverBehavior.onCoverRightclickClient(side, this, player, clickX, clickY, clickZ) ||
                    coverBehavior.isGUIClickable(side, coverIdAtSide, coverDataAtSide, this);
        }
        if (isServerSide()) {
            if (!privateAccess() || (mOwnerId != null && mOwnerId.equals(player.getPersistentID()))) {
                ItemStack currentItem = player.inventory.getCurrentItem();
                if (currentItem != null) {
                    //colorization
                    if (getColorization() >= 0 && GT_Utility.areStacksEqual(new ItemStack(Items.WATER_BUCKET, 1), currentItem)) {
                        currentItem.setItem(Items.BUCKET);
                        setColorization((byte) (getColorization() >= 16 ? -2 : -1));
                        return true;
                    }
                    //wrench interaction
                    if (GT_Utility.isStackInList(currentItem, GregTech_API.sWrenchList)) {
                        EnumFacing wrenchDirection = GT_Utility.determineWrenchingSide(side, clickX, clickY, clickZ);
                        if (player.isSneaking() && mMetaTileEntity instanceof GT_MetaTileEntity_BasicMachine &&
                                ((GT_MetaTileEntity_BasicMachine) mMetaTileEntity).setMainFacing(wrenchDirection)) {
                            GT_ModHandler.damageOrDechargeItem(currentItem, 1, 1000, player);
                            playSound(GregTech_API.sSoundList.get(100));
                        } else if (mMetaTileEntity.onWrenchRightClick(side, wrenchDirection, player, clickX, clickY, clickZ)) {
                            GT_ModHandler.damageOrDechargeItem(currentItem, 1, 1000, player);
                            playSound(GregTech_API.sSoundList.get(100));
                        }
                        return true;
                    }
                    //screwdriver interaction
                    if (GT_Utility.isStackInList(currentItem, GregTech_API.sScrewdriverList)) {
                        if (GT_ModHandler.damageOrDechargeItem(currentItem, 1, 200, player)) {
                            setCoverDataAtSide(side, coverBehavior.onCoverScrewdriverclick(side, coverIdAtSide, coverDataAtSide,
                                    this, player, clickX, clickY, clickZ));
                            mMetaTileEntity.onScrewdriverRightClick(side, player, clickX, clickY, clickZ);
                            playSound(GregTech_API.sSoundList.get(100));
                        }
                        return true;
                    }
                    //hammer interaction
                    if (GT_Utility.isStackInList(currentItem, GregTech_API.sHardHammerList)) {
                        if (GT_ModHandler.damageOrDechargeItem(currentItem, 1, 1000, player)) {
                            mInputDisabled = !mInputDisabled;
                            if (mInputDisabled) mOutputDisabled = !mOutputDisabled;
                            player.addChatMessage(new TextComponentString(
                                    "Auto-Input: " + (mInputDisabled ? "Disabled" : "Enabled") + "  " +
                                            "Auto-Output: " + (mOutputDisabled ? "Disabled" : "Enabled")));
                            playSound(GregTech_API.sSoundList.get(1));
                        }
                        return true;
                    }
                    //soft hammer interaction
                    if (GT_Utility.isStackInList(currentItem, GregTech_API.sSoftHammerList)) {
                        if (GT_ModHandler.damageOrDechargeItem(currentItem, 1, 1000, player)) {
                            if (mWorks) disableWorking();
                            else enableWorking();
                            player.addChatMessage(new TextComponentString(
                                    "Machine Processing: " + (isAllowedToWork() ? "Enabled" : "Disabled")));
                            playSound(GregTech_API.sSoundList.get(101));
                        }
                        return true;
                    }
                    //soldering iron interaction
                    if (GT_Utility.isStackInList(currentItem, GregTech_API.sSolderingToolList)) {
                        EnumFacing wrenchSide = GT_Utility.determineWrenchingSide(side, clickX, clickY, clickZ);
                        if (GT_ModHandler.useSolderingIron(currentItem, player)) {
                            mStrongRedstone ^= (1 << wrenchSide.getIndex());
                            player.addChatMessage(new TextComponentString(
                                    "Redstone Output at Side " + wrenchSide.name().toLowerCase() + " set to: " +
                                            ((mStrongRedstone & (1 << wrenchSide.getIndex())) != 0 ? "Strong" : "Weak")));
                            playSound(GregTech_API.sSoundList.get(103));
                        }
                        return true;
                    }
                    //cover interaction
                    if (coverIdAtSide == 0) {
                        GT_ItemStack gtItemStack = new GT_ItemStack(currentItem);
                        if (GregTech_API.sCoverItems.containsKey(gtItemStack)) {
                            GT_CoverBehavior behavior = GregTech_API.getCoverBehavior(gtItemStack);
                            if (behavior.isCoverPlaceable(side, gtItemStack, this) && mMetaTileEntity.allowCoverOnSide(side, gtItemStack)) {
                                setCoverItemAtSide(side, currentItem);
                                if (!player.capabilities.isCreativeMode) currentItem.stackSize--;
                                playSound(GregTech_API.sSoundList.get(100));
                            }
                            return true;
                        }
                    } else {
                        //crowbar interaction
                        if (GT_Utility.isStackInList(currentItem, GregTech_API.sCrowbarList)) {
                            if (GT_ModHandler.damageOrDechargeItem(currentItem, 1, 1000, player)) {
                                dropCover(side, side, false);
                                playSound(GregTech_API.sSoundList.get(0));
                            }
                            return true;
                        }
                    }
                }

                if (coverBehavior.onCoverRightclick(side, coverIdAtSide, coverDataAtSide, this, player, clickX, clickY, clickZ))
                    return true;

                if (!coverBehavior.isGUIClickable(side, coverIdAtSide, coverDataAtSide, this))
                    return false;

                if (currentItem != null) {
                    if (ItemList.Upgrade_Muffler.isStackEqual(currentItem)) {
                        if (addMufflerUpgrade()) {
                            if (!player.capabilities.isCreativeMode) currentItem.stackSize--;
                            playSound(GregTech_API.sSoundList.get(3));
                        }
                        return true;
                    }
                    if (ItemList.Upgrade_Lock.isStackEqual(currentItem)) {
                        if (!mLockUpgrade) {
                            mLockUpgrade = true;
                            setOwnerId(player.getPersistentID());
                            if (!player.capabilities.isCreativeMode) currentItem.stackSize--;
                            playSound(GregTech_API.sSoundList.get(3));
                        }
                        return true;
                    }
                }

            }
        }

        return !hasValidMetaTileEntity() || mMetaTileEntity.onRightclick(this, player, side, clickX, clickY, clickZ, hand);
    }
    
    public void playSound(ResourceLocation soundEvent) {
        GT_Utility.playSound(worldObj, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, soundEvent, SoundCategory.BLOCKS, 1.0F, -1f);
    }

    @Override
    public void onLeftclick(EntityPlayer aPlayer) {
        if (hasValidMetaTileEntity()) {
            mMetaTileEntity.onLeftclick(this, aPlayer, EnumHand.MAIN_HAND);
        }
    }

    @Override
    public boolean isDigitalChest() {
        return hasValidMetaTileEntity() && mMetaTileEntity.isDigitalChest();
    }

    @Override
    public ItemStack[] getStoredItemData() {
        if (hasValidMetaTileEntity()) return mMetaTileEntity.getStoredItemData();
        return null;
    }

    @Override
    public void setItemCount(int aCount) {
        if (hasValidMetaTileEntity()) mMetaTileEntity.setItemCount(aCount);
    }

    @Override
    public int getMaxItemCount() {
        if (hasValidMetaTileEntity()) return mMetaTileEntity.getMaxItemCount();
        return 0;
    }

    /**
     * Can put aStack into Slot
     */
    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return hasValidMetaTileEntity() && mMetaTileEntity.isItemValidForSlot(aIndex, aStack);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
    }

    /**
     * returns all valid Inventory Slots, no matter which Side (Unless it's covered).
     * The Side Stuff is done in the following two Functions.
     */
    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        if(hasValidMetaTileEntity() &&
                coverBehavior.letsItemsIn(side, coverIdAtSide, coverDataAtSide, -1, this) ||
                coverBehavior.letsItemsOut(side, coverIdAtSide, coverDataAtSide, -1, this))
            return mMetaTileEntity.getSlotsForFace(side);
        return new int[0];
    }

    /**
     * Can put aStack into Slot at Side
     */
    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        return hasValidMetaTileEntity() && !mInputDisabled &&
                coverBehavior.letsItemsIn(side, coverIdAtSide, coverDataAtSide, index, this) &&
                mMetaTileEntity.canInsertItem(index, stack, side);
    }

    /**
     * Can pull aStack out of Slot from Side
     */
    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        return hasValidMetaTileEntity() && !mOutputDisabled &&
                coverBehavior.letsItemsOut(side, coverIdAtSide, coverDataAtSide, index, this) &&
                mMetaTileEntity.canExtractItem(index, stack, side);
    }

    @Override
    public byte getInternalInputRedstoneSignal(EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        return coverBehavior.getRedstoneInput(side, getInputRedstoneSignal(side), coverIdAtSide, coverDataAtSide, this);
    }

    @Override
    public byte getInputRedstoneSignal(EnumFacing side) {
        return (byte) worldObj.getRedstonePower(getPos().offset(side), side.getOpposite());
    }

    @Override
    public byte getOutputRedstoneSignal(EnumFacing side) {
        if(side != null) {
            int coverIdAtSide = getCoverIDAtSide(side);
            int coverDataAtSide = getCoverDataAtSide(side);
            GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

            if(coverBehavior.manipulatesSidedRedstoneOutput(side, coverIdAtSide, coverDataAtSide, this)) {
                return mSidedRedstone[side.getIndex()];
            }
        }
        return 0;
    }

    @Override
    public void setInternalOutputRedstoneSignal(EnumFacing side, byte aStrength) {
        if(side != null) {
            int coverIdAtSide = getCoverIDAtSide(side);
            int coverDataAtSide = getCoverDataAtSide(side);
            GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

            if(coverBehavior.manipulatesSidedRedstoneOutput(side, coverIdAtSide, coverDataAtSide, this)) {
                setOutputRedstoneSignal(side, aStrength);
            }
        }
    }

    @Override
    public void setOutputRedstoneSignal(EnumFacing side, byte aStrength) {
        if(side != null) {
            aStrength = (byte) Math.min(Math.max(0, aStrength), 15);
            if (mSidedRedstone[side.getIndex()] != aStrength) {
                mSidedRedstone[side.getIndex()] = aStrength;
                issueBlockUpdate();
            }
        }
    }

    @Override
    public boolean hasMufflerUpgrade() {
        return mMuffler;
    }

    @Override
    public boolean isMufflerUpgradable() {
        return !hasMufflerUpgrade();
    }

    @Override
    public boolean addMufflerUpgrade() {
        return isMufflerUpgradable() && (mMuffler = true);
    }

    @Override
    public boolean hasInventoryBeenModified() {
        return mInventoryChanged;
    }

    @Override
    public void setGenericRedstoneOutput(boolean aOnOff) {
        mRedstone = aOnOff;
    }

    @Override
    public int getErrorDisplayID() {
        return mDisplayErrorCode;
    }

    @Override
    public void setErrorDisplayID(int aErrorID) {
        mDisplayErrorCode = aErrorID;
    }

    @Override
    public IMetaTileEntity getMetaTileEntity() {
        return hasValidMetaTileEntity() ? mMetaTileEntity : null;
    }

    @Override
    public void setMetaTileEntity(IMetaTileEntity aMetaTileEntity) {
        mMetaTileEntity = (MetaTileEntity) aMetaTileEntity;
    }

    @Override
    public void setCoverIDAtSide(EnumFacing side, int aID) {
        if(side == null) return;
        mCoverSides[side.getIndex()] = aID;
        mCoverData[side.getIndex()] = 0;
        issueCoverUpdate(side);
        issueBlockUpdate();
    }

    @Override
    public void setCoverItemAtSide(EnumFacing side, ItemStack aStack) {
        if(side == null) return;
        int coverId = GregTech_API.getCoverId(new GT_ItemStack(aStack));
        GT_CoverBehavior behavior = GregTech_API.getCoverBehavior(coverId);
        behavior.placeCover(side, coverId, aStack, this);
    }

    @Override
    public int getCoverIDAtSide(EnumFacing side) {
        if(side == null) return 0;
        return mCoverSides[side.getIndex()];
    }

    @Override
    public boolean canPlaceCoverIDAtSide(EnumFacing side, int aID) {
        return side != null && getCoverIDAtSide(side) == 0;
    }

    @Override
    public boolean canPlaceCoverItemAtSide(EnumFacing side, ItemStack aCover) {
        return side != null && getCoverIDAtSide(side) == 0;
    }

    @Override
    public void setCoverDataAtSide(EnumFacing side, int aData) {
        if(side == null) return;
        mCoverData[side.getIndex()] = aData;
    }

    @Override
    public int getCoverDataAtSide(EnumFacing side) {
        if(side == null) return 0;
        return mCoverData[side.getIndex()];
    }


    public byte getLightValue() {
        return mLightValue;
    }


    public void setLightValue(byte lightValue) {
        mLightValue = (byte) (lightValue & 15);
    }

    @Override
    public long getAverageElectricInput() {
        int rEU = 0;
        for (int tEU : mAverageEUInput) rEU += tEU;
        return rEU / mAverageEUInput.length;
    }

    @Override
    public long getAverageElectricOutput() {
        int rEU = 0;
        for (int tEU : mAverageEUOutput) rEU += tEU;
        return rEU / mAverageEUOutput.length;
    }

    @Override
    public boolean dropCover(EnumFacing side, EnumFacing droppedSide, boolean forced) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior behavior = GregTech_API.getCoverBehavior(coverIdAtSide);
        if (behavior.onCoverRemoval(side, coverIdAtSide, coverDataAtSide, this, forced) || forced) {
            ItemStack tStack = behavior.getDrop(side, coverIdAtSide, coverDataAtSide, this);
            if (tStack != null) {
                Vec3d dropCoords = new Vec3d(pos.offset(droppedSide)).add(new Vec3d(0.5f, 0.5f, 0.5f));
                EntityItem tEntity = new EntityItem(worldObj, dropCoords.xCoord, dropCoords.yCoord, dropCoords.zCoord, tStack);
                tEntity.motionX = 0;
                tEntity.motionY = 0;
                tEntity.motionZ = 0;
                worldObj.spawnEntityInWorld(tEntity);
            }
            setCoverIDAtSide(side, 0);
            if (mMetaTileEntity.hasSidedRedstoneOutputBehavior()) {
                setOutputRedstoneSignal(side, (byte) 0);
            } else {
                setOutputRedstoneSignal(side, (byte) 15);
            }
            return true;
        }
        return false;
    }

    @Override
    public UUID getOwnerId() {
        return mOwnerId;
    }

    @Override
    public UUID setOwnerId(UUID ownerId) {
        this.mOwnerId = ownerId;
        return mOwnerId;
    }

    @Override
    public byte getComparatorValue(EnumFacing side) {
        return hasValidMetaTileEntity() && side != null ? mMetaTileEntity.getComparatorValue(side) : 0;
    }

    @Override
    public byte getStrongOutputRedstoneSignal(EnumFacing side) {
        return side != null && (mStrongRedstone & (1 << side.getIndex())) != 0 ? (byte) (mSidedRedstone[side.getIndex()] & 15) : 0;
    }

    @Override
    public void setStrongOutputRedstoneSignal(EnumFacing side, byte strength) {
        if(side != null) {
            mStrongRedstone |= (1 << side.getIndex());
            setOutputRedstoneSignal(side, strength);
        }
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (hasValidMetaTileEntity()) {
            mInventoryChanged = true;
            return mMetaTileEntity.decrStackSize(index, amount);
        }
        return null;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public long injectEnergyUnits(EnumFacing side, long voltage, long amperage) {
        if (!hasValidMetaTileEntity() || !mMetaTileEntity.isElectric() ||
                !inputEnergyFrom(side) ||
                amperage <= 0 ||
                voltage <= 0 ||
                getStoredEU() >= getEUCapacity() ||
                mMetaTileEntity.maxAmperesIn() <= mAcceptedAmperes)
            return 0;
        if (voltage > getInputVoltage()) {
            doExplosion(voltage);
            return 0;
        }
        amperage = Math.min(amperage, Math.min(mMetaTileEntity.maxAmperesIn() - mAcceptedAmperes, 1 + ((getEUCapacity() - getStoredEU()) / voltage)));

        if (increaseStoredEnergyUnits(voltage * amperage, true)) {
            mAverageEUInput[mAverageEUInputIndex] += voltage * amperage;
            mAcceptedAmperes += amperage;
            return amperage;
        }
        return 0;
    }



    @Override
    public int fill(EnumFacing side, FluidStack fluid, boolean doFill) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        if(hasValidMetaTileEntity() && !mInputDisabled &&
                coverBehavior.letsFluidIn(side, coverIdAtSide, coverDataAtSide,
                        fluid.getFluid(), this) &&
                mMetaTileEntity.isLiquidOutput(side))
            return mMetaTileEntity.fill(side, fluid, doFill);

        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing side, int maxDrain, boolean doDrain) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        if (hasValidMetaTileEntity() && !mOutputDisabled &&
                coverBehavior.letsFluidOut(side, coverIdAtSide, coverDataAtSide,
                        mMetaTileEntity.getFluid() == null ? null : mMetaTileEntity.getFluid().getFluid(), this) &&
                mMetaTileEntity.isLiquidOutput(side))
            return mMetaTileEntity.drain(side, maxDrain, doDrain);

        return null;
    }

    @Override
    public FluidStack drain(EnumFacing side, FluidStack fluid, boolean doDrain) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        if (hasValidMetaTileEntity() && !mOutputDisabled &&
                coverBehavior.letsFluidOut(side, coverIdAtSide, coverDataAtSide,
                        fluid.getFluid(), this) &&
                mMetaTileEntity.isLiquidOutput(side))
            return mMetaTileEntity.drain(side, fluid, doDrain);

        return null;
    }

    @Override
    public boolean canFill(EnumFacing side, Fluid fluid) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        return hasValidMetaTileEntity() && !mInputDisabled &&
                coverBehavior.letsFluidIn(side, coverIdAtSide, coverDataAtSide, fluid, this) &&
                mMetaTileEntity.isLiquidInput(side) && mMetaTileEntity.canFill(side, fluid);

    }

    @Override
    public boolean canDrain(EnumFacing side, Fluid fluid) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        return hasValidMetaTileEntity() && !mOutputDisabled &&
                coverBehavior.letsFluidOut(side, coverIdAtSide, coverDataAtSide, fluid, this) &&
                mMetaTileEntity.isLiquidOutput(side) && mMetaTileEntity.canDrain(side, fluid);

    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing side) {
        int coverIdAtSide = getCoverIDAtSide(side);
        int coverDataAtSide = getCoverDataAtSide(side);
        GT_CoverBehavior coverBehavior = GregTech_API.getCoverBehavior(coverIdAtSide);

        if (hasValidMetaTileEntity() && (
                (!mOutputDisabled && mMetaTileEntity.isLiquidOutput(side) && coverBehavior.letsFluidOut(side, coverIdAtSide, coverDataAtSide, null, this) ||
                        (!mInputDisabled && mMetaTileEntity.isLiquidInput(side) && coverBehavior.letsFluidIn(side, coverIdAtSide, coverDataAtSide, null, this)))))
            return mMetaTileEntity.getTankInfo(side);

        return new FluidTankInfo[]{};
    }


    ////////////////////////////////////////////////////////////////////////////////
    //                  IEnergySink Implementation                               //
    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public double getDemandedEnergy() {
        if (mReleaseEnergy || !hasValidMetaTileEntity() || !mMetaTileEntity.isEnetInput()) return 0;
        return getEUCapacity() - getStoredEU();
    }

    @Override
    public int getSinkTier() {
        return GT_Utility.getTier(getOutputVoltage());
    }

    @Override
    public double injectEnergy(EnumFacing direction, double amount, double voltage) {
        return injectEnergyUnits(direction, (int) amount, 1) > 0 ? 0 : amount;
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
        return inputEnergyFrom(direction);
    }


    ////////////////////////////////////////////////////////////////////////////////
    //                  IEnergySource Implementation                             //
    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public double getOfferedEnergy() {
        long outputVoltage = getOutputVoltage();
        return (hasValidMetaTileEntity() && getStoredEU() - mMetaTileEntity.getMinimumStoredEU() >= outputVoltage) ? outputVoltage : 0;
    }

    @Override
    public void drawEnergy(double amount) {
        mAverageEUOutput[mAverageEUOutputIndex] += amount;
        decreaseStoredEU((int) amount, true);
    }

    @Override
    public int getSourceTier() {
        return GT_Utility.getTier(getOutputVoltage());
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing direction) {
        return outputsEnergyTo(direction);
    }


    ////////////////////////////////////////////////////////////////////////////////
    //                  IEnergyStorage Implementation                            //
    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public int getStored() {
        return (int) Math.min(Integer.MAX_VALUE, Math.min(getStoredEU(), getCapacity()));
    }

    @Override
    public void setStored(int euStored) {
        if (hasValidMetaTileEntity()) setStoredEU(euStored);
    }

    @Override
    public int addEnergy(int energy) {
        if (!hasValidMetaTileEntity()) return 0;
        if (energy > 0)
            increaseStoredEnergyUnits(energy, true);
        else
            decreaseStoredEU(-energy, true);
        return (int) Math.min(Integer.MAX_VALUE, mMetaTileEntity.getEUVar());
    }

    @Override
    public int getCapacity() {
        return (int) Math.min(Integer.MAX_VALUE, getEUCapacity());
    }

    @Override
    public int getOutput() {
        return (int) getOutputVoltage();
    }

    @Override
    public double getOutputEnergyUnitsPerTick() {
        return getOutputVoltage();
    }

    public boolean isTeleporterCompatible(EnumFacing side) {
        return hasValidMetaTileEntity() && mMetaTileEntity.isTeleporterCompatible();
    }

}