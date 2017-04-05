package gregtech.common;

import gregtech.api.GregTech_API;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_Log;
import gregtech.api.world.GT_Worldgen;
import gregtech.common.blocks.GT_Block_GeneratedOres;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class GT_Worldgenerator implements IWorldGenerator {

    private static int mEndAsteroidProbability = 300;
    private static int mGCAsteroidProbability = 50;
    private static int mSize = 100;
    private static int endMinSize = 50;
    private static int endMaxSize = 200;
    private static int gcMinSize = 100;
    private static int gcMaxSize = 400;
    private static boolean endAsteroids = true;
    private static boolean gcAsteroids = true;
    private static Random mRandom;

    public GT_Worldgenerator() {
        endAsteroids = GregTech_API.sWorldgenFile.get("endasteroids", "GenerateAsteroids", true);
        endMinSize = GregTech_API.sWorldgenFile.get("endasteroids", "AsteroidMinSize", 50);
        endMaxSize = GregTech_API.sWorldgenFile.get("endasteroids", "AsteroidMaxSize", 200);
        mEndAsteroidProbability = GregTech_API.sWorldgenFile.get("endasteroids", "AsteroidProbability", 300);
        gcAsteroids = GregTech_API.sWorldgenFile.get("gcasteroids", "GenerateGCAsteroids", true);
        gcMinSize = GregTech_API.sWorldgenFile.get("gcasteroids", "GCAsteroidMinSize", 100);
        gcMaxSize = GregTech_API.sWorldgenFile.get("gcasteroids", "GCAsteroidMaxSize", 400);
        mGCAsteroidProbability = GregTech_API.sWorldgenFile.get("gcasteroids", "GCAsteroidProbability", 300);
        GameRegistry.registerWorldGenerator(this, 1073741823);
    }

    @Override
    public void generate(Random aRandom, int aX, int aZ, World aWorld, IChunkGenerator aChunkGenerator, IChunkProvider aChunkProvider) {
        if(mRandom == null) {
            mRandom = new XSTR(aRandom.nextLong() ^ System.nanoTime());
        }
        Biome biome = aWorld.getBiome(new BlockPos(aX * 16, 64, aZ * 16));
        generateInternal(aX * 16, aZ * 16,
                getDimensionType(aChunkGenerator, biome),
                aWorld, aChunkGenerator, aChunkProvider,
                biome.getBiomeName());
    }

    private int getDimensionType(IChunkGenerator aChunkGenerator, Biome biome) {
        return (aChunkGenerator instanceof ChunkProviderEnd || biome == Biomes.SKY) ? 1 :
                (aChunkGenerator instanceof ChunkProviderHell || biome == Biomes.HELL) ? -1 : 0;
    }


    private void generateInternal(int mX, int mZ, int mDimensionType, World mWorld, IChunkGenerator mChunkGenerator, IChunkProvider mChunkProvider, String mBiome) {
        if ((Math.abs(mX / 16) % 3 == 1) && (Math.abs(mZ / 16) % 3 == 1)) {
            if ((GT_Worldgen_GT_Ore_Layer.sWeight > 0) && (GT_Worldgen_GT_Ore_Layer.sList.size() > 0)) {
                boolean temp = true;
                int tRandomWeight;
                for (int i = 0; (i < 256) && (temp); i++) {
                    tRandomWeight = mRandom.nextInt(GT_Worldgen_GT_Ore_Layer.sWeight);
                    for (GT_Worldgen tWorldGen : GT_Worldgen_GT_Ore_Layer.sList) {
                        tRandomWeight -= ((GT_Worldgen_GT_Ore_Layer) tWorldGen).mWeight;
                        if (tRandomWeight <= 0) {
                            try {
                                if (tWorldGen.executeWorldgen(mWorld, mRandom, mBiome, mDimensionType, mX, mZ, mChunkGenerator, mChunkProvider)) {
                                    temp = false;
                                }
                                break;
                            } catch (Throwable e) {
                                e.printStackTrace(GT_Log.err);
                            }
                        }
                    }
                }
            }
            int i = 0;
            for (int tX = mX - 16; i < 3; tX += 16) {
                int j = 0;
                for (int tZ = mZ - 16; j < 3; tZ += 16) {
                    String tBiome = mWorld.getBiome(new BlockPos(tX + 8, 0, tZ + 8)).getBiomeName();
                    try {
                        for (GT_Worldgen tWorldGen : GregTech_API.sWorldgenList) {
                            tWorldGen.executeWorldgen(mWorld, mRandom, mBiome, mDimensionType, tX, tZ, mChunkGenerator, mChunkProvider);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace(GT_Log.err);
                    }
                    j++;
                }
                i++;
            }
        }
        //Asteroid Worldgen
        int tDimensionType = mWorld.provider.getDimension();
        String tDimensionName = mWorld.getChunkProvider().makeString();
        Random aRandom = new Random();
        if (((tDimensionType == 1) && endAsteroids && ((mEndAsteroidProbability <= 1) || (aRandom.nextInt(mEndAsteroidProbability) == 0))) || ((tDimensionName.equals("Asteroids")) && gcAsteroids && ((mGCAsteroidProbability <= 1) || (aRandom.nextInt(mGCAsteroidProbability) == 0)))) {
            short primaryMeta = 0;
            short secondaryMeta = 0;
            short betweenMeta = 0;
            short sporadicMeta = 0;
            if ((GT_Worldgen_GT_Ore_Layer.sWeight > 0) && (GT_Worldgen_GT_Ore_Layer.sList.size() > 0)) {
                boolean temp = true;
                int tRandomWeight;
                for (int i = 0; (i < 256) && (temp); i++) {
                    tRandomWeight = aRandom.nextInt(GT_Worldgen_GT_Ore_Layer.sWeight);
                    for (GT_Worldgen_GT_Ore_Layer tWorldGen : GT_Worldgen_GT_Ore_Layer.sList) {
                        tRandomWeight -= tWorldGen.mWeight;
                        if (tRandomWeight <= 0) {
                            try {
                                if ((tWorldGen.mEndAsteroid && tDimensionType == 1) || (tWorldGen.mAsteroid && tDimensionType == -30)) {
                                    primaryMeta = tWorldGen.mPrimaryMeta;
                                    secondaryMeta = tWorldGen.mSecondaryMeta;
                                    betweenMeta = tWorldGen.mBetweenMeta;
                                    sporadicMeta = tWorldGen.mSporadicMeta;
                                    temp = false;
                                    break;
                                }
                            } catch (Throwable e) {
                                e.printStackTrace(GT_Log.err);
                            }
                        }
                    }
                }
            }
            int tX = mX + aRandom.nextInt(16);
            int tY = 50 + aRandom.nextInt(200 - 50);
            int tZ = mZ + aRandom.nextInt(16);
            if (tDimensionType == 1) {
                mSize = aRandom.nextInt(endMaxSize - endMinSize);
            } else if (tDimensionName.equals("Asteroids")) {
                mSize = aRandom.nextInt(gcMaxSize - gcMinSize);
            }
            if ((mWorld.isAirBlock(new BlockPos(tX, tY, tZ)))) {
                float var6 = aRandom.nextFloat() * 3.141593F;
                double var7 = tX + 8 + MathHelper.sin(var6) * mSize / 8.0F;
                double var9 = tX + 8 - MathHelper.sin(var6) * mSize / 8.0F;
                double var11 = tZ + 8 + MathHelper.cos(var6) * mSize / 8.0F;
                double var13 = tZ + 8 - MathHelper.cos(var6) * mSize / 8.0F;
                double var15 = tY + aRandom.nextInt(3) - 2;
                double var17 = tY + aRandom.nextInt(3) - 2;
                for (int var19 = 0; var19 <= mSize; var19++) {
                    double var20 = var7 + (var9 - var7) * var19 / mSize;
                    double var22 = var15 + (var17 - var15) * var19 / mSize;
                    double var24 = var11 + (var13 - var11) * var19 / mSize;
                    double var26 = aRandom.nextDouble() * mSize / 16.0D;
                    double var28 = (MathHelper.sin(var19 * 3.141593F / mSize) + 1.0F) * var26 + 1.0D;
                    double var30 = (MathHelper.sin(var19 * 3.141593F / mSize) + 1.0F) * var26 + 1.0D;
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
                                        BlockPos randPos = new BlockPos(eX, eY, eZ);
                                        if ((var39 * var39 + var42 * var42 + var45 * var45 < 1.0D) && mWorld.isAirBlock(randPos)) {
                                            int ranOre = aRandom.nextInt(50);
                                            if (ranOre < 3) {
                                                GT_Block_GeneratedOres.setOreBlock(mWorld, randPos, primaryMeta, false);
                                            } else if (ranOre < 6) {
                                                GT_Block_GeneratedOres.setOreBlock(mWorld, randPos, secondaryMeta, false);
                                            } else if (ranOre < 8) {
                                                GT_Block_GeneratedOres.setOreBlock(mWorld, randPos, betweenMeta, false);
                                            } else if (ranOre < 10) {
                                                GT_Block_GeneratedOres.setOreBlock(mWorld, randPos, sporadicMeta, false);
                                            } else {
                                                if (tDimensionType == -1) {
                                                    mWorld.setBlockState(randPos, Blocks.END_STONE.getDefaultState());
                                                } else if (tDimensionName.equals("Asteroids")) {
                                                    mWorld.setBlockState(randPos, GregTech_API.sBlockGranites.getDefaultState());
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
        }
    }

}