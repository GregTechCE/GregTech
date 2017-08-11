package gregtech.api.capability.internal;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.IFluidHandler;

public interface IHasWorldObjectAndCoords {

    World getWorldObj();

    BlockPos getWorldPos();

    default boolean isServerSide() {
        return !getWorldObj().isRemote;
    }

    default boolean isClientSide() {
        return getWorldObj().isRemote;
    }

    /**
     * @return the time this TileEntity has been loaded.
     */
    long getTimer();

    default int getRandomNumber(int range) {
        return getWorldObj().rand.nextInt(range);
    }

    void markDirty();

}