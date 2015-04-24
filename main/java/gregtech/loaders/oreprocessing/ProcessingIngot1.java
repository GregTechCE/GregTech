/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.ItemList;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.enums.SubTag;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_RecipeRegistrator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingIngot1 implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingIngot1()
/*    */   {
/* 19 */     OrePrefixes.ingot.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 24 */     if (aMaterial.mFuelPower > 0) { GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower, aMaterial.mFuelType);
/*    */     }
/* 26 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), 100, 8);
/* 27 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
/*    */     
/* 29 */     if (aMaterial.mStandardMoltenFluid != null) GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), aMaterial.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), 32, 8);
/* 30 */     GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
/* 31 */     GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
/* 32 */     if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) { GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }), aMaterial, aPrefix.mMaterialAmount, null, null, null);
/*    */     }
/* 34 */     if (!aMaterial.contains(SubTag.NO_SMASHING)) {
/* 35 */       GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.copy(new Object[] { GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 2L), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 8L) }), 100, 4);
/* 36 */       GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int)Math.max(aMaterial.getMass(), 1L), 16);
/* 37 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 1L, 1L), 24);
/* 38 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 2L, 1L), 96);
/* 39 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 3L, 1L), 96);
/* 40 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 4L, 1L), 96);
/* 41 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 5L, 1L), 96);
/* 42 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 9L, 1L), 96);
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 47 */     if (!OrePrefixes.block.isIgnored(aMaterial)) GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L));
/* 48 */     if (!aMaterial.contains(SubTag.NO_WORKING)) GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial.mMacerateInto, 2L), (int)Math.max(aMaterial.getMass() * 5L, 1L), 16);
/* 49 */     if (!aMaterial.contains(SubTag.NO_SMELTING)) {
/* 50 */       GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Mold_Nugget.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial, 9L), 100, 1);
/* 51 */       if ((GT_ModHandler.getSmeltingOutput(aStack, false, null) == null) && (GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L) != null) && (!GT_ModHandler.addSmeltingRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 9L)))) GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 9L), new Object[] { aOreDictName }); }
/*    */     ItemStack tStack;
/* 53 */     if ((null != (tStack = GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L))) && (
/* 54 */       (aMaterial.mBlastFurnaceRequired) || (aMaterial.contains(SubTag.NO_SMELTING)))) { GT_ModHandler.removeFurnaceSmelting(tStack);
/*    */     }
/*    */     
/* 57 */     GT_RecipeRegistrator.registerUsagesForMaterials(GT_Utility.copyAmount(1L, new Object[] { aStack }), OrePrefixes.plate.get(aMaterial).toString(), !aMaterial.contains(SubTag.NO_SMASHING));
/*    */     
/* 59 */     if (aMaterial == Materials.Mercury) {
/* 60 */       System.err.println("Quicksilver Ingots?, Don't tell me there is an Armor made of that highly toxic and very likely to be melting Material!");
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingIngot1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */