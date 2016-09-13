package gregtech.api.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

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
                if (mAllowToGenerateinVoid || aWorld.getBlock(tX, tY, tZ).isAir(aWorld, tX, tY, tZ)) {
                    float math_pi = 3.141593F;float var1b = mSize / 8.0F;
                    float var6 = aRandom.nextFloat() * math_pi;
                    float var3b = MathHelper.sin(var6) * var1b; float var4b = MathHelper.cos(var6) * var1b;
                    float var8b = -2*var3b;float var9b = -2*var4b;
                    int var10b = (tX + 8);int var11b = (tZ + 8);
                    float var7 = (var10b + var3b);
                    float var11 = (var11b + var4b);
                    int var5b = aRandom.nextInt(3);int var6b = aRandom.nextInt(3);int var7b = var6b - var5b;
                    float var15 = (tY + var5b - 2);
                    float var12b = math_pi / mSize;

                    for (int var19 = 0; var19 <= mSize; ++var19) {
                        float var2b = var19 / mSize;
                        float var20 = var7 + var8b * var2b;
                        float var22 = var15 + var7b * var2b;
                        float var24 = var11 + var9b * var2b;
                        float var26 = aRandom.nextFloat() * mSize / 16.0F;
                        float var28 = ((MathHelper.sin(var19 * var12b) + 1.0F) * var26 + 1.0F) / 2.0F;
                        int var32 = MathHelper.floor_float(var20 - var28);
                        int var33 = MathHelper.floor_float(var22 - var28);
                        int var34 = MathHelper.floor_float(var24 - var28);
                        int var35 = MathHelper.floor_float(var20 + var28);
                        int var36 = MathHelper.floor_float(var22 + var28);
                        int var37 = MathHelper.floor_float(var24 + var28);

                        for (int var38 = var32; var38 <= var35; ++var38) {
                            float var39 = (var38 + 0.5F - var20) / (var28);
                            float var13b = var39 * var39;
                            if (var13b < 1.0F) {
                                for (int var41 = var33; var41 <= var36; ++var41) {
                                    float var42 = (var41 + 0.5F - var22) / (var28);
                                    float var14b = var13b + var42 * var42;
                                    if (var14b < 1.0F) {
                                        for (int var44 = var34; var44 <= var37; ++var44) {
                                            float var45 = (var44 + 0.5F - var24) / (var28);
                                            Block block = aWorld.getBlock(var38, var41, var44);
                                            if (var14b + var45 * var45 < 1.0F && ((mAllowToGenerateinVoid && aWorld.getBlock(var38, var41, var44).isAir(aWorld, var38, var41, var44)) || (block != null && (block.isReplaceableOreGen(aWorld, var38, var41, var44, Blocks.stone) || block.isReplaceableOreGen(aWorld, var38, var41, var44, Blocks.end_stone) || block.isReplaceableOreGen(aWorld, var38, var41, var44, Blocks.netherrack))))) {
                                                aWorld.setBlock(var38, var41, var44, mBlock, mBlockMeta, 0);
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
}