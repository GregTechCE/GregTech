/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  9:   */ 
/* 10:   */ public class GT_MinableOreGenerator
/* 11:   */   extends WorldGenerator
/* 12:   */ {
/* 13:   */   private Block minableBlockId;
/* 14:   */   private Block mBlock;
/* 15:17 */   private int minableBlockMeta = 0;
/* 16:   */   private int numberOfBlocks;
/* 17:21 */   private boolean allowVoid = false;
/* 18:   */   
/* 19:   */   public GT_MinableOreGenerator(Block par1, int par2)
/* 20:   */   {
/* 21:24 */     this.minableBlockId = par1;
/* 22:25 */     this.numberOfBlocks = par2;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public GT_MinableOreGenerator(Block id, int meta, int number, boolean aAllowVoid, Block aBlock)
/* 26:   */   {
/* 27:29 */     this(id, number);
/* 28:30 */     this.minableBlockMeta = meta;
/* 29:31 */     this.allowVoid = aAllowVoid;
/* 30:32 */     this.mBlock = aBlock;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 34:   */   {
/* 35:37 */     float var6 = par2Random.nextFloat() * 3.141593F;
/* 36:38 */     double var7 = par3 + 8 + MathHelper.sin(var6) * this.numberOfBlocks / 8.0F;
/* 37:39 */     double var9 = par3 + 8 - MathHelper.sin(var6) * this.numberOfBlocks / 8.0F;
/* 38:40 */     double var11 = par5 + 8 + MathHelper.cos(var6) * this.numberOfBlocks / 8.0F;
/* 39:41 */     double var13 = par5 + 8 - MathHelper.cos(var6) * this.numberOfBlocks / 8.0F;
/* 40:42 */     double var15 = par4 + par2Random.nextInt(3) - 2;
/* 41:43 */     double var17 = par4 + par2Random.nextInt(3) - 2;
/* 42:45 */     for (int var19 = 0; var19 <= this.numberOfBlocks; var19++)
/* 43:   */     {
/* 44:47 */       double var20 = var7 + (var9 - var7) * var19 / this.numberOfBlocks;
/* 45:48 */       double var22 = var15 + (var17 - var15) * var19 / this.numberOfBlocks;
/* 46:49 */       double var24 = var11 + (var13 - var11) * var19 / this.numberOfBlocks;
/* 47:50 */       double var26 = par2Random.nextDouble() * this.numberOfBlocks / 16.0D;
/* 48:51 */       double var28 = (MathHelper.sin(var19 * 3.141593F / this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
/* 49:52 */       double var30 = (MathHelper.sin(var19 * 3.141593F / this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
/* 50:53 */       int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
/* 51:54 */       int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
/* 52:55 */       int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
/* 53:56 */       int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
/* 54:57 */       int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
/* 55:58 */       int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);
/* 56:60 */       for (int var38 = var32; var38 <= var35; var38++)
/* 57:   */       {
/* 58:61 */         double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);
/* 59:62 */         if (var39 * var39 < 1.0D) {
/* 60:63 */           for (int var41 = var33; var41 <= var36; var41++)
/* 61:   */           {
/* 62:64 */             double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);
/* 63:65 */             if (var39 * var39 + var42 * var42 < 1.0D) {
/* 64:66 */               for (int var44 = var34; var44 <= var37; var44++)
/* 65:   */               {
/* 66:67 */                 double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);
/* 67:68 */                 Block block = par1World.getBlock(var38, var41, var44);
/* 68:69 */                 if ((var39 * var39 + var42 * var42 + var45 * var45 < 1.0D) && (((this.allowVoid) && (par1World.getBlock(var38, var41, var44) == Blocks.air)) || ((block != null) && (block.isReplaceableOreGen(par1World, var38, var41, var44, this.mBlock))))) {
/* 69:70 */                   par1World.setBlock(var38, var41, var44, this.minableBlockId, this.minableBlockMeta, 0);
/* 70:   */                 }
/* 71:   */               }
/* 72:   */             }
/* 73:   */           }
/* 74:   */         }
/* 75:   */       }
/* 76:   */     }
/* 77:79 */     return true;
/* 78:   */   }
/* 79:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_MinableOreGenerator
 * JD-Core Version:    0.7.0.1
 */