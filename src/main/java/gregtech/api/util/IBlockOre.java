package gregtech.api.util;

import gregtech.api.unification.ore.StoneType;
import net.minecraft.block.state.IBlockState;

public interface IBlockOre {

    public IBlockState getOreBlock(StoneType stoneType, boolean small);

}
