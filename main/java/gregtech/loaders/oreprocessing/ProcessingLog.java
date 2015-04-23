/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
/*  4:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  5:   */ import gregtech.api.enums.ConfigCategories.Recipes;
/*  6:   */ import gregtech.api.enums.GT_Values;
/*  7:   */ import gregtech.api.enums.ItemList;
/*  8:   */ import gregtech.api.enums.Materials;
/*  9:   */ import gregtech.api.enums.OrePrefixes;
/* 10:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/* 11:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 12:   */ import gregtech.api.util.GT_Config;
/* 13:   */ import gregtech.api.util.GT_ModHandler;
/* 14:   */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/* 15:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 16:   */ import gregtech.api.util.GT_Utility;
/* 17:   */ import gregtech.common.GT_Proxy;
/* 18:   */ import net.minecraft.init.Items;
/* 19:   */ import net.minecraft.item.ItemStack;
/* 20:   */ 
/* 21:   */ public class ProcessingLog
/* 22:   */   implements IOreRecipeRegistrator
/* 23:   */ {
/* 24:   */   public ProcessingLog()
/* 25:   */   {
/* 26:23 */     OrePrefixes.log.add(this);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 30:   */   {
/* 31:28 */     if (aOreDictName.equals("logRubber"))
/* 32:   */     {
/* 33:29 */       GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, Materials.Methane.getGas(60L), ItemList.IC2_Resin.get(1L, new Object[0]), GT_ModHandler.getIC2Item("plantBall", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), null, null, new int[] { 5000, 3750, 2500, 2500 }, 200, 20);
/* 34:30 */       GT_ModHandler.addSawmillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.IC2_Resin.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 16L));
/* 35:31 */       GT_ModHandler.addExtractionRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Rubber, 1L));
/* 36:32 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 6L), ItemList.IC2_Resin.get(1L, new Object[0]), 33, false);
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:34 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 6L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 80, false);
/* 41:   */     }
/* 42:37 */     GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 2L), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "sLf", Character.valueOf('L'), GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 43:38 */     GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 160, 8);
/* 44:39 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), Materials.SeedOil.getFluid(50L), ItemList.FR_Stick.get(1L, new Object[0]), 16, 8);
/* 45:40 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), Materials.SeedOil.getFluid(250L), ItemList.FR_Casing_Impregnated.get(1L, new Object[0]), 64, 16);
/* 46:41 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Creosote.getFluid(1000L), GT_ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1L, 8), null, null, null, 16, 16);
/* 47:   */     
/* 48:43 */     int aMeta = aStack.getItemDamage();
/* 49:45 */     if (aMeta == 32767)
/* 50:   */     {
/* 51:46 */       if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[] { aStack }), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 52:47 */         (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) {
/* 53:47 */         GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }));
/* 54:   */       }
/* 55:49 */       for (int i = 0; i < 16; i++)
/* 56:   */       {
/* 57:50 */         if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(new ItemStack(aStack.getItem(), 1, i), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 58:51 */           (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) {
/* 59:51 */           GT_ModHandler.removeFurnaceSmelting(new ItemStack(aStack.getItem(), 1, i));
/* 60:   */         }
/* 61:53 */         ItemStack tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(aStack.getItem(), 1, i) });
/* 62:54 */         if (tStack != null)
/* 63:   */         {
/* 64:55 */           ItemStack tPlanks = GT_Utility.copy(new Object[] { tStack });
/* 65:56 */           tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
/* 66:57 */           GT_Values.RA.addCutterRecipe(new ItemStack(aStack.getItem(), 1, i), Materials.Lubricant.getFluid(1L), GT_Utility.copy(new Object[] { tPlanks }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 200, 8);
/* 67:58 */           GT_Values.RA.addCutterRecipe(new ItemStack(aStack.getItem(), 1, i), GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 200, 8);
/* 68:59 */           GT_ModHandler.addSawmillRecipe(new ItemStack(aStack.getItem(), 1, i), tPlanks, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
/* 69:60 */           GT_ModHandler.removeRecipe(new ItemStack[] { new ItemStack(aStack.getItem(), 1, i) });
/* 70:61 */           GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), new Object[] { "s", "L", Character.valueOf('L'), new ItemStack(aStack.getItem(), 1, i) });
/* 71:62 */           GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(tStack.stackSize / (GT_Mod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), new Object[] { tStack }), new Object[] { new ItemStack(aStack.getItem(), 1, i) });
/* 72:   */         }
/* 73:   */       }
/* 74:   */     }
/* 75:   */     else
/* 76:   */     {
/* 77:66 */       if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[] { aStack }), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 78:67 */         (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) {
/* 79:67 */         GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }));
/* 80:   */       }
/* 81:69 */       ItemStack tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 82:70 */       if (tStack != null)
/* 83:   */       {
/* 84:71 */         ItemStack tPlanks = GT_Utility.copy(new Object[] { tStack });
/* 85:72 */         tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
/* 86:73 */         GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Lubricant.getFluid(1L), GT_Utility.copy(new Object[] { tPlanks }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 200, 8);
/* 87:74 */         GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 200, 8);
/* 88:75 */         GT_ModHandler.addSawmillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), tPlanks, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
/* 89:76 */         GT_ModHandler.removeRecipe(new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 90:77 */         GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), new Object[] { "s", "L", Character.valueOf('L'), GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 91:78 */         GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(tStack.stackSize / (GT_Mod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), new Object[] { tStack }), new Object[] { GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 92:   */       }
/* 93:   */     }
/* 94:82 */     if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[] { aStack }), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 95:83 */       (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) {
/* 96:83 */       GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }));
/* 97:   */     }
/* 98:   */   }
/* 99:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingLog
 * JD-Core Version:    0.7.0.1
 */