package gregtech.api.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Collection;
import java.util.Random;

public class GT_Worldgen_Ore_SingleBlock_UnderLava extends GT_Worldgen_Ore {
    public GT_Worldgen_Ore_SingleBlock_UnderLava(String aName, boolean aDefault, Block aBlock, int aBlockMeta, int aDimensionType, int aAmount, int aSize, int aProbability, int aMinY, int aMaxY, Collection<String> aBiomeList, boolean aAllowToGenerateinVoid) {
        super(aName, aDefault, aBlock, aBlockMeta, aDimensionType, aAmount, aSize, aProbability, aMinY, aMaxY, aBiomeList, aAllowToGenerateinVoid);
    }

    @Override
    public boolean executeCavegen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider) {
        if (isGenerationAllowed(aWorld, aDimensionType, mDimensionType) && (mBiomeList.isEmpty() || mBiomeList.contains(aBiome)) && (mProbability <= 1 || aRandom.nextInt(mProbability) == 0)) {
            for (int i = 0; i < mAmount; i++) {
                int tX = aChunkX + aRandom.nextInt(16), tY = mMinY + aRandom.nextInt(mMaxY - mMinY), tZ = aChunkZ + aRandom.nextInt(16);
                BlockPos blockPos = new BlockPos(tX, tY, tZ);
                IBlockState tBlock = aWorld.getBlockState(blockPos);
                if ((mAllowToGenerateinVoid && aWorld.isAirBlock(blockPos)) || tBlock.getBlock().isReplaceableOreGen(tBlock, aWorld, blockPos, GT_Worldgen_Ore_Normal.STONE)) {
                    if (aWorld.getBlockState(blockPos.up()).getBlock() == Blocks.LAVA || aWorld.getBlockState(blockPos).getBlock() == Blocks.FLOWING_LAVA)
                        aWorld.setBlockState(blockPos, mBlock.getStateFromMeta(mBlockMeta));
                }
            }
            return true;
        }
        return false;
    }
}