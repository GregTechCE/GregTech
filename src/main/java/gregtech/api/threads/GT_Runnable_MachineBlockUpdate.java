package gregtech.api.threads;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IMachineBlockUpdateable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GT_Runnable_MachineBlockUpdate implements Runnable {
    private final int mX, mY, mZ;
    private final World mWorld;

    public GT_Runnable_MachineBlockUpdate(World aWorld, int aX, int aY, int aZ) {
        mWorld = aWorld;
        mX = aX;
        mY = aY;
        mZ = aZ;
    }

    private static void stepToUpdateMachine(World aWorld, int aX, int aY, int aZ, ArrayList<BlockPos> aList) {
        aList.add(new BlockPos(aX, aY, aZ));
        TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(aX, aY, aZ));
        if (tTileEntity instanceof IMachineBlockUpdateable)
            ((IMachineBlockUpdateable) tTileEntity).onMachineBlockUpdate();
        BlockPos pos = new BlockPos(aX, aY, aZ);
        IBlockState block = aWorld.getBlockState(pos);
        if (aList.size() < 5 || (tTileEntity instanceof IMachineBlockUpdateable) || GregTech_API.isMachineBlock(block.getBlock(), block.getBlock().getMetaFromState(block))) {
            if (!aList.contains(new BlockPos(aX + 1, aY, aZ))) stepToUpdateMachine(aWorld, aX + 1, aY, aZ, aList);
            if (!aList.contains(new BlockPos(aX - 1, aY, aZ))) stepToUpdateMachine(aWorld, aX - 1, aY, aZ, aList);
            if (!aList.contains(new BlockPos(aX, aY + 1, aZ))) stepToUpdateMachine(aWorld, aX, aY + 1, aZ, aList);
            if (!aList.contains(new BlockPos(aX, aY - 1, aZ))) stepToUpdateMachine(aWorld, aX, aY - 1, aZ, aList);
            if (!aList.contains(new BlockPos(aX, aY, aZ + 1))) stepToUpdateMachine(aWorld, aX, aY, aZ + 1, aList);
            if (!aList.contains(new BlockPos(aX, aY, aZ - 1))) stepToUpdateMachine(aWorld, aX, aY, aZ - 1, aList);
        }
    }

    @Override
    public void run() {
        try {
            stepToUpdateMachine(mWorld, mX, mY, mZ, new ArrayList<BlockPos>());
        } catch (Throwable e) {/**/}
    }
}