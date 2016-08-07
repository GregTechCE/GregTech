package gregtech.api.metatileentity;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.net.GT_Packet_Block_Event;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.IFluidHandler;

import static gregtech.api.enums.GT_Values.GT;
import static gregtech.api.enums.GT_Values.NW;

/**
 * The Functions my old TileEntities and my BaseMetaTileEntities have in common.
 * <p/>
 * Basically everything a TileEntity should have.
 */
public abstract class BaseTileEntity extends TileEntity implements IHasWorldObjectAndCoords, ITickable {
    /**
     * Buffers adjacent TileEntities for faster access
     * <p/>
     * "this" means that there is no TileEntity, while "null" means that it doesn't know if there is even a TileEntity and still needs to check that if needed.
     */
    private final TileEntity[] mBufferedTileEntities = new TileEntity[6];
    /**
     * If this TileEntity checks for the Chunk to be loaded before returning World based values.
     * The AdvPump hacks this to false to ensure everything runs properly even when far Chunks are not actively loaded.
     * But anything else should not cause worfin' Chunks, uhh I mean orphan Chunks.
     */
    public boolean ignoreUnloadedChunks = true;
    /**
     * This Variable checks if this TileEntity is dead, because Minecraft is too stupid to have proper TileEntity unloading.
     */
    public boolean isDead = false;

    private final void clearNullMarkersFromTileEntityBuffer() {
        for (int i = 0; i < mBufferedTileEntities.length; i++)
            if (mBufferedTileEntities[i] == this) mBufferedTileEntities[i] = null;
    }

    /**
     * Called automatically when the Coordinates of this TileEntity have been changed
     */
    protected final void clearTileEntityBuffer() {
        for (int i = 0; i < mBufferedTileEntities.length; i++) mBufferedTileEntities[i] = null;
    }

    @Override
    public final World getWorld() {
        return worldObj;
    }

    @Override
    public final int getXCoord() {
        return getPos().getX();
    }

    @Override
    public final short getYCoord() {
        return (short) getPos().getY();
    }

    @Override
    public final int getZCoord() {
        return getPos().getZ();
    }

    @Override
    public final int getOffsetX(byte aSide, int aMultiplier) {
        return getXCoord() + EnumFacing.VALUES[aSide].getFrontOffsetX() * aMultiplier;
    }

    @Override
    public final short getOffsetY(byte aSide, int aMultiplier) {
        return (short) (getYCoord() + EnumFacing.VALUES[aSide].getFrontOffsetY() * aMultiplier);
    }

    @Override
    public final int getOffsetZ(byte aSide, int aMultiplier) {
        return getZCoord() + EnumFacing.VALUES[aSide].getFrontOffsetZ() * aMultiplier;
    }

    @Override
    public final boolean isServerSide() {
        return !worldObj.isRemote;
    }

    @Override
    public final boolean isClientSide() {
        return worldObj.isRemote;
    }

    @Override
    public final boolean openGUI(EntityPlayer aPlayer) {
        return openGUI(aPlayer, 0);
    }

    @Override
    public final boolean openGUI(EntityPlayer aPlayer, int aID) {
        if (aPlayer == null) return false;
        aPlayer.openGui(GT, aID, worldObj, getXCoord(), getYCoord(), getZCoord());
        return true;
    }

    @Override
    public final int getRandomNumber(int aRange) {
        return worldObj.rand.nextInt(aRange);
    }

    @Override
    public final Biome getBiome(int aX, int aZ) {
        return worldObj.getBiomeGenForCoords(new BlockPos(aX, 1, aZ));
    }

    @Override
    public final Biome getBiome() {
        return getBiome(getXCoord(), getZCoord());
    }

    @Override
    public final Block getBlockOffset(int aX, int aY, int aZ) {
        return getBlock(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ);
    }

    @Override
    public final Block getBlockAtSide(byte aSide) {
        return getBlockAtSideAndDistance(aSide, 1);
    }

