/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.enums.SubTag;
/*  8:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  9:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 10:   */ import gregtech.api.util.GT_ModHandler;
/* 11:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 12:   */ import gregtech.api.util.GT_RecipeRegistrator;
/* 13:   */ import gregtech.api.util.GT_Utility;
/* 14:   */ import java.io.PrintStream;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ 
/* 17:   */ public class ProcessingIngot1
/* 18:   */   implements IOreRecipeRegistrator
/* 19:   */ {
/* 20:   */   public ProcessingIngot1()
/* 21:   */   {
/* 22:19 */     OrePrefixes.ingot.add(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 26:   */   {
/* 27:24 */     if (aMaterial.mFuelPower > 0) {
/* 28:24 */       GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower, aMaterial.mFuelType);
/* 29:   */     }
/* 30:26 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), 100, 8);
/* 31:27 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
/* 32:29 */     if (aMaterial.mStandardMoltenFluid != null) {
/* 33:29 */       GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), aMaterial.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), 32, 8);
/* 34:   */     }
/* 35:30 */     GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
/* 36:31 */     GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
/* 37:32 */     if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
/* 38:32 */       GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }), aMaterial, aPrefix.mMaterialAmount, null, null, null);
/* 39:   */     }
/* 40:34 */     if (!aMaterial.contains(SubTag.NO_SMASHING))
/* 41:   */     {
/* 42:35 */       GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.copy(new Object[] { GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 2L), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 8L) }), 100, 4);
/* 43:36 */       GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int)Math.max(aMaterial.getMass(), 1L), 16);
/* 44:37 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 1L, 1L), 24);
/* 45:38 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 2L, 1L), 96);
/* 46:39 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 3L, 1L), 96);
/* 47:40 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 4L, 1L), 96);
/* 48:41 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 5L, 1L), 96);
/* 49:42 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 9L, 1L), 96);
/* 50:   */     }
/* 51:47 */     if (!OrePrefixes.block.isIgnored(aMaterial)) {
/* 52:47 */       GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));
/* 53:   */     }
/* 54:48 */     if (!aMaterial.contains(SubTag.NO_WORKING)) {
/* 55:48 */       GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial.mMacerateInto, 2L), (int)Math.max(aMaterial.getMass() * 5L, 1L), 16);
/* 56:   */     }
/* 57:49 */     if (!aMaterial.contains(SubTag.NO_SMELTING))
/* 58:   */     {
/* 59:50 */       GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Mold_Nugget.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial, 9L), 100, 1);
/* 60:51 */       if ((GT_ModHandler.getSmeltingOutput(aStack, false, null) == null) && (GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L) != null) && (!GT_ModHandler.addSmeltingRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 9L)))) {
/* 61:51 */         GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 9L), new Object[] { aOreDictName });
/* 62:   */       }
/* 63:   */     }
/* 64:   */     ItemStack tStack;
/* 65:53 */     if ((null != (tStack = GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L))) && (
/* 66:54 */       (aMaterial.mBlastFurnaceRequired) || (aMaterial.contains(SubTag.NO_SMELTING)))) {
/* 67:54 */       GT_ModHandler.removeFurnaceSmelting(tStack);
/* 68:   */     }
/* 69:57 */     GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, new Object[] { aStack }), OrePrefixes.plate.get(aMaterial).toString(), !aMaterial.contains(SubTag.NO_SMASHING));
/* 70:59 */     if (aMaterial == Materials.Mercury) {
/* 71:60 */       System.err.println("Quicksilver Ingots?, Don't tell me there is an Armor made of that highly toxic and very likely to be melting Material!");
/* 72:   */     }
/* 73:   */   }
/* 74:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingIngot1
 * JD-Core Version:    0.7.0.1
 */