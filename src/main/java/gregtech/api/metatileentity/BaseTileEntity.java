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
    public World getWorldObj() {
        return worldObj;
    }

    @Override
    public BlockPos getWorldPos() {
        return pos;
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

    private BlockPos.MutableBlockPos M = new BlockPos.MutableBlockPos();

    @Override
    public final Biome getBiome() {
        return worldObj.getBiome(getPos());
    }

    @Override
    public final IBlockState getBlockStateOffset(int aX, int aY, int aZ) {
        return worldObj.getBlockState(M.setPos(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ));
    }

    @Override
    public final Block getBlockOffset(int aX, int aY, int aZ) {
        return getBlockStateOffset(aX, aY, aZ).getBlock();
    }

    @Override
    public final Block getBlockAtSide(byte aSide) {
        return getBlockAtSideAndDistance(aSide, 1);
    }

    public final IBlockState getBlockStateAtSideAndDistance(byte aSide, int aDistance) {
        EnumFacing side = EnumFacing.VALUES[aSide];
        return getBlockStateOffset(side.getFrontOffsetX() * aDistance, side.getFrontOffsetY() * aDistance, side.getFrontOffsetZ() * aDistance);
    }

    @Override
    public final Block getBlockAtSideAndDistance(byte aSide, int aDistance) {
        return getBlockStateAtSideAndDistance(aSide, aDistance).getBlock();
    }

    @Override
    public final byte getMetaIDOffset(int aX, int aY, int aZ) {
        IBlockState blockState = getBlockStateOffset(aX, aY, aZ);
        return (byte) blockState.getBlock().getMetaFromState(blockState);
    }

    @Override
    public final byte getMetaIDAtSide(byte aSide) {
        return getMetaIDAtSideAndDistance(aSide, 1);
    }

    @Override
    public final byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {
        IBlockState blockState = getBlockStateAtSideAndDistance(aSide, aDistance);
        return (byte) blockState.getBlock().getMetaFromState(blockState);
    }

    @Override
    public final boolean getOpacityOffset(int aX, int aY, int aZ) {
        return getBlockStateOffset(aX, aY, aZ).isOpaqueCube();
    }

    @Override
    public final boolean getOpacityAtSide(byte aSide) {
        return getOpacityAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getOpacityAtSideAndDistance(byte aSide, int aDistance) {
        return getBlockStateAtSideAndDistance(aSide, aDistance).isOpaqueCube();
    }

    @Override
    public final boolean getSkyOffset(int aX, int aY, int aZ) {
        return worldObj.canSeeSky(M.setPos(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ));
    }

    @Override
    public final boolean getSkyAtSide(byte aSide) {
        return getSkyAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {
        EnumFacing side = EnumFacing.VALUES[aSide];
        return getSkyOffset(side.getFrontOffsetX() * aDistance, side.getFrontOffsetY() * aDistance, side.getFrontOffsetZ() * aDistance);
    }

    @Override
    public final boolean getAirOffset(int aX, int aY, int aZ) {
        return worldObj.isAirBlock(M.setPos(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ));
    }

    @Override
    public final boolean getAirAtSide(byte aSide) {
        return getAirAtSideAndDistance(aSide, 1);
    }

    @Override
    public final boolean getAirAtSideAndDistance(byte aSide, int aDistance) {
        EnumFacing side = EnumFacing.VALUES[aSide];
        return getAirOffset(side.getFrontOffsetX() * aDistance, side.getFrontOffsetY() * aDistance, side.getFrontOffsetZ() * aDistance);
    }

    @Override
    public final TileEntity getTileEntityOffset(int aX, int aY, int aZ) {
        return worldObj.getTileEntity(M.setPos(getXCoord() + aX, getYCoord() + aY, getZCoord() + aZ));
    }

    @Override
    public final TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        EnumFacing side = EnumFacing.VALUES[aSide];
        return getTileEntityOffset(side.getFrontOffsetX() * aDistance, side.getFrontOffsetY() * aDistance, side.getFrontOffsetZ() * aDistance);
    }

    @Override
    public final IFluidHandler getITankContainer(int aX, int aY, int aZ) {
        TileEntity tileEntity = getTileEntity(aX, aY, aZ);
        return tileEntity instanceof IFluidHandler ? (IFluidHandler) tileEntity : null;
    }

    @Override
    public final IFluidHandler getITankContainerOffset(int aX, int aY, int aZ) {
        TileEntity tileEntity = getTileEntityOffset(aX, aY, aZ);
        return tileEntity instanceof IFluidHandler ? (IFluidHandler) tileEntity : null;
    }

    @Override
    public final IFluidHandler getITankContainerAtSide(byte aSide) {
        return getITankContainerAtSideAndDistance(aSide, 1);
    }

    @Override
    public final IFluidHandler getITankContainerAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        return tileEntity instanceof IFluidHandler ? (IFluidHandler) tileEntity : null;
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntity(int aX, int aY, int aZ) {
        TileEntity tileEntity = getTileEntity(aX, aY, aZ);
        return tileEntity instanceof IGregTechTileEntity ? (IGregTechTileEntity) tileEntity : null;
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntityOffset(int aX, int aY, int aZ) {
        TileEntity tileEntity = getTileEntityOffset(aX, aY, aZ);
        return tileEntity instanceof IGregTechTileEntity ? (IGregTechTileEntity) tileEntity : null;
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntityAtSide(byte aSide) {
        return getIGregTechTileEntityAtSideAndDistance(aSide, 1);
    }

    @Override
    public final IGregTechTileEntity getIGregTechTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        return tileEntity instanceof IGregTechTileEntity ? (IGregTechTileEntity) tileEntity : null;
    }

    @Override
    public final Block getBlock(int aX, int aY, int aZ) {
        M.setPos(aX, aY, aZ);
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(M))
            return Blocks.AIR;
        return worldObj.getBlockState(M).getBlock();
    }

    @Override
    public final byte getMetaID(int aX, int aY, int aZ) {
        M.setPos(aX, aY, aZ);
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(M))
            return 0;
        IBlockState blockState = worldObj.getBlockState(M);
        return (byte) blockState.getBlock().getMetaFromState(blockState);
    }

    @Override
    public final boolean getSky(int aX, int aY, int aZ) {
        M.setPos(aX, aY, aZ);
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(M))
            return false;
        return worldObj.canSeeSky(M);
    }

    @Override
    public final boolean getOpacity(int aX, int aY, int aZ) {
        M.setPos(aX, aY, aZ);
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(M))
            return false;
        IBlockState blockState = worldObj.getBlockState(M);
        return blockState.isOpaqueCube();
    }

    @Override
    public final boolean getAir(int aX, int aY, int aZ) {
        M.setPos(aX, aY, aZ);
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(M))
            return false;
        return worldObj.isAirBlock(M);
    }

    @Override
    public final TileEntity getTileEntity(int aX, int aY, int aZ) {
        M.setPos(aX, aY, aZ);
        if (ignoreUnloadedChunks && crossedChunkBorder(aX, aZ) && !worldObj.isBlockLoaded(M))
            return null;
        return worldObj.getTileEntity(M);
    }

    @Override
    public final TileEntity getTileEntityAtSide(byte aSide) {
        EnumFacing side = EnumFacing.VALUES[aSide];
        M.setPos(getXCoord() + side.getFrontOffsetX(), getYCoord() + side.getFrontOffsetY(), getZCoord() + side.getFrontOffsetZ());
        
        if (aSide < 0 || aSide >= 6 || mBufferedTileEntities[aSide] == this) return null;
        int tX = getOffsetX(aSide, 1), tY = getOffsetY(aSide, 1), tZ = getOffsetZ(aSide, 1);
        if (crossedChunkBorder(tX, tZ)) {
            mBufferedTileEntities[aSide] = null;
            if (ignoreUnloadedChunks && !worldObj.isBlockLoaded(M)) return null;
        }
        if (mBufferedTileEntities[aSide] == null) {
            mBufferedTileEntities[aSide] = worldObj.getTileEntity(M);
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
    public IBlockState getBlockState(BlockPos pos) {
        return worldObj.getBlockState(pos);
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState state) {
        return worldObj.setBlockState(pos, state);
    }

    @Override
    public boolean setBlockToAir(BlockPos pos) {
        return worldObj.setBlockToAir(pos);
    }

    @Override
    public boolean isAir(BlockPos pos) {
        return worldObj.isAirBlock(pos);
    }

    @Override
    public int getOffsetX(byte aSide, int aMultiplier) {
        return getXCoord() + EnumFacing.VALUES[aSide].getFrontOffsetX() * aMultiplier;
    }

    @Override
    public short getOffsetY(byte aSide, int aMultiplier) {
        return (short) (getYCoord() + EnumFacing.VALUES[aSide].getFrontOffsetY() * aMultiplier);
    }

    @Override
    public int getOffsetZ(byte aSide, int aMultiplier) {
        return getZCoord() + EnumFacing.VALUES[aSide].getFrontOffsetZ() * aMultiplier;
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
        NW.sendToAllAround(worldObj, new GT_Packet_Block_Event(getXCoord(),
                                                               (short) getYCoord(),
                                                               getZCoord(),
                                                               aID,
                                                               aValue),
                           getXCoord(),
                           getYCoord(),
                           getZCoord());
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
    
    @Override 
    public void markDirty() {/* Do not do the super Function */} 
}