    @Override
    public final Block getBlockAtSideAndDistance(byte aSide, int aDistance) {
        return getBlock(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final byte getMetaIDOffset(int aX, int aY, int aZ) {
        return getMetaID(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ);
    }

    @Override
    public final byte getMetaIDAtSide(byte aSide) {
        return getMetaIDAtSideAndDistance(aSide, 1);
    }

    @Override
    public final byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {
        return getMetaID(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final byte getLightLevelOffset(int aX, int aY, int aZ) {
        return getLightLevel(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ);
    }

    @Override
    public final byte getLightLevelAtSide(byte aSide) {
        return getLightLevelAtSideAndDistance(aSide, 1);
    }

    @Override
    public final byte getLightLevelAtSideAndDistance(byte aSide, int aDistance) {
        return getLightLevel(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final boolean getOpacityOffset(int aX, int aY, int aZ) {
        return getOpacity(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ);
    }

    @Override
    public final boolean getOpacityAtSide(byte aSide) {
        return getOpacityAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getOpacityAtSideAndDistance(byte aSide, int aDistance) {
        return getOpacity(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final boolean getSkyOffset(int aX, int aY, int aZ) {
        return getSky(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ);
    }

    @Override
    public final boolean getSkyAtSide(byte aSide) {
        return getSkyAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {
        return getSky(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final boolean getAirOffset(int aX, int aY, int aZ) {
        return getAir(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ);
    }

    @Override
    public final boolean getAirAtSide(byte aSide) {
        return getAirAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getAirAtSideAndDistance(byte aSide, int aDistance) {
        return getAir(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final TileEntity getTileEntityOffset(int aX, int aY, int aZ) {
        return getTileEntity(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ);
    }

    @Override
    public final TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        if (aDistance == 1) return getTileEntityAtSide(aSide);
        return getTileEntity(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    @Override
    public final IInventory getIInventory(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryOffset(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntityOffset(aX, aY, aZ);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryAtSide(byte aSide) {
        TileEntity tTileEntity = getTileEntityAtSide(aSide);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tTileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainer(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerOffset(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntityOffset(aX, aY, aZ);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerAtSide(byte aSide) {
        TileEntity tTileEntity = getTileEntityAtSide(aSide);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tTileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntity(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof IGregTechTileEntity) return (IGregTechTileEntity) tTileEntity;
        return null;
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntityOffset(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntityOffset(aX, aY, aZ);
        if (tTileEntity instanceof IGregTechTileEntity) return (IGregTechTileEntity) tTileEntity;
        return null;
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntityAtSide(byte aSide) {
        TileEntity tTileEntity = getTileEntityAtSide(aSide);
        if (tTileEntity instanceof IGregTechTileEntity) return (IGregTechTileEntity) tTileEntity;
        return null;
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tTileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        if (tTileEntity instanceof IGregTechTileEntity) return (IGregTechTileEntity) tTileEntity;
        return null;
    }

    @Override
    public final Block getBlock(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(new BlockPos(aX, aY, aZ))) return Blocks.AIR;
        return worldObj.getBlockState(new BlockPos(aX, aY, aZ)).getBlock();
    }

    @Override
    public final byte getMetaID(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(new BlockPos(aX, aY, aZ))) return 0;
        IBlockState blockState = worldObj.getBlockState(new BlockPos(aX, aY, aZ));
        return (byte) blockState.getBlock().getMetaFromState(blockState);
    }

    @Override
    public final byte getLightLevel(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(new BlockPos(aX, aY, aZ))) return 0;
        return (byte) (worldObj.getLightBrightness(new BlockPos(aX, aY, aZ)) * 15);
    }

    @Override
    public final boolean getSky(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(new BlockPos(aX, aY, aZ))) return true;
        return worldObj.canSeeSky(new BlockPos(aX, aY, aZ));
    }

    @Override
    public final boolean getOpacity(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(new BlockPos(aX, aY, aZ))) return false;
        return GT_Utility.isOpaqueBlock(worldObj, aX, aY, aZ);
    }

    @Override
    public final boolean getAir(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(new BlockPos(aX, aY, aZ))) return true;
        return GT_Utility.isBlockAir(worldObj, aX, aY, aZ);
    }

    @Override
    public final TileEntity getTileEntity(int aX, int aY, int aZ) {
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(new BlockPos(aX, aY, aZ))) return null;
        return worldObj.getTileEntity(new BlockPos(aX, aY, aZ));
    }

    @Override
    public final TileEntity getTileEntityAtSide(byte aSide) {
        if (aSide < 0 || aSide >= 6 || mBufferedTileEntities[aSide] == this) return null;
        int tX = getOffsetX(aSide, 1), tY = getOffsetY(aSide, 1), tZ = getOffsetZ(aSide, 1);
        if (crossedChunkBorder(tX, tZ)) {
            mBufferedTileEntities[aSide] = null;
            if (ignoreUnloadedChunks && !worldObj.isBlockLoaded(new BlockPos(tX, tY, tZ))) return null;
        }
        if (mBufferedTileEntities[aSide] == null) {
            mBufferedTileEntities[aSide] = worldObj.getTileEntity(new BlockPos(tX, tY, tZ));
            if (mBufferedTileEntities[aSide] == null) {
                mBufferedTileEntities[aSide] = this;
                return null;
            }
            return mBufferedTileEntities[aSide];
        }
        if (mBufferedTileEntities[aSide].isInvalid()) {
            mBufferedTileEntities[aSide] = null;
            return getTileEntityAtSide(aSide);
        }
        if (mBufferedTileEntities[aSide].getPos().getX() == tX && mBufferedTileEntities[aSide].getPos().getY() == tY && mBufferedTileEntities[aSide].getPos().getZ() == tZ) {
            return mBufferedTileEntities[aSide];
        }
        return null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound aNBT) {
        super.writeToNBT(aNBT);
        //isDead = true;
        return aNBT;
    }

    @Override
    public boolean isDead() {
        return isDead || isInvalidTileEntity();
    }

    @Override
    public void validate() {
        clearNullMarkersFromTileEntityBuffer();
        super.validate();
    }

    @Override
    public void invalidate() {
        clearNullMarkersFromTileEntityBuffer();
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {
        clearNullMarkersFromTileEntityBuffer();
        super.onChunkUnload();
        isDead = true;
    }

    @Override
    public void update() {
        // Well if the TileEntity gets ticked it is alive.
        isDead = false;
    }

    public final void onAdjacentBlockChange(int aX, int aY, int aZ) {
        clearNullMarkersFromTileEntityBuffer();
    }

    @Override
    public final void sendBlockEvent(byte aID, byte aValue) {
        NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_Block_Event(getXCoord(), (short) getYCoord(), getZCoord(), aID, aValue), getXCoord(), getZCoord());
    }

    private boolean crossedChunkBorder(int aX, int aZ) {
        return aX >> 4 != getXCoord() >> 4 || aZ >> 4 != getZCoord() >> 4;
    }

    public final void setOnFire() {
        GT_Utility.setCoordsOnFire(worldObj, getXCoord(), getYCoord(), getZCoord(), false);
    }

    public final void setToFire() {
        worldObj.setBlockState(getPos(), Blocks.FIRE.getDefaultState());
    }
}