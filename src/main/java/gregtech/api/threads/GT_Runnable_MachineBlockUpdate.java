package gregtech.api.threads;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IMachineBlockUpdateable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GT_Runnable_MachineBlockUpdate implements Runnable {

    private final BlockPos pos;
    private final World mWorld;

    public GT_Runnable_MachineBlockUpdate(World aWorld, BlockPos pos) {
        mWorld = aWorld;
        this.pos = pos.toImmutable();
    }

    private static void stepToUpdateMachine(World aWorld, BlockPos nextPos, ArrayList<BlockPos> aList) {
        if(aList.contains(nextPos)) return;
        aList.add(nextPos);
        TileEntity tTileEntity = aWorld.getTileEntity(nextPos);
        if (tTileEntity instanceof IMachineBlockUpdateable)
            ((IMachineBlockUpdateable) tTileEntity).onMachineBlockUpdate();
        IBlockState block = aWorld.getBlockState(nextPos);
        if (aList.size() < 5 || (tTileEntity instanceof IMachineBlockUpdateable) || GregTech_API.isMachineBlock(block)) {
            for(EnumFacing facing : EnumFacing.VALUES) {
                stepToUpdateMachine(aWorld, nextPos.offset(facing), aList);
            }
        }
    }

    @Override
    public void run() {
        try {
            stepToUpdateMachine(mWorld, pos, new ArrayList<BlockPos>());
        } catch (Throwable e) {/**/}
    }

}