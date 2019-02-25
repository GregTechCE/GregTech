package gregtech.api.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface WorldBlockPredicate {

    boolean test(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos);
}
