/*  1:   */ package gregtech.loaders.postload;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
/*  4:   */ import gregtech.api.util.GT_Utility;
/*  5:   */ import gregtech.common.GT_Proxy;
/*  6:   */ import java.util.Set;
/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.block.material.Material;
/*  9:   */ import net.minecraft.init.Blocks;
/* 10:   */ import net.minecraft.item.ItemAxe;
/* 11:   */ import net.minecraft.item.ItemPickaxe;
/* 12:   */ 
/* 13:   */ public class GT_BlockResistanceLoader
/* 14:   */   implements Runnable
/* 15:   */ {
/* 16:   */   public void run()
/* 17:   */   {
/* 18:16 */     if (GT_Mod.gregtechproxy.mHardRock)
/* 19:   */     {
/* 20:17 */       Blocks.stone.setHardness(16.0F);
/* 21:18 */       Blocks.brick_block.setHardness(32.0F);
/* 22:19 */       Blocks.hardened_clay.setHardness(32.0F);
/* 23:20 */       Blocks.stained_hardened_clay.setHardness(32.0F);
/* 24:21 */       Blocks.cobblestone.setHardness(12.0F);
/* 25:22 */       Blocks.stonebrick.setHardness(24.0F);
/* 26:   */     }
/* 27:25 */     Blocks.stone.setResistance(10.0F);
/* 28:26 */     Blocks.cobblestone.setResistance(10.0F);
/* 29:27 */     Blocks.stonebrick.setResistance(10.0F);
/* 30:28 */     Blocks.brick_block.setResistance(20.0F);
/* 31:29 */     Blocks.hardened_clay.setResistance(15.0F);
/* 32:30 */     Blocks.stained_hardened_clay.setResistance(15.0F);
/* 33:   */     
/* 34:   */ 
/* 35:   */ 
/* 36:34 */     Blocks.bed.setHarvestLevel("axe", 0);
/* 37:35 */     Blocks.hay_block.setHarvestLevel("axe", 0);
/* 38:36 */     Blocks.tnt.setHarvestLevel("pickaxe", 0);
/* 39:37 */     Blocks.sponge.setHarvestLevel("axe", 0);
/* 40:38 */     Blocks.monster_egg.setHarvestLevel("pickaxe", 0);
/* 41:   */     
/* 42:40 */     GT_Utility.callMethod(Material.tnt, "func_85158_p", true, false, false, new Object[0]);
/* 43:41 */     GT_Utility.callMethod(Material.tnt, "setAdventureModeExempt", true, false, false, new Object[0]);
/* 44:   */     
/* 45:43 */     Set tSet = (Set)GT_Utility.getFieldContent(ItemAxe.class, "field_150917_c", true, true);
/* 46:44 */     tSet.add(Blocks.bed);
/* 47:45 */     tSet.add(Blocks.hay_block);
/* 48:46 */     tSet.add(Blocks.sponge);
/* 49:   */     
/* 50:48 */     tSet = (Set)GT_Utility.getFieldContent(ItemPickaxe.class, "field_150915_c", true, true);
/* 51:49 */     tSet.add(Blocks.monster_egg);
/* 52:50 */     tSet.add(Blocks.tnt);
/* 53:   */   }
/* 54:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_BlockResistanceLoader
 * JD-Core Version:    0.7.0.1
 */