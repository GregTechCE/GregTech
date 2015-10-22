package gregtech.common;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_Config;
import gregtech.api.world.GT_Worldgen;
import gregtech.common.blocks.GT_TileEntity_Ores;
import gregtech.loaders.misc.GT_Achievements;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class GT_Worldgen_GT_Ore_Layer
  extends GT_Worldgen
{
  public static ArrayList<GT_Worldgen_GT_Ore_Layer> sList = new ArrayList();
  public static int sWeight = 0;
  public final short mMinY;
  public final short mMaxY;
  public final short mWeight;
  public final short mDensity;
  public final short mSize;
  public final short mPrimaryMeta;
  public final short mSecondaryMeta;
  public final short mBetweenMeta;
  public final short mSporadicMeta;
  public final boolean mOverworld;
  public final boolean mNether;
  public final boolean mEnd;
  
  public GT_Worldgen_GT_Ore_Layer(String aName, boolean aDefault, int aMinY, int aMaxY, int aWeight, int aDensity, int aSize, boolean aOverworld, boolean aNether, boolean aEnd, Materials aPrimary, Materials aSecondary, Materials aBetween, Materials aSporadic)
  {
    super(aName, sList, aDefault);
    this.mOverworld = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Overworld", aOverworld);
    this.mNether = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Nether", aNether);
    this.mEnd = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "TheEnd", aEnd);
    this.mMinY = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MinHeight", aMinY));
    this.mMaxY = ((short)Math.max(this.mMinY + 5, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MaxHeight", aMaxY)));
    this.mWeight = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "RandomWeight", aWeight));
    this.mDensity = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Density", aDensity));
    this.mSize = ((short)Math.max(1, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Size", aSize)));
    this.mPrimaryMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OrePrimaryLayer", aPrimary.mMetaItemSubID));
    this.mSecondaryMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSecondaryLayer", aSecondary.mMetaItemSubID));
    this.mBetweenMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSporadiclyInbetween", aBetween.mMetaItemSubID));
    this.mSporadicMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSporaticlyAround", aSporadic.mMetaItemSubID));
    if (this.mEnabled) {
				GT_Achievements.registerOre(aPrimary,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
    GT_Achievements.registerOre(aSecondary,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
    GT_Achievements.registerOre(aBetween,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
    GT_Achievements.registerOre(aSporadic,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
      sWeight += this.mWeight;
    }
  }
  
  public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider)
  {
    if (!isGenerationAllowed(aWorld, aDimensionType, ((aDimensionType == -1) && (this.mNether)) || ((aDimensionType == 0) && (this.mOverworld)) || ((aDimensionType == 1) && (this.mEnd)) ? aDimensionType : aDimensionType ^ 0xFFFFFFFF)) {
      return false;
    }
    int tMinY = this.mMinY + aRandom.nextInt(this.mMaxY - this.mMinY - 5);
    
    int cX = aChunkX - aRandom.nextInt(this.mSize);int eX = aChunkX + 16 + aRandom.nextInt(this.mSize);
    for (int tX = cX; tX <= eX; tX++)
    {
      int cZ = aChunkZ - aRandom.nextInt(this.mSize);int eZ = aChunkZ + 16 + aRandom.nextInt(this.mSize);
      for (int tZ = cZ; tZ <= eZ; tZ++)
      {
        if (this.mSecondaryMeta > 0) {
          for (int i = tMinY - 1; i < tMinY + 2; i++) {
            if ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0)) {
              GT_TileEntity_Ores.setOreBlock(aWorld, tX, i, tZ, this.mSecondaryMeta);
            }
          }
        }
        if ((this.mBetweenMeta > 0) && ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0))) {
          GT_TileEntity_Ores.setOreBlock(aWorld, tX, tMinY + 2 + aRandom.nextInt(2), tZ, this.mBetweenMeta);
        }
        if (this.mPrimaryMeta > 0) {
          for (int i = tMinY + 3; i < tMinY + 6; i++) {
            if ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0)) {
              GT_TileEntity_Ores.setOreBlock(aWorld, tX, i, tZ, this.mPrimaryMeta);
            }
          }
        }
        if ((this.mSporadicMeta > 0) && ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0))) {
          GT_TileEntity_Ores.setOreBlock(aWorld, tX, tMinY - 1 + aRandom.nextInt(7), tZ, this.mSporadicMeta);
        }
      }
    }
    if(GT_Values.D1){
    System.out.println("Generated Orevein: "+this.mWorldGenName);}
    return true;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Worldgen_GT_Ore_Layer
 * JD-Core Version:    0.7.0.1
 */