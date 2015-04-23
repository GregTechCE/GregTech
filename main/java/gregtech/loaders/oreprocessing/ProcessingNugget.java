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
/* 16:   */ public class ProcessingNugget
/* 17:   */   implements IOreRecipeRegistrator
/* 18:   */ {
/* 19:   */   public ProcessingNugget()
/* 20:   */   {
/* 21:19 */     OrePrefixes.nugget.add(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:24 */     if (aMaterial == Materials.Iron) {
/* 27:24 */       GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.WroughtIron, 1L));
/* 28:   */     }
/* 29:25 */     if (!aMaterial.contains(SubTag.NO_WORKING)) {
/* 30:25 */       GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.round, aMaterial, 1L), null, (int)Math.max(aMaterial.getMass() / 4L, 1L), 8);
/* 31:   */     }
/* 32:26 */     GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), aMaterial.contains(SubTag.SMELTING_TO_GEM) ? ItemList.Shape_Mold_Ball.get(0L, new Object[0]) : ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefixes.gem : OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 200, 2);
/* 33:27 */     if (aMaterial.mStandardMoltenFluid != null) {
/* 34:27 */       GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Nugget.get(0L, new Object[0]), aMaterial.getMolten(16L), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial, 1L), 16, 4);
/* 35:   */     }
/* 36:28 */     GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
/* 37:29 */     GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
/* 38:   */   }
/* 39:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingNugget
 * JD-Core Version:    0.7.0.1
 */