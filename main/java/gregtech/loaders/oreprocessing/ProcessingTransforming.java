/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ProcessingTransforming
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingTransforming()
/* 16:   */   {
/* 17:17 */     for (OrePrefixes tPrefix : OrePrefixes.values()) {
/* 18:17 */       if (((tPrefix.mMaterialAmount > 0L) && (!tPrefix.mIsContainer) && (!tPrefix.mIsEnchantable)) || (tPrefix == OrePrefixes.plank)) {
/* 19:17 */         tPrefix.add(this);
/* 20:   */       }
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:22 */     if (aPrefix == OrePrefixes.plank) {
/* 27:22 */       aPrefix = OrePrefixes.plate;
/* 28:   */     }
/* 29:23 */     switch (aMaterial.ordinal())
/* 30:   */     {
/* 31:   */     case 1: 
/* 32:25 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SeedOil.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 120L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 33:26 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SeedOilLin.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 34:27 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SeedOilHemp.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 80L, true)), GT_OreDictUnificator.get(aPrefix, Materials.WoodSealed, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 35:28 */       break;
/* 36:   */     case 2: 
/* 37:30 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 250L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 38:31 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.IronMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
/* 39:32 */       break;
/* 40:   */     case 3: 
/* 41:34 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 225L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 42:35 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.IronMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
/* 43:36 */       break;
/* 44:   */     case 4: 
/* 45:38 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.FierySteel.getFluid(GT_Utility.translateMaterialToAmount(aPrefix.mMaterialAmount, 200L, true)), GT_OreDictUnificator.get(aPrefix, Materials.FierySteel, 1L), GT_Values.NI, GT_Values.NI, null, 100, 8);
/* 46:39 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.SteelMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 16);
/* 47:40 */       break;
/* 48:   */     case 5: 
/* 49:42 */       GT_Values.RA.addPolarizerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(aPrefix, Materials.NeodymiumMagnetic, 1L), (int)Math.max(16L, aPrefix.mMaterialAmount * 128L / 3628800L), 256);
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingTransforming
 * JD-Core Version:    0.7.0.1
 */