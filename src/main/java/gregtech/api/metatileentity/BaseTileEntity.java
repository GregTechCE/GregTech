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

    public final void setOnFire() {
        GT_Utility.setCoordsOnFire(worldObj, getXCoord(), getYCoord(), getZCoord(), false);
    }

    public final void setToFire() {
        worldObj.setBlockState(getPos(), Blocks.FIRE.getDefaultState());
    }

    @Override
    public void markDirty() {/* Do not do the super Function */}
}
