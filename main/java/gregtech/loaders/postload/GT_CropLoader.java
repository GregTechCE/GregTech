/*  1:   */ package gregtech.loaders.postload;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.util.GT_BaseCrop;
/*  7:   */ import gregtech.api.util.GT_Log;
/*  8:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ import net.minecraft.init.Blocks;
/* 11:   */ import net.minecraft.init.Items;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class GT_CropLoader
/* 15:   */   implements Runnable
/* 16:   */ {
/* 17:   */   public void run()
/* 18:   */   {
/* 19:16 */     GT_Log.out.println("GT_Mod: Register Crops to IC2.");
/* 20:   */     try
/* 21:   */     {
/* 22:18 */       new GT_BaseCrop(124, "Indigo", "Eloraam", ItemList.Crop_Drop_Indigo.get(1L, new Object[0]), null, ItemList.Crop_Drop_Indigo.get(4L, new Object[0]), 2, 4, 0, 1, 4, 1, 1, 0, 4, 0, new String[] { "Flower", "Color", "Ingredient" });
/* 23:19 */       new GT_BaseCrop(125, "Flax", "Eloraam", new ItemStack(Items.string, 1), null, null, 2, 4, 0, 1, 4, 1, 1, 2, 0, 1, new String[] { "Silk", "Vine", "Addictive" });
/* 24:20 */       new GT_BaseCrop(126, "Oilberries", "Spacetoad", ItemList.Crop_Drop_OilBerry.get(1L, new Object[0]), null, null, 9, 4, 0, 1, 4, 6, 1, 2, 1, 12, new String[] { "Fire", "Dark", "Reed", "Rotten", "Coal", "Oil" });
/* 25:21 */       new GT_BaseCrop(127, "Bobsyeruncleranks", "GenerikB", ItemList.Crop_Drop_BobsYerUncleRanks.get(1L, new Object[0]), new ItemStack[] { new ItemStack(Items.emerald, 1) }, null, 11, 4, 0, 1, 4, 4, 0, 8, 2, 9, new String[] { "Shiny", "Vine", "Emerald", "Berylium", "Crystal" });
/* 26:22 */       new GT_BaseCrop(128, "Diareed", "Direwolf20", GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Diamond, 1L), new ItemStack[] { new ItemStack(Items.diamond, 1) }, null, 12, 4, 0, 1, 4, 5, 0, 10, 2, 10, new String[] { "Fire", "Shiny", "Reed", "Coal", "Diamond", "Crystal" });
/* 27:23 */       new GT_BaseCrop(129, "Withereed", "CovertJaguar", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L), new ItemStack[] { new ItemStack(Items.coal, 1), new ItemStack(Items.coal, 1) }, null, 8, 4, 0, 1, 4, 2, 0, 4, 1, 3, new String[] { "Fire", "Undead", "Reed", "Coal", "Rotten", "Wither" });
/* 28:24 */       new GT_BaseCrop(130, "Blazereed", "Mr. Brain", new ItemStack(Items.blaze_powder, 1), new ItemStack[] { new ItemStack(Items.blaze_rod, 1) }, null, 6, 4, 0, 1, 4, 0, 4, 1, 0, 0, new String[] { "Fire", "Blaze", "Reed", "Sulfur" });
/* 29:25 */       new GT_BaseCrop(131, "Eggplant", "Link", new ItemStack(Items.egg, 1), new ItemStack[] { new ItemStack(Items.chicken, 1), new ItemStack(Items.feather, 1), new ItemStack(Items.feather, 1), new ItemStack(Items.feather, 1) }, null, 6, 3, 900, 2, 3, 0, 4, 1, 0, 0, new String[] { "Chicken", "Egg", "Edible", "Feather", "Flower", "Addictive" });
/* 30:26 */       new GT_BaseCrop(132, "Corium", "Gregorius Techneticies", new ItemStack(Items.leather, 1), null, null, 6, 4, 0, 1, 4, 0, 2, 3, 1, 0, new String[] { "Cow", "Silk", "Vine" });
/* 31:27 */       new GT_BaseCrop(133, "Corpseplant", "Mr. Kenny", new ItemStack(Items.rotten_flesh, 1), new ItemStack[] { ItemList.Dye_Bonemeal.get(1L, new Object[0]), ItemList.Dye_Bonemeal.get(1L, new Object[0]), new ItemStack(Items.bone, 1) }, null, 5, 4, 0, 1, 4, 0, 2, 1, 0, 3, new String[] { "Toxic", "Undead", "Vine", "Edible", "Rotten" });
/* 32:28 */       new GT_BaseCrop(134, "Creeperweed", "General Spaz", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gunpowder, 1L), null, null, 7, 4, 0, 1, 4, 3, 0, 5, 1, 3, new String[] { "Creeper", "Vine", "Explosive", "Fire", "Sulfur", "Saltpeter", "Coal" });
/* 33:29 */       new GT_BaseCrop(135, "Enderbloom", "RichardG", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EnderPearl, 1L), new ItemStack[] { new ItemStack(Items.ender_pearl, 1), new ItemStack(Items.ender_pearl, 1), new ItemStack(Items.ender_eye, 1) }, null, 10, 4, 0, 1, 4, 5, 0, 2, 1, 6, new String[] { "Ender", "Flower", "Shiny" });
/* 34:30 */       new GT_BaseCrop(136, "Meatrose", "VintageBeef", new ItemStack(Items.dye, 1, 9), new ItemStack[] { new ItemStack(Items.beef, 1), new ItemStack(Items.porkchop, 1), new ItemStack(Items.chicken, 1), new ItemStack(Items.fish, 1) }, null, 7, 4, 1500, 1, 4, 0, 4, 1, 3, 0, new String[] { "Edible", "Flower", "Cow", "Fish", "Chicken", "Pig" });
/* 35:31 */       new GT_BaseCrop(137, "Milkwart", "Mr. Brain", ItemList.Crop_Drop_MilkWart.get(1L, new Object[0]), null, ItemList.Crop_Drop_MilkWart.get(4L, new Object[0]), 6, 3, 900, 1, 3, 0, 3, 0, 1, 0, new String[] { "Edible", "Milk", "Cow" });
/* 36:32 */       new GT_BaseCrop(138, "Slimeplant", "Neowulf", new ItemStack(Items.slime_ball, 1), null, null, 6, 4, 0, 3, 4, 3, 0, 0, 0, 2, new String[] { "Slime", "Bouncy", "Sticky", "Bush" });
/* 37:33 */       new GT_BaseCrop(139, "Spidernip", "Mr. Kenny", new ItemStack(Items.string, 1), new ItemStack[] { new ItemStack(Items.spider_eye, 1), new ItemStack(Blocks.web, 1) }, null, 4, 4, 600, 1, 4, 2, 1, 4, 1, 3, new String[] { "Toxic", "Silk", "Spider", "Flower", "Ingredient", "Addictive" });
/* 38:34 */       new GT_BaseCrop(140, "Tearstalks", "Neowulf", new ItemStack(Items.ghast_tear, 1), null, null, 8, 4, 0, 1, 4, 1, 2, 0, 0, 0, new String[] { "Healing", "Nether", "Ingredient", "Reed", "Ghast" });
/* 39:35 */       new GT_BaseCrop(141, "Tine", "Gregorius Techneticies", ItemList.Crop_Drop_Tine.get(1L, new Object[0]), null, null, 5, 3, 0, 2, 3, 2, 0, 3, 0, 0, new String[] { "Shiny", "Metal", "Pine", "Tin", "Bush" });
/* 40:36 */       new GT_BaseCrop(142, "Coppon", "Mr. Brain", ItemList.Crop_Drop_Coppon.get(1L, new Object[0]), null, null, 6, 3, 0, 2, 3, 2, 0, 1, 1, 1, new String[] { "Shiny", "Metal", "Cotton", "Copper", "Bush" });
/* 41:37 */       new GT_BaseCrop(143, "Brown Mushrooms", "Mr. Brain", new ItemStack(Blocks.brown_mushroom, 1), null, new ItemStack(Blocks.brown_mushroom, 4), 1, 3, 0, 1, 3, 0, 2, 0, 0, 2, new String[] { "Edible", "Mushroom", "Ingredient" });
/* 42:38 */       new GT_BaseCrop(144, "Red Mushrooms", "Mr. Kenny", new ItemStack(Blocks.red_mushroom, 1), null, new ItemStack(Blocks.red_mushroom, 4), 1, 3, 0, 1, 3, 0, 1, 3, 0, 2, new String[] { "Toxic", "Mushroom", "Ingredient" });
/* 43:39 */       new GT_BaseCrop(145, "Argentia", "Eloraam", ItemList.Crop_Drop_Argentia.get(1L, new Object[0]), null, null, 7, 4, 0, 3, 4, 2, 0, 1, 0, 0, new String[] { "Shiny", "Metal", "Silver", "Reed" });
/* 44:40 */       new GT_BaseCrop(146, "Plumbilia", "KingLemming", ItemList.Crop_Drop_Plumbilia.get(1L, new Object[0]), null, null, 6, 4, 0, 3, 4, 2, 0, 3, 1, 1, new String[] { "Heavy", "Metal", "Lead", "Reed" });
/* 45:41 */       new GT_BaseCrop(147, "Steeleafranks", "Benimatic", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steeleaf, 1L), new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steeleaf, 1L) }, null, 10, 4, 0, 1, 4, 3, 0, 7, 2, 8, new String[] { "Metal", "Vine", "Iron" });
/* 46:42 */       new GT_BaseCrop(148, "Liveroots", "Benimatic", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.LiveRoot, 1L), new ItemStack[] { ItemList.TF_LiveRoot.get(1L, new Object[0]) }, null, 8, 4, 0, 1, 4, 2, 0, 5, 2, 6, new String[] { "Wood", "Vine" });
/* 47:   */     }
/* 48:   */     catch (Throwable e)
/* 49:   */     {
/* 50:44 */       GT_Log.err.println("GT_Mod: Failed to register Crops to IC2.");
/* 51:45 */       e.printStackTrace(GT_Log.err);
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_CropLoader
 * JD-Core Version:    0.7.0.1
 */