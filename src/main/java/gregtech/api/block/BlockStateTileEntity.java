package gregtech.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public class BlockStateTileEntity extends TileEntity {

    private IBlockState blockState;

    public IBlockState getBlockState() {
        if(blockState == null) {
            this.blockState = world.getBlockState(getPos());
        }
        return blockState;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.blockState = null;
    }
}
