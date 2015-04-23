/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.world.GT_Worldgen_Ore;
/*  5:   */ import gregtech.common.blocks.GT_TileEntity_Ores;
/*  6:   */ import java.util.Collection;
/*  7:   */ import java.util.Random;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.init.Blocks;
/* 10:   */ import net.minecraft.tileentity.TileEntity;
/* 11:   */ import net.minecraft.util.MathHelper;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ import net.minecraft.world.chunk.IChunkProvider;
/* 14:   */ 
/* 15:   */ public class GT_Worldgen_Stone
/* 16:   */   extends GT_Worldgen_Ore
/* 17:   */ {
/* 18:   */   public GT_Worldgen_Stone(String aName, boolean aDefault, Block aBlock, int aBlockMeta, int aDimensionType, int aAmount, int aSize, int aProbability, int aMinY, int aMaxY, Collection<String> aBiomeList, boolean aAllowToGenerateinVoid)
/* 19:   */   {
/* 20:19 */     super(aName, aDefault, aBlock, aBlockMeta, aDimensionType, aAmount, aSize, aProbability, aMinY, aMaxY, aBiomeList, aAllowToGenerateinVoid);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider)
/* 24:   */   {
/* 25:24 */     if ((isGenerationAllowed(aWorld, aDimensionType, this.mDimensionType)) && ((this.mBiomeList.isEmpty()) || (this.mBiomeList.contains(aBiome))) && ((this.mProbability <= 1) || (aRandom.nextInt(this.mProbability) == 0)))
/* 26:   */     {
/* 27:25 */       for (int i = 0; i < this.mAmount; i++)
/* 28:   */       {
/* 29:26 */         int tX = aChunkX + aRandom.nextInt(16);int tY = this.mMinY + aRandom.nextInt(this.mMaxY - this.mMinY);int tZ = aChunkZ + aRandom.nextInt(16);
/* 30:27 */         if ((this.mAllowToGenerateinVoid) || (!aWorld.getBlock(tX, tY, tZ).isAir(aWorld, tX, tY, tZ)))
/* 31:   */         {
/* 32:28 */           float var6 = aRandom.nextFloat() * 3.141593F;
/* 33:29 */           double var7 = tX + 8 + MathHelper.sin(var6) * this.mSize / 8.0F;
/* 34:30 */           double var9 = tX + 8 - MathHelper.sin(var6) * this.mSize / 8.0F;
/* 35:31 */           double var11 = tZ + 8 + MathHelper.cos(var6) * this.mSize / 8.0F;
/* 36:32 */           double var13 = tZ + 8 - MathHelper.cos(var6) * this.mSize / 8.0F;
/* 37:33 */           double var15 = tY + aRandom.nextInt(3) - 2;
/* 38:34 */           double var17 = tY + aRandom.nextInt(3) - 2;
/* 39:36 */           for (int var19 = 0; var19 <= this.mSize; var19++)
/* 40:   */           {
/* 41:37 */             double var20 = var7 + (var9 - var7) * var19 / this.mSize;
/* 42:38 */             double var22 = var15 + (var17 - var15) * var19 / this.mSize;
/* 43:39 */             double var24 = var11 + (var13 - var11) * var19 / this.mSize;
/* 44:40 */             double var26 = aRandom.nextDouble() * this.mSize / 16.0D;
/* 45:41 */             double var28 = (MathHelper.sin(var19 * 3.141593F / this.mSize) + 1.0F) * var26 + 1.0D;
/* 46:42 */             double var30 = (MathHelper.sin(var19 * 3.141593F / this.mSize) + 1.0F) * var26 + 1.0D;
/* 47:43 */             int tMinX = MathHelper.floor_double(var20 - var28 / 2.0D);
/* 48:44 */             int tMinY = MathHelper.floor_double(var22 - var30 / 2.0D);
/* 49:45 */             int tMinZ = MathHelper.floor_double(var24 - var28 / 2.0D);
/* 50:46 */             int tMaxX = MathHelper.floor_double(var20 + var28 / 2.0D);
/* 51:47 */             int tMaxY = MathHelper.floor_double(var22 + var30 / 2.0D);
/* 52:48 */             int tMaxZ = MathHelper.floor_double(var24 + var28 / 2.0D);
/* 53:50 */             for (int eX = tMinX; eX <= tMaxX; eX++)
/* 54:   */             {
/* 55:51 */               double var39 = (eX + 0.5D - var20) / (var28 / 2.0D);
/* 56:52 */               if (var39 * var39 < 1.0D) {
/* 57:53 */                 for (int eY = tMinY; eY <= tMaxY; eY++)
/* 58:   */                 {
/* 59:54 */                   double var42 = (eY + 0.5D - var22) / (var30 / 2.0D);
/* 60:55 */                   if (var39 * var39 + var42 * var42 < 1.0D) {
/* 61:56 */                     for (int eZ = tMinZ; eZ <= tMaxZ; eZ++)
/* 62:   */                     {
/* 63:57 */                       double var45 = (eZ + 0.5D - var24) / (var28 / 2.0D);
/* 64:58 */                       if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D)
/* 65:   */                       {
/* 66:59 */                         Block tTargetedBlock = aWorld.getBlock(eX, eY, eZ);
/* 67:60 */                         if (tTargetedBlock == GregTech_API.sBlockOres1)
/* 68:   */                         {
/* 69:61 */                           TileEntity tTileEntity = aWorld.getTileEntity(eX, eY, eZ);
/* 70:62 */                           if ((tTileEntity instanceof GT_TileEntity_Ores)) {
/* 71:62 */                             ((GT_TileEntity_Ores)tTileEntity).overrideOreBlockMaterial(this.mBlock, (byte)this.mBlockMeta);
/* 72:   */                           }
/* 73:   */                         }
/* 74:64 */                         else if (((this.mAllowToGenerateinVoid) && (aWorld.getBlock(eX, eY, eZ).isAir(aWorld, eX, eY, eZ))) || ((tTargetedBlock != null) && ((tTargetedBlock.isReplaceableOreGen(aWorld, eX, eY, eZ, Blocks.stone)) || (tTargetedBlock.isReplaceableOreGen(aWorld, eX, eY, eZ, Blocks.end_stone)) || (tTargetedBlock.isReplaceableOreGen(aWorld, eX, eY, eZ, Blocks.netherrack)))))
/* 75:   */                         {
/* 76:65 */                           aWorld.setBlock(eX, eY, eZ, this.mBlock, this.mBlockMeta, 0);
/* 77:   */                         }
/* 78:   */                       }
/* 79:   */                     }
/* 80:   */                   }
/* 81:   */                 }
/* 82:   */               }
/* 83:   */             }
/* 84:   */           }
/* 85:   */         }
/* 86:   */       }
/* 87:77 */       return true;
/* 88:   */     }
/* 89:79 */     return false;
/* 90:   */   }
/* 91:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Worldgen_Stone
 * JD-Core Version:    0.7.0.1
 */