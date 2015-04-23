/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.enums.SubTag;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class ProcessingDirty
/* 15:   */   implements IOreRecipeRegistrator
/* 16:   */ {
/* 17:   */   public ProcessingDirty()
/* 18:   */   {
/* 19:16 */     OrePrefixes.clump.add(this);
/* 20:17 */     OrePrefixes.shard.add(this);
/* 21:18 */     OrePrefixes.crushed.add(this);
/* 22:19 */     OrePrefixes.dirtyGravel.add(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 26:   */   {
/* 27:24 */     GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1L), 10, 16);
/* 28:25 */     GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, GT_Utility.selectItemInList(0, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), 10, false);
/* 29:26 */     GT_ModHandler.addOreWasherRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), 1000, new Object[] { GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(0, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L) });
/* 30:27 */     GT_ModHandler.addThermalCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), (int)Math.min(5000L, Math.abs(aMaterial.getMass() * 20L)), new Object[] { GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedCentrifuged : OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, GT_Utility.selectItemInList(1, aMaterial.mMacerateInto, aMaterial.mOreByProducts), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L) });
/* 31:29 */     if (aMaterial.contains(SubTag.WASHING_MERCURY)) {
/* 32:29 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Mercury.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[] { 10000, 7000, 4000 }, 800, 8);
/* 33:   */     }
/* 34:30 */     if (aMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE)) {
/* 35:30 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SodiumPersulfate.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[] { 10000, 7000, 4000 }, 800, 8);
/* 36:   */     }
/* 37:31 */     for (Materials tMaterial : aMaterial.mOreByProducts)
/* 38:   */     {
/* 39:32 */       if (tMaterial.contains(SubTag.WASHING_MERCURY)) {
/* 40:32 */         GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Mercury.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[] { 10000, 7000, 4000 }, 800, 8);
/* 41:   */       }
/* 42:33 */       if (tMaterial.contains(SubTag.WASHING_SODIUMPERSULFATE)) {
/* 43:33 */         GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.SodiumPersulfate.getFluid(1000L), GT_OreDictUnificator.get(aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified : OrePrefixes.dustPure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial.mMacerateInto, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new int[] { 10000, 7000, 4000 }, 800, 8);
/* 44:   */       }
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingDirty
 * JD-Core Version:    0.7.0.1
 */