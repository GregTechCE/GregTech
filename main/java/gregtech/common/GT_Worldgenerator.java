/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.IWorldGenerator;
/*  4:   */ import cpw.mods.fml.common.registry.GameRegistry;
/*  5:   */ import gregtech.api.GregTech_API;
/*  6:   */ import gregtech.api.util.GT_Log;
/*  7:   */ import gregtech.api.world.GT_Worldgen;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Random;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ import net.minecraft.world.biome.BiomeGenBase;
/* 13:   */ import net.minecraft.world.chunk.Chunk;
/* 14:   */ import net.minecraft.world.chunk.IChunkProvider;
/* 15:   */ import net.minecraft.world.gen.ChunkProviderEnd;
/* 16:   */ import net.minecraft.world.gen.ChunkProviderHell;
/* 17:   */ 
/* 18:   */ public class GT_Worldgenerator
/* 19:   */   implements IWorldGenerator
/* 20:   */ {
/* 21:21 */   public static boolean sAsteroids = true;
/* 22:23 */   public List<Runnable> mList = new ArrayList();
/* 23:24 */   public boolean mIsGenerating = false;
/* 24:   */   
/* 25:   */   public GT_Worldgenerator()
/* 26:   */   {
/* 27:27 */     GameRegistry.registerWorldGenerator(this, 1073741823);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void generate(Random aRandom, int aX, int aZ, World aWorld, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider)
/* 31:   */   {
/* 32:32 */     this.mList.add(new WorldGenContainer(new Random(aRandom.nextInt()), aX * 16, aZ * 16, ((aChunkGenerator instanceof ChunkProviderEnd)) || (aWorld.getBiomeGenForCoords(aX * 16 + 8, aZ * 16 + 8) == BiomeGenBase.sky) ? 1 : ((aChunkGenerator instanceof ChunkProviderHell)) || (aWorld.getBiomeGenForCoords(aX * 16 + 8, aZ * 16 + 8) == BiomeGenBase.hell) ? -1 : 0, aWorld, aChunkGenerator, aChunkProvider, aWorld.getBiomeGenForCoords(aX * 16 + 8, aZ * 16 + 8).biomeName));
/* 33:33 */     if (!this.mIsGenerating)
/* 34:   */     {
/* 35:34 */       this.mIsGenerating = true;
/* 36:35 */       for (int i = 0; i < this.mList.size(); i++) {
/* 37:35 */         ((Runnable)this.mList.get(i)).run();
/* 38:   */       }
/* 39:36 */       this.mList.clear();
/* 40:37 */       this.mIsGenerating = false;
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static class WorldGenContainer
/* 45:   */     implements Runnable
/* 46:   */   {
/* 47:   */     public final Random mRandom;
/* 48:   */     public final int mX;
/* 49:   */     public final int mZ;
/* 50:   */     public final int mDimensionType;
/* 51:   */     public final World mWorld;
/* 52:   */     public final IChunkProvider mChunkGenerator;
/* 53:   */     public final IChunkProvider mChunkProvider;
/* 54:   */     public final String mBiome;
/* 55:   */     
/* 56:   */     public WorldGenContainer(Random aRandom, int aX, int aZ, int aDimensionType, World aWorld, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider, String aBiome)
/* 57:   */     {
/* 58:49 */       this.mRandom = aRandom;
/* 59:50 */       this.mX = aX;
/* 60:51 */       this.mZ = aZ;
/* 61:52 */       this.mDimensionType = aDimensionType;
/* 62:53 */       this.mWorld = aWorld;
/* 63:54 */       this.mChunkGenerator = aChunkGenerator;
/* 64:55 */       this.mChunkProvider = aChunkProvider;
/* 65:56 */       this.mBiome = aBiome;
/* 66:   */     }
/* 67:   */     
/* 68:   */     public void run()
/* 69:   */     {
/* 70:61 */       if ((Math.abs(this.mX / 16) % 3 == 1) && (Math.abs(this.mZ / 16) % 3 == 1))
/* 71:   */       {
/* 72:62 */         if ((GT_Worldgen_GT_Ore_Layer.sWeight > 0) && (GT_Worldgen_GT_Ore_Layer.sList.size() > 0))
/* 73:   */         {
/* 74:63 */           boolean temp = true;
/* 75:   */           int tRandomWeight;
/* 76:64 */           for (int i = 0; (i < 256) && (temp); i++)
/* 77:   */           {
/* 78:65 */             tRandomWeight = this.mRandom.nextInt(GT_Worldgen_GT_Ore_Layer.sWeight);
/* 79:66 */             for (GT_Worldgen tWorldGen : GT_Worldgen_GT_Ore_Layer.sList)
/* 80:   */             {
/* 81:67 */               tRandomWeight -= ((GT_Worldgen_GT_Ore_Layer)tWorldGen).mWeight;
/* 82:68 */               if (tRandomWeight <= 0) {
/* 83:   */                 try
/* 84:   */                 {
/* 85:70 */                   if (tWorldGen.executeWorldgen(this.mWorld, this.mRandom, this.mBiome, this.mDimensionType, this.mX, this.mZ, this.mChunkGenerator, this.mChunkProvider))
/* 86:   */                   {
/* 87:71 */                     temp = false;
/* 88:72 */                     break;
/* 89:   */                   }
/* 90:   */                 }
/* 91:   */                 catch (Throwable e)
/* 92:   */                 {
/* 93:75 */                   e.printStackTrace(GT_Log.err);
/* 94:   */                 }
/* 95:   */               }
/* 96:   */             }
/* 97:   */           }
/* 98:   */         }
/* 99:82 */         int i = 0;
/* :0:82 */         for (int tX = this.mX - 16; i < 3; tX += 16)
/* :1:   */         {
/* :2:82 */           int j = 0;
/* :3:82 */           for (int tZ = this.mZ - 16; j < 3; tZ += 16)
/* :4:   */           {
/* :5:83 */             String tBiome = this.mWorld.getBiomeGenForCoords(tX + 8, tZ + 8).biomeName;
/* :6:84 */             if (tBiome == null) {
/* :7:84 */               tBiome = BiomeGenBase.plains.biomeName;
/* :8:   */             }
/* :9:85 */             for (GT_Worldgen tWorldGen : GregTech_API.sWorldgenList) {
/* ;0:   */               try
/* ;1:   */               {
/* ;2:87 */                 tWorldGen.executeWorldgen(this.mWorld, this.mRandom, this.mBiome, this.mDimensionType, tX, tZ, this.mChunkGenerator, this.mChunkProvider);
/* ;3:   */               }
/* ;4:   */               catch (Throwable e)
/* ;5:   */               {
/* ;6:89 */                 e.printStackTrace(GT_Log.err);
/* ;7:   */               }
/* ;8:   */             }
/* ;9:82 */             j++;
/* <0:   */           }
/* <1:82 */           i++;
/* <2:   */         }
/* <3:   */       }
/* <4:95 */       Chunk tChunk = this.mWorld.getChunkFromBlockCoords(this.mX, this.mZ);
/* <5:96 */       if (tChunk != null) {
/* <6:96 */         tChunk.isModified = true;
/* <7:   */       }
/* <8:   */     }
/* <9:   */   }
/* =0:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Worldgenerator
 * JD-Core Version:    0.7.0.1
 */