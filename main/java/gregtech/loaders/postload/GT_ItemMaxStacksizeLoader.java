/*  1:   */ package gregtech.loaders.postload;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.util.GT_Log;
/*  7:   */ import gregtech.common.GT_Proxy;
/*  8:   */ import java.io.PrintStream;
/*  9:   */ import net.minecraft.init.Blocks;
/* 10:   */ import net.minecraft.init.Items;
/* 11:   */ import net.minecraft.item.Item;
/* 12:   */ 
/* 13:   */ public class GT_ItemMaxStacksizeLoader
/* 14:   */   implements Runnable
/* 15:   */ {
/* 16:   */   public void run()
/* 17:   */   {
/* 18:14 */     GT_Log.out.println("GT_Mod: Changing maximum Stacksizes if configured.");
/* 19:   */     
/* 20:16 */     ItemList.Upgrade_Overclocker.getItem().setMaxStackSize(GT_Mod.gregtechproxy.mUpgradeCount);
/* 21:17 */     Items.cake.setMaxStackSize(64);
/* 22:18 */     Items.wooden_door.setMaxStackSize(8);
/* 23:19 */     Items.iron_door.setMaxStackSize(8);
/* 24:21 */     if (OrePrefixes.plank.mDefaultStackSize < 64)
/* 25:   */     {
/* 26:22 */       Item.getItemFromBlock(Blocks.wooden_slab).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 27:23 */       Item.getItemFromBlock(Blocks.double_wooden_slab).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 28:24 */       Item.getItemFromBlock(Blocks.oak_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 29:25 */       Item.getItemFromBlock(Blocks.jungle_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 30:26 */       Item.getItemFromBlock(Blocks.birch_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 31:27 */       Item.getItemFromBlock(Blocks.spruce_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 32:28 */       Item.getItemFromBlock(Blocks.acacia_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 33:29 */       Item.getItemFromBlock(Blocks.dark_oak_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
/* 34:   */     }
/* 35:32 */     if (OrePrefixes.block.mDefaultStackSize < 64)
/* 36:   */     {
/* 37:33 */       Item.getItemFromBlock(Blocks.stone_slab).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 38:34 */       Item.getItemFromBlock(Blocks.double_stone_slab).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 39:35 */       Item.getItemFromBlock(Blocks.brick_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 40:36 */       Item.getItemFromBlock(Blocks.nether_brick_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 41:37 */       Item.getItemFromBlock(Blocks.sandstone_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 42:38 */       Item.getItemFromBlock(Blocks.stone_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 43:39 */       Item.getItemFromBlock(Blocks.stone_brick_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 44:40 */       Item.getItemFromBlock(Blocks.packed_ice).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 45:41 */       Item.getItemFromBlock(Blocks.ice).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 46:42 */       Item.getItemFromBlock(Blocks.soul_sand).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 47:43 */       Item.getItemFromBlock(Blocks.glowstone).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 48:44 */       Item.getItemFromBlock(Blocks.snow).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 49:45 */       Item.getItemFromBlock(Blocks.snow).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 50:46 */       Item.getItemFromBlock(Blocks.iron_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 51:47 */       Item.getItemFromBlock(Blocks.gold_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 52:48 */       Item.getItemFromBlock(Blocks.emerald_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 53:49 */       Item.getItemFromBlock(Blocks.lapis_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 54:50 */       Item.getItemFromBlock(Blocks.diamond_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 55:51 */       Item.getItemFromBlock(Blocks.clay).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 56:52 */       Item.getItemFromBlock(Blocks.redstone_lamp).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 57:53 */       Item.getItemFromBlock(Blocks.dirt).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 58:54 */       Item.getItemFromBlock(Blocks.grass).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 59:55 */       Item.getItemFromBlock(Blocks.mycelium).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 60:56 */       Item.getItemFromBlock(Blocks.gravel).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 61:57 */       Item.getItemFromBlock(Blocks.sand).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 62:58 */       Item.getItemFromBlock(Blocks.brick_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 63:59 */       Item.getItemFromBlock(Blocks.wool).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 64:60 */       Item.getItemFromBlock(Blocks.melon_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 65:61 */       Item.getItemFromBlock(Blocks.pumpkin).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 66:62 */       Item.getItemFromBlock(Blocks.lit_pumpkin).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 67:63 */       Item.getItemFromBlock(Blocks.dispenser).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 68:64 */       Item.getItemFromBlock(Blocks.obsidian).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 69:65 */       Item.getItemFromBlock(Blocks.piston).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 70:66 */       Item.getItemFromBlock(Blocks.sticky_piston).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 71:67 */       Item.getItemFromBlock(Blocks.crafting_table).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 72:68 */       Item.getItemFromBlock(Blocks.glass).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 73:69 */       Item.getItemFromBlock(Blocks.jukebox).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 74:70 */       Item.getItemFromBlock(Blocks.anvil).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 75:71 */       Item.getItemFromBlock(Blocks.chest).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 76:72 */       Item.getItemFromBlock(Blocks.trapped_chest).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 77:73 */       Item.getItemFromBlock(Blocks.noteblock).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 78:74 */       Item.getItemFromBlock(Blocks.mob_spawner).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 79:75 */       Item.getItemFromBlock(Blocks.bookshelf).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 80:76 */       Item.getItemFromBlock(Blocks.furnace).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 81:77 */       Item.getItemFromBlock(Blocks.lit_furnace).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
/* 82:   */     }
/* 83:   */   }
/* 84:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_ItemMaxStacksizeLoader
 * JD-Core Version:    0.7.0.1
 */