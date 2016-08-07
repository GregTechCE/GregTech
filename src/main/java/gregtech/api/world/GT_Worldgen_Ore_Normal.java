package gregtech.api.world;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IBlockStatePalette;
import net.minecraft.world.chunk.IChunkProvider;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Random;

public class GT_Worldgen_Ore_Normal extends GT_Worldgen_Ore {
    public GT_Worldgen_Ore_Normal(String aName, boolean aDefault, Block aBlock, int aBlockMeta, int aDimensionType, int aAmount, int aSize, int aProbability, int aMinY, int aMaxY, Collection<String> aBiomeList, boolean aAllowToGenerateinVoid) {
        super(aName, aDefault, aBlock, aBlockMeta, aDimensionType, aAmount, aSize, aProbability, aMinY, aMaxY, aBiomeList, aAllowToGenerateinVoid);
    }

    @Override
    public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider) {
        if (isGenerationAllowed(aWorld, aDimensionType, mDimensionType) && (mBiomeList.isEmpty() || mBiomeList.contains(aBiome)) && (mProbability <= 1 || aRandom.nextInt(mProbability) == 0)) {
            for (int i = 0; i < mAmount; i++) {
                int tX = aChunkX + aRandom.nextInt(16), tY = mMinY + aRandom.nextInt(mMaxY - mMinY), tZ = aChunkZ + aRandom.nextInt(16);
                if (mAllowToGenerateinVoid || aWorld.isAirBlock(new BlockPos(tX, tY, tZ))) {
                    float var6 = aRandom.nextFloat() * (float) Math.PI;
                    double var7 = ((tX + 8) + MathHelper.sin(var6) * mSize / 8.0F);
                    double var9 = ((tX + 8) - MathHelper.sin(var6) * mSize / 8.0F);
                    double var11 = ((tZ + 8) + MathHelper.cos(var6) * mSize / 8.0F);
                    double var13 = ((tZ + 8) - MathHelper.cos(var6) * mSize / 8.0F);
                    double var15 = (tY + aRandom.nextInt(3) - 2);
                    double var17 = (tY + aRandom.nextInt(3) - 2);

                    for (int var19 = 0; var19 <= mSize; ++var19) {
                        double var20 = var7 + (var9 - var7) * var19 / mSize;
                        double var22 = var15 + (var17 - var15) * var19 / mSize;
                        double var24 = var11 + (var13 - var11) * var19 / mSize;
                        double var26 = aRandom.nextDouble() * mSize / 16.0D;
                        double var28 = (MathHelper.sin(var19 * (float) Math.PI / mSize) + 1.0F) * var26 + 1.0D;
                        double var30 = (MathHelper.sin(var19 * (float) Math.PI / mSize) + 1.0F) * var26 + 1.0D;
                        int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
                        int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
                        int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
                        int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
                        int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
                        int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);

                        for (int var38 = var32; var38 <= var35; ++var38) {
                            double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);
                            if (var39 * var39 < 1.0D) {
                                for (int var41 = var33; var41 <= var36; ++var41) {
                                    double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);
                                    if (var39 * var39 + var42 * var42 < 1.0D) {
                                        for (int var44 = var34; var44 <= var37; ++var44) {
                                            double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);
                                            BlockPos blockPos = new BlockPos(var38, var41, var44);
                                            IBlockState block = aWorld.getBlockState(blockPos);
                                            if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && ((mAllowToGenerateinVoid && aWorld.isAirBlock(blockPos)) || block.getBlock().isReplaceableOreGen(block, aWorld, blockPos, STONE))) {
                                                aWorld.setBlockState(new BlockPos(var38, var41, var44), mBlock.getStateFromMeta(mBlockMeta));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;



    }


    public static Predicate<IBlockState> STONE = new Predicate<IBlockState>() {
        @Override
        public boolean apply(@Nullable IBlockState input) {
            return input.getBlock() == Blocks.STONE ||
                    input.getBlock() == Blocks.END_STONE ||
                    input.getBlock() == Blocks.NETHERRACK;
        }
    };

}