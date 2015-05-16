/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.util.GT_Config;
/*  6:   */ import gregtech.api.world.GT_Worldgen;
/*  7:   */ import gregtech.common.blocks.GT_TileEntity_Ores;
import gregtech.loaders.misc.GT_Achievements;

/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.Random;

/* 10:   */ import net.minecraft.world.World;
/* 11:   */ import net.minecraft.world.chunk.IChunkProvider;
/* 12:   */ 
/* 13:   */ public class GT_Worldgen_GT_Ore_Layer
/* 14:   */   extends GT_Worldgen
/* 15:   */ {
/* 16:15 */   public static ArrayList<GT_Worldgen_GT_Ore_Layer> sList = new ArrayList();
/* 17:16 */   public static int sWeight = 0;
/* 18:   */   public final short mMinY;
/* 19:   */   public final short mMaxY;
/* 20:   */   public final short mWeight;
/* 21:   */   public final short mDensity;
/* 22:   */   public final short mSize;
/* 23:   */   public final short mPrimaryMeta;
/* 24:   */   public final short mSecondaryMeta;
/* 25:   */   public final short mBetweenMeta;
/* 26:   */   public final short mSporadicMeta;
/* 27:   */   public final boolean mOverworld;
/* 28:   */   public final boolean mNether;
/* 29:   */   public final boolean mEnd;
/* 30:   */   
/* 31:   */   public GT_Worldgen_GT_Ore_Layer(String aName, boolean aDefault, int aMinY, int aMaxY, int aWeight, int aDensity, int aSize, boolean aOverworld, boolean aNether, boolean aEnd, Materials aPrimary, Materials aSecondary, Materials aBetween, Materials aSporadic)
/* 32:   */   {
/* 33:21 */     super(aName, sList, aDefault);
/* 34:22 */     this.mOverworld = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Overworld", aOverworld);
/* 35:23 */     this.mNether = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Nether", aNether);
/* 36:24 */     this.mEnd = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "TheEnd", aEnd);
/* 37:25 */     this.mMinY = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MinHeight", aMinY));
/* 38:26 */     this.mMaxY = ((short)Math.max(this.mMinY + 5, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MaxHeight", aMaxY)));
/* 39:27 */     this.mWeight = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "RandomWeight", aWeight));
/* 40:28 */     this.mDensity = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Density", aDensity));
/* 41:29 */     this.mSize = ((short)Math.max(1, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Size", aSize)));
/* 42:30 */     this.mPrimaryMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OrePrimaryLayer", aPrimary.mMetaItemSubID));
/* 43:31 */     this.mSecondaryMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSecondaryLayer", aSecondary.mMetaItemSubID));
/* 44:32 */     this.mBetweenMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSporadiclyInbetween", aBetween.mMetaItemSubID));
/* 45:33 */     this.mSporadicMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "OreSporaticlyAround", aSporadic.mMetaItemSubID));
/* 46:34 */     if (this.mEnabled) {
				GT_Achievements.registerOre(aPrimary,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
/* 46:34 */     GT_Achievements.registerOre(aSecondary,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
/* 46:34 */     GT_Achievements.registerOre(aBetween,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
/* 46:34 */     GT_Achievements.registerOre(aSporadic,aMinY,aMaxY,aWeight,aOverworld,aNether,aEnd);
/* 47:34 */       sWeight += this.mWeight;
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider)
/* 52:   */   {
/* 53:39 */     if (!isGenerationAllowed(aWorld, aDimensionType, ((aDimensionType == -1) && (this.mNether)) || ((aDimensionType == 0) && (this.mOverworld)) || ((aDimensionType == 1) && (this.mEnd)) ? aDimensionType : aDimensionType ^ 0xFFFFFFFF)) {
/* 54:39 */       return false;
/* 55:   */     }
/* 56:41 */     int tMinY = this.mMinY + aRandom.nextInt(this.mMaxY - this.mMinY - 5);
/* 57:   */     
/* 58:43 */     int cX = aChunkX - aRandom.nextInt(this.mSize);int eX = aChunkX + 16 + aRandom.nextInt(this.mSize);
/* 59:43 */     for (int tX = cX; tX <= eX; tX++)
/* 60:   */     {
/* 61:44 */       int cZ = aChunkZ - aRandom.nextInt(this.mSize);int eZ = aChunkZ + 16 + aRandom.nextInt(this.mSize);
/* 62:44 */       for (int tZ = cZ; tZ <= eZ; tZ++)
/* 63:   */       {
/* 64:45 */         if (this.mSecondaryMeta > 0) {
/* 65:45 */           for (int i = tMinY - 1; i < tMinY + 2; i++) {
/* 66:46 */             if ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0)) {
/* 67:47 */               GT_TileEntity_Ores.setOreBlock(aWorld, tX, i, tZ, this.mSecondaryMeta);
/* 68:   */             }
/* 69:   */           }
/* 70:   */         }
/* 71:50 */         if ((this.mBetweenMeta > 0) && ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0))) {
/* 72:51 */           GT_TileEntity_Ores.setOreBlock(aWorld, tX, tMinY + 2 + aRandom.nextInt(2), tZ, this.mBetweenMeta);
/* 73:   */         }
/* 74:53 */         if (this.mPrimaryMeta > 0) {
/* 75:53 */           for (int i = tMinY + 3; i < tMinY + 6; i++) {
/* 76:54 */             if ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0)) {
/* 77:55 */               GT_TileEntity_Ores.setOreBlock(aWorld, tX, i, tZ, this.mPrimaryMeta);
/* 78:   */             }
/* 79:   */           }
/* 80:   */         }
/* 81:58 */         if ((this.mSporadicMeta > 0) && ((aRandom.nextInt(Math.max(1, Math.max(Math.abs(cZ - tZ), Math.abs(eZ - tZ)) / this.mDensity)) == 0) || (aRandom.nextInt(Math.max(1, Math.max(Math.abs(cX - tX), Math.abs(eX - tX)) / this.mDensity)) == 0))) {
/* 82:59 */           GT_TileEntity_Ores.setOreBlock(aWorld, tX, tMinY - 1 + aRandom.nextInt(7), tZ, this.mSporadicMeta);
/* 83:   */         }
/* 84:   */       }
/* 85:   */     }
/* 86:63 */     return true;
/* 87:   */   }
/* 88:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Worldgen_GT_Ore_Layer
 * JD-Core Version:    0.7.0.1
 */