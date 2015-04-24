/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.GT_Mod;
/*    */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*    */ import gregtech.api.enums.ConfigCategories.Recipes;
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.ItemList;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_Config;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import gregtech.common.GT_Proxy;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingLog implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingLog()
/*    */   {
/* 23 */     OrePrefixes.log.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 28 */     if (aOreDictName.equals("logRubber")) {
/* 29 */       GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, Materials.Methane.getGas(60L), ItemList.IC2_Resin.get(1L, new Object[0]), GT_ModHandler.getIC2Item("plantBall", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), null, null, new int[] { 5000, 3750, 2500, 2500 }, 200, 20);
/* 30 */       GT_ModHandler.addSawmillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.IC2_Resin.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 16L));
/* 31 */       GT_ModHandler.addExtractionRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Rubber, 1L));
/* 32 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 6L), ItemList.IC2_Resin.get(1L, new Object[0]), 33, false);
/*    */     } else {
/* 34 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 6L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 80, false);
/*    */     }
/*    */     
/* 37 */     GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 2L), gregtech.api.util.GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | gregtech.api.util.GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "sLf", Character.valueOf('L'), GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 38 */     GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 160, 8);
/* 39 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), Materials.SeedOil.getFluid(50L), ItemList.FR_Stick.get(1L, new Object[0]), 16, 8);
/* 40 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), Materials.SeedOil.getFluid(250L), ItemList.FR_Casing_Impregnated.get(1L, new Object[0]), 64, 16);
/* 41 */     GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Creosote.getFluid(1000L), GT_ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1L, 8), null, null, null, 16, 16);
/*    */     
/* 43 */     int aMeta = aStack.getItemDamage();
/*    */     
/* 45 */     if (aMeta == 32767) {
/* 46 */       if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[] { aStack }), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 47 */         (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) { GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }));
/*    */       }
/* 49 */       for (int i = 0; i < 16; i++) {
/* 50 */         if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(new ItemStack(aStack.getItem(), 1, i), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 51 */           (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) { GT_ModHandler.removeFurnaceSmelting(new ItemStack(aStack.getItem(), 1, i));
/*    */         }
/* 53 */         ItemStack tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] { new ItemStack(aStack.getItem(), 1, i) });
/* 54 */         if (tStack != null) {
/* 55 */           ItemStack tPlanks = GT_Utility.copy(new Object[] { tStack });
/* 56 */           tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
/* 57 */           GT_Values.RA.addCutterRecipe(new ItemStack(aStack.getItem(), 1, i), Materials.Lubricant.getFluid(1L), GT_Utility.copy(new Object[] { tPlanks }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 200, 8);
/* 58 */           GT_Values.RA.addCutterRecipe(new ItemStack(aStack.getItem(), 1, i), GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 200, 8);
/* 59 */           GT_ModHandler.addSawmillRecipe(new ItemStack(aStack.getItem(), 1, i), tPlanks, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
/* 60 */           GT_ModHandler.removeRecipe(new ItemStack[] { new ItemStack(aStack.getItem(), 1, i) });
/* 61 */           GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), new Object[] { "s", "L", Character.valueOf('L'), new ItemStack(aStack.getItem(), 1, i) });
/* 62 */           GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(tStack.stackSize / (GT_Mod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), new Object[] { tStack }), new Object[] { new ItemStack(aStack.getItem(), 1, i) });
/*    */         }
/*    */       }
/*    */     } else {
/* 66 */       if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[] { aStack }), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 67 */         (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) { GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }));
/*    */       }
/* 69 */       ItemStack tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 70 */       if (tStack != null) {
/* 71 */         ItemStack tPlanks = GT_Utility.copy(new Object[] { tStack });
/* 72 */         tPlanks.stackSize = (tPlanks.stackSize * 3 / 2);
/* 73 */         GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Lubricant.getFluid(1L), GT_Utility.copy(new Object[] { tPlanks }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), 200, 8);
/* 74 */         GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), 200, 8);
/* 75 */         GT_ModHandler.addSawmillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), tPlanks, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
/* 76 */         GT_ModHandler.removeRecipe(new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 77 */         GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[] { tStack }), new Object[] { "s", "L", Character.valueOf('L'), GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 78 */         GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(tStack.stackSize / (GT_Mod.gregtechproxy.mNerfedWoodPlank ? 2 : 1), new Object[] { tStack }), new Object[] { GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/*    */       }
/*    */     }
/*    */     
/* 82 */     if ((GT_Utility.areStacksEqual(GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(1L, new Object[] { aStack }), false, null), new ItemStack(Items.coal, 1, 1))) && 
/* 83 */       (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "wood2charcoalsmelting", false))) GT_ModHandler.removeFurnaceSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }));
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */