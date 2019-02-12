package gregtech.api.worldgen.populator;

import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.generator.GridEntryInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface VeinBufferPopulator extends IVeinPopulator {

    void populateBlockBuffer(OreDepositDefinition definition, Random random, GridEntryInfo gridEntryInfo, IBlockModifierAccess modifier);

    IBlockState getBlockByIndex(World world, OreDepositDefinition definition, BlockPos pos, int index);
}
