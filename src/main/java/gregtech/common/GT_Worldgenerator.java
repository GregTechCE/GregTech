package gregtech.common;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Log;
import gregtech.api.world.GT_Worldgen;
import gregtech.common.blocks.GT_TileEntity_Ores;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderHell;

public class GT_Worldgenerator
  implements IWorldGenerator
{
  public static boolean sAsteroids = true;
  public List<Runnable> mList = new ArrayList();
  public boolean mIsGenerating = false;
	private static int mEndAsteroidProbability = 300;
	private static int mSize = 100;
	private static int endMinSize = 50;
	private static int endMaxSize = 200;
	private static boolean endAsteroids=true;
  
  
  public GT_Worldgenerator()
  {		endAsteroids = GregTech_API.sWorldgenFile.get("endasteroids", "GenerateAsteroids", true);
		endMinSize = GregTech_API.sWorldgenFile.get("endasteroids", "AsteroidMinSize", 50);
		endMaxSize = GregTech_API.sWorldgenFile.get("endasteroids", "AsteroidMaxSize", 200);
		mEndAsteroidProbability = GregTech_API.sWorldgenFile.get("endasteroids", "AsteroidProbability", 300);
    GameRegistry.registerWorldGenerator(this, 1073741823);
  }
  
  public void generate(Random aRandom, int aX, int aZ, World aWorld, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider)
  {
    this.mList.add(new WorldGenContainer(new Random(aRandom.nextInt()), aX * 16, aZ * 16, ((aChunkGenerator instanceof ChunkProviderEnd)) || (aWorld.getBiomeGenForCoords(aX * 16 + 8, aZ * 16 + 8) == BiomeGenBase.sky) ? 1 : ((aChunkGenerator instanceof ChunkProviderHell)) || (aWorld.getBiomeGenForCoords(aX * 16 + 8, aZ * 16 + 8) == BiomeGenBase.hell) ? -1 : 0, aWorld, aChunkGenerator, aChunkProvider, aWorld.getBiomeGenForCoords(aX * 16 + 8, aZ * 16 + 8).biomeName));
    if (!this.mIsGenerating)
    {
      this.mIsGenerating = true;
      for (int i = 0; i < this.mList.size(); i++) {
        ((Runnable)this.mList.get(i)).run();
      }
      this.mList.clear();
      this.mIsGenerating = false;
    }
  }
  
  public static class WorldGenContainer
    implements Runnable
  {
    public final Random mRandom;
    public final int mX;
    public final int mZ;
    public final int mDimensionType;
    public final World mWorld;
    public final IChunkProvider mChunkGenerator;
    public final IChunkProvider mChunkProvider;
    public final String mBiome;
    
    public WorldGenContainer(Random aRandom, int aX, int aZ, int aDimensionType, World aWorld, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider, String aBiome)
    {
      this.mRandom = aRandom;
      this.mX = aX;
      this.mZ = aZ;
      this.mDimensionType = aDimensionType;
      this.mWorld = aWorld;
      this.mChunkGenerator = aChunkGenerator;
      this.mChunkProvider = aChunkProvider;
      this.mBiome = aBiome;
    }
    
    public void run()
    {
      if ((Math.abs(this.mX / 16) % 3 == 1) && (Math.abs(this.mZ / 16) % 3 == 1))
      {
        if ((GT_Worldgen_GT_Ore_Layer.sWeight > 0) && (GT_Worldgen_GT_Ore_Layer.sList.size() > 0))
        {
          boolean temp = true;
          int tRandomWeight;
          for (int i = 0; (i < 256) && (temp); i++)
          {
            tRandomWeight = this.mRandom.nextInt(GT_Worldgen_GT_Ore_Layer.sWeight);
            for (GT_Worldgen tWorldGen : GT_Worldgen_GT_Ore_Layer.sList)
            {
              tRandomWeight -= ((GT_Worldgen_GT_Ore_Layer)tWorldGen).mWeight;
              if (tRandomWeight <= 0) {
                try
                {
                  if (tWorldGen.executeWorldgen(this.mWorld, this.mRandom, this.mBiome, this.mDimensionType, this.mX, this.mZ, this.mChunkGenerator, this.mChunkProvider))
                  {
                    temp = false;
                  }
                  break;
                }
                catch (Throwable e)
                {
                  e.printStackTrace(GT_Log.err);
                }
              }
            }
          }
        }
        int i = 0;
        for (int tX = this.mX - 16; i < 3; tX += 16)
        {
          int j = 0;
          for (int tZ = this.mZ - 16; j < 3; tZ += 16)
          {
            String tBiome = this.mWorld.getBiomeGenForCoords(tX + 8, tZ + 8).biomeName;
            if (tBiome == null) {
              tBiome = BiomeGenBase.plains.biomeName;
            }
            for (GT_Worldgen tWorldGen : GregTech_API.sWorldgenList) {
              try
              {
                tWorldGen.executeWorldgen(this.mWorld, this.mRandom, this.mBiome, this.mDimensionType, tX, tZ, this.mChunkGenerator, this.mChunkProvider);
              }
              catch (Throwable e)
              {
                e.printStackTrace(GT_Log.err);
              }
            }
            j++;
          }
          i++;
        }
      }
      //Asteroid Worldgen
      int tDimensionType = this.mWorld.provider.dimensionId;
      Random aRandom = new Random();
      if (((tDimensionType == 1)&& endAsteroids && ((mEndAsteroidProbability <= 1) || (aRandom.nextInt(mEndAsteroidProbability) == 0)))) {
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
						tRandomWeight -= ((GT_Worldgen_GT_Ore_Layer) tWorldGen).mWeight;
						if (tRandomWeight <= 0) {
							try {
								if (tWorldGen.mEndAsteroid && tDimensionType == 1) {
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
				mSize = aRandom.nextInt((int) (endMaxSize - endMinSize));
			}
			if ((mWorld.getBlock(tX, tY, tZ).isAir(mWorld, tX, tY, tZ))) {
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
										if ((var39 * var39 + var42 * var42 + var45 * var45 < 1.0D) && (mWorld.getBlock(tX, tY, tZ).isAir(mWorld, tX, tY, tZ))) {
											int ranOre = aRandom.nextInt(50);
											if (ranOre < 3) {
												GT_TileEntity_Ores.setOreBlock(mWorld, eX, eY, eZ, primaryMeta + (tDimensionType == -30 ? 4000 : 2000), true);
											} else if (ranOre < 6) {
												GT_TileEntity_Ores.setOreBlock(mWorld, eX, eY, eZ, secondaryMeta + (tDimensionType == -30 ? 4000 : 2000), true);
											} else if (ranOre < 8) {
												GT_TileEntity_Ores.setOreBlock(mWorld, eX, eY, eZ, betweenMeta + (tDimensionType == -30 ? 4000 : 2000), true);
											} else if (ranOre < 10) {
												GT_TileEntity_Ores.setOreBlock(mWorld, eX, eY, eZ, sporadicMeta + (tDimensionType == -30 ? 4000 : 2000), true);
											} else {
												if (tDimensionType == -30) {
													mWorld.setBlock(eX, eY, eZ, GregTech_API.sBlockGranites, 8, 3);
												} else {
													mWorld.setBlock(eX, eY, eZ, Blocks.end_stone, 0, 0);
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
      
      
      
      
      Chunk tChunk = this.mWorld.getChunkFromBlockCoords(this.mX, this.mZ);
      if (tChunk != null) {
        tChunk.isModified = true;
      }
    }
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Worldgenerator
 * JD-Core Version:    0.7.0.1
 */