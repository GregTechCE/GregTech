package gregtech.common;

import com.google.common.base.Predicate;
import gregtech.api.enums.StoneTypes;
import gregtech.api.world.GT_Worldgen;
import gregtech.api.world.GT_Worldgen_Ore;
import gregtech.common.blocks.GT_Block_GeneratedOres;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Collection;
import java.util.Random;

public class GT_Worldgen_Stone
        extends GT_Worldgen_Ore {
    public GT_Worldgen_Stone(String aName, boolean aDefault, IBlockState aBlockState, int aDimensionType, int aAmount, int aSize, int aProbability, int aMinY, int aMaxY, Collection<String> aBiomeList, boolean aAllowToGenerateinVoid) {
        super(aName, aDefault, aBlockState, aDimensionType, aAmount, aSize, aProbability, aMinY, aMaxY, aBiomeList, aAllowToGenerateinVoid);
    }

    private BlockPos.MutableBlockPos temp = new BlockPos.MutableBlockPos();

    @Override
    public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkGenerator aChunkGenerator, IChunkProvider aChunkProvider) {
        if ((isDimensionAllowed(aWorld, aDimensionType, this.mDimensionType)) && ((this.mBiomeList.isEmpty()) || (this.mBiomeList.contains(aBiome))) && ((this.mProbability <= 1) || (aRandom.nextInt(this.mProbability) == 0))) {
            for (int i = 0; i < this.mAmount; i++) {
                int tX = aChunkX + aRandom.nextInt(16);
                int tY = this.mMinY + aRandom.nextInt(this.mMaxY - this.mMinY);
                int tZ = aChunkZ + aRandom.nextInt(16);
                temp.setPos(tX, tY, tZ);
                if ((this.mAllowToGenerateinVoid) || !aWorld.isAirBlock(temp)) {
                    float math_pi = 3.141593F;
                    float var6 = aRandom.nextFloat() * math_pi;
                    float var1d = this.mSize / 8.0F;int var2d = tX + 8;int var3d = tZ + 8;int var4d = tY - 2;
                    float mh_s_0 = MathHelper.sin(var6) * var1d;float mh_c_0 = MathHelper.cos(var6) * var1d;
                    float var7 = var2d + mh_s_0;
                    float var11 = var3d + mh_c_0;
                    int var15r = aRandom.nextInt(3);int var17r = aRandom.nextInt(3);
                    int var15 = var4d + var15r;
                    int mh_n_4=var17r - var15r;
                    float mh_n_0 = -2*mh_s_0;float mh_n_1 = -2*mh_c_0;
                    for (int var19 = 0; var19 <= this.mSize; var19++) {
                        float var5d = var19 / this.mSize;
                        float var20 = var7 + mh_n_0 * var5d;
                        float var22 = var15 + mh_n_4 * var5d;
                        float var24 = var11 + mh_n_1 * var5d;
                        float var6d = var19 * math_pi / this.mSize;
                        float var26 = aRandom.nextFloat() * this.mSize / 16.0F;
                        float var28 = ((MathHelper.sin(var6d) + 1.0F) * var26 + 1.0F) / 2.0F;
                        int tMinX = MathHelper.floor_float(var20 - var28);
                        int tMinY = MathHelper.floor_float(var22 - var28);
                        int tMinZ = MathHelper.floor_float(var24 - var28);
                        int tMaxX = MathHelper.floor_float(var20 + var28);
                        int tMaxY = MathHelper.floor_float(var22 + var28);
                        int tMaxZ = MathHelper.floor_float(var24 + var28);
                        for (int eX = tMinX; eX <= tMaxX; eX++) {
                            float var39 = (eX + 0.5F - var20) / (var28);
                            float var10d = var39 * var39;
                            if (var10d < 1.0F) {
                                for (int eY = tMinY; eY <= tMaxY; eY++) {
                                    float var42 = (eY + 0.5F - var22) / (var28);
                                    float var12d = var10d + var42 * var42;
                                    if (var12d < 1.0F) {
                                        for (int eZ = tMinZ; eZ <= tMaxZ; eZ++) {
                                            float var45 = (eZ + 0.5F - var24) / (var28);
                                            if (var12d + var45 * var45 < 1.0F) {
                                                temp.setPos(eX, eY, eZ);
                                                IBlockState tTargetedBlock = aWorld.getBlockState(temp);
                                                if (tTargetedBlock.getBlock() instanceof GT_Block_GeneratedOres) {
                                                    GT_Block_GeneratedOres block = (GT_Block_GeneratedOres) tTargetedBlock.getBlock();
                                                    StoneTypes stoneTypes = StoneTypes.computeStoneType(mBlockState);
                                                    aWorld.setBlockState(temp, block.overrideStoneType(tTargetedBlock, stoneTypes));
                                                } else if ((this.mAllowToGenerateinVoid && aWorld.isAirBlock(temp)) ||
                                                        (tTargetedBlock != null && tTargetedBlock.getBlock().isReplaceableOreGen(tTargetedBlock, aWorld, temp, (Predicate<IBlockState>) GT_Worldgen.ANY))) {
                                                    aWorld.setBlockState(temp, mBlockState);
                                                }
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