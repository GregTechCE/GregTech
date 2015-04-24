/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingTransforming
/*    */   implements IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingTransforming()
/*    */   {
/* 17 */     for (OrePrefixes tPrefix : OrePrefixes.values()) if (((tPrefix.mMaterialAmount > 0L) && (!tPrefix.mIsContainer) && (!tPrefix.mIsEnchantable)) || (tPrefix == OrePrefixes.plank)) tPrefix.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 22 */     if (aPrefix == OrePrefixes.plank) aPrefix = OrePrefixes.plate;
/* 23 */     switch (aMaterial) {
/*    */     case Wood: 
/* 25 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SeedOil.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 120L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 26 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SeedOilLin.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 27 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SeedOilHemp.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 28 */       break;
/*    */     case Iron: 
/* 30 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 250L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 31 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.IronMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
/* 32 */       break;
/*    */     case WroughtIron: 
/* 34 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 225L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 35 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.IronMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
/* 36 */       break;
/*    */     case Steel: 
/* 38 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 200L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 39 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.SteelMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
/* 40 */       break;
/*    */     case Neodymium: 
/* 42 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.NeodymiumMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 256);
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingTransforming.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */