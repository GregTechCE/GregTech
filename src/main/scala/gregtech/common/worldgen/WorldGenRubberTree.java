package gregtech.common.worldgen;

import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockSaplingGT;
import gregtech.common.blocks.wood.BlockLogGT.LogVariant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenRubberTree implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.isSurfaceWorld() && random.nextInt(5) == 0) {
            BlockPos randomPos = new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8);
            randomPos = world.getTopSolidOrLiquidBlock(randomPos).down();
            IBlockState solidBlockState = world.getBlockState(randomPos);
            BlockSaplingGT sapling = MetaBlocks.SAPLING;
            if(solidBlockState.getBlock().canSustainPlant(solidBlockState, world, randomPos, EnumFacing.UP, sapling)) {
                BlockPos abovePos = randomPos.up();
                IBlockState saplingState = sapling.getDefaultState()
                    .withProperty(BlockSaplingGT.VARIANT, LogVariant.RUBBER_WOOD);
                world.setBlockState(abovePos, saplingState);
                sapling.generateTree(world, abovePos, saplingState, random);
            }
        }
    }

}
