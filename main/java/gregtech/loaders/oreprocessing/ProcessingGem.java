/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ConfigCategories.Recipes;
/*  5:   */ import gregtech.api.enums.GT_Values;
/*  6:   */ import gregtech.api.enums.ItemList;
/*  7:   */ import gregtech.api.enums.Materials;
/*  8:   */ import gregtech.api.enums.OrePrefixes;
/*  9:   */ import gregtech.api.enums.SubTag;
/* 10:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/* 11:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 12:   */ import gregtech.api.util.GT_Config;
/* 13:   */ import gregtech.api.util.GT_ModHandler;
/* 14:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 15:   */ import gregtech.api.util.GT_RecipeRegistrator;
/* 16:   */ import gregtech.api.util.GT_Utility;
/* 17:   */ import net.minecraft.init.Items;
/* 18:   */ import net.minecraft.item.ItemStack;
/* 19:   */ 
/* 20:   */ public class ProcessingGem
/* 21:   */   implements IOreRecipeRegistrator
/* 22:   */ {
/* 23:   */   public ProcessingGem()
/* 24:   */   {
/* 25:18 */     OrePrefixes.gem.add(this);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 29:   */   {
/* 30:23 */     if (aMaterial.mFuelPower > 0) {
/* 31:23 */       GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower * 2, aMaterial.mFuelType);
/* 32:   */     }
/* 33:25 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtGem, aMaterial, 1L), 100, 8);
/* 34:26 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtGem, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
/* 35:28 */     if (!OrePrefixes.block.isIgnored(aMaterial)) {
/* 36:28 */       GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));
/* 37:   */     }
/* 38:29 */     if (!aMaterial.contains(SubTag.NO_SMELTING)) {
/* 39:29 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L));
/* 40:   */     }
/* 41:31 */     if (aMaterial.contains(SubTag.NO_SMASHING))
/* 42:   */     {
/* 43:32 */       GT_Values.RA.addForgeHammerRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, 2L), 64, 16);
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:34 */       GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int)Math.max(aMaterial.getMass(), 1L), 16);
/* 48:35 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 2L, 1L), 24);
/* 49:36 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 2L, 1L), 96);
/* 50:37 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 3L, 1L), 96);
/* 51:38 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 4L, 1L), 96);
/* 52:39 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 5L, 1L), 96);
/* 53:40 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 9L, 1L), 96);
/* 54:   */     }
/* 55:43 */     if (!aMaterial.contains(SubTag.NO_WORKING)) {
/* 56:43 */       GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 2L), (int)Math.max(aMaterial.getMass(), 1L), 16);
/* 57:   */     }
/* 58:45 */     GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, new Object[] { aStack }), OrePrefixes.plate.get(aMaterial).toString(), !aMaterial.contains(SubTag.NO_SMASHING));
/* 59:47 */     switch (aMaterial.ordinal())
/* 60:   */     {
/* 61:   */     case 1: 
/* 62:   */       break;
/* 63:   */     case 2: 
/* 64:   */     case 3: 
/* 65:50 */       if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
/* 66:50 */         GT_ModHandler.removeRecipe(new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, new ItemStack(Items.stick, 1, 0) });
/* 67:   */       }
/* 68:   */       break;
/* 69:   */     case 4: 
/* 70:53 */       GT_Values.RA.addElectrolyzerRecipe(aStack, 0, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), null, null, null, null, null, 2000, 30);
/* 71:   */     }
/* 72:   */   }
/* 73:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingGem
 * JD-Core Version:    0.7.0.1
 */