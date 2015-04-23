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
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ 
/* 16:   */ public class ProcessingDustTiny
/* 17:   */   implements IOreRecipeRegistrator
/* 18:   */ {
/* 19:   */   public ProcessingDustTiny()
/* 20:   */   {
/* 21:18 */     OrePrefixes.dustTiny.add(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:23 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Schematic_Dust.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 100, 4);
/* 27:24 */     if (!aMaterial.mBlastFurnaceRequired)
/* 28:   */     {
/* 29:25 */       GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
/* 30:26 */       if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
/* 31:26 */         GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }), aMaterial, aPrefix.mMaterialAmount, null, null, null);
/* 32:   */       }
/* 33:   */     }
/* 34:29 */     if (!aMaterial.contains(SubTag.NO_SMELTING)) {
/* 35:30 */       if (aMaterial.mBlastFurnaceRequired)
/* 36:   */       {
/* 37:31 */         GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), null, (int)Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
/* 38:32 */         GT_ModHandler.removeFurnaceSmelting(aStack);
/* 39:   */       }
/* 40:   */       else
/* 41:   */       {
/* 42:34 */         GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.mSmeltInto, 1L));
/* 43:35 */         GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 130, 3, true);
/* 44:   */       }
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingDustTiny
 * JD-Core Version:    0.7.0.1
 */