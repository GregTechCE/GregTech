/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import gregtech.api.util.GT_RecipeRegistrator;
/* 12:   */ import gregtech.api.util.GT_Utility;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class ProcessingDustSmall
/* 16:   */   implements IOreRecipeRegistrator
/* 17:   */ {
/* 18:   */   public ProcessingDustSmall()
/* 19:   */   {
/* 20:17 */     OrePrefixes.dustSmall.add(this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 24:   */   {
/* 25:22 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), ItemList.Schematic_Dust.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 100, 4);
/* 26:23 */     if (!aMaterial.mBlastFurnaceRequired)
/* 27:   */     {
/* 28:24 */       GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
/* 29:25 */       if (aMaterial.mSmeltInto.mArcSmeltInto != aMaterial) {
/* 30:25 */         GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, new Object[] { aStack }), aMaterial, aPrefix.mMaterialAmount, null, null, null);
/* 31:   */       }
/* 32:   */     }
/* 33:28 */     if (aMaterial.mBlastFurnaceRequired) {
/* 34:29 */       GT_Values.RA.addBlastRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), null, null, null, aMaterial.mBlastFurnaceTemp > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.mSmeltInto, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), null, (int)Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.mBlastFurnaceTemp, 120, aMaterial.mBlastFurnaceTemp);
/* 35:   */     } else {
/* 36:31 */       GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 130, 3, true);
/* 37:   */     }
/* 38:   */   }
/* 39:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingDustSmall
 * JD-Core Version:    0.7.0.1
 */