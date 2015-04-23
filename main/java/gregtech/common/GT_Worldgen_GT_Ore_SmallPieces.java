/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.util.GT_Config;
/*  6:   */ import gregtech.api.world.GT_Worldgen;
/*  7:   */ import gregtech.common.blocks.GT_TileEntity_Ores;
/*  8:   */ import java.util.Random;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ import net.minecraft.world.chunk.IChunkProvider;
/* 11:   */ 
/* 12:   */ public class GT_Worldgen_GT_Ore_SmallPieces
/* 13:   */   extends GT_Worldgen
/* 14:   */ {
/* 15:   */   public final short mMinY;
/* 16:   */   public final short mMaxY;
/* 17:   */   public final short mAmount;
/* 18:   */   public final short mMeta;
/* 19:   */   public final boolean mOverworld;
/* 20:   */   public final boolean mNether;
/* 21:   */   public final boolean mEnd;
/* 22:   */   
/* 23:   */   public GT_Worldgen_GT_Ore_SmallPieces(String aName, boolean aDefault, int aMinY, int aMaxY, int aAmount, boolean aOverworld, boolean aNether, boolean aEnd, Materials aPrimary)
/* 24:   */   {
/* 25:18 */     super(aName, GregTech_API.sWorldgenList, aDefault);
/* 26:19 */     this.mOverworld = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Overworld", aOverworld);
/* 27:20 */     this.mNether = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Nether", aNether);
/* 28:21 */     this.mEnd = GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "TheEnd", aEnd);
/* 29:22 */     this.mMinY = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MinHeight", aMinY));
/* 30:23 */     this.mMaxY = ((short)Math.max(this.mMinY + 1, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "MaxHeight", aMaxY)));
/* 31:24 */     this.mAmount = ((short)Math.max(1, GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Amount", aAmount)));
/* 32:25 */     this.mMeta = ((short)GregTech_API.sWorldgenFile.get("worldgen." + this.mWorldGenName, "Ore", aPrimary.mMetaItemSubID));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider)
/* 36:   */   {
/* 37:30 */     if (!isGenerationAllowed(aWorld, aDimensionType, ((aDimensionType == -1) && (this.mNether)) || ((aDimensionType == 0) && (this.mOverworld)) || ((aDimensionType == 1) && (this.mEnd)) ? aDimensionType : aDimensionType ^ 0xFFFFFFFF)) {
/* 38:30 */       return false;
/* 39:   */     }
/* 40:31 */     if (this.mMeta > 0)
/* 41:   */     {
/* 42:31 */       int i = 0;
/* 43:31 */       for (int j = Math.max(1, this.mAmount / 2 + aRandom.nextInt(this.mAmount) / 2); i < j; i++) {
/* 44:31 */         GT_TileEntity_Ores.setOreBlock(aWorld, aChunkX + aRandom.nextInt(16), this.mMinY + aRandom.nextInt(Math.max(1, this.mMaxY - this.mMinY)), aChunkZ + aRandom.nextInt(16), this.mMeta + 16000);
/* 45:   */       }
/* 46:   */     }
/* 47:32 */     return true;
/* 48:   */   }
/* 49:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Worldgen_GT_Ore_SmallPieces
 * JD-Core Version:    0.7.0.1
 */