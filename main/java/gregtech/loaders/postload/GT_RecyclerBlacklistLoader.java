/*  1:   */ package gregtech.loaders.postload;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ConfigCategories.Recipes;
/*  5:   */ import gregtech.api.enums.ItemList;
/*  6:   */ import gregtech.api.util.GT_Config;
/*  7:   */ import gregtech.api.util.GT_Log;
/*  8:   */ import gregtech.api.util.GT_ModHandler;

/*  9:   */ import java.io.PrintStream;

/* 10:   */ import net.minecraft.init.Blocks;
/* 11:   */ import net.minecraft.init.Items;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class GT_RecyclerBlacklistLoader
/* 15:   */   implements Runnable
/* 16:   */ {
/* 17:   */   public void run()
/* 18:   */   {
/* 19:16 */     GT_Log.out.println("GT_Mod: Adding Stuff to the Recycler Blacklist.");
/* 20:18 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "easymobgrinderrecycling", true))
/* 21:   */     {
/* 22:20 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.arrow, 1, 0));
/* 23:21 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.bone, 1, 0));
/* 24:22 */       GT_ModHandler.addToRecyclerBlackList(ItemList.Dye_Bonemeal.get(1L, new Object[0]));
/* 25:   */       
/* 26:   */ 
/* 27:25 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.rotten_flesh, 1, 0));
/* 28:   */       
/* 29:   */ 
/* 30:28 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.string, 1, 0));
/* 31:   */       
/* 32:   */ 
/* 33:31 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.egg, 1, 0));
/* 34:   */     }
/* 35:33 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "easystonerecycling", true))
/* 36:   */     {
/* 37:34 */       ItemStack tStack = new ItemStack(Blocks.cobblestone, 1, 0);
/* 38:35 */       while (tStack != null)
/* 39:   */       {
/* 40:36 */         GT_ModHandler.addToRecyclerBlackList(tStack);
/* 41:37 */         tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] { tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack });
/* 42:   */       }
/* 43:39 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.cobblestone_wall, 1, 32767));
/* 44:40 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.sandstone_stairs, 1, 32767));
/* 45:41 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.stone_stairs, 1, 32767));
/* 46:42 */       GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.stone_brick_stairs, 1, 32767));
/* 47:43 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getSmeltingOutput(new ItemStack(Blocks.stone, 1, 0), false, null));
/* 48:44 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.glass, 1, 0), null, null, new ItemStack(Blocks.glass, 1, 0) }));
/* 49:45 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.stone, 1, 0), null, null, new ItemStack(Blocks.stone, 1, 0) }));
/* 50:46 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0), null, null, new ItemStack(Blocks.cobblestone, 1, 0) }));
/* 51:47 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.stone, 1, 0), null, new ItemStack(Blocks.stone, 1, 0), null, new ItemStack(Blocks.stone, 1, 0) }));
/* 52:48 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.stone, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.stone, 1, 0) }));
/* 53:49 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0) }));
/* 54:50 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.sandstone, 1, 0) }));
/* 55:51 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.sand, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.sand, 1, 0) }));
/* 56:52 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0) }));
/* 57:53 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.glass, 1, 0) }));
/* 58:54 */       GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.glass, 1, 0) }));
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_RecyclerBlacklistLoader
 * JD-Core Version:    0.7.0.1
 */