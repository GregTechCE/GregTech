package gregtech.common;

import gregtech.api.GregTech_API;
import gregtech.api.world.GT_Worldgen_Ore;
import gregtech.api.world.GT_Worldgen_Constants;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Collection;
import java.util.Random;

public class GT_Worldgen_Stone
        extends GT_Worldgen_Ore {
    public GT_Worldgen_Stone(String aName, boolean aDefault, Block aBlock, int aBlockMeta, int aDimensionType, int aAmount, int aSize, int aProbability, int aMinY, int aMaxY, Collection<String> aBiomeList, boolean aAllowToGenerateinVoid) {
        super(aName, aDefault, aBlock, aBlockMeta, aDimensionType, aAmount, aSize, aProbability, aMinY, aMaxY, aBiomeList, aAllowToGenerateinVoid);
    }

    @Override
    public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkGenerator aChunkGenerator, IChunkProvider aChunkProvider) {
        if ((isDimensionAllowed(aWorld, aDimensionType, this.mDimensionType)) && ((this.mBiomeList.isEmpty()) || (this.mBiomeList.contains(aBiome))) && ((this.mProbability <= 1) || (aRandom.nextInt(this.mProbability) == 0))) {
            for (int i = 0; i < this.mAmount; i++) {
                int tX = aChunkX + aRandom.nextInt(16);
                int tY = this.mMinY + aRandom.nextInt(this.mMaxY - this.mMinY);
                int tZ = aChunkZ + aRandom.nextInt(16);
                if ((this.mAllowToGenerateinVoid) || (!aWorld.isAirBlock(new BlockPos(tX, tY, tZ)))) {
                    float var6 = aRandom.nextFloat() * 3.141593F;
                    double var7 = tX + 8 + MathHelper.sin(var6) * this.mSize / 8.0F;
                    double var9 = tX + 8 - MathHelper.sin(var6) * this.mSize / 8.0F;
                    double var11 = tZ + 8 + MathHelper.cos(var6) * this.mSize / 8.0F;
                    double var13 = tZ + 8 - MathHelper.cos(var6) * this.mSize / 8.0F;
                    double var15 = tY + aRandom.nextInt(3) - 2;
                    double var17 = tY + aRandom.nextInt(3) - 2;
                    for (int var19 = 0; var19 <= this.mSize; var19++) {
                        double var20 = var7 + (var9 - var7) * var19 / this.mSize;
                        double var22 = var15 + (var17 - var15) * var19 / this.mSize;
                        double var24 = var11 + (var13 - var11) * var19 / this.mSize;
                        double var26 = aRandom.nextDouble() * this.mSize / 16.0D;
                        double var28 = (MathHelper.sin(var19 * 3.141593F / this.mSize) + 1.0F) * var26 + 1.0D;
                        double var30 = (MathHelper.sin(var19 * 3.141593F / this.mSize) + 1.0F) * var26 + 1.0D;
                        int tMinX = MathHelper.floor_double(var20 - var28 / 2.0D);
                        int tMinY = MathHelper.floor_double(var22 - var30 / 2.0D);
                        int tMinZ = MathHelper.floor_double(var24 - var28 / 2.0D);
                        int tMaxX = MathHelper.floor_double(var20 + var28 / 2.0D);
                        int tMaxY = MathHelper.floor_double(var22 + var30 / 2.0D);
                        int tMaxZ = MathHelper.floor_double(var24 + var28 / 2.0D);
                        for (int eX = tMinX; eX <= tMaxX; eX++) {
                            double var39 = (eX + 0.5D - var20) / (var28 / 2.0D);
                            if (var39 * var39 < 1.0D) {
                                for (int eY = tMinY; eY <= tMaxY; eY++) {
                                    double var42 = (eY + 0.5D - var22) / (var30 / 2.0D);
                                    if (var39 * var39 + var42 * var42 < 1.0D) {
                                        for (int eZ = tMinZ; eZ <= tMaxZ; eZ++) {
                                            double var45 = (eZ + 0.5D - var24) / (var28 / 2.0D);
                                            if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D) {
                                                BlockPos randPos = new BlockPos(eX, eY, eZ);
                                                IBlockState tTargetedBlock = aWorld.getBlockState(randPos);
                                                if (tTargetedBlock instanceof GT_Block_Ores_Abstract) {
                                                    TileEntity tTileEntity = aWorld.getTileEntity(randPos);
                                                    if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                                                        if (tTargetedBlock != GregTech_API.sBlockOres1) {
                                                            ((GT_TileEntity_Ores) tTileEntity).convertOreBlock(aWorld, eX, eY, eZ);
                                                        }
                                                        ((GT_TileEntity_Ores)tTileEntity).overrideOreBlockMaterial(this.mBlock, (byte) this.mBlockMeta);
                                                    }
                                                } else if ((this.mAllowToGenerateinVoid && aWorld.isAirBlock(randPos)) ||
                                                        (tTargetedBlock != null && tTargetedBlock.getBlock().isReplaceableOreGen(tTargetedBlock, aWorld, randPos, GT_Worldgen_Constants.ANY))) {
                                                    aWorld.setBlockState(randPos, this.mBlock.getStateFromMeta(mBlockMeta));
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
